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

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.NumberTextField;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class VerbatimPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	protected GridPanel defaultsPanel;
	private JCheckBox shadeVerbatim;
	private GridPanel lineNumberingPanel;
	private JCheckBox linenumberingExtension;
	private NumberTextField linenumberingEveryNth;
	private NumberTextField linenumberingWidth;
	private JTextField linenumberingSeparator;

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);

		defaultsPanel = createDefaultsPanel();
		addComponent(defaultsPanel, Anchor.NORTHWEST);

		incrRow();

		lineNumberingPanel = createLinenumberingPanel();
		addComponent(lineNumberingPanel, Anchor.NORTHWEST);
	}

	private GridPanel createLinenumberingPanel() {

		GridPanel panel = new GridPanel(ResourceServices.getString(res,
				"C_LINE_NUMBERING"));

		panel.startSubPanel();

		linenumberingExtension = jf.createCheckBox(new Identifier(
				Constants.PARAM_LINENUMBERING_EXTENSION),
				Constants.PARAM_LINENUMBERING_EXTENSION);
		panel.addComponent(linenumberingExtension);

		linenumberingEveryNth = jf.createNumberTextField(new Identifier(
				Constants.PARAM_LINENUMBERING_EVERY_NTH), 2);
		panel.addLabeledComponent(Constants.PARAM_LINENUMBERING_EVERY_NTH,
				linenumberingEveryNth);

		panel.startSubPanel();

		linenumberingWidth = jf.createNumberTextField(
				new Identifier(Constants.PARAM_LINENUMBERING_WIDTH), 5);
		panel.addLabeledComponent(Constants.PARAM_LINENUMBERING_WIDTH,
				linenumberingWidth);

		linenumberingSeparator = jf.createTextField(new Identifier(Constants.PARAM_LINENUMBERING_SEPARATOR), 3);
		panel.addLabeledComponent(Constants.PARAM_LINENUMBERING_SEPARATOR,
				linenumberingSeparator);

		return panel;
	}

	private GridPanel createDefaultsPanel() {

		GridPanel panel = new GridPanel(ResourceServices.getString(res,
				"C_DEFAULT_VALUES"));

		panel.startSubPanel();

		shadeVerbatim = jf.createCheckBox(new Identifier(Constants.PARAM_SHADE_VERBATIM), Constants.PARAM_SHADE_VERBATIM);
		panel.addComponent(shadeVerbatim);

		return panel;
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		shadeVerbatim.setSelected(driver.isParameterEnabled(
				Constants.PARAM_SHADE_VERBATIM, false));
		linenumberingExtension.setSelected(driver.isParameterEnabled(
				Constants.PARAM_LINENUMBERING_EXTENSION, true));
		linenumberingEveryNth.setText(driver
				.getParameter(Constants.PARAM_LINENUMBERING_EVERY_NTH));
		linenumberingWidth.setText(driver
				.getParameter(Constants.PARAM_LINENUMBERING_WIDTH));
		linenumberingSeparator.setText(driver
				.getParameter(Constants.PARAM_LINENUMBERING_SEPARATOR));
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_SHADE_VERBATIM,
				shadeVerbatim.isSelected());
		driver.setParameter(Constants.PARAM_LINENUMBERING_EXTENSION,
				linenumberingExtension.isSelected());
		driver.setParameter(Constants.PARAM_LINENUMBERING_EVERY_NTH,
				linenumberingEveryNth.getText());
		driver.setParameter(Constants.PARAM_LINENUMBERING_WIDTH,
				linenumberingWidth.getText());
		driver.setParameter(Constants.PARAM_LINENUMBERING_SEPARATOR,
				linenumberingSeparator.getText());
	}

}
