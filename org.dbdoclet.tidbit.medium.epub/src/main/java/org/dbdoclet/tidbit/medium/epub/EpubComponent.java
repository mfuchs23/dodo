package org.dbdoclet.tidbit.medium.epub;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.medium.AbstractMediumService;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;
import org.w3c.dom.Element;

public class EpubComponent extends AbstractMediumService implements
		MediumService {

	@SuppressWarnings("unchecked")
	protected void activate(ComponentContext context) {

		Dictionary<String, String> dictionary = context.getProperties();
		setCategory(dictionary.get("category"));
	}

	public Generator getGenerator(Settings settings) throws IOException {

		EpubGenerator epubGenerator = new EpubGenerator();
		return epubGenerator;
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/epub.png",
				EpubComponent.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "epub";
	}

	public String getName() {
		return "EPUB";
	}

	public void write(AntFileWriter writer, Project project) throws IOException {

		Element target;

		saveDocBookXslt(writer, "epub", "epub.xsl");

		target = writer.addTarget("dbdoclet.docbook.epub");
		writer.addProperty(target, "dbdoclet.media", "epub");

		Element xsltTarget = writer.addAntCall(target, "dbdoclet.xslt.epub");
		writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
		writer.addParam(xsltTarget, "out", "${dbdoclet.docbook.file.base}.epub");

		writer.addCopy(target, "${dbdoclet.home}/xslt/epub/dbdoclet.css",
				project.getHtmlDestDir(project.getDriver(Output.epub)));

		Element copy = writer.addCopy(target, FileServices.appendPath(
				project.getHtmlDestDir(project.getDriver(Output.epub)),
				"images"));
		writer.addFileset(copy, "${dbdoclet.home}/docbook/xsl/images");
	}
}
