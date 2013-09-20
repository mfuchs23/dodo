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
import java.io.File;

import javax.swing.Icon;

import org.dbdoclet.jive.dialog.DialogAction;
import org.dbdoclet.jive.dialog.WarningBox;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.dialog.CreateProjectDialog;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.Project;

public class ActionNewProject extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;
	private final Application application;

	public ActionNewProject(Application application, String name, Icon icon) {

		super(application, name, icon);
		this.application = application;
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		try {

			CreateProjectDialog dlg = new CreateProjectDialog(
					application.getContext(), ResourceServices.getString(res,
							"C_NEW_PROJECT"), true);
			dlg.setVisible(true);

			if (dlg.isCanceled() == false) {

				File projectFile = new File(dlg.getProjectFileName());

				if (projectFile.exists() == true) {
					DialogAction rc = WarningBox.showWithCancel(application
							.getContext().getDialogOwner(), ResourceServices
							.getString(res, "C_WARNING"), ResourceServices
							.getString(res,
									"C_WARNING_PROJECT_FILE_ALREADY_EXISTS"));

					if (rc == DialogAction.CANCEL) {
						return;
					}

					projectFile.delete();

				}

				Project project = new Project(projectFile, new File(
						dlg.getProjectDirectory()), new File(
						dlg.getBuildDirectory()));
				project.setProjectName(dlg.getProjectName());

				String docBookFileName = FileServices.appendFileName(
						dlg.getProjectDirectory(), "Document.xml");
				project.setDocBookFileName(docBookFileName);

				application.setProject(project);
				application.addRecent(projectFile);
				application.reset();
				application.setFrameTitle(project.getProjectName());

				for (Perspective perspective : application.getPerspectiveList()) {
					perspective.syncView(project);
				}

				application.unlock();
				wm.enableSaveWidgets();
			}

		} finally {

			finished();
		}
	}
}
