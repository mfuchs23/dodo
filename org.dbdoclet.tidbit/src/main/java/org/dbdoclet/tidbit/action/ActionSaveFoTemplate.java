/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.Editor;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class ActionSaveFoTemplate extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;
	private final String templateName;
	private final Editor editor;
	private final Project project;

	public ActionSaveFoTemplate(Project project, String name, Icon icon,
			String templateName, Editor editor) {

		super(name, icon);
		this.project = project;
		this.templateName = templateName;
		this.editor = editor;
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		try {

			if (project == null) {
				return;
			}

			final AbstractDriver driver = project.getDriver(Output.pdf);
			String buffer = editor.getText();
			editor.setChanged(false);
			driver.setTemplate(templateName, buffer);

			JiveFactory jf = JiveFactory.getInstance();
			jf.enableSaveWidgets();
			
		} finally {
			finished();
		}
	}
}
