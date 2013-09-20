/* 
 * ### Copyright (C) 2005-2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.perspective.dbdoclet;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.SortedMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.Identifier;
import org.dbdoclet.doclet.docbook.DbdScript;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.NumberTextField;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.common.Visibility;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.Project;

public class JavadocPanel extends GridPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(JavadocPanel.class);

	private final JiveFactory wm;
	private final ResourceBundle res;
	private JTextField overviewFileEntry;
	private NumberTextField jvmMaxMemoryEntry;
	private JComboBox sourceVersionComboBox;
	private JComboBox sourceEncodingComboBox;
	private JComboBox destinationEncodingComboBox;
	private JCheckBox linkSourceCheckBox;
	private JRadioButton publicRadioButton;
	private JRadioButton protectedRadioButton;
	private JRadioButton packageRadioButton;
	private JRadioButton privateRadioButton;
	private File baseDir;

	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}

	public JavadocPanel(Perspective perspective) {

		super();

		if (perspective == null) {
			throw new IllegalArgumentException(
					"The argument perspective must not be null!");
		}

		res = StaticContext.getResourceBundle();
		wm = JiveFactory.getInstance();

		startSubPanel(Fill.HORIZONTAL);

		addComponent(createOverviewPanel(wm, res), Anchor.NORTHWEST,
				Fill.HORIZONTAL);

		startSubPanel();

		addComponent(createOptionPanel(wm, res), Anchor.NORTHWEST);
		addComponent(createEncodingPanel(wm, res), Anchor.NORTHWEST);

		startSubPanel();

		addComponent(createVisibilityPanel(wm, res), Anchor.NORTHWEST);
		// addComponent(createFlagsPanel(jf, res), Anchor.NORTHWEST);

		leaveSubPanel();

		addVerticalGlue();
	}

	public void actionPerformed(ActionEvent event) {

		logger.debug("event=" + event);

		try {

			String cmd = event.getActionCommand();

			if (cmd == null) {
				return;
			}

			if (cmd.equals("chooseOverviewFile")) {
				chooseOverviewFile(baseDir);
			}

		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(
					StaticContext.getDialogOwner(), oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	public String getDestinationEncoding() {
		return (String) destinationEncodingComboBox.getSelectedItem();
	}

	public String getJvmMaxMemory() {
		return jvmMaxMemoryEntry.getText();
	}

	public String getOverviewFile() {
		return overviewFileEntry.getText();
	}

	public String getSourceEncoding() {
		return (String) sourceEncodingComboBox.getSelectedItem();
	}

	public String getSourceVersion() {
		return (String) sourceVersionComboBox.getSelectedItem();
	}

	public boolean isLinkSourceEnabled() {
		return linkSourceCheckBox.isSelected();
	}

	public void setDestinationEncoding(String value) {
		destinationEncodingComboBox.setSelectedItem(value);
	}

	public void setJvmMaxMemory(String value) {
		jvmMaxMemoryEntry.setText(value);
	}

	public void setOverviewFile(String fileName) {
		overviewFileEntry.setText(fileName);
	}

	public void setSourceEncoding(String value) {
		sourceEncodingComboBox.setSelectedItem(value);
	}

	public void setSourceVersion(String value) {
		sourceVersionComboBox.setSelectedItem(value);
	}

	public void syncModel(Project project, DbdScript script) {

		script.setDestinationEncoding(getDestinationEncoding());
		project.setDestinationEncoding(getDestinationEncoding());
		project.setOverviewPath(getOverviewFile());
		project.setJvmMaxMemory(getJvmMaxMemory());
		project.setSourceVersion(getSourceVersion());
		project.setLinkSourceEnabled(isLinkSourceEnabled());

		if (privateRadioButton.isSelected()) {
			project.setVisibility(Visibility.PRIVATE);
		}

		if (protectedRadioButton.isSelected()) {
			project.setVisibility(Visibility.PROTECTED);
		}

		if (packageRadioButton.isSelected()) {
			project.setVisibility(Visibility.PACKAGE);
		}

		if (publicRadioButton.isSelected()) {
			project.setVisibility(Visibility.PUBLIC);
		}
	}

	public void syncView(Project project, DbdScript script) {

		setBaseDir(project.getBaseDir());

		setOverviewFile(project.getOverviewPath());
		setJvmMaxMemory(project.getJvmMaxMemory());
		setSourceVersion(project.getSourceVersion());
		setDestinationEncoding(script.getDestinationEncoding());
		setLinkSourceEnabled(project.isLinkSourceEnabled());

		if (project.getVisibility() == Visibility.PRIVATE) {
			privateRadioButton.setSelected(true);
		}

		if (project.getVisibility() == Visibility.PROTECTED) {
			protectedRadioButton.setSelected(true);
		}

		if (project.getVisibility() == Visibility.PACKAGE) {
			packageRadioButton.setSelected(true);
		}

		if (project.getVisibility() == Visibility.PUBLIC) {
			publicRadioButton.setSelected(true);
		}
	}

	private void chooseOverviewFile(File projectDir) {

		JFileChooser fc = new JFileChooser(projectDir);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File dir = fc.getSelectedFile();
			overviewFileEntry.setText(dir.getAbsolutePath());
		}
	}

	private JPanel createEncodingPanel(JiveFactory wm, ResourceBundle res) {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				res.getString("C_ENCODING")));

		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.gridwidth = 1;

		SortedMap<String, Charset> map = Charset.availableCharsets();
		Iterator<String> iterator = map.keySet().iterator();

		String[] encodings = new String[map.size()];

		int index = 0;
		while (iterator.hasNext()) {
			encodings[index++] = iterator.next();
		}

		JLabel label;

		label = wm.createLabel(new Identifier("javadoc.encoding.source"),
				ResourceServices.getString(res, "C_ENCODING_SOURCE"));

		gbc.gridwidth = GridBagConstraints.RELATIVE;
		panel.add(label, gbc);

		sourceEncodingComboBox = wm.createComboBox(new Identifier(
				"encoding.source"), encodings);
		sourceEncodingComboBox.setSelectedItem("UTF-8");

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(sourceEncodingComboBox, gbc);

		label = wm.createLabel(new Identifier("javadoc.encoding.destination"),
				ResourceServices.getString(res, "C_ENCODING_DESTINATION"));

		gbc.gridwidth = GridBagConstraints.RELATIVE;
		panel.add(label, gbc);

		destinationEncodingComboBox = wm.createComboBox(new Identifier(
				"encoding.destination"), encodings);
		destinationEncodingComboBox.setSelectedItem("UTF-8");

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(destinationEncodingComboBox, gbc);

		return panel;
	}

	private GridPanel createOptionPanel(JiveFactory wm, ResourceBundle res) {

		GridPanel panel = new GridPanel();

		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				res.getString("C_PARAMETERS")));

		JLabel label;

		linkSourceCheckBox = wm.createCheckBox(new Identifier("-linksource"),
				"-linksource");
		panel.addComponent(linkSourceCheckBox);
		panel.incrRow();

		label = wm.createLabel(ResourceServices.getString(res, "C_SOURCE"));
		panel.addComponent(label);

		String[] sourceList = { "---", "1.3", "1.4", "1.5", "1.6", "1.7" };
		sourceVersionComboBox = wm.createComboBox(
				new Identifier("java.version"), sourceList);
		sourceVersionComboBox.setSelectedItem("1.5");
		panel.addComponent(sourceVersionComboBox);
		panel.incrRow();

		label = wm.createLabel(ResourceServices.getString(res,
				"C_MAXIMUM_MEMORY"));
		panel.addComponent(label);

		jvmMaxMemoryEntry = wm.createNumberTextField(new Identifier(
				"jvm.maxmemory"), 6);
		panel.addComponent(jvmMaxMemoryEntry);

		return panel;
	}

	private GridPanel createOverviewPanel(JiveFactory wm, ResourceBundle res) {

		GridPanel panel = new GridPanel();

		JLabel label = wm.createLabel(ResourceServices.getString(res,
				"C_OVERVIEW_FILE"));
		overviewFileEntry = wm.createTextField(new Identifier("overview.file"),
				32);
		overviewFileEntry.setEditable(false);
		panel.addLabeledComponent(label, overviewFileEntry);

		JButton button = new JButton(
				ResourceServices.getString(res, "C_BROWSE"));
		button.setActionCommand("chooseOverviewFile");
		button.addActionListener(this);
		panel.addComponent(button);

		panel.addHorizontalGlue();

		return panel;
	}

	private JPanel createVisibilityPanel(JiveFactory wm, ResourceBundle res) {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				res.getString("C_VISIBILITY")));

		ButtonGroup group = new ButtonGroup();

		publicRadioButton = new JRadioButton(ResourceServices.getString(res,
				"C_PUBLIC"));
		publicRadioButton.setActionCommand("public");
		publicRadioButton.setSelected(true);
		group.add(publicRadioButton);
		panel.addComponent(publicRadioButton);

		protectedRadioButton = new JRadioButton(ResourceServices.getString(res,
				"C_PROTECTED"));
		protectedRadioButton.setActionCommand("protected");
		protectedRadioButton.setSelected(true);
		group.add(protectedRadioButton);
		panel.addComponent(protectedRadioButton);

		packageRadioButton = new JRadioButton(ResourceServices.getString(res,
				"C_PACKAGE"));
		packageRadioButton.setActionCommand("package");
		packageRadioButton.setSelected(true);
		group.add(packageRadioButton);
		panel.addComponent(packageRadioButton);

		privateRadioButton = new JRadioButton(ResourceServices.getString(res,
				"C_PRIVATE"));
		privateRadioButton.setActionCommand("private");
		privateRadioButton.setSelected(true);
		group.add(privateRadioButton);
		panel.addComponent(privateRadioButton);

		return panel;
	}

	private void setLinkSourceEnabled(boolean linkSourceEnabled) {
		linkSourceCheckBox.setSelected(linkSourceEnabled);
	}
}
