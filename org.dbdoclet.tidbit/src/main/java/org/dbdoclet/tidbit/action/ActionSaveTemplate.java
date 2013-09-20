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
import java.text.MessageFormat;
import java.util.regex.Pattern;

import javax.swing.Icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.dialog.DialogAction;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.jive.dialog.InputDialog;
import org.dbdoclet.jive.dialog.WarningBox;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.Tidbit;
import org.dbdoclet.tidbit.Validator;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;

public class ActionSaveTemplate extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(ActionSaveTemplate.class);
	private static final Pattern PATTERN_XSL = Pattern.compile("^.*\\.xsl$");

	private final Tidbit tidbit;

	public ActionSaveTemplate(Tidbit application, String name, Icon icon) {

		super(application, name, icon);
		tidbit = application;
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		try {
			save();
		} finally {
			finished();
		}
	}

	@Override
	public void finished() {
		logger.debug("ActionSaveProject finished.");
	}

	public boolean save() throws Exception {

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

		File dir = saveDialog();

		if (dir == null) {
			return false;
		}

		FileServices.createPath(dir);
		FileServices.copyDir(project.getDriverDirectory(), new File(
				FileServices.appendPath(dir, "xsl")), PATTERN_XSL);

		return true;
	}

	private File saveDialog() throws IOException {

		String path = FileServices.appendPath(System.getProperty("user.home"),
				".tidbit.d");
		path = FileServices.appendPath(path, "templates");

		InputDialog dlg = new InputDialog(application.getContext()
				.getDialogOwner(), ResourceServices.getString(res,
				"C_NEW_TEMPLATE"));
		dlg.setLabel(ResourceServices.getString(res, "C_NEW_TEMPLATE"));
		dlg.setVisible(true);

		if (dlg.isCanceled() == true) {
			return null;
		}

		path = FileServices.appendPath(path, dlg.getValue());
		File dir = new File(path);

		if (dir.exists() == true) {

			DialogAction rc = WarningBox.showYesNo(StaticContext
					.getDialogOwner(), ResourceServices.getString(res,
					"C_WARNING"), MessageFormat.format(
					res.getString("C_WARNING_TEMPLATE_ALREADY_EXISTS"),
					dlg.getValue()));

			if (rc == DialogAction.NO) {
				return null;
			}

			FileServices.delete(dir);
		}

		return new File(path);
	}
}
