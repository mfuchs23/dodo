package org.dbdoclet.tidbit.medium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractAction;

import org.dbdoclet.Sfv;
import org.dbdoclet.antol.AntFileReader;
import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.antol.ant.Property;
import org.dbdoclet.antol.ant.Target;
import org.dbdoclet.antol.ant.TargetItem;
import org.dbdoclet.io.FileSet;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.FileSetServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.common.MediumTargetParams;
import org.dbdoclet.tidbit.common.Visibility;
import org.w3c.dom.Element;

public abstract class AbstractMediumService implements MediumService {

	protected String category;

	public int compareTo(MediumService other) {
		return getName().compareTo(other.getName());
	}

	public AbstractAction getAction(Context Context) {
		return null;
	}

	protected void createDbdocletTask(AntFileWriter writer, Element target,
			MediumTargetParams javadocParams,
			HashMap<String, String> dbdocletParamMap) throws IOException {

		String str;
		String path;
		Element javadoc;

		writer.addDelete(target,
				"${dbdoclet.destination.path}/${dbdoclet.media}");

		javadoc = writer.addJavadoc(target);
		javadoc.setAttribute("useexternalfile", "true");

		path = javadocParams.getOverview();

		if ((path != null) && (path.length() > 0)) {

			str = path.toLowerCase();

			if (str.endsWith(".html") || str.endsWith(".htm")) {
				javadoc.setAttribute("overview", "${dbdoclet.overview.file}");
			}
		}

		str = javadocParams.getSourceEncoding();

		if ((str != null) && (str.length() > 0)) {
			javadoc.setAttribute("encoding", str);
		}

		if (javadocParams.isLinkSourceEnabled()) {
			javadoc.setAttribute("linksource", "yes");
		}

		Visibility visibility = javadocParams.getVisibility();

		if ((str != null) && (str.length() > 0)) {
			javadoc.setAttribute("access", visibility.getValue());
		}

		str = javadocParams.getSource();

		if ((str != null) && (str.length() > 0) && (str.equals("---") == false)) {
			javadoc.setAttribute("source", str);
		}

		str = javadocParams.getMemory();

		if ((str != null) && (str.length() > 0)) {
			javadoc.setAttribute("maxmemory", str + "m");
		}

		ArrayList<FileSet> list = javadocParams.getSourcepath();
		FileSetServices.createAntFileSets(writer.getDocument(), javadoc, list);

		writer.addClasspath(javadoc, "dbdoclet.classpath");

		Element doclet = writer.addDoclet(javadoc,
				"org.dbdoclet.doclet.docbook.DocBookDoclet",
				"${dbdoclet.home}/jars/dbdoclet.jar");

		if (dbdocletParamMap != null) {

			String name;
			String value;

			for (Iterator<String> iterator = dbdocletParamMap.keySet()
					.iterator(); iterator.hasNext();) {

				name = iterator.next();
				value = dbdocletParamMap.get(name);

				writer.addParam(doclet, name, value);
			}
		}

		String docfilesPath = "";

		for (FileSet fileSet : list) {

			String dirName = fileSet.getDirName();
			dirName = FileServices.normalizePath(dirName);

			if (FileServices.isAbsolutePath(dirName)) {
				docfilesPath += dirName;
			} else {
				docfilesPath += "${basedir}/" + dirName;
			}

			docfilesPath += Sfv.PSEP;
		}

		docfilesPath = StringServices.cutSuffix(docfilesPath, Sfv.PSEP);

		// writer.addParam(doclet, "-docfilespath", docfilesPath);
		writer.addParam(doclet, "-d", "${dbdoclet.project.path}");
		writer.addParam(doclet, "-profile", "${basedir}/dbdoclet.conf");
	}

	protected String getProjectProperty(AntFileReader buildFile, String name) {

		if (buildFile == null) {
			return null;
		}

		Target target = buildFile.findTarget("dbdoclet.docbook.eclipse");

		if (target != null) {
			for (TargetItem item : target.getTargetItem()) {

				Property property = item.getProperty();

				if (property != null && name.equals(property.getName())) {
					return property.getValue();
				}
			}
		}

		return null;
	}

	public boolean isReadyForUse() {
		return true;
	}

	protected void saveDocBookXslt(AntFileWriter writer, String media,
			String stylesheet) throws IOException {

		Element target;
		Element antCall;

		target = writer.addTarget("dbdoclet.xslt." + media);

		writer.addMessage(target, "DocBook Transformation(" + media + ")...");
		// writer.addMkDir(target, "${dbdoclet.destination.path}/" + media);

		String xmlFileName = "${in}";

		writer.addComment(target, "DocBook 5 Strip Namespaces");
		antCall = writer.addAntCall(target, "dbdoclet.xslt");
		writer.addParam(antCall, "stylesheet",
				"${dbdoclet.home}/docbook/xsl/common/stripns.xsl");
		writer.addParam(antCall, "in", xmlFileName);
		xmlFileName = FileServices.getFileBase(xmlFileName) + ".db5";
		writer.addParam(antCall, "out", xmlFileName);

		antCall = writer.addAntCall(target, "dbdoclet.xslt");
		writer.addParam(antCall, "stylesheet", "${dbdoclet.project.path}/xsl/"
				+ stylesheet);
		writer.addParam(antCall, "in", xmlFileName);
		writer.addParam(antCall, "out", "${out}");

	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
}
