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

public class QandaPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private GridPanel defaultsPanel;
	private JCheckBox qandaInToc;

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);

		defaultsPanel = createDefaultsPanel();
		addComponent(defaultsPanel, Anchor.NORTHWEST);
	}

	private GridPanel createDefaultsPanel() {

		GridPanel panel = new GridPanel(ResourceServices.getString(res,
				"C_DEFAULT_VALUES"));
		panel.startSubPanel();

		qandaInToc = jf.createCheckBox(new Identifier(
				Constants.PARAM_QANDA_IN_TOC), Constants.PARAM_QANDA_IN_TOC);
		panel.addComponent(qandaInToc);

		return panel;
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		qandaInToc.setSelected(driver.isParameterEnabled(
				Constants.PARAM_QANDA_IN_TOC, false));
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_QANDA_IN_TOC,
				qandaInToc.isSelected());
	}

}
