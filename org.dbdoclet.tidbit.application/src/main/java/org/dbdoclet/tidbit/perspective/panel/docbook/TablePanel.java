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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;
import org.dbdoclet.unit.Length;
import org.dbdoclet.unit.LengthUnit;

public class TablePanel extends AbstractPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JSpinner defaultWidthSpinner;
	private JSpinner nominalWidthSpinner;
	private JComboBox frameComboBox;
	private JComboBox rulesComboBox;
	private JSpinner frameThicknessSpinner;
	private JComboBox frameStyleComboBox;
	private JButton frameColorButton;
	private JSpinner cellThicknessSpinner;
	private JComboBox cellStyleComboBox;
	private JButton cellColorButton;
	private Color frameColor;
	private Color cellColor;
	protected GridPanel framePanel;

	private JCheckBox tablecolumnsExtension;

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);

		JPanel defaultsPanel = createDefaultsPanel();
		addComponent(defaultsPanel, Anchor.NORTHWEST);
		incrRow();

		framePanel = createFramePanel();
		addComponent(framePanel, Anchor.NORTHWEST);
		incrRow();

		JPanel cellPanel = createCellPanel();
		addComponent(cellPanel, Anchor.NORTHWEST);

		addVerticalGlue();
		addHorizontalGlue();
	}

	private JPanel createCellPanel() {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(ResourceServices
				.getString(res, "C_TABLE_CELL")));

		cellThicknessSpinner = jf.createDistanceSpinner(new Identifier(
				Constants.PARAM_TABLE_CELL_BORDER_THICKNESS), new Length(
				StaticContext.getLocale(), 1.0, LengthUnit.POINT));
		panel.addLabeledComponent(
				ResourceServices.getString(res, "C_THICKNESS"),
				cellThicknessSpinner);

		cellStyleComboBox = jf.createComboBox(new Identifier(
				Constants.PARAM_DEFAULT_TABLE_RULES),
				Constants.FRAME_STYLE_LIST);
		panel.addLabeledComponent(
				ResourceServices.getString(res, "C_RULES_TYPE"),
				cellStyleComboBox);

		cellColorButton = jf.createButton(Constants.COLOR_CHAR + " "
				+ ResourceServices.getString(res, "C_COLOR") + "...");
		cellColorButton.addActionListener(this);
		panel.addComponent(cellColorButton);

		return panel;
	}

	private GridPanel createFramePanel() {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(ResourceServices
				.getString(res, "C_FRAME")));

		frameThicknessSpinner = jf.createDistanceSpinner(new Identifier(
				Constants.PARAM_TABLE_FRAME_BORDER_THICKNESS), new Length(
				StaticContext.getLocale(), 1.0, LengthUnit.POINT));
		panel.addLabeledComponent(
				ResourceServices.getString(res, "C_THICKNESS"),
				frameThicknessSpinner);

		frameStyleComboBox = jf.createComboBox(new Identifier(
				Constants.PARAM_DEFAULT_TABLE_FRAME),
				Constants.FRAME_STYLE_LIST);
		panel.addLabeledComponent(
				ResourceServices.getString(res, "C_FRAME_STYLE"),
				frameStyleComboBox);

		frameColorButton = jf.createButton(Constants.COLOR_CHAR + " "
				+ ResourceServices.getString(res, "C_COLOR") + "...");
		frameColorButton.addActionListener(this);
		panel.addComponent(frameColorButton);

		return panel;
	}

	private JPanel createDefaultsPanel() {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(ResourceServices
				.getString(res, "C_DEFAULT_VALUES")));

		panel.startSubPanel();

		tablecolumnsExtension = jf.createCheckBox(new Identifier(
				Constants.PARAM_TABLECOLUMNS_EXTENSION),
				Constants.PARAM_TABLECOLUMNS_EXTENSION);
		panel.addComponent(tablecolumnsExtension);

		defaultWidthSpinner = jf.createWidthSpinner(new Identifier(
				Constants.PARAM_DEFAULT_TABLE_WIDTH));
		panel.addLabeledComponent(ResourceServices.getString(res, "C_WIDTH"),
				defaultWidthSpinner);

		nominalWidthSpinner = jf.createWidthSpinner(new Identifier(
				Constants.PARAM_NOMINAL_TABLE_WIDTH));
		panel.addLabeledComponent(
				ResourceServices.getString(res, "C_NOMINAL_WIDTH"),
				nominalWidthSpinner);

		panel.startSubPanel();

		frameComboBox = jf
				.createComboBox(new Identifier(
						Constants.PARAM_DEFAULT_TABLE_FRAME),
						Constants.FRAME_TYPE_LIST);
		panel.addLabeledComponent(
				ResourceServices.getString(res, "C_FRAME_TYPE"), frameComboBox);

		rulesComboBox = jf
				.createComboBox(new Identifier(
						Constants.PARAM_DEFAULT_TABLE_RULES),
						Constants.RULES_TYPE_LIST);
		panel.addLabeledComponent(
				ResourceServices.getString(res, "C_RULES_TYPE"), rulesComboBox);

		panel.leaveSubPanel();
		
		return panel;
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		tablecolumnsExtension.setSelected(driver.isParameterEnabled(
				Constants.PARAM_TABLECOLUMNS_EXTENSION, true));
		defaultWidthSpinner.setValue(Length.valueOf(driver.getParameter(
				Constants.PARAM_DEFAULT_TABLE_WIDTH, "100%")));
		nominalWidthSpinner.setValue(Length.valueOf(driver
				.getParameter(Constants.PARAM_NOMINAL_TABLE_WIDTH)));
		frameComboBox.setSelectedItem(driver
				.getParameter(Constants.PARAM_DEFAULT_TABLE_FRAME));
		rulesComboBox.setSelectedItem(driver
				.getParameter(Constants.PARAM_DEFAULT_TABLE_RULES));

		frameThicknessSpinner.setValue(Length.valueOf(driver
				.getParameter(Constants.PARAM_TABLE_FRAME_BORDER_THICKNESS)));
		frameStyleComboBox.setSelectedItem(driver
				.getParameter(Constants.PARAM_TABLE_FRAME_BORDER_STYLE));
		frameColor = driver
				.getColorParameter(Constants.PARAM_TABLE_FRAME_BORDER_COLOR);
		frameColorButton.setForeground(frameColor);

		cellThicknessSpinner.setValue(Length.valueOf(driver
				.getParameter(Constants.PARAM_TABLE_CELL_BORDER_THICKNESS)));
		cellStyleComboBox.setSelectedItem(driver
				.getParameter(Constants.PARAM_TABLE_CELL_BORDER_STYLE));
		cellColor = driver
				.getColorParameter(Constants.PARAM_TABLE_CELL_BORDER_COLOR);
		cellColorButton.setForeground(cellColor);
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_TABLECOLUMNS_EXTENSION,
				tablecolumnsExtension.isSelected());
		driver.setParameter(Constants.PARAM_DEFAULT_TABLE_WIDTH,
				getDistance(defaultWidthSpinner));
		driver.setParameter(Constants.PARAM_NOMINAL_TABLE_WIDTH,
				getDistance(nominalWidthSpinner));
		driver.setParameter(Constants.PARAM_DEFAULT_TABLE_FRAME,
				frameComboBox.getSelectedItem());
		driver.setParameter(Constants.PARAM_DEFAULT_TABLE_RULES,
				rulesComboBox.getSelectedItem());

		driver.setParameter(Constants.PARAM_TABLE_FRAME_BORDER_THICKNESS,
				getDistance(frameThicknessSpinner));
		driver.setParameter(Constants.PARAM_TABLE_FRAME_BORDER_STYLE,
				frameStyleComboBox.getSelectedItem());
		driver.setColorParameter(Constants.PARAM_TABLE_FRAME_BORDER_COLOR,
				frameColor);

		driver.setParameter(Constants.PARAM_TABLE_CELL_BORDER_THICKNESS,
				getDistance(cellThicknessSpinner));
		driver.setParameter(Constants.PARAM_TABLE_CELL_BORDER_STYLE,
				cellStyleComboBox.getSelectedItem());
		driver.setColorParameter(Constants.PARAM_TABLE_CELL_BORDER_COLOR,
				cellColor);
	}

	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		if (source != null && source == frameColorButton) {

			Color color = JColorChooser.showDialog(this,
					ResourceServices.getString(res, "C_COLOR"), frameColor);

			if (color != null) {
				frameColor = color;
				frameColorButton.setForeground(color);
			}
		}

		if (source != null && source == cellColorButton) {

			Color color = JColorChooser.showDialog(this,
					ResourceServices.getString(res, "C_COLOR"), cellColor);

			if (color != null) {
				cellColor = color;
				cellColorButton.setForeground(color);
			}
		}
	}
}
