/* 
 * ### Copyright (C) 2008-2009 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;

public class ActionExit extends AbstractTidbitAction {

    private static final long serialVersionUID = 1L;

    public ActionExit(Application application, String name, Icon icon) {
        
        super(application, name, icon);
    }

    @Override
    public void action(ActionEvent event) throws Exception {

        application.shutdown();
        finished();
        System.exit(0);
    }
}
