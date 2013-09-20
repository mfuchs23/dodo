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
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class MiscellaneousPanel extends AbstractPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JCheckBox highlightSourceCheckBox;
	private JTextField highlightDefaultLanguage;
	private JTextField highlightXslthlConfig;
	private JButton highlightXslthlConfigButton;

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);

		JPanel highlightPanel = createHighlightPanel();
		addComponent(highlightPanel, Anchor.NORTHWEST);
		incrRow();

		addHorizontalGlue();
	}

	private JPanel createHighlightPanel() {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(ResourceServices
				.getString(res, "C_SYNTAX_HIGHLIGHTNING")));
		panel.startSubPanel();

		highlightSourceCheckBox = jf.createCheckBox(new Identifier(
				Constants.PARAM_HIGHLIGHT_SOURCE),
				Constants.PARAM_HIGHLIGHT_SOURCE);
		panel.addComponent(highlightSourceCheckBox);

		highlightDefaultLanguage = jf.createTextField(new Identifier(Constants.PARAM_HIGHLIGHT_DEFAULT_LANGUAGE), 9);
		panel.addLabeledComponent(Constants.PARAM_HIGHLIGHT_DEFAULT_LANGUAGE,
				highlightDefaultLanguage);

		panel.startSubPanel();

		highlightXslthlConfig = jf.createTextField(new Identifier(Constants.PARAM_HIGHLIGHT_XSLTHL_CONFIG), 21);
		panel.addLabeledComponent(Constants.PARAM_HIGHLIGHT_XSLTHL_CONFIG,
				highlightXslthlConfig);
		setDefaultXslthlConfig();

		highlightXslthlConfigButton = jf.createButton(ResourceServices
				.getString(res, "C_BROWSE"));
		highlightXslthlConfigButton.addActionListener(this);
		highlightXslthlConfigButton
				.setActionCommand("chooseHighlightXsltConfig");
		panel.addComponent(highlightXslthlConfigButton);

		return panel;
	}

	private void setDefaultXslthlConfig() {

		String path = FileServices.appendPath(StaticContext.getHome(), "docbook");
		path = FileServices.appendPath(path, "xsl");
		path = FileServices.appendPath(path, "highlighting");
		path = FileServices.appendFileName(path, "xslthl-config.xml");

		try {
			highlightXslthlConfig.setText(new File(path).toURI().toURL()
					.toString());
		} catch (MalformedURLException e) {
			highlightXslthlConfig.setText("file://"
					+ FileServices.normalizePath(path));
		}
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		highlightSourceCheckBox.setSelected(driver.isParameterEnabled(
				Constants.PARAM_HIGHLIGHT_SOURCE, true));
		highlightDefaultLanguage.setText(driver
				.getParameter(Constants.PARAM_HIGHLIGHT_DEFAULT_LANGUAGE));

		String path = driver
				.getParameter(Constants.PARAM_HIGHLIGHT_XSLTHL_CONFIG);

		if (path != null && path.length() > 0) {
			highlightXslthlConfig.setText(path);
		} else {
			setDefaultXslthlConfig();
		}
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_HIGHLIGHT_SOURCE,
				highlightSourceCheckBox.isSelected());
		driver.setParameter(Constants.PARAM_HIGHLIGHT_DEFAULT_LANGUAGE,
				highlightDefaultLanguage.getText());
		driver.setParameter(Constants.PARAM_HIGHLIGHT_XSLTHL_CONFIG,
				highlightXslthlConfig.getText());
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		if (cmd == null) {
			return;
		}

		if (cmd.equals("chooseHighlightXsltConfig")) {
			chooseFileAsUrl(highlightXslthlConfig);
		}
	}
}
