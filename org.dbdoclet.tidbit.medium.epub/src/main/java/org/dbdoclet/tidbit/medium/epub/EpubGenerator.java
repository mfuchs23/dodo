/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.medium.epub;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.dbdoclet.Sfv;
import org.dbdoclet.io.EndsWithFilter;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.service.ZipServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;
import org.dbdoclet.xiphias.NodeSerializer;
import org.dbdoclet.xiphias.XPathServices;
import org.dbdoclet.xiphias.XmlServices;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class EpubGenerator extends Generator {

	public EpubGenerator() throws IOException {

		super();
		setTarget("dbdoclet.docbook.epub");
	}

	@Override
	public void run() {

		File tsFile = null;

		try {

			String path = project.getFileManager().getDocBookFileName();
			File file = new File(path);

			if (file.exists() == false) {

				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_ERROR_FILE_DOESNT_EXIST"), file
						.getAbsolutePath());
				ErrorBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_ERROR"), msg);
				return;
			}

			// tsFile = File.createTempFile("dbd-epub", ".ts");

			ExecResult result = executeAntTarget();

			if (buildFailed(result)) {
				return;
			}

			File baseDir = getBaseDir(project.getDriver(Output.epub));
			String imgDirName = FileServices.appendPath(baseDir, "images");
			File imgDir = new File(imgDirName);
			FileServices.createPath(imgDir);

			String contentFileName = FileServices.appendFileName(baseDir,
					"content.opf");
			File contentFile = new File(contentFileName);

			if (contentFile.exists() == false) {
				return;
			}

			Document contentDoc = XmlServices.loadDocument(contentFile);
			Element manifest = (Element) XPathServices.getNode(contentDoc,
					"opf", Sfv.NS_OPF, "//opf:manifest");

			messageListener.info("Relocating images..");
			for (File htmlFile : baseDir.listFiles(new EndsWithFilter(".html",
					".htm"))) {
				try {
					messageListener.info("Relocating " + htmlFile.getPath()
							+ "...");
					relocateImages(manifest, htmlFile, imgDir);
				} catch (Exception oops) {
					oops.printStackTrace();
				}
			}

			NodeSerializer serializer = new NodeSerializer();
			serializer.write(contentDoc, contentFile);

			messageListener.info("Relocation of images finished.");

			File zipFile = new File(FileServices.appendPath(
					project.getBuildDirectory(), project.getProjectName()
							+ ".epub"));

			File epubDir = baseDir.getParentFile().getCanonicalFile();
			messageListener.info("Packing " + epubDir.getAbsolutePath()
					+ " into zip archive " + zipFile.getAbsolutePath() + "...");
			ZipServices.zip(zipFile, epubDir);

			messageListener.info("Creation of zip archive finished.");

		} catch (Throwable oops) {

			messageListener.fatal("EpubGenerator", oops);

		} finally {

			if (tsFile != null) {
				tsFile.delete();
			}

			finished();
		}
	}

	private void relocateImages(Element manifest, File htmlFile, File imgDir)
			throws SAXException, IOException, ParserConfigurationException {

		AbstractDriver driver = project.getDriver(Output.epub);
		String imgSrcPath = driver.getParameter(Constants.PARAM_IMG_SRC_PATH,
				"");
		File docBookFileDir = new File(project.getFileManager()
				.getDocBookFileDirName());

		Document doc = XmlServices.loadDocument(htmlFile);

		ArrayList<Node> imgNodeList = XPathServices.getNodes(doc, "xhtml",
				Sfv.NS_XHTML, "//xhtml:img");

		for (Node node : imgNodeList) {

			Element img = (Element) node;
			String srcAttr = img.getAttribute("src");

			if (srcAttr == null || srcAttr.trim().length() == 0) {
				continue;
			}

			String toFileName;
			File toFile;

			if (FileServices.isAbsolutePath(srcAttr) == false) {
				toFileName = FileServices.appendFileName(
						htmlFile.getParentFile(), srcAttr);
				toFile = new File(toFileName);
			} else {
				toFileName = FileServices.appendPath(htmlFile.getParentFile(),
						"images");
				toFileName = FileServices.appendFileName(toFileName,
						FileServices.getFileName(srcAttr));
				toFile = new File(toFileName);
				toFile = FileServices.createUniqueFile(toFile);
			}

			if (toFile.exists() == true) {
				continue;
			}

			File fromFile;

			if (FileServices.isAbsolutePath(srcAttr) == false) {

				if (imgSrcPath.trim().length() > 0) {
					srcAttr = FileServices
							.removeParentPath(srcAttr, imgSrcPath);
				}

				String path = FileServices.appendFileName(docBookFileDir,
						srcAttr);
				fromFile = new File(path);

			} else {
				fromFile = new File(srcAttr);
			}

			if (fromFile.exists() == false) {
				continue;
			}

			messageListener.info("Relocating image " + srcAttr + "...");
			FileServices.copyFileToFile(fromFile, toFile);

			String href = FileServices.relativePath(htmlFile, toFile);
			href = FileServices.normalizePath(href);

			if (href.startsWith("./")) {
				href = StringServices.cutPrefix(href, "./");
			}

			img.setAttribute("src", href);

			Element item = (Element) XPathServices.getNode(manifest, "opf",
					Sfv.NS_OPF, "opf:item[@href='" + srcAttr + "']");

			if (item != null) {
				messageListener.info("Relocating opf:item " + srcAttr + "...");
				item.setAttribute("href", href);
			}
		}

		NodeSerializer serializer = new NodeSerializer();
		serializer.write(doc, htmlFile);

	}
}
