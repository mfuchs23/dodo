package org.dbdoclet.tidbit.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.tree.TreePath;

import org.dbdoclet.jive.dialog.SettingsDialog;
import org.dbdoclet.jive.dialog.settings.JdkPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.settings.HelperPanel;

public class ActionSettings extends AbstractTidbitAction {

	private static final long serialVersionUID = 1L;

	public ActionSettings(Application application, String name, Icon icon) {
		super(application, name, icon);
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		SettingsDialog dlg = new SettingsDialog(StaticContext.getDialogOwner(),
				ResourceServices.getString(res, "C_SETTINGS"));

		dlg.addGroup(ResourceServices.getString(res, "C_GENERAL"));
		TreePath dtp = dlg.addPanel(ResourceServices
				.getString(res, "C_GENERAL"), ResourceServices.getString(res,
				"C_HELPERS"), new HelperPanel(application.getContext()));

		dlg.addGroup("Java");
		dlg.addPanel("Java", "JDK", new JdkPanel());

		dlg.setSettings(application.getContext().getSettings());
		dlg.setSelected(dtp);
		dlg.setVisible(true);

		for (Perspective perspective : application.getPerspectiveList()) {
			perspective.refresh();
		}

		finished();
	}

}
