/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.action;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.perspective.Perspective;

public class ActionSetLookAndFeel extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;
	private LookAndFeelInfo info;
	private String lnfClassName;

	public ActionSetLookAndFeel(Application application, String label,
			LookAndFeelInfo info) {

		super(application, label, null);
		this.info = info;
	}

	@Override
	protected void action(ActionEvent event) throws Exception {

		application.setWaitCursor();
		
		if (info != null) {
			lnfClassName = info.getClassName();
		}

		if (lnfClassName != null) {

			UIManager.setLookAndFeel(lnfClassName);
			
			Frame frame = application.getContext().getDialogOwner();
			SwingUtilities.updateComponentTreeUI(frame);

			for (Perspective perspective : application.getPerspectiveList()) {

				if (perspective != null && perspective.getPanel() != null) {
					SwingUtilities.updateComponentTreeUI(perspective.getPanel());
				}
			}

			frame.pack();
		}
		
		application.setDefaultCursor();
	}
}
