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
import java.util.List;

import javax.swing.Icon;

import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.perspective.Perspective;

public class ActionCloseProject extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;

	public ActionCloseProject(Application application, String name, Icon icon) {
		super(application, name, icon);
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		try {

			closeProject(application);
			wm.disableSaveWidgets();

		} finally {
			finished();
		}
	}

	public static void closeProject(Application application) {

		application.setProject(null);

		List<Perspective> perspectiveList = application.getPerspectiveList();

		for (Perspective perspective : perspectiveList) {
			perspective.reset();
		}

		application.setFrameTitle(Constants.WND_PROJECT_TITLE);
		application.setProject(null);
		application.lock();
	}
}
