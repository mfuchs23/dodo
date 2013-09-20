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
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class AdmonitionPanel extends AbstractPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JCheckBox admonGraphicsCheckBox;
	private JCheckBox admonTextLabelCheckBox;
	private JTextField admonGraphicsExtension;
	private JTextField admonGraphicsPath;
	private JButton admonGraphicsPathBrowseButton;
	protected GridPanel defaultsPanel;

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);

		defaultsPanel = new GridPanel();
		addComponent(defaultsPanel, Anchor.NORTHWEST, Fill.NONE);

		defaultsPanel.startSubPanel();

		admonGraphicsCheckBox = jf.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_ADMON_GRAPHICS")),
				ResourceServices.getString(res, "C_ADMON_GRAPHICS"));
		defaultsPanel.addComponent(admonGraphicsCheckBox);

		admonTextLabelCheckBox = jf.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_ADMON_TEXT_LABEL")),
				ResourceServices.getString(res, "C_ADMON_TEXT_LABEL"));
		defaultsPanel.addComponent(admonTextLabelCheckBox);
		defaultsPanel.startSubPanel();

		admonGraphicsExtension = jf.createTextField(new Identifier(
				"admon.graphics.extension"), 8);
		defaultsPanel.addLabeledComponent(
				ResourceServices.getString(res, "C_ADMON_GRAPHICS_EXTENSION"),
				admonGraphicsExtension);
		defaultsPanel.startSubPanel();

		admonGraphicsPath = jf.createTextField(new Identifier(
				"admon.graphics.apth"), 21);
		defaultsPanel.addLabeledComponent(
				ResourceServices.getString(res, "C_ADMON_GRAPHICS_PATH"),
				admonGraphicsPath);

		admonGraphicsPathBrowseButton = jf.createButton(ResourceServices
				.getString(res, "C_BROWSE"));
		admonGraphicsPathBrowseButton.addActionListener(this);
		admonGraphicsPathBrowseButton.setActionCommand("chooseGraphicsPath");
		defaultsPanel.addComponent(admonGraphicsPathBrowseButton);

		addHorizontalGlue();
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		if (driver == null) {
			return;
		}
		
		admonGraphicsCheckBox.setSelected(driver.isParameterEnabled(
				"admon.graphics", false));
		admonTextLabelCheckBox.setSelected(driver.isParameterEnabled(
				"admon.textlabel", false));
		admonGraphicsExtension.setText(driver
				.getParameter("admon.graphics.extension"));
		admonGraphicsPath.setText(driver.getParameter("admon.graphics.path"));
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter("admon.graphics",
				admonGraphicsCheckBox.isSelected());
		driver.setParameter("admon.textlabel",
				admonTextLabelCheckBox.isSelected());
		driver.setParameter("admon.graphics.extension",
				admonGraphicsExtension.getText());
		driver.setParameter("admon.graphics.path", admonGraphicsPath.getText());
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		if (cmd == null) {
			return;
		}

		if (cmd.equals("chooseGraphicsPath")) {
			chooseDirectory(admonGraphicsPath);
		}
	}

}
