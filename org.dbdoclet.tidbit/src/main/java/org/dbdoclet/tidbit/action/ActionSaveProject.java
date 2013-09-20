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
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.Tidbit;
import org.dbdoclet.tidbit.Validator;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.FileManager;
import org.dbdoclet.tidbit.project.Project;

public class ActionSaveProject extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(ActionSaveProject.class);

	private boolean isSaveAs = false;

	private final Tidbit tidbit;

	public ActionSaveProject(Tidbit application, String name, Icon icon) {
		this(application, name, icon, false);
	}

	public ActionSaveProject(Tidbit application, String name, Icon icon,
			boolean isSaveAs) {

		super(application, name, icon);
		tidbit = application;
		this.isSaveAs = isSaveAs;
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		try {
			application.setWaitCursor();
			save(isSaveAs);
		} finally {
			finished();
		}
	}

	@Override
	public void finished() {
		application.setDefaultCursor();
		logger.debug("ActionSaveProject finished.");
	}

	public boolean save() throws Exception {
		return save(false);
	}

	public boolean save(boolean isSaveAs) throws Exception {

		Project project = application.getProject();

		if (project == null) {
			InfoBox.show(StaticContext.getDialogOwner(),
					ResourceServices.getString(res, "C_INFORMATION"),
					ResourceServices.getString(res, "C_INFO_NO_ACTIVE_PROJECT"));
			return false;
		}

		if (project.getProjectFile() == null || isSaveAs == true) {

			File file = saveDialog();

			if (file == null) {
				return false;
			}

			if (project == null || isSaveAs) {

				project = new Project(file, project.getProjectDirectory(),
						project.getBuildDirectory());
				project.setProjectFile(file);
				application.setProject(project);
				application.addRecent(file);
				application.setFrameTitle(file.getName());
				wm.disableSaveWidgets();
			}

			application.addRecent(file);
			application.setFrameTitle(file.getName());
		}

		for (Perspective perspective : application.getPerspectiveList()) {
			perspective.syncModel(project);
		}

		Validator validator = new Validator(tidbit, "save");

		if (validator.check() == false) {
			return false;
		}

		FileManager fileManager = project.getFileManager();

		if (fileManager.getDocBookFile() == null) {

			ErrorBox.show(StaticContext.getDialogOwner(),
					res.getString("C_ERROR"),
					res.getString("C_ERROR_DOCBOOK_FILE_UNDEFINED"));
			return false;
		}

		AntFileWriter writer = project.createAntWriter();

		for (MediumService mediumService : application.getMediumServiceList()) {
			mediumService.write(writer, project);
		}

		writer.save();

		for (Perspective perspective : application.getPerspectiveList()) {
			perspective.syncView(project);
		}

		if (isSaveAs == false) {
			wm.disableSaveWidgets();
		}

		application.setFrameTitle(project.getProjectName());

		return true;
	}

	private File saveDialog() throws IOException {

		File dir = null;

		Project project = application.getProject();
		String path = project.getDestinationPath();

		if ((path != null) && (path.length() > 0)) {
			dir = new File(path);
		} else {
			dir = new File(".");
		}

		JFileChooser fc = new JFileChooser(dir);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int rc = fc.showSaveDialog(StaticContext.getDialogOwner());

		if (rc == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		} else {
			return null;
		}
	}
}
