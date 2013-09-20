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

import org.dbdoclet.tidbit.Tidbit;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.perspective.Perspective;

public class ActionOpenPerspective extends AbstractTidbitAction {

    private static final long serialVersionUID = 1L;
    private final Perspective perspective;
    private final Tidbit application;

    public ActionOpenPerspective(Tidbit application, Perspective perspective) {
        
        super(perspective, perspective.getLocalizedName(), perspective.getIcon());
        
        this.application = application;
        this.perspective = perspective;
    }

    @Override
    public void action(ActionEvent event) throws Exception {

        try {

            if (application != null && perspective != null) {
                application.openPerspective(perspective);
            }

        } finally {
            finished();
        }
    }

}
