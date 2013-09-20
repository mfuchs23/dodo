/* 
 * ### Copyright (C) 2003-2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.dbdoclet.FileAccessDeniedException;
import org.dbdoclet.antol.AntFileReader;
import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.io.FileSet;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.tidbit.common.MediumTargetParams;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.common.Visibility;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;
import org.dbdoclet.tidbit.project.driver.DriverManager;
import org.dbdoclet.trafo.TrafoException;
import org.xml.sax.SAXException;

class FopJarsFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {

		name = name.toLowerCase();

		if (name.equals("tidbit.jar")) {
			return false;
		}

		if (name.equals("log4j.jar")) {
			return false;
		}

		if (name.endsWith(".jar")) {
			return true;
		} else {
			return false;
		}
	}
}

class JarsFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {

		name = name.toLowerCase();

		if (name.endsWith(".jar")) {
			return true;
		} else if (name.endsWith(".zip")) {
			return true;
		} else {
			return false;
		}
	}
}

class JavaFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {

		name = name.toLowerCase();

		if (name.endsWith(".java")) {

			return true;
		} else if (name.endsWith(".JAVA")) {

			return true;
		} else {

			return false;
		}
	}
}

/**
 * The class <code>Project</code> represents the model of the tidbit
 * application.
 * 
 * @author <a href ="mailto:mfuchs@unico-consulting.com">Michael Fuchs</a>
 * @version 1.0
 */
public class Project implements Serializable {

	public static final String FTYPE_DOCBOOK_XML = "DocBook XML";

	public static final String FTYPE_HTML = "HTML";
	private static final String PSEP = System.getProperty("path.separator");

	private static final long serialVersionUID = 1L;

	private ArrayList<FileSet> classpath;
	private String destinationEncoding = "UTF-8";
	private String docBookVersion = "5.0";
	private File jdkHome;
	private String jvmMaxMemory = "1024";
	private boolean linkSourceEnabled;
	private String overviewFileType;
	private String overviewPath = "";
	private File projectDirectory;
	private File projectFile;
	private String projectName = "";
	private String releaseInfo = "";
	private String sourceEncoding = "UTF-8";
	private ArrayList<FileSet> sourcepath;
	private String sourceVersion = "1.5";
	private Visibility visibility = Visibility.PUBLIC;
	private final FileManager fileManager;
	private final File homeDir;
	private final DriverManager driverManager;

	public Project(File projectFile, File projectDirectory, File buildDirectory)
			throws IOException, ParserConfigurationException, SAXException,
			ConfigurationException, TrafoException {

		this.homeDir = StaticContext.getHomeDirectory();
		this.projectFile = projectFile;

		fileManager = new FileManager();
		setBuildDirectory(buildDirectory);
		setProjectDirectory(projectDirectory);

		sourcepath = new ArrayList<FileSet>();
		classpath = new ArrayList<FileSet>();

		driverManager = new DriverManager(homeDir, getDriverDirectory());
		driverManager.createDrivers();

		setProjectName("");
		read();
	}

	public File getBaseDir() {
		return Project.getBaseDir(projectFile);
	}

	public static File getBaseDir(File projectFile) {

		if (projectFile == null) {
			throw new IllegalStateException(
					"The field antfile must not be null!");
		}

		File basedir = projectFile.getParentFile();

		if (basedir == null) {
			throw new IllegalStateException(
					"Project.getBaseDir(): basedir must not be null!");
		}

		return basedir;
	}

	public String getBootClasspathAsString() {

		String buffer = "";

		buffer += "${dbdoclet.home}jars/xml-apis.jar" + PSEP;
		buffer += "${dbdoclet.home}jars/xercesImpl.jar" + PSEP;
		buffer += "${dbdoclet.home}jars/serializer.jar" + PSEP;
		buffer += "${dbdoclet.home}jars/xalan.jar";

		return buffer;
	}

	public File getBuildDirectory() {
		return fileManager.getBuildDirectory();
	}

	public ArrayList<FileSet> getClasspath() {
		return classpath;
	}

	public String getDestinationEncoding() {
		return destinationEncoding;
	}

	public String getDestinationPath() throws IOException {
		return fileManager.getBuildDirectory().getPath();
	}

	public String getDocBookVersion() {
		return docBookVersion;
	}

	public String getDocletPropertiesPath() {

		return FileServices.appendFileName(getProjectPath(),
				"dbdoclet.properties");
	}

	public AbstractDriver getDriver(Output output) {
		return driverManager.getDriver(output);
	}

	public File getDriverDirectory() {
		return new File(FileServices.appendPath(getProjectDirectory(), "xsl"));
	}

	public DriverManager getDriverManager() {
		return driverManager;
	}

	public FileManager getFileManager() {
		return fileManager;
	}

