/* 
 * ### Copyright (C) 2006-2007 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.perspective.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.dialog.WarningBox;
import org.dbdoclet.jive.model.LabelItem;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.JvmServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;

public class ProjectPanel extends GridPanel implements ActionListener {

	private static Log logger = LogFactory.getLog(ProjectPanel.class);

	private static final long serialVersionUID = 1L;

	private final JTextField buildDirectoryEntry;
	private final Context ctx;
	private JTextField docBookFileEntry;
	private JComboBox jdkComboBox;
	private final JTextField projectDirectoryEntry;
	private final JTextField projectFileEntry;
	private final JTextField projectNameEntry;
	private final ResourceBundle res;
	private JRadioButton useCustomLocationButton;
	private JButton useDocletOutputButton;
	private JButton useHeroldLocationButton;
	private final JiveFactory jf;
	private GridPanel jdkPanel;

	private static final int TEXTFIELD_WIDTH = 41;

	public ProjectPanel(Context ctx) throws IOException {

		JLabel label;
		JButton button;
		String toolTipText;

		this.ctx = ctx;

		res = ctx.getResourceBundle();
		jf = JiveFactory.getInstance();

		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		addSeparator(4, "DocBook");
		createDocBookSection();

		addSeparator(4, ResourceServices.getString(res, "C_COMMON"));

		toolTipText = jf.createToolTipText(ResourceServices.getString(res,
				"C_HELP_ENTRY_PROJECT_FILE"));
		label = jf.createLabel(ResourceServices
				.getString(res, "C_PROJECT_FILE"));
		label.setToolTipText(toolTipText);
		projectFileEntry = jf.createTextField(new Identifier("project.file"),
				TEXTFIELD_WIDTH);
		projectFileEntry.setEnabled(false);
		addLabeledComponent(label, projectFileEntry);
		incrRow();

		toolTipText = jf.createToolTipText(ResourceServices.getString(res,
				"C_HELP_ENTRY_PROJECT_DIRECTORY"));
		label = jf.createLabel(ResourceServices.getString(res,
				"C_PROJECT_DIRECTORY"));
		label.setToolTipText(toolTipText);
		projectDirectoryEntry = jf.createTextField(new Identifier(
				"project.directory"), TEXTFIELD_WIDTH);
		projectDirectoryEntry.setEnabled(false);
		addLabeledComponent(label, projectDirectoryEntry);
		incrRow();

		toolTipText = jf.createToolTipText(ResourceServices.getString(res,
				"C_HELP_ENTRY_PROJECT_NAME"));
		label = jf.createLabel(ResourceServices
				.getString(res, "C_PROJECT_NAME"));
		label.setToolTipText(toolTipText);
		projectNameEntry = jf.createTextField(new Identifier("project.name"),
				TEXTFIELD_WIDTH);
		projectNameEntry.setToolTipText(toolTipText);
		addLabeledComponent(label, projectNameEntry);
		incrRow();

		toolTipText = jf.createToolTipText(ResourceServices.getString(res,
				"C_HELP_ENTRY_DESTINATION_DIRECTORY"));
		label = jf.createLabel(ResourceServices.getString(res,
				"C_DESTINATION_DIRECTORY"));
		label.setToolTipText(toolTipText);
		buildDirectoryEntry = jf.createTextField(new Identifier(
				"destination.directory"), TEXTFIELD_WIDTH);
		buildDirectoryEntry.setName("textfield.build-directory");
		buildDirectoryEntry.setToolTipText(toolTipText);
		buildDirectoryEntry.setEditable(false);
		buildDirectoryEntry.setFocusable(false);
		addLabeledComponent(label, buildDirectoryEntry);

		button = jf.createButton(ResourceServices.getString(res, "C_BROWSE"));
		button.setActionCommand("chooseDestinationDirectory");
		button.addActionListener(this);
		addComponent(button);
		incrRow();

		addJdkPanel(ctx);
		addVerticalGlue();
	}

	public void actionPerformed(ActionEvent event) {

		logger.debug("event=" + event);

		try {

			String cmd = event.getActionCommand();

			if (cmd == null) {
				return;
			}

			if (cmd.equals("chooseProjectFile")) {
				chooseProjectFile();
			}

			if (cmd.equals("chooseProjectDirectory")) {
				chooseProjectDirectory();
			}

			if (cmd.equals("chooseDestinationDirectory")) {
				chooseDestinationDirectory();
			}

			if (cmd.equals("chooseDocBookFile")) {
				chooseDocBookFile();
			}

			if (cmd.equals("useDocletLocation")) {

				String path = FileServices.appendFileName(
						projectDirectoryEntry.getText(), "Reference.xml");

				File file = new File(path);

				if (file.exists() == false) {
					path = FileServices.appendFileName(
							buildDirectoryEntry.getText(), "Reference.xml");
					file = new File(path);
				}

				if (file.exists() == false) {
					WarningBox.show(StaticContext.getDialogOwner(),
							ResourceServices.getString(res, "C_WARNING"),
							MessageFormat.format(ResourceServices.getString(
									res, "C_WARNING_FILE_DOES_NOT_EXIST"), file
									.getAbsolutePath()));
				}

				docBookFileEntry.setText(path);
			}

			if (cmd.equals("useHeroldLocation")) {

				String fileName = getHeroldOutputFileName();
				File file = new File(fileName);

				if (file.exists() == false) {

					WarningBox.show(StaticContext.getDialogOwner(),
							ResourceServices.getString(res, "C_WARNING"),
							MessageFormat.format(ResourceServices.getString(
									res, "C_WARNING_FILE_DOES_NOT_EXIST"), file
									.getAbsolutePath()));
				}

				docBookFileEntry.setText(fileName);
			}

			if (cmd.equals("useCustomLocation")) {

				docBookFileEntry.setText(ctx.getProject().getFileManager()
						.getDocBookFileName());
			}

		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(
					StaticContext.getDialogOwner(), oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	private String getHeroldOutputFileName() {

		String fileName = "";

		JComponent comp = JiveFactory.getInstance().getWidget(
				new Identifier("herold.outputFile"));

		if (comp != null && comp instanceof JTextComponent) {
			fileName = ((JTextComponent) comp).getText();
		}

		return fileName;
	}

	public boolean checkIntegrity() {
		return true;
	}

	public String getBuildDirectory() {
		return buildDirectoryEntry.getText();
	}

	public String getDocBookFile() {
		return docBookFileEntry.getText();
	}

	public String getJdkHome() {

		if (jdkComboBox == null) {
			return null;
		}

		LabelItem item = (LabelItem) jdkComboBox.getSelectedItem();
		return (String) item.getValue();
	}

	public String getProjectDirectory() {
		return projectDirectoryEntry.getText();
	}

	public String getProjectFile() {
		return projectFileEntry.getText();
	}

	public String getProjectName() {
		return projectNameEntry.getText();
	}

	public void refresh() {

		ArrayList<LabelItem> jdkList = ctx.getAvailableJdkList();

		if (jdkComboBox == null) {

			if (jdkList.size() > 0) {
				addJdkComboBox();
			}

		} else if (jdkComboBox != null && jdkList.size() > 0) {

			jdkComboBox.removeAllItems();

			if (jdkList.size() == 0) {

				if (JvmServices.isJdk()) {

					File javaHome = JvmServices.getJdkHomeDirectory();
					LabelItem item = new LabelItem(javaHome.getName(),
							javaHome.getAbsolutePath());
					jdkList.add(item);
				}
			}

			for (LabelItem item : jdkList) {
				jdkComboBox.addItem(item);
			}

		} else if (jdkComboBox != null && jdkList.size() == 0) {
			jdkComboBox = null;
			addNoJdkErrorArea();
		}

		if (useHeroldLocationButton.isSelected() == true) {

			docBookFileEntry.setEnabled(false);
			docBookFileEntry.setText(getHeroldOutputFileName());
		}
	}

	public void setBuildDirectory(File buildDirectory) {
		buildDirectoryEntry.setText(buildDirectory.getAbsolutePath());
	}

	public void setDocBookFile(String path) {
		docBookFileEntry.setText(path);
	}

	public void setDocBookSource(String docBookSource) {

		if (docBookSource == null) {

			useCustomLocationButton.setSelected(true);
			return;
		}

		if (docBookSource.equals("herold")) {
			useHeroldLocationButton.setSelected(true);
			return;
		}

		if (docBookSource.equals("custom")) {
			useCustomLocationButton.setSelected(true);
			return;
		}

		useDocletOutputButton.setSelected(true);
	}

	public void setProjectDirectory(File projectDirectory) {
		projectDirectoryEntry.setText(projectDirectory.getAbsolutePath());
	}

	public void setProjectFile(File projectFile) {
		projectFileEntry.setText(projectFile.getAbsolutePath());
	}

	public void setProjectName(String projectName) {
		projectNameEntry.setText(projectName);
	}

	private JComboBox addJdkComboBox() {

		jdkPanel.removeAll();
		String toolTipText = jf.createToolTipText(ResourceServices.getString(
				res, "C_HELP_ENTRY_JDK"));

		JLabel label = jf.createLabel("JDK");
		label.setToolTipText(toolTipText);
		jdkPanel.addComponent(label);

		jdkComboBox = jf.createComboBox(new Identifier("jdk"));
		jdkComboBox.setToolTipText(toolTipText);
		jdkComboBox.setEditable(false);

		ArrayList<LabelItem> jdkList = ctx.getAvailableJdkList();
		for (LabelItem item : jdkList) {
			jdkComboBox.addItem(item);
		}

		jdkPanel.addComponent(jdkComboBox, Colspan.CS_2);
		revalidate();
		return jdkComboBox;
	}

	private void addJdkPanel(Context ctx) throws IOException {

		jdkPanel = new GridPanel();
		addComponent(jdkPanel, Colspan.CS_3);
		incrRow();

		ArrayList<LabelItem> jdkList = ctx.getAvailableJdkList();

		if (jdkList.size() == 0) {

			if (JvmServices.isJdk()) {

				File javaHome = JvmServices.getJdkHomeDirectory();
				LabelItem item = new LabelItem(javaHome.getName(),
						javaHome.getCanonicalPath());
				jdkList.add(item);
			}
		}

		if (jdkList.size() > 0) {
			addJdkComboBox();
		} else {
			jdkComboBox = null;
			addNoJdkErrorArea();
		}
	}

	private void addNoJdkErrorArea() {

		jdkPanel.removeAll();
		JTextComponent noJdkErrorArea = jf.createErrorArea(this,
				ResourceServices.getString(res, "C_ERROR_NO_JDK"));
		noJdkErrorArea.setPreferredSize(new Dimension(800, 120));
		jdkPanel.addComponent(noJdkErrorArea, Colspan.CS_4, Anchor.CENTER,
				Fill.BOTH);
		revalidate();
	}

	private void chooseDestinationDirectory() throws IOException {

		String destDir = buildDirectoryEntry.getText();

		JFileChooser fc = new JFileChooser(destDir);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File dir = fc.getSelectedFile();
			buildDirectoryEntry.setText(dir.getAbsolutePath());

			if (useDocletOutputButton.isSelected()) {
				docBookFileEntry.setText(dir.getAbsolutePath());
			}
		}
	}

	private void chooseDocBookFile() throws IOException {

		Project project = ctx.getProject();

		File dir = project.getProjectDirectory();

		String fileName = docBookFileEntry.getText();

		if (fileName != null && fileName.trim().length() > 0) {

			File file = new File(fileName);

			if (file.exists() && file.getParentFile() != null) {
				dir = file.getParentFile();
			}
		}

		JFileChooser fc = new JFileChooser(dir);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();
			docBookFileEntry.setText(file.getCanonicalPath());
		}
	}

	private void chooseProjectDirectory() throws IOException {

		String projectDir = projectDirectoryEntry.getText();

		JFileChooser fc = new JFileChooser(projectDir);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File dir = fc.getSelectedFile();
			projectDirectoryEntry.setText(dir.getAbsolutePath());
		}
	}

	private void chooseProjectFile() throws IOException {

		JFileChooser fc = new JFileChooser();

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File dir;
			String value;
			String path;

			File file = fc.getSelectedFile();

			projectFileEntry.setText(file.getCanonicalPath());

			value = projectDirectoryEntry.getText();

			if (value == null || value.trim().length() == 0) {

				projectDirectoryEntry.setText(file.getParentFile()
						.getCanonicalPath());
			}

			value = buildDirectoryEntry.getText();

			if (value == null || value.trim().length() == 0) {

				path = FileServices.appendFileName(file.getParentFile(),
						"build");
				buildDirectoryEntry.setText(path);
				dir = new File(path);

				if (dir.exists() == false) {
					FileServices.createPath(dir);
				}
			}
		}
	}

	/**
	 * Panel zur Auswahl der DocBook XML-Datei.
	 */
	private void createDocBookSection() {

		JLabel label = jf.createLabel(ResourceServices.getString(res,
				"C_DOCBOOK_FILE"));
		docBookFileEntry = jf.createTextField(new Identifier("docbook.file"),
				TEXTFIELD_WIDTH);
		addLabeledComponent(label, docBookFileEntry);

		JButton button = jf.createButton(ResourceServices.getString(res,
				"C_BROWSE"));
		button.setActionCommand("chooseDocBookFile");
		button.addActionListener(this);
		addComponent(button);
		incrRow();

		GridPanel panel = new GridPanel();

		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				ResourceServices.getString(res, "C_LINK_TO")));

		useDocletOutputButton = new JButton(ResourceServices.getString(res,
				"C_DOCLET_OUTPUT_FILE"));
		useDocletOutputButton.setName("bt.use-doclet-output");
		useDocletOutputButton.setSelected(false);
		useDocletOutputButton.setActionCommand("useDocletLocation");
		useDocletOutputButton.addActionListener(this);
		panel.addComponent(useDocletOutputButton, Anchor.CENTER, Fill.NONE,
				new Insets(5, 5, 5, 5));

		useHeroldLocationButton = new JButton(ResourceServices.getString(res,
				"C_HEROLD_OUTPUT_FILE"));
		useHeroldLocationButton.setSelected(false);
		useHeroldLocationButton.setActionCommand("useHeroldLocation");
		useHeroldLocationButton.addActionListener(this);
		panel.addComponent(useHeroldLocationButton, Anchor.CENTER, Fill.NONE,
				new Insets(5, 5, 5, 5));
		addComponent(panel);

		incrRow();

	}
}
