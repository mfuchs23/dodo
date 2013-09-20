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
package org.dbdoclet.tidbit.perspective.panel.docbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.dialog.GenerateTocDialog;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class TocPanel extends AbstractPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JCheckBox bridgeHeadInTocCheckBox;
	private JCheckBox simpleSectInTocCheckBox;
	private JSpinner tocSectionDepth;
	private JSpinner generateSectionTocLevel;
	private JButton generateToc;
	private JCheckBox processEmptySourceToc;
	private JCheckBox processSourceToc;
	private JSpinner tocMaxDepth;
	private JSpinner tocIndentWidth;
	private JTextField autotocLabelSeparator;
	private String generateTocParam;

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);
		// setBorder(BorderFactory.createLineBorder(Color.red));

		GridPanel tocPanel = createTocPanel();
		addComponent(tocPanel, Anchor.NORTHWEST);
	}

	private GridPanel createTocPanel() {

		GridPanel tocPanel = new GridPanel(ResourceServices.getString(res,
				"C_DEFAULT_VALUES"));

		tocPanel.startSubPanel();

		generateToc = jf.createButton(new Identifier(
				Constants.PARAM_GENERATE_TOC),
				Constants.PARAM_GENERATE_TOC + "...");
		tocPanel.addComponent(generateToc);
		generateToc.setActionCommand(Constants.PARAM_GENERATE_TOC);
		generateToc.addActionListener(this);
		
		processEmptySourceToc = jf.createCheckBox(new Identifier(
				Constants.PARAM_PROCESS_EMPTY_SOURCE_TOC),
				Constants.PARAM_PROCESS_EMPTY_SOURCE_TOC);
		tocPanel.addComponent(processEmptySourceToc);

		processSourceToc = jf.createCheckBox(new Identifier(
				Constants.PARAM_PROCESS_SOURCE_TOC),
				Constants.PARAM_PROCESS_SOURCE_TOC);
		tocPanel.addComponent(processSourceToc);

		simpleSectInTocCheckBox = jf.createCheckBox(new Identifier(
				Constants.PARAM_SIMPLESECT_IN_TOC),
				Constants.PARAM_SIMPLESECT_IN_TOC);
		tocPanel.addComponent(simpleSectInTocCheckBox);

		bridgeHeadInTocCheckBox = jf.createCheckBox(new Identifier(
				Constants.PARAM_BRIDGEHEAD_IN_TOC),
				Constants.PARAM_BRIDGEHEAD_IN_TOC);
		tocPanel.addComponent(bridgeHeadInTocCheckBox);

		tocPanel.startSubPanel();

		tocSectionDepth = createNumberSpinner();
		tocPanel.addLabeledComponent(Constants.PARAM_TOC_SECTION_DEPTH,
				tocSectionDepth);

		tocMaxDepth = createNumberSpinner();
		tocPanel.addLabeledComponent(Constants.PARAM_TOC_MAX_DEPTH, tocMaxDepth);

		tocIndentWidth = createNumberSpinner();
		tocPanel.addLabeledComponent(Constants.PARAM_TOC_INDENT_WIDTH,
				tocIndentWidth);

		tocPanel.startSubPanel();

		generateSectionTocLevel = createNumberSpinner();
		tocPanel.addLabeledComponent(
				Constants.PARAM_GENERATE_SECTION_TOC_LEVEL,
				generateSectionTocLevel);

		autotocLabelSeparator = jf.createTextField(new Identifier(Constants.PARAM_AUTOTOC_LABEL_SEPARATOR), 5);
		tocPanel.addLabeledComponent(Constants.PARAM_AUTOTOC_LABEL_SEPARATOR,
				autotocLabelSeparator);

		return tocPanel;
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		generateTocParam = driver.getParameter(Constants.PARAM_GENERATE_TOC);
				
		processEmptySourceToc.setSelected(driver.isParameterEnabled(
				Constants.PARAM_PROCESS_EMPTY_SOURCE_TOC, false));
		processSourceToc.setSelected(driver.isParameterEnabled(
				Constants.PARAM_PROCESS_SOURCE_TOC, false));
		tocSectionDepth.setValue(driver.getNumberParameter(
				Constants.PARAM_TOC_SECTION_DEPTH, 2));
		tocMaxDepth.setValue(driver.getNumberParameter(
				Constants.PARAM_TOC_MAX_DEPTH, 2));
		tocIndentWidth.setValue(driver.getNumberParameter(
				Constants.PARAM_TOC_INDENT_WIDTH, 2));
		bridgeHeadInTocCheckBox.setSelected(driver.isParameterEnabled(
				Constants.PARAM_BRIDGEHEAD_IN_TOC, false));
		simpleSectInTocCheckBox.setSelected(driver.isParameterEnabled(
				Constants.PARAM_SIMPLESECT_IN_TOC, false));
		generateSectionTocLevel.setValue(driver.getNumberParameter(
				Constants.PARAM_GENERATE_SECTION_TOC_LEVEL, 2));
		autotocLabelSeparator.setText(driver
				.getParameter(Constants.PARAM_AUTOTOC_LABEL_SEPARATOR));
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_GENERATE_TOC, generateTocParam);
		
		driver.setParameter(Constants.PARAM_PROCESS_EMPTY_SOURCE_TOC,
				processEmptySourceToc.isSelected());
		driver.setParameter(Constants.PARAM_PROCESS_SOURCE_TOC,
				processSourceToc.isSelected());
		driver.setParameter(Constants.PARAM_TOC_SECTION_DEPTH,
				tocSectionDepth.getValue());
		driver.setParameter(Constants.PARAM_TOC_MAX_DEPTH,
				tocMaxDepth.getValue());
		driver.setParameter(Constants.PARAM_TOC_INDENT_WIDTH,
				tocIndentWidth.getValue());
		driver.setParameter(Constants.PARAM_BRIDGEHEAD_IN_TOC,
				bridgeHeadInTocCheckBox.isSelected());
		driver.setParameter(Constants.PARAM_SIMPLESECT_IN_TOC,
				simpleSectInTocCheckBox.isSelected());
		driver.setParameter(Constants.PARAM_GENERATE_SECTION_TOC_LEVEL,
				generateSectionTocLevel.getValue());
		driver.setParameter(Constants.PARAM_AUTOTOC_LABEL_SEPARATOR,
				autotocLabelSeparator.getText());
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		if (cmd == null) {
			return;
		}
		
		if (cmd.equals(Constants.PARAM_GENERATE_TOC)) {
			GenerateTocDialog dlg = new GenerateTocDialog(StaticContext.getDialogOwner(), Constants.PARAM_GENERATE_TOC, generateTocParam);
			dlg.setVisible(true);
			
			if (dlg.isCanceled() == false) {
				generateTocParam = dlg.getGenerateTocParam();
			}
		}
	}
}
