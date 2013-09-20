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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.io.DirectoryFilter;
import org.dbdoclet.jive.dialog.DialogAction;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.jive.dialog.ListChooser;
import org.dbdoclet.jive.dialog.WarningBox;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.Tidbit;
import org.dbdoclet.tidbit.Validator;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.Project;

public class ActionApplyTemplate extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(ActionApplyTemplate.class);
	private final Tidbit tidbit;

	public ActionApplyTemplate(Tidbit application, String name, Icon icon) {

		super(application, name, icon);
		tidbit = application;
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		try {
			applyTemplate();
		} finally {
			finished();
		}
	}

	@Override
	public void finished() {
		logger.debug("ActionSaveProject finished.");
	}

	public boolean applyTemplate() throws Exception {

		Project project = application.getProject();

		if (project == null) {

			InfoBox.show(StaticContext.getDialogOwner(),
					ResourceServices.getString(res, "C_INFORMATION"),
					res.getString("C_INFO_NO_ACTIVE_PROJECT"));
			return false;
		}

		Validator validator = new Validator(tidbit, "save");

		if (validator.check() == false) {
			return false;
		}

		DialogAction rc = WarningBox.showYesNo(StaticContext.getDialogOwner(),
				ResourceServices.getString(res, "C_WARNING"),
				res.getString("C_WARNING_APPLYING_TEMPLATE"));

		if (rc == DialogAction.CANCEL) {
			return false;
		}

		String path = FileServices.appendPath(System.getProperty("user.home"),
				".tidbit.d");
		path = FileServices.appendPath(path, "templates");

		File dir = new File(path);
		File[] templateFiles = dir.listFiles(new DirectoryFilter());

		ListChooser<File> chooser = new ListChooser<File>(
				StaticContext.getDialogOwner(), templateFiles);
		chooser.setDescription(ResourceServices.getString(res,
				"C_CHOOSE_TEMPLATE"));
		chooser.createGui();
		chooser.setVisible(true);

		if (chooser.isCanceled() == true) {
			return false;
		}

		File templateDir = chooser.getValue();

		if (templateDir == null) {
			return false;
		}

		path = FileServices.appendPath(templateDir, "xsl");
		templateDir = new File(path);

		project.createAntWriter();
		FileServices.copyDir(templateDir, project.getDriverDirectory());
		project.refresh();

		for (Perspective perspective : application.getPerspectiveList()) {
			perspective.reset();
			perspective.syncView(project);
		}

		return true;
	}
}
