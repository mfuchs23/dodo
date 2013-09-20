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
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.NumberTextField;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class CalloutPanel extends AbstractPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	protected GridPanel defaultsPanel;
	private NumberTextField calloutDefaultColumn;
	private NumberTextField calloutGraphicsNumberLimit;
	private JCheckBox calloutGraphics;
	private JTextField calloutGraphicsExtension;
	private JTextField calloutGraphicsPath;
	private JButton calloutGraphicsPathBrowseButton;
	private JCheckBox calloutsExtension;
	private GridPanel unicodePanel;
	private NumberTextField calloutUnicodeNumberLimit;
	private JCheckBox calloutUnicode;
	private NumberTextField calloutUnicodeStartCharacter;

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);

		defaultsPanel = createDefaultsPanel();
		addComponent(defaultsPanel, Anchor.NORTHWEST, Fill.NONE);
		incrRow();

		unicodePanel = createUnicodePanel();
		addComponent(unicodePanel, Anchor.NORTHWEST, Fill.NONE);

		addVerticalGlue();
	}

	private GridPanel createUnicodePanel() {

		GridPanel panel = new GridPanel("Unicode");

		calloutUnicode = jf.createCheckBox(new Identifier(
				Constants.PARAM_CALLOUT_UNICODE),
				Constants.PARAM_CALLOUT_UNICODE);
		panel.addComponent(calloutUnicode);

		panel.startSubPanel();

		calloutUnicodeNumberLimit = jf.createNumberTextField(new Identifier(
				Constants.PARAM_CALLOUT_UNICODE_NUMBER_LIMIT), 3);
		panel.addLabeledComponent(Constants.PARAM_CALLOUT_UNICODE_NUMBER_LIMIT,
				calloutUnicodeNumberLimit);

		calloutUnicodeStartCharacter = jf.createNumberTextField(new Identifier(
				Constants.PARAM_CALLOUT_UNICODE_START_CHARACTER), 5);
		panel.addLabeledComponent(
				Constants.PARAM_CALLOUT_UNICODE_START_CHARACTER,
				calloutUnicodeStartCharacter);

		return panel;
	}

	private GridPanel createDefaultsPanel() {

		GridPanel panel = new GridPanel(ResourceServices.getString(res,
				"C_DEFAULT_VALUES"));

		panel.startSubPanel();

		calloutGraphics = jf.createCheckBox(new Identifier(
				Constants.PARAM_CALLOUT_GRAPHICS),
				Constants.PARAM_CALLOUT_GRAPHICS);
		panel.addComponent(calloutGraphics);

		calloutsExtension = jf.createCheckBox(new Identifier(
				Constants.PARAM_CALLOUTS_EXTENSION),
				Constants.PARAM_CALLOUTS_EXTENSION);
		panel.addComponent(calloutsExtension);

		panel.startSubPanel();

		calloutGraphicsExtension = jf.createTextField(new Identifier(
				Constants.PARAM_CALLOUT_GRAPHICS_EXTENSION), 8);
		panel.addLabeledComponent(Constants.PARAM_CALLOUT_GRAPHICS_EXTENSION,
				calloutGraphicsExtension);

		panel.startSubPanel();

		calloutGraphicsPath = jf.createTextField(new Identifier(
				Constants.PARAM_CALLOUT_GRAPHICS_PATH), 21);
		panel.addLabeledComponent(Constants.PARAM_CALLOUT_GRAPHICS_PATH,
				calloutGraphicsPath);

		calloutGraphicsPathBrowseButton = jf.createButton(ResourceServices
				.getString(res, "C_BROWSE"));
		calloutGraphicsPathBrowseButton.addActionListener(this);
		calloutGraphicsPathBrowseButton.setActionCommand("chooseGraphicsPath");
		panel.addComponent(calloutGraphicsPathBrowseButton);

		panel.startSubPanel();

		calloutDefaultColumn = jf.createNumberTextField(new Identifier(
				Constants.PARAM_CALLOUT_DEFAULTCOLUMN), 3);
		panel.addLabeledComponent(Constants.PARAM_CALLOUT_DEFAULTCOLUMN,
				calloutDefaultColumn);

		calloutGraphicsNumberLimit = jf.createNumberTextField(new Identifier(
				Constants.PARAM_CALLOUT_GRAPHICS_NUMBER_LIMIT), 3);
		panel.addLabeledComponent(
				Constants.PARAM_CALLOUT_GRAPHICS_NUMBER_LIMIT,
				calloutGraphicsNumberLimit);

		return panel;
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		calloutGraphics.setSelected(driver.isParameterEnabled(
				Constants.PARAM_CALLOUT_GRAPHICS, true));
		calloutsExtension.setSelected(driver.isParameterEnabled(
				Constants.PARAM_CALLOUTS_EXTENSION, true));
		calloutUnicode.setSelected(driver.isParameterEnabled(
				Constants.PARAM_CALLOUT_UNICODE, false));
		calloutUnicodeNumberLimit.setText(driver
				.getParameter(Constants.PARAM_CALLOUT_UNICODE_NUMBER_LIMIT));
		calloutUnicodeStartCharacter.setText(driver
				.getParameter(Constants.PARAM_CALLOUT_UNICODE_START_CHARACTER));
		calloutDefaultColumn.setText(driver
				.getParameter(Constants.PARAM_CALLOUT_DEFAULTCOLUMN));
		calloutGraphicsNumberLimit.setText(driver
				.getParameter(Constants.PARAM_CALLOUT_GRAPHICS_NUMBER_LIMIT));
		calloutGraphicsExtension.setText(driver
				.getParameter(Constants.PARAM_CALLOUT_GRAPHICS_EXTENSION));
		calloutGraphicsPath.setText(driver
				.getParameter(Constants.PARAM_CALLOUT_GRAPHICS_PATH));
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_CALLOUT_GRAPHICS,
				calloutGraphics.isSelected());
		driver.setParameter(Constants.PARAM_CALLOUTS_EXTENSION,
				calloutsExtension.isSelected());
		driver.setParameter(Constants.PARAM_CALLOUT_UNICODE,
				calloutUnicode.isSelected());
		driver.setParameter(Constants.PARAM_CALLOUT_UNICODE_NUMBER_LIMIT,
				calloutUnicodeNumberLimit.getText());
		driver.setParameter(Constants.PARAM_CALLOUT_UNICODE_START_CHARACTER,
				calloutUnicodeStartCharacter.getText());
		driver.setParameter(Constants.PARAM_CALLOUT_DEFAULTCOLUMN,
				calloutDefaultColumn.getText());
		driver.setParameter(Constants.PARAM_CALLOUT_GRAPHICS_NUMBER_LIMIT,
				calloutGraphicsNumberLimit.getText());
		driver.setParameter(Constants.PARAM_CALLOUT_GRAPHICS_EXTENSION,
				calloutGraphicsExtension.getText());
		driver.setParameter(Constants.PARAM_CALLOUT_GRAPHICS_PATH,
				calloutGraphicsPath.getText());
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		if (cmd == null) {
			return;
		}

		if (cmd.equals("chooseGraphicsPath")) {
			chooseDirectory(calloutGraphicsPath);
		}

	}
}
