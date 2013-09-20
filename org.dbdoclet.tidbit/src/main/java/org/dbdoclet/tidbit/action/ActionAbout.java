/* 
 * ### Copyright (C) 20082-009 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.action;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.Icon;

import org.dbdoclet.jive.dialog.AboutDialog;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.StaticContext;

public class ActionAbout extends AbstractTidbitAction {

    private static final long serialVersionUID = 1L;

    public ActionAbout(Application application, String name, Icon icon) {
        super(application, name, icon);
    }

    @Override
    public void action(ActionEvent event) throws Exception {

        URL resourceUrl = ResourceServices.getResourceAsUrl("/text/About.txt", ActionAbout.class.getClassLoader());
        AboutDialog aboutDialog = new AboutDialog(StaticContext.getDialogOwner(), "About", resourceUrl, "text/plain");

        aboutDialog.setVisible(true);
        finished();
    }
}
