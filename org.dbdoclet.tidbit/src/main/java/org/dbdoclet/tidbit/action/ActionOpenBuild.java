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

import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;

public class ActionOpenBuild extends AbstractTidbitAction {

    private static final long serialVersionUID = 1L;

    public ActionOpenBuild(Application application, Icon icon) {

        super(application, "", icon);
    }

    @Override
    public void action(ActionEvent event) throws Exception {

        try {

            Project project = application.getProject();

            if (project != null) {
            
                File dir = project.getBuildDirectory();
                ExecServices.open(dir);
            
            } else {
            
                InfoBox.show(StaticContext.getDialogOwner(), ResourceServices.getString(res,"C_INFORMATION"), ResourceServices.getString(res,"C_INFO_NO_ACTIVE_PROJECT"));
            }
            
        } finally {
            finished();
        }
    }
}
