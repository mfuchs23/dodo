package org.dbdoclet.tidbit.importer.eclipse;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.xml.parsers.ParserConfigurationException;

import org.dbdoclet.io.FileSet;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.widget.HelpPanel;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.importer.ImportService;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.xiphias.XPathServices;
import org.dbdoclet.xiphias.XmlServices;
import org.osgi.service.component.ComponentContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class EclipseImportComponent implements ImportService {

	public void importProject(Project project) {

		try {

			ResourceBundle res = StaticContext.getResourceBundle();

			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			HelpPanel helpPanel = new HelpPanel(ResourceServices.getString(res,
					"C_HELP_ECLIPSE_IMPORT_PROJECT_CHOOSER"));
			chooser.setAccessory(helpPanel);

			int rc = chooser.showOpenDialog(StaticContext.getDialogOwner());

			if (rc == JFileChooser.APPROVE_OPTION) {

				File eclipseProjectDir = chooser.getSelectedFile();

				if (eclipseProjectDir != null && eclipseProjectDir.exists()) {

					String path = FileServices.appendFileName(
							eclipseProjectDir, ".classpath");
					File projectFile = new File(path);

					if (projectFile.exists() == false) {

						ErrorBox.show(
								StaticContext.getDialogOwner(),
								ResourceServices.getString(res, "C_ERROR"),
								MessageFormat.format(
										res.getString("C_ERROR_INVALID_ECLIPSE_DIRECTORY"),
										eclipseProjectDir.getAbsolutePath()));
						return;
					}

					String projectPath = FileServices.appendPath(
							eclipseProjectDir, "doc");
					File dbdProjectDir = new File(projectPath);
					// project.setProjectDirectory(dbdProjectDir);

					path = FileServices.appendPath(projectPath, "build");
					File dbdBuildDir = new File(path);
					project.setBuildDirectory(dbdBuildDir);

					path = FileServices.appendFileName(projectPath,
							"dbdoclet.xml");
					File dbdProjectFile = new File(path);
					project.setProjectFile(dbdProjectFile);

					project.setSourcepath(new ArrayList<FileSet>());
					project.setClasspath(new ArrayList<FileSet>());

					addClasspathFile(project, eclipseProjectDir, projectFile,
							dbdProjectDir, dbdProjectFile);
				}

			}

		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(
					StaticContext.getDialogOwner(), oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	private void addClasspathFile(Project project, File projectDir,
			File projectFile, File dbdProjectDir, File dbdProjectFile)
			throws IOException, SAXException, ParserConfigurationException {

		Document doc = XmlServices.parse(projectFile);

		String path;
		ArrayList<String> values = XPathServices.getValues(doc,
				"//classpathentry[@kind='src']/@path");
		ArrayList<FileSet> sourcepath = project.getSourcepath();

		for (Object value : values) {

			path = value.toString();

			if (path.startsWith("/") == false) {

				path = FileServices.appendPath(projectDir, path);
				sourcepath.add(new FileSet(dbdProjectDir, new File(path)));

			} else {

				path = StringServices.cutPrefix(path, "/");
				File workspaceDir = projectDir.getParentFile();
				path = FileServices.appendPath(workspaceDir, path);
				File subProjectDir = new File(path);

				if (subProjectDir.exists()) {

					// path = FileServices.appendFileName(path, ".classpath");
					// File subProjectFile = new File(path);
					// addSubClasspathFile(project, subProjectDir,
					// subProjectFile, dbdProjectDir, dbdProjectFile);
				}
			}
		}

		project.setSourcepath(sourcepath);

		addClasspathLib(project, projectDir, dbdProjectDir, dbdProjectFile, doc);
	}

	private void addClasspathLib(Project project, File projectDir,
			File dbdProjectDir, File dbdProjectFile, Document doc)
			throws IOException {

		String path;
		File file;

		ArrayList<String> values;
		values = XPathServices.getValues(doc,
				"//classpathentry[@kind='lib']/@path");

		ArrayList<FileSet> classpath = project.getClasspath();

		for (Object value : values) {

			String lib = value.toString();

			if (lib.startsWith("/")) {

				File workspaceDir = projectDir.getParentFile();
				path = FileServices.appendPath(workspaceDir, lib);
				file = new File(path);

				if (file.exists()) {
					classpath.add(new FileSet(dbdProjectDir, file));
				}

			} else {

				path = FileServices.appendPath(projectDir, value.toString());
				classpath.add(new FileSet(dbdProjectDir, new File(path)));
			}
		}

		project.setClasspath(classpath);
	}

	// private void addSubClasspathFile(Project project, File projectDir, File
	// projectFile, File dbdProjectDir,
	// File dbdProjectFile) throws IOException, SAXException,
	// ParserConfigurationException {
	//
	// Document doc = XmlServices.parse(projectFile);
	//
	// String path;
	// ArrayList<Object> values = XPathServices.getValues(doc,
	// "//classpathentry[@kind='src']/@path");
	//
	// for (Object value : values) {
	//
	// path = value.toString();
	//
	// if (path.startsWith("/") == true) {
	//
	// path = StringServices.cutPrefix(path, "/");
	// File workspaceDir = projectDir.getParentFile();
	// path = FileServices.appendPath(workspaceDir, path);
	// File subProjectDir = new File(path);
	//
	// if (subProjectDir.exists()) {
	//
	// path = FileServices.appendFileName(path, ".classpath");
	// File subProjectFile = new File(path);
	// addSubClasspathFile(project, subProjectDir, subProjectFile,
	// dbdProjectDir, dbdProjectFile);
	// }
	// }
	// }
	//
	// values = XPathServices.getValues(doc,
	// "//classpathentry[@kind='output']/@path");
	// ArrayList<FileSet> classpath = project.getClasspath();
	//
	// for (Object value : values) {
	//
	// path = value.toString();
	// path = FileServices.appendPath(projectDir, path);
	// classpath.add(new FileSet(dbdProjectDir, new File(path)));
	// }
	//
	// addClasspathLib(project, projectDir, dbdProjectDir, dbdProjectFile, doc);
	// }

	protected void activate(ComponentContext context) {
	}

	public String getName() {
		return "Eclipse Import";
	}

	public String getResourceKey() {
		return "C_IMPORT_ECLIPSE_PROJECT";
	}

}
