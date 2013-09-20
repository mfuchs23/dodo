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

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class MetaInfoPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private GridPanel defaultsPanel;
	private JCheckBox makeSingleYearRanges;

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(final int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);

		defaultsPanel = createDefaultsPanel();
		addComponent(defaultsPanel, Anchor.NORTHWEST);
	}

	private GridPanel createDefaultsPanel() {

		final GridPanel panel = new GridPanel(ResourceServices.getString(res,
				"C_DEFAULT_VALUES"));
		panel.startSubPanel();

		makeSingleYearRanges = jf.createCheckBox(new Identifier(
				Constants.PARAM_MAKE_SINGLE_YEAR_RANGES),
				Constants.PARAM_MAKE_SINGLE_YEAR_RANGES);
		panel.addComponent(makeSingleYearRanges);

		return panel;
	}

	@Override
	public void syncView(Project project, final AbstractDriver driver) {

		makeSingleYearRanges.setSelected(driver.isParameterEnabled(
				Constants.PARAM_MAKE_SINGLE_YEAR_RANGES, false));
	}

	@Override
	public void syncModel(Project project, final AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_MAKE_SINGLE_YEAR_RANGES,
				makeSingleYearRanges.isSelected());
	}

}
