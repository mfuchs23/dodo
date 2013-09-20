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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.xml.parsers.ParserConfigurationException;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.Validator;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.ConfigurationException;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.trafo.TrafoException;
import org.xml.sax.SAXException;

public class ActionOpenProject extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;

	private File selectedFile = null;

	public ActionOpenProject(Application application, String name, Icon icon) {
		this(application, name, icon, null);
	}

	public ActionOpenProject(Application application, String name, Icon icon,
			File file) {

		super(application, name, icon);
		this.selectedFile = file;
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		try {

			File file = selectedFile;

			if (file == null) {

				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

				int rc = fc.showOpenDialog(StaticContext.getDialogOwner());

				if (rc == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
				} else {
					return;
				}

			}

			if (file.isFile() == false) {

				ErrorBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_ERROR"),
						res.getString("C_ERROR_INVALID_PROJECT_FILE"));
				return;
			}

			openProject(application, file);

			Validator validator = new Validator(application, "open");
			validator.check();

		} finally {

			finished();
		}
	}

	public static void openProject(Application application, File file)
			throws IOException, SAXException, FileNotFoundException,
			ParserConfigurationException, ConfigurationException,
			TrafoException {

		Project project = application.getProject();

		if (project != null) {
			ActionCloseProject.closeProject(application);
		}

		project = Project.newInstance(file);
		openProject(application, project);
		application.addRecent(file);
	}

	public static void openProject(Application application, Project project)
			throws IOException, SAXException, FileNotFoundException,
			ParserConfigurationException, ConfigurationException {

		try {

			application.setWaitCursor();

			List<Perspective> perspectiveList = application
					.getPerspectiveList();

			synchronized (perspectiveList) {
				for (Perspective perspective : perspectiveList) {
					perspective.reset();
					perspective.syncView(project);
				}
			}

			application.setFrameTitle(project.getProjectName());
			application.setProject(project);
			application.unlock();

			JiveFactory jf = JiveFactory.getInstance();
			jf.disableSaveWidgets();

		} finally {
			application.setDefaultCursor();
		}
	}
}