	public ArrayList<String> getFopClasspath() {

		ArrayList<String> jars = getJarList();
		ArrayList<String> list = new ArrayList<String>();

		Iterator<String> iterator = jars.iterator();

		String item;
		String jarname;

		while (iterator.hasNext()) {

			item = iterator.next();
			jarname = FileServices.getFileName(item);
			jarname = jarname.toLowerCase();

			if (jarname.equals("fop-hyph.jar")) {
				list.add("${dbdoclet.home}jars/" + jarname);
			}
		}

		return list;
	}

	public ArrayList<String> getHeroldClasspath() {

		ArrayList<String> list = new ArrayList<String>();
		list.add("${dbdoclet.home}jars/dbdoclet.jar");

		return list;
	}

	public File getHomeDir() {
		return homeDir;
	}

	public String getHtmlDestDir(AbstractDriver driver) throws IOException {

		String baseDir = driver.getParameter("base.dir");

		String destDir = ".";

		if (FileServices.isAbsolutePath(baseDir)) {
			destDir = FileServices.normalizePath(baseDir);
		} else {
			destDir = "${dbdoclet.docbook.file.dir}/"
					+ FileServices.normalizePath(baseDir);
		}

		return destDir;
	}

	public ArrayList<String> getJarList() {

		ArrayList<String> list = new ArrayList<String>();

		String libPath = FileServices.appendPath(homeDir, "jars");
		File libdir = new File(libPath);

		String pathelement = "";
		String[] jars = libdir.list(new JarsFilter());

		if ((jars == null) || (jars.length == 0)) {
			throw new IllegalStateException("Couldn't find any jar files in "
					+ libPath + "!");
		}

		for (int i = 0; i < jars.length; i++) {

			pathelement = libPath + jars[i];
			list.add(pathelement);
		}

		return list;
	}

	public MediumTargetParams getMediumTargetParams() {

		MediumTargetParams mediumTargetParams = new MediumTargetParams();
		mediumTargetParams.setOverview(getOverviewPath());
		mediumTargetParams.setDestinationEncoding(getDestinationEncoding());
		mediumTargetParams.setSourceEncoding(getSourceEncoding());
		mediumTargetParams.setVisibility(getVisibility());
		mediumTargetParams.setLinkSourceEnabled(isLinkSourceEnabled());
		mediumTargetParams.setSource(getSourceVersion());
		mediumTargetParams.setMemory(getJvmMaxMemory());
		mediumTargetParams.setSourcepath(getSourcepath());
		mediumTargetParams.setDocBook5(isDocBook5());

		return mediumTargetParams;
	}

	public File getJdkHome() {
		return jdkHome;
	}

	public String getJvmMaxMemory() {
		return jvmMaxMemory;
	}

	public String getOdtFilePath() throws IOException {

		String path = FileServices.appendPath(getDestinationPath(), "odt");
		path = FileServices.appendFileName(path, "Reference.odt");
		path = FileServices.normalizePath(path);
		return path;
	}

	public String getOverviewFileType() {
		return overviewFileType;
	}

	public String getOverviewPath() {

		if (overviewPath != null) {
			return overviewPath;
		} else {
			return "";
		}
	}

	public File getProjectDirectory() {
		return projectDirectory;
	}

	public File getProjectFile() {
		return projectFile;
	}

	public String getProjectName() {
		if (projectName != null) {
			return projectName;
		} else {
			return "";
		}
	}

	public String getProjectPath() {

		if (projectDirectory != null) {
			return projectDirectory.getPath();
		}

		return "";
	}

	public String getPsFilePath() throws IOException {

		String path = FileServices.appendPath(getDestinationPath(), "ps");
		path = FileServices.appendFileName(path, "Reference.ps");
		path = FileServices.normalizePath(path);
		return path;
	}

	public String getReleaseInfo() {
		return releaseInfo;
	}

	public String getRtfFilePath() throws IOException {

		String path = FileServices.appendPath(getDestinationPath(), "rtf");
		path = FileServices.appendFileName(path, "Reference.rtf");
		path = FileServices.normalizePath(path);
		return path;
	}

	public File getScriptFile() {

		String path = FileServices.appendFileName(getProjectPath(),
				"dbdoclet.conf");
		path = FileServices.normalizePath(path);
		return new File(path);
	}

	public String getSourceEncoding() {
		return sourceEncoding;
	}

	public ArrayList<FileSet> getSourcepath() {
		return sourcepath;
	}

