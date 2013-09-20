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

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;
import org.dbdoclet.unit.Length;

public class GraphicPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private JSpinner defaultWidthSpinner;
	private GridPanel graphicPanel;
	private JCheckBox ignoreImageScalingCheckBox;
	protected JCheckBox useRoleForMediaObjectCheckBox;
	protected JTextField preferredMediaObjectRole;
	private JTextField graphicDefaultExtension;
	private JCheckBox keepRelativeImageUrisCheckBox;
	private JTextField imgSrcPath;
	protected GridPanel defaultsPanel;

	public void setImageSrcPathEnabled(boolean flag) {
	
		if (imgSrcPath != null) {
			imgSrcPath.setEnabled(flag);
		}
	}
	
	public void setKeepRelativeImageUrisEnabled(boolean flag) {
	
		if (keepRelativeImageUrisCheckBox != null) {
			keepRelativeImageUrisCheckBox.setEnabled(flag);
		}
	}
	
	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);
		graphicPanel = createGraphicPanel();

		addComponent(graphicPanel, Anchor.NORTHWEST, Fill.NONE);
		incrRow();

		GridPanel rolePanel = createRolePanel();
		addComponent(rolePanel, Anchor.NORTHWEST, Fill.NONE);

	}

	private GridPanel createGraphicPanel() {

		defaultsPanel = new GridPanel(ResourceServices.getString(res,
				"C_DEFAULT_VALUES"));

		defaultsPanel.startSubPanel();

		graphicDefaultExtension = jf.createTextField(new Identifier(
				Constants.PARAM_GRAPHIC_DEFAULT_EXTENSION), 12);
		defaultsPanel.addLabeledComponent(
				Constants.PARAM_GRAPHIC_DEFAULT_EXTENSION,
				graphicDefaultExtension);

		imgSrcPath = jf.createTextField(new Identifier(Constants.PARAM_IMG_SRC_PATH), 21);
		defaultsPanel.addLabeledComponent(Constants.PARAM_IMG_SRC_PATH, imgSrcPath);

		defaultsPanel.startSubPanel();

		defaultWidthSpinner = jf.createWidthSpinner(new Identifier(
				Constants.PARAM_DEFAULT_IMAGE_WIDTH));
		defaultsPanel.addLabeledComponent(Constants.PARAM_DEFAULT_IMAGE_WIDTH,
				defaultWidthSpinner);

		ignoreImageScalingCheckBox = jf.createCheckBox(new Identifier(
				Constants.PARAM_IGNORE_IMAGE_SCALING),
				Constants.PARAM_IGNORE_IMAGE_SCALING);
		defaultsPanel.addComponent(ignoreImageScalingCheckBox);

		keepRelativeImageUrisCheckBox = jf.createCheckBox(new Identifier(
				Constants.PARAM_KEEP_RELATIVE_IMAGE_URIS),
				Constants.PARAM_KEEP_RELATIVE_IMAGE_URIS);
		defaultsPanel.addComponent(keepRelativeImageUrisCheckBox);

		return defaultsPanel;
	}

	private GridPanel createRolePanel() {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder("role"));

		preferredMediaObjectRole = jf.createTextField(new Identifier(
				Constants.PARAM_PREFERRED_MEDIAOBJECT_ROLE), 8);
		panel.addLabeledComponent(Constants.PARAM_PREFERRED_MEDIAOBJECT_ROLE,
				preferredMediaObjectRole);

		useRoleForMediaObjectCheckBox = jf.createCheckBox(new Identifier(
				Constants.PARAM_USE_ROLE_FOR_MEDIAOBJECT),
				Constants.PARAM_USE_ROLE_FOR_MEDIAOBJECT);
		panel.addComponent(useRoleForMediaObjectCheckBox);

		return panel;
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		defaultWidthSpinner.setValue(Length.valueOf(driver
				.getParameter(Constants.PARAM_DEFAULT_IMAGE_WIDTH)));
		graphicDefaultExtension.setText(driver
				.getParameter(Constants.PARAM_GRAPHIC_DEFAULT_EXTENSION));
		ignoreImageScalingCheckBox.setSelected(driver.isParameterEnabled(
				Constants.PARAM_IGNORE_IMAGE_SCALING, false));
		imgSrcPath.setText(driver.getParameter(Constants.PARAM_IMG_SRC_PATH));
		keepRelativeImageUrisCheckBox.setSelected(driver.isParameterEnabled(
				Constants.PARAM_KEEP_RELATIVE_IMAGE_URIS, true));
		preferredMediaObjectRole.setText(driver
				.getParameter(Constants.PARAM_PREFERRED_MEDIAOBJECT_ROLE));
		useRoleForMediaObjectCheckBox.setSelected(driver.isParameterEnabled(
				Constants.PARAM_USE_ROLE_FOR_MEDIAOBJECT, false));

	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_DEFAULT_IMAGE_WIDTH,
				getDistance(defaultWidthSpinner));
		driver.setParameter(Constants.PARAM_IGNORE_IMAGE_SCALING,
				ignoreImageScalingCheckBox.isSelected());
		driver.setParameter(Constants.PARAM_IMG_SRC_PATH, imgSrcPath.getText());
		driver.setParameter(Constants.PARAM_KEEP_RELATIVE_IMAGE_URIS,
				keepRelativeImageUrisCheckBox.isSelected());
		driver.setParameter(Constants.PARAM_USE_ROLE_FOR_MEDIAOBJECT,
				useRoleForMediaObjectCheckBox.isSelected());
		driver.setParameter(Constants.PARAM_PREFERRED_MEDIAOBJECT_ROLE,
				preferredMediaObjectRole.getText());
		driver.setParameter(Constants.PARAM_GRAPHIC_DEFAULT_EXTENSION,
				graphicDefaultExtension.getText());
	}
}
