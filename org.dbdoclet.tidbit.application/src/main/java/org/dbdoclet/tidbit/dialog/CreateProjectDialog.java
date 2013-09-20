package org.dbdoclet.tidbit.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ContinueBox;
import org.dbdoclet.jive.dialog.DataDialog;
import org.dbdoclet.jive.dialog.DialogAction;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.tidbit.common.Context;

public class CreateProjectDialog extends DataDialog implements ActionListener,
		DocumentListener {

	private static final long serialVersionUID = 1L;
	private static final int ENTRY_WIDTH = 29;
	private final Context context;

	private final JButton browseProjectDirectoryButton;
	private final JButton browseBuildDirectoryButton;
	private final JCheckBox useDefaultLocationCheckBox;
	private final JLabel projectDirectoryLabel;
	private final JLabel buildDirectoryLabel;
	private final JTextField buildDirectoryEntry;
	private final JTextField projectDirectoryEntry;
	private final JTextField projectFileEntry;
	private final ResourceBundle res;
	private final JTextField projectNameEntry;
	private final JButton browseProjectFileButton;
	private final JLabel projectFileLabel;

	public CreateProjectDialog(Context context, String title, boolean isModal) {

		super(context.getDialogOwner(), title, isModal);

		this.context = context;

		JLabel label;
		String toolTipText;

		JiveFactory wm = JiveFactory.getInstance();
		res = context.getResourceBundle();
		GridPanel panel = getGridPanel();

		toolTipText = wm.createToolTipText(ResourceServices.getString(res,
				"C_HELP_ENTRY_PROJECT_NAME"));
		label = wm.createLabel(ResourceServices
				.getString(res, "C_PROJECT_NAME"));
		label.setToolTipText(toolTipText);
		projectNameEntry = wm.createTextField(new Identifier("project.name"),
				ENTRY_WIDTH);
		projectNameEntry.setName("ProjectFileTextField");
		projectNameEntry.setToolTipText(toolTipText);
		projectNameEntry.getDocument().addDocumentListener(this);
		panel.addLabeledComponent(label, projectNameEntry);
		panel.incrRow();

		toolTipText = wm.createToolTipText(ResourceServices.getString(res,
				"C_HELP_ENTRY_PROJECT_DIRECTORY"));
		projectDirectoryLabel = wm.createLabel(ResourceServices.getString(res,
				"C_PROJECT_DIRECTORY"));
		projectDirectoryLabel.setToolTipText(toolTipText);
		projectDirectoryEntry = wm.createTextField(new Identifier(
				"project.directory"), ENTRY_WIDTH);
		projectDirectoryEntry.setToolTipText(toolTipText);
		projectDirectoryEntry.getDocument().addDocumentListener(this);
		projectDirectoryLabel.setLabelFor(projectDirectoryEntry);

		panel.addLabeledComponent(projectDirectoryLabel, projectDirectoryEntry,
				Fill.HORIZONTAL);

		browseProjectDirectoryButton = wm.createButton(ResourceServices
				.getString(res, "C_BROWSE"));
		browseProjectDirectoryButton.setActionCommand("chooseProjectDirectory");
		browseProjectDirectoryButton.addActionListener(this);
		panel.addComponent(browseProjectDirectoryButton);
		panel.incrRow();

		useDefaultLocationCheckBox = wm.createCheckBox(new Identifier(
				"use.default.location"), ResourceServices.getString(res,
				"C_USE_DEFAULT_LOCATIONS"));
		useDefaultLocationCheckBox.setSelected(true);
		useDefaultLocationCheckBox.setActionCommand("useDefaultLocation");
		useDefaultLocationCheckBox.addActionListener(this);
		panel.addComponent(useDefaultLocationCheckBox, Colspan.CS_2);
		panel.incrRow();

		toolTipText = wm.createToolTipText(ResourceServices.getString(res,
				"C_HELP_ENTRY_PROJECT_FILE"));
		projectFileLabel = wm.createLabel(ResourceServices.getString(res,
				"C_PROJECT_FILE"));
		projectFileLabel.setEnabled(false);
		projectFileLabel.setToolTipText(toolTipText);
		projectFileEntry = wm.createTextField(new Identifier("project.file"),
				ENTRY_WIDTH);
		projectFileEntry.setName("ProjectFileTextField");
		projectFileEntry.setEnabled(false);
		projectFileEntry.setToolTipText(toolTipText);
		panel.addLabeledComponent(projectFileLabel, projectFileEntry);
		projectFileLabel.setLabelFor(projectFileEntry);

		browseProjectFileButton = wm.createButton(ResourceServices.getString(
				res, "C_BROWSE"));
		browseProjectFileButton.setEnabled(false);
		browseProjectFileButton.setActionCommand("chooseProjectFile");
		browseProjectFileButton.addActionListener(this);
		panel.addComponent(browseProjectFileButton);
		panel.incrRow();

		toolTipText = wm.createToolTipText(ResourceServices.getString(res,
				"C_HELP_ENTRY_DESTINATION_DIRECTORY"));
		buildDirectoryLabel = wm.createLabel(ResourceServices.getString(res,
				"C_DESTINATION_DIRECTORY"));
		buildDirectoryLabel.setToolTipText(toolTipText);
		buildDirectoryLabel.setEnabled(false);
		buildDirectoryEntry = wm.createTextField(new Identifier(
				"destination.directory"), ENTRY_WIDTH);
		buildDirectoryEntry.setToolTipText(toolTipText);
		buildDirectoryEntry.setEnabled(false);
		panel.addLabeledComponent(buildDirectoryLabel, buildDirectoryEntry);

		browseBuildDirectoryButton = wm.createButton(ResourceServices
				.getString(res, "C_BROWSE"));
		browseBuildDirectoryButton.setEnabled(false);
		browseBuildDirectoryButton
				.setActionCommand("chooseDestinationDirectory");
		browseBuildDirectoryButton.addActionListener(this);
		panel.addComponent(browseBuildDirectoryButton);
		panel.incrRow();

		JButton button = new JButton(ResourceServices.getString(res, "C_OK"));
		button.setMnemonic(KeyEvent.VK_O);
		button.setName("OkButton");
		button.setActionCommand("ok");
		button.addActionListener(this);
		panel.addButton(button);

		button = new JButton(ResourceServices.getString(res, "C_CANCEL"));
		button.setActionCommand("cancel");
		button.addActionListener(this);
		panel.addButton(button);
		panel.prepare();

		pack();
		center(context.getDialogOwner());
	}

	public void actionPerformed(ActionEvent event) {

		try {

			String cmd = event.getActionCommand();

			if (cmd == null) {
				return;
			}

			if (cmd.equals("ok")) {
				if (validateInput() == true) {
					setVisible(false);
				}
			}

			if (cmd.equals("cancel")) {
				setPerformedAction(DialogAction.CANCEL);
				setVisible(false);
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

			if (cmd.equals("useDefaultLocation")) {

				if (useDefaultLocationCheckBox.isSelected()) {

					projectFileLabel.setEnabled(false);
					projectFileEntry.setEnabled(false);
					browseProjectFileButton.setEnabled(false);

					buildDirectoryLabel.setEnabled(false);
					buildDirectoryEntry.setEnabled(false);
					browseBuildDirectoryButton.setEnabled(false);

				} else {

					projectFileLabel.setEnabled(true);
					projectFileEntry.setEnabled(true);
					browseProjectFileButton.setEnabled(true);

					buildDirectoryLabel.setEnabled(true);
					buildDirectoryEntry.setEnabled(true);
					browseBuildDirectoryButton.setEnabled(true);
				}
			}

		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(context.getDialogOwner(), oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	public void changedUpdate(DocumentEvent e) {
		updateEntries();
	}

	private void chooseDestinationDirectory() throws IOException {

		String destDir = buildDirectoryEntry.getText();

		JFileChooser fc = new JFileChooser(destDir);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File dir = fc.getSelectedFile();
			buildDirectoryEntry.setText(dir.getAbsolutePath());
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

			File file = fc.getSelectedFile();
			projectFileEntry.setText(file.getCanonicalPath());
		}
	}

	public String getBuildDirectory() {
		return buildDirectoryEntry.getText();
	}

	public String getProjectDirectory() {
		return projectDirectoryEntry.getText();
	}

	public String getProjectFileName() {
		return projectFileEntry.getText();
	}

	public String getProjectName() {
		return projectNameEntry.getText();
	}

	public void insertUpdate(DocumentEvent e) {
		updateEntries();
	}

	public void removeUpdate(DocumentEvent e) {
		updateEntries();
	}

	private void updateEntries() {

		unsetError();

		String projectDirectory = projectDirectoryEntry.getText();

		if (useDefaultLocationCheckBox.isSelected()) {

			if (projectDirectory != null && projectDirectory.length() > 0) {

				String path = FileServices.appendFileName(projectDirectory,
						"dbdoclet.xml");
				projectFileEntry.setText(new File(path).getAbsolutePath());

				path = FileServices.appendPath(projectDirectory, "build");
				buildDirectoryEntry.setText(new File(path).getAbsolutePath());
			}
		}

	}

	private boolean validateInput() {

		String projectName = getProjectName();
		String projectFileName = getProjectFileName();
		String projectDirectory = getProjectDirectory();
		String buildDirectory = getBuildDirectory();

		if (projectName == null || projectName.trim().length() == 0) {
			setError(ResourceServices.getString(res,
					"C_HINT_PROJECT_NAME_MUST_BE_SET"));
			return false;
		}

		if (projectDirectory == null || projectDirectory.trim().length() == 0) {
			setError(ResourceServices.getString(res,
					"C_HINT_PROJECT_DIRECTORY_MUST_BE_SET"));
			projectDirectoryEntry.requestFocusInWindow();
			return false;
		}

		if (projectFileName == null || projectFileName.trim().length() == 0) {
			setError(ResourceServices.getString(res,
					"C_HINT_PROJECT_FILE_MUST_BE_SET"));
			return false;
		}

		if (buildDirectory == null || buildDirectory.trim().length() == 0) {
			setError(ResourceServices.getString(res,
					"C_HINT_BUILD_DIRECTORY_MUST_BE_SET"));
			return false;
		}

		File pdir = new File(projectDirectory);
		File bdir = new File(buildDirectory);

		if (bdir.equals(pdir)) {
			setError(ResourceServices
					.getString(res,
							"C_HINT_PROJECT_DIRECTORY_BUILD_DIRECTORY_MUST_NOT_BE_EQUAL"));
			return false;
		}

		if (pdir.exists() && FileServices.isEmptyDirectory(pdir) == false) {

			boolean cont = ContinueBox.show(this, ResourceServices.getString(
					res, "C_WARNING"), MessageFormat.format(ResourceServices
					.getString(res,
							"C_WARNING_PROJECT_DIRECTORY_ALREADY_EXISTS"),
					StringServices.makeWrapable(pdir.getAbsolutePath())));

			if (cont == false) {
				setError(ResourceServices.getString(res,
						"C_HINT_PROJECT_DIRECTORY_MUST_BE_EMPTY"));
				return false;
			} else {
				return true;
			}
		}

		if (bdir.exists()) {
			setError(ResourceServices.getString(res,
					"C_HINT_BUILD_DIRECTORY_ALREADY_EXISTS"));
			return false;
		}

		return true;
	}
}
