package org.dbdoclet.tidbit.medium.javahelp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

public class JavaHelpComponent extends AbstractMediumService implements
		MediumService {

	@SuppressWarnings("unchecked")
	protected void activate(ComponentContext context) {

		Dictionary<String, String> dictionary = context.getProperties();
		setCategory(dictionary.get("category"));
	}

	public Generator getGenerator(Settings settings) {

		JavaHelpGenerator javaHelpGenerator = new JavaHelpGenerator();
		return javaHelpGenerator;
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl(
				"/images/superhero32.gif",
				JavaHelpComponent.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "javahelp";
	}

	private ArrayList<String> getJavaHelpClasspath() {

		ArrayList<String> list = new ArrayList<String>();
		list.add("${dbdoclet.home}jars/jhall.jar");

		return list;
	}

	public String getName() {
		return "JavaHelp";
	}

	public void write(AntFileWriter writer, Project project) throws IOException {

		String str;
		Element target;
		Element java;

		String baseDir = project.getHtmlDestDir(project
				.getDriver(Output.javahelp));

		saveDocBookXslt(writer, "javahelp", "javahelp.xsl");

		target = writer.addTarget("dbdoclet.docbook.javahelp");
		writer.addProperty(target, "dbdoclet.media", "javahelp");

		Element xsltTarget = writer
				.addAntCall(target, "dbdoclet.xslt.javahelp");
		writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
		writer.addParam(xsltTarget, "out",
				"${dbdoclet.docbook.file.base}.javahelp");

		writer.addCopy(target, "${dbdoclet.home}/xslt/javahelp/dbdoclet.css",
				baseDir);

		Element copy = writer.addCopy(target,
				FileServices.appendPath(baseDir, "images"));
		writer.addFileset(copy, "${dbdoclet.home}/docbook/xsl/images");

		String javaHelpSearchDirName = FileServices.appendFileName(baseDir,
				"JavaHelpSearch");
		writer.addDelete(target, javaHelpSearchDirName);

		writer.addTextPath(target, "dbdoclet.javahelp.classpath",
				getJavaHelpClasspath());

		java = writer.addJava(target);

		str = project.getMediumTargetParams().getMemory();

		if ((str != null) && (str.length() > 0)) {
			java.setAttribute("maxmemory", str + "m");
		}

		java.setAttribute("classname", "com.sun.java.help.search.Indexer");

		writer.addJvmarg(java, "-Xbootclasspath/p:${dbdoclet.bootclasspath}");
		writer.addArg(java, "-verbose");
		writer.addArg(java, "-db");
		writer.addArg(java, javaHelpSearchDirName);
		writer.addArg(java, baseDir);
		writer.addClasspath(java, "dbdoclet.javahelp.classpath");

	}
}
