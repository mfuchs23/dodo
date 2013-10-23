package org.dbdoclet.tidbit.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.dbdoclet.antol.AntFileReader;
import org.dbdoclet.antol.AntTransformer;
import org.dbdoclet.antol.ant.Dirset;
import org.dbdoclet.antol.ant.Fileset;
import org.dbdoclet.antol.ant.Javadoc;
import org.dbdoclet.antol.ant.JavadocItem;
import org.dbdoclet.antol.ant.Packageset;
import org.dbdoclet.antol.ant.Path;
import org.dbdoclet.antol.ant.PathItem;
import org.dbdoclet.antol.ant.Pathelement;
import org.dbdoclet.antol.ant.Target;
import org.dbdoclet.io.FileSet;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.trafo.TrafoException;
import org.xml.sax.SAXException;

public class ProjectReader extends AbstractProjectStream {

	private AntFileReader buildFile;

	public ProjectReader(Project project) {
		super(project);
	}

	public AntFileReader read(File projectFile)
			throws ParserConfigurationException, SAXException, IOException,
			ConfigurationException, TrafoException {

		buildFile = new AntFileReader(projectFile);
		project.setProjectName(buildFile.getAntProject().getName());
		project.setBuildDirectory(readBuildDirectory());
		project.setSourcepath(readSourcepath());
		project.setClasspath(readClasspath());
		project.setOverviewPath(readOverviewFile());
		project.setDocBookFileName(readDocBookFileName());
		return buildFile;
	}

	private String readDocBookFileName() {
		String value = buildFile.getProperty("dbdoclet.docbook.file");
		value = replaceVariables(value);
		return value;
	}

	private String readOverviewFile() throws IOException {

		String value = buildFile.getProperty("dbdoclet.overview.file");
		value = replaceVariables(value);

		if (value == null) {
			return "";
		}

		return value;
	}

	private String replaceVariables(String value) {
		value = StringServices.replace(value, "${basedir}", project
				.getBaseDir().getAbsolutePath());
		return value;
	}

	private File readBuildDirectory() throws IOException {

		String value = buildFile.getProperty("dbdoclet.destination.path");
		value = replaceVariables(value);

		if (value == null) {
			return new File("./build");
		}

		File buildDir = new File(value);

		if (buildDir.exists() == false) {
			FileServices.createPath(buildDir);
		}

		return buildDir;
	}

	private ArrayList<FileSet> readClasspath() {

		ArrayList<FileSet> list = new ArrayList<FileSet>();
		Fileset fileset;

		Target target = buildFile.findTarget("dbdoclet.docbook");

		if (target == null) {
			return list;
		}

		Path classpath = buildFile.findPath("dbdoclet.classpath");

		if (classpath != null) {

			PathItem[] items = classpath.getPathItem();

			for (int i = 0; i < items.length; i++) {

				Pathelement pathelement = items[i].getPathelement();

				if (pathelement != null) {

					String str = pathelement.getLocation();

					if (str == null) {
						str = pathelement.getPath();
					}

					list.add(new FileSet(project.getProjectDirectory(),
							new File(str)));
				}

				Dirset dirset = items[i].getDirset();

				if (dirset != null) {
					list.addAll(AntTransformer.toFileSetList(dirset,
							project.getProjectDirectory()));
				}

				fileset = items[i].getFileset();

				if (fileset != null) {
					list.addAll(AntTransformer.toFileSetList(fileset,
							project.getProjectDirectory()));
				}
			}
		}

		return list;
	}

	private ArrayList<FileSet> readSourcepath() {

		ArrayList<FileSet> list = new ArrayList<FileSet>();
		Packageset packageset;
		Fileset fileset;

		Target target = buildFile.findTarget("dbdoclet.docbook");

		if (target == null) {
			return list;
		}

		Javadoc javadoc = (Javadoc) buildFile.getTask(target, Javadoc.class);
		JavadocItem[] items = javadoc.getJavadocItem();

		for (int i = 0; i < items.length; i++) {

			packageset = items[i].getPackageset();

			if (packageset != null) {
				list.addAll(AntTransformer.toFileSetList(packageset,
						project.getProjectDirectory()));
			}

			fileset = items[i].getFileset();

			if (fileset != null) {
				list.addAll(AntTransformer.toFileSetList(fileset,
						project.getProjectDirectory()));
			}
		}

		Path sourcepath = buildFile.findPath("dbdoclet.sourcepath");

		if (sourcepath != null) {

			Pathelement pathelement;
			PathItem[] pathItems = sourcepath.getPathItem();

			for (int i = 0; i < pathItems.length; i++) {

				pathelement = pathItems[i].getPathelement();

				if (pathelement != null) {

					list.add(new FileSet(project.getProjectDirectory(),
							pathelement.getPath()));
				}
			}
		}

		return list;
	}

}
