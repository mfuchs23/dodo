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
package org.dbdoclet.tidbit.perspective.dbdoclet;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.Identifier;
import org.dbdoclet.io.FileSet;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ContinueBox;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.PathList;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;

class ClasspathFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {

		if (file.isDirectory()) {

			return true;
		}

		String fname = file.getName().toLowerCase();

		if (fname.endsWith(".jar") || fname.endsWith(".zip")) {

			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {

		return "Jar and Zip archives";
	}
}

public class PathPanel extends GridPanel implements ActionListener,
		MouseListener {

	private static final long serialVersionUID = 1L;

	private final static int MODE_DIRECTORY = 1;
	private final static int MODE_CLASSPATH = 2;

	private static Log logger = LogFactory.getLog(PathPanel.class);

	private final int width = 100;

	private File lastUsedDirectory;
	private final JLabel label;
	private final JTextComponent helpArea;
	private final JiveFactory widgetMap;

	protected ResourceBundle res;
	protected PathList pathList;

	private int mode = MODE_DIRECTORY;

	private Project project;

	public void setProject(Project project) {
		this.project = project;
	}

	private final JTextField addPathEntry;

	public PathPanel(String id) {

		if (id == null) {
			throw new IllegalArgumentException(
					"The argument id must not be null!");
		}

		widgetMap = JiveFactory.getInstance();
		res = StaticContext.getResourceBundle();

		JScrollPane scrollPane;

		helpArea = widgetMap.createHelpArea(this);
		helpArea.setPreferredSize(new Dimension(width, 80));
		addComponent(helpArea, Colspan.CS_3, Anchor.NORTHWEST, Fill.HORIZONTAL);
		incrRow();

		addPathEntry = widgetMap.createTextField(
				new Identifier("path.location"), 40);
		addPathEntry.setActionCommand("addEntry");
		addPathEntry.addActionListener(this);

		label = widgetMap.createLabel("");
		addLabeledComponent(label, addPathEntry, Fill.HORIZONTAL);

		JButton buttonBrowse = widgetMap.createButton(ResourceServices
				.getString(res, "C_BROWSE"));
		buttonBrowse.setActionCommand("choose");
		buttonBrowse.addActionListener(this);

		addComponent(buttonBrowse);
		incrRow();

		int filterMask = PathList.FILE_FILTER_MASK
				| PathList.PACKAGE_FILTER_MASK;
		pathList = widgetMap.createPathList(new Identifier(id), filterMask);

		scrollPane = new JScrollPane(pathList);
		scrollPane.setPreferredSize(new Dimension(width, 240));

		addComponent(scrollPane, Colspan.CS_2, Anchor.NORTHWEST, Fill.BOTH);

		GridPanel buttonPanel = new GridPanel();
		addComponent(buttonPanel, Colspan.CS_2, Anchor.NORTH, Fill.NONE);
		incrRow();

		JButton buttonAdd = widgetMap.createButton(null,
				ResourceServices.getString(res, "C_ADD"), "New");
		buttonAdd.setActionCommand("addEntry");
		buttonAdd.addActionListener(this);

		buttonPanel.addComponent(buttonAdd, Anchor.CENTER, Fill.HORIZONTAL);
		buttonPanel.incrRow();

		JButton buttonRemove = widgetMap.createButton(null,
				ResourceServices.getString(res, "C_REMOVE"), "Delete");
		buttonRemove.setActionCommand("removeEntry");
		buttonRemove.addActionListener(this);

		buttonPanel.addComponent(buttonRemove, Anchor.CENTER, Fill.HORIZONTAL);
	}

	public void actionPerformed(ActionEvent event) {

		try {

			String cmd = event.getActionCommand();

			if (cmd == null) {
				return;
			}

			if (cmd.equals("addEntry")) {
				addEntry();
				return;
			}

			if (cmd.equals("removeEntry")) {
				removeEntry();
				return;
			}

			if (cmd.equals("choose")) {
				choose();
				return;
			}

		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(
					StaticContext.getDialogOwner(), oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	private boolean addEntry() throws IOException {

		if (project == null) {
			ErrorBox.show(ResourceServices.getString(res, "C_ERROR"),
					ResourceServices
							.getString(res, "C_ERROR_PROJECT_UNDEFINED"));
			return false;
		}

		File projectDir = project.getProjectDirectory();

		if (projectDir == null) {

			ErrorBox.show(ResourceServices.getString(res, "C_ERROR"),
					ResourceServices.getString(res,
							"C_ERROR_UNDEFINED_PROJECT_DIRECTORY"));
			return false;
		}

		pathList.setWorkingDir(project.getProjectDirectory());

		String text = addPathEntry.getText();
		logger.debug("text=" + text);

		if (text == null || text.length() == 0) {

			File[] selectedFiles = choose();

			if (selectedFiles == null) {
				return false;
			}

			for (File file : selectedFiles) {
				addPath(file.getPath());
			}

		} else {
			addPath(text);
			addPathEntry.setText("");
		}

		return true;
	}

	private boolean addPath(String pathName) {

		File projectFile = project.getProjectFile();

		if (projectFile != null) {

			if (pathName.startsWith("." + File.separator)
					|| pathName.startsWith(".." + File.separator)) {

				String dir = projectFile.getParent();

				if (dir == null) {
					dir = ".";
				}

				pathName = FileServices.appendPath(dir, pathName);
			}
		}

		File path = new File(pathName);
		String item;

		try {

			item = path.getCanonicalPath();

			if (projectFile != null) {
				item = FileServices.relativePath(projectFile.getParentFile(),
						path);
			}

		} catch (IOException oops) {

			ExceptionBox ebox = new ExceptionBox(oops);
			ebox.setVisible(true);

			item = pathName;
		}

		if (path.exists() == false) {

			String msg = MessageFormat.format(ResourceServices.getString(res,
					"C_ERROR_PATH_DOESNT_EXIST"), item);
			ErrorBox.show(ResourceServices.getString(res, "C_ERROR"), msg);

			return false;
		}

		if ((mode == MODE_DIRECTORY) && path.isDirectory() == false) {

			String msg = MessageFormat.format(ResourceServices.getString(res,
					"C_ERROR_PATH_NO_DIRECTORY"), item);
			ErrorBox.show(ResourceServices.getString(res, "C_ERROR"), msg);

			return false;
		}

		if (pathList.findEntry(path) != null) {
			boolean rc = ContinueBox.show(StaticContext.getDialogOwner(),
					ResourceServices.getString(res, "C_QUESTION"),
					ResourceServices.getString(res,
							"C_WARNING_FILESET_ALREADY_EXISTS"));
			if (rc == false) {
				return false;
			}
		}

		if (path.isDirectory()
				&& (item.toLowerCase().endsWith("jars") || item.toLowerCase()
						.endsWith("lib"))) {

			FileSet fileSet = new FileSet(new File("."), path);
			fileSet.setFilterType(FileSet.FILTER_INCLUDE_FILES);
			fileSet.setFilter("**/*.jar");
			pathList.addEntry(fileSet);

		} else {

			pathList.addEntry(new FileSet(projectFile.getParentFile(), item));
		}

		return true;
	}

	public void changed() {
		pathList.changed();
	}

	private File[] choose() throws IOException {

		JFileChooser fc = new JFileChooser(getWorkingDir());
		fc.setMultiSelectionEnabled(true);
		switch (mode) {

		case MODE_DIRECTORY:
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			break;

		case MODE_CLASSPATH:
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fc.setFileFilter(new ClasspathFileFilter());
			fc.setAcceptAllFileFilterUsed(false);
			break;
		}

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File path = fc.getSelectedFile();
			lastUsedDirectory = path.getParentFile();
			return fc.getSelectedFiles();
		}

		return null;
	}

	public int getPathCount() {

		if (pathList == null) {
			throw new IllegalStateException(
					"The field pathList must not be null!");
		}

		return pathList.getPathCount();
	}

	public PathList getPathList() {

		if (pathList == null) {
			throw new IllegalStateException(
					"The field pathList must not be null!");
		}

		return pathList;
	}

	public File getWorkingDir() throws IOException {

		if (lastUsedDirectory == null) {

			if (project != null) {
				lastUsedDirectory = project.getProjectDirectory();
			} else {
				lastUsedDirectory = new File(".");
			}
		}

		return lastUsedDirectory;
	}

	public void mouseClicked(MouseEvent mouseEvent) {

		logger.debug("mouseClicked=" + mouseEvent);

		Component component = mouseEvent.getComponent();

		if (component == pathList) {

			int clicks = mouseEvent.getClickCount();

			if (clicks == 2) {

				int row = pathList.rowAtPoint(mouseEvent.getPoint());
				int col = pathList.columnAtPoint(mouseEvent.getPoint());

				addPathEntry.setText((String) pathList.getValueAt(row, col));
			}
		}
	}

	public void mouseEntered(MouseEvent mouseEvent) {
	}

	public void mouseExited(MouseEvent mouseEvent) {
	}

	public void mousePressed(MouseEvent mouseEvent) {
	}

	public void mouseReleased(MouseEvent mouseEvent) {
	}

	private void removeEntry() throws IOException {

		if (project == null) {
			return;
		}

		pathList.setWorkingDir(project.getProjectDirectory());
		pathList.removeSelectedEntry();
	}

	public void setClasspathMode() {
		mode = MODE_CLASSPATH;
	}

	public void setDirectoryMode() {
		mode = MODE_DIRECTORY;
	}

	public void setHelpText(String text) {

		if (helpArea != null) {
			helpArea.setText(text);
		}
	}

	public void setLabelText(String text) {

		if (label == null) {
			throw new IllegalStateException("The field label must not be null!");
		}

		label.setText(text);
	}

	public void syncModel(Project project, ArrayList<FileSet> modelList) {

		String path;
		File qualified;

		modelList.clear();

		for (FileSet fileSet : pathList.getFileSets()) {
			modelList.add(fileSet);
		}

		for (FileSet fileSet : modelList) {

			try {

				qualified = fileSet.getQualifiedPath();

				if (fileSet.isVariable() == false) {
					path = FileServices.relativePath(project.getProjectFile()
							.getParentFile(), qualified);
				} else {
					path = qualified.getPath();
				}

				fileSet.setPath(project.getProjectFile().getParentFile(),
						new File(path));

			} catch (IOException oops) {

				ExceptionBox ebox = new ExceptionBox(oops);
				ebox.setVisible(true);
			}
		}

	}

	public void syncView(Project project, ArrayList<FileSet> modelList) {

		setProject(project);
		pathList.clear();

		for (FileSet fileSet : modelList) {
			pathList.addEntry(fileSet);
		}
	}

}