	public String getSourceVersion() {
		return sourceVersion;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public String getWmlFilePath() throws IOException {

		String path = FileServices.appendPath(getDestinationPath(), "wordml");
		path = FileServices.appendFileName(path, "Reference-wml.xml");
		path = FileServices.normalizePath(path);
		return path;
	}

	public String getXsltDestPath() throws IOException {

		String dirName = FileServices.appendPath(getProjectPath(), "xsl");
		FileServices.createPath(dirName);

		return dirName;
	}

	public boolean isDocBook5() {

		if (docBookVersion != null && docBookVersion.startsWith("5")) {
			return true;
		}

		return false;
	}

	public boolean isLinkSourceEnabled() {
		return linkSourceEnabled;
	}

	/* ------------------------------------------------------------- */
	/* Load project from project files. */
	/* ------------------------------------------------------------- */
	public void read() throws SAXException, FileNotFoundException, IOException,
			ParserConfigurationException, ConfigurationException,
			TrafoException {

		if (projectFile == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		if (projectFile.exists()
				&& (projectFile.canRead() == false || projectFile.canWrite() == false)) {

			FileServices.setWritable(projectFile);

			if (projectFile.canRead() == false
					|| projectFile.canWrite() == false) {
				throw new FileAccessDeniedException(projectFile);
			}
		}

		ProjectReader reader = new ProjectReader(this);

		if (projectFile.exists()) {
			reader.read(projectFile);
		}

	}

	public void refresh() throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException, ConfigurationException,
			TrafoException {
		read();
	}

	public void setBuildDirectory(File buildDirectory) {
		fileManager.setBuildDirectory(buildDirectory);
	}

	public void setClasspath(ArrayList<FileSet> classpath) {

		if (classpath == null) {
			throw new IllegalArgumentException("Parameter classpath is null!");
		}

		this.classpath = classpath;
	}

	public void setDocBookFileName(String docBookFileName) {
		fileManager.setDocBookFileName(docBookFileName);
	}

	public void setDocBookVersion(String docBookVersion) {
		this.docBookVersion = docBookVersion;

	}

	public void setJavadocProperty(String key, String value) {

		if (key == null) {

			throw new IllegalArgumentException("Parameter key is null!");
		}

		if (value == null) {

			throw new IllegalArgumentException("Parameter value is null!");
		}
	}

	public void setJdkHome(File jdkHome) {
		this.jdkHome = jdkHome;
	}

	public void setJvmMaxMemory(String jvmMaxMemory) {
		this.jvmMaxMemory = jvmMaxMemory;
	}

	public void setLinkSourceEnabled(boolean linkSourceEnabled) {
		this.linkSourceEnabled = linkSourceEnabled;
	}

	public void setOverviewFileType(String ftype) {
		overviewFileType = ftype;
	}

	public void setOverviewPath(String name) {
		overviewPath = name;
	}

	private void setProjectDirectory(File projectDir) {

		if (projectDir != null) {
			this.projectDirectory = new File(projectDir.getAbsolutePath());
		}
	}

	public void setProjectFile(File file) {
		this.projectFile = file;
	}

	public void setProjectName(String name) {
		projectName = name;
	}

	public void setReleaseInfo(String releaseInfo) {
		this.releaseInfo = releaseInfo;
	}

	public void setSourceEncoding(String sourceEncoding) {
		this.sourceEncoding = sourceEncoding;
	}

	public void setSourcepath(ArrayList<FileSet> sourcepath) {

		if (sourcepath == null) {
			throw new IllegalArgumentException("Parameter sourcepath is null!");
		}

		this.sourcepath = sourcepath;
	}

	public void setSourceVersion(String sourceVersion) {
		this.sourceVersion = sourceVersion;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public AntFileWriter createAntWriter() {

		if (projectFile == null) {
			throw new IllegalStateException(
					"The argument file must not be null!");
		}

		if (projectFile.exists() && projectFile.canWrite() == false) {

			FileServices.setWritable(projectFile);

			if (projectFile.canWrite() == false) {
				throw new FileAccessDeniedException(projectFile);
			}
		}

		ProjectWriter writer = new ProjectWriter(this);

		try {

			return writer.write();

		} catch (Exception oops) {

			ExceptionBox ebox = new ExceptionBox(
					StaticContext.getDialogOwner(),
					"Failed to save project!!!", oops);
			ebox.setVisible(true);
		}

		return null;
	}

	public static Project newInstance(File file) throws IOException,
			ParserConfigurationException, SAXException, ConfigurationException,
			TrafoException {

		AntFileReader buildFile = new AntFileReader(file);

		String value = buildFile.getProperty("dbdoclet.project.path");
		value = StringServices.replace(value, "${basedir}",
				Project.getBaseDir(file).getAbsolutePath());

		if (value == null) {
			value = "./";
		}

		File projectDir = new File(value);

		value = buildFile.getProperty("dbdoclet.destination.path");
		value = StringServices.replace(value, "${basedir}",
				Project.getBaseDir(file).getAbsolutePath());

		if (value == null) {
			value = "./build";
		}

		File buildDir = new File(value);
		return new Project(file, projectDir, buildDir);
	}

	public String getParameter(String pdfUserPassword) {
		// TODO Auto-generated method stub
		return "";
	}

	public void setParameter(String pdfUserPassword, String password) {
		// TODO Auto-generated method stub

	}

	public String getParameter(String sourceEncoding2, String string) {
		// TODO Auto-generated method stub
		return string;
	}

	public boolean getBooleanParameter(String useAbsoluteImagePath) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDestinationEncoding(String destinationEncoding) {
		this.destinationEncoding = destinationEncoding;
	}
}
