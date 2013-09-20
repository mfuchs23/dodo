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

import org.dbdoclet.jive.JiveServices;
import org.dbdoclet.jive.widget.Editor;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class ActionOpenFoTemplate extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;
	private final String templateName;
	private Project project; 
	
	public void setProject(Project project) {
		this.project = project;
	}

	public ActionOpenFoTemplate(String name, Icon icon,
			String templateName) {

		super(name, icon);
		this.templateName = templateName;
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		try {

			if (project == null) {
				return;
			}
			
			final AbstractDriver driver = project.getDriver(Output.pdf);
			final String template = driver.getTemplateAsText(templateName);
			

			Editor editor = new Editor();
			editor.setSaveAction(new ActionSaveFoTemplate(project,
					ResourceServices.getString(res, "C_SAVE"),
					JiveServices.getJlfgrIcon("general", "Save24.gif"), templateName, editor));
			
			editor.open(template);
			editor.setSize(800, 600);
			JiveServices.center(editor);
			editor.setVisible(true);
 
		} finally {
			finished();
		}
	}
}
