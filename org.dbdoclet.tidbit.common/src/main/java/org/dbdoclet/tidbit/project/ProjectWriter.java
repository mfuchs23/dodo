package org.dbdoclet.tidbit.project;

import java.io.File;
import java.util.ArrayList;

import org.dbdoclet.Sfv;
import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.driver.DriverManager;
import org.dbdoclet.tidbit.project.target.PrepareTarget;
import org.dbdoclet.tidbit.project.target.TargetdbTarget;
import org.dbdoclet.tidbit.project.target.XsltTarget;
import org.w3c.dom.Element;

public class ProjectWriter extends AbstractProjectStream {

	public ProjectWriter(Project project) {
		super(project);
	}

	private void saveTargetFop(AntFileWriter writer, String media) {

		if (writer == null) {
			throw new IllegalArgumentException(
					"The argument writer must not be null!");
		}

		if (media == null) {
			throw new IllegalArgumentException(
					"The argument media must not be null!");
		}

		String str;
		Element java;
		Element target;

		target = writer.addTarget("dbdoclet.fop." + media);

		java = writer.addJava(target);
		java.setAttribute("classname", "org.apache.fop.cli.Main");
		java.setAttribute("fork", "true");

		str = project.getJvmMaxMemory();

		if ((str != null) && (str.length() > 0)) {
			java.setAttribute("maxmemory", str + "m");
		}

		if (project.getParameter(Constants.PDF_USER_PASSWORD) != null) {
			writer.addArg(java, "-u");
			writer.addArg(java,
					project.getParameter(Constants.PDF_USER_PASSWORD)
							.toString());
		}

		writer.addArg(java, "-c");
		writer.addArg(java, "${fop.home}/conf/fop.xconf");
		writer.addArg(java, "-fo");
		writer.addArg(java, "${in}");
		writer.addArg(java, "-" + media);
		writer.addArg(java, "${out}");

		writer.addJvmarg(java, "-Xbootclasspath/p:${dbdoclet.bootclasspath}");
		writer.addClasspath(java, "dbdoclet.fop.classpath");

	}

	public AntFileWriter write() throws Exception {

		String projectPath = FileServices.normalizePath(project
				.getProjectPath());
		String destinationPath = FileServices.normalizePath(project
				.getDestinationPath());

		File basedir = project.getBaseDir();
		File projectDir = project.getProjectDirectory();

		if (basedir != null && projectDir != null && basedir.equals(projectDir)) {

			if (destinationPath.startsWith(projectPath)) {

				destinationPath = StringServices.cutPrefix(destinationPath,
						projectPath);

				if (destinationPath.startsWith("/") == false) {
					destinationPath = "/" + destinationPath;
				}

				destinationPath = "${basedir}" + destinationPath;
			}

			projectPath = "${basedir}";
		}

		projectPath = StringServices.cutSuffix(projectPath, "/");
		destinationPath = StringServices.cutSuffix(destinationPath, "/");

		AntFileWriter writer = new AntFileWriter(project.getProjectFile());

		Element doc = writer.setProject(project.getProjectName(),
				"dbdoclet.pdf");

		writer.addPropertiesFile(doc, projectPath + Sfv.FSEP
				+ "dbdoclet.properties");
		writer.addPropertiesFile(doc, projectPath + Sfv.FSEP
				+ "params.properties");
		writer.addFileProperty(doc, "dbdoclet.home", FileServices
				.normalizePath(project.getHomeDir().getCanonicalPath()));
		writer.addFileProperty(doc, "dbdoclet.destination.path",
				destinationPath);
		writer.addFileProperty(doc, "dbdoclet.project.path", projectPath);
		writer.addFileProperty(doc, "dbdoclet.overview.file",
				project.getOverviewPath());

		String fileName = fileManager.getDocBookFileName();

		if (fileName == null || fileName.trim().length() == 0) {

			writer.addFileProperty(doc, "dbdoclet.docbook.file", "");
			writer.addFileProperty(doc, "dbdoclet.docbook.file.base", "");
			writer.addFileProperty(doc, "dbdoclet.docbook.file.dir", "");

		} else {

			File file = new File(fileName);
			writer.addFileProperty(doc, "dbdoclet.docbook.file", fileName);
			writer.addFileProperty(doc, "dbdoclet.docbook.file.base",
					FileServices.getFileBase(fileName));
			writer.addFileProperty(doc, "dbdoclet.docbook.file.dir", file
					.getAbsoluteFile().getParent());
			writer.addFileProperty(doc, "dbdoclet.docbook.name", file.getName());
			writer.addFileProperty(doc, "dbdoclet.docbook.name.base",
					FileServices.getFileBase(file.getName()));
		}

		writer.addProperty(doc, "dbdoclet.bootclasspath",
				project.getBootClasspathAsString());
		writer.addProperty(doc, "fop.home", "${dbdoclet.home}fop");
		writer.addPath(doc, "dbdoclet.classpath", project.getClasspath());

		Element fopPath = writer.addTextPath(doc, "dbdoclet.fop.classpath",
				project.getFopClasspath());
		writer.addFileset(fopPath, "${fop.home}/lib", "*.jar");
		writer.addFileset(fopPath, "${fop.home}/build", "fop*.jar");

		writer.addTextPath(doc, "dbdoclet.herold.classpath",
				project.getHeroldClasspath());

		ArrayList<String> pathList = new ArrayList<String>();
		pathList.add("${dbdoclet.home}conf/");
		pathList.add("${dbdoclet.home}jars/resolver.jar");
		pathList.add("${dbdoclet.home}docbook/xsl/extensions/saxon65.jar");
		pathList.add("${dbdoclet.home}jars/xercesImpl.jar");
		pathList.add("${dbdoclet.home}jars/xslthl.jar");
		pathList.add("${dbdoclet.home}jars/saxon.jar");
		writer.addTextPath(doc, "dbdoclet.saxon.classpath", pathList);

		PrepareTarget.createTarget(writer, project);
		XsltTarget.createTarget(writer, project);
		TargetdbTarget.createTarget(writer, project);

		saveTargetFop(writer, "pdf");
		saveTargetFop(writer, "rtf");

		DriverManager driverManager = project.getDriverManager();
		driverManager.save();
		return writer;
	}
}
