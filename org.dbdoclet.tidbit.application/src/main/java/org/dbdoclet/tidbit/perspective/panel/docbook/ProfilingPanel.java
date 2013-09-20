/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.perspective.panel.docbook;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class ProfilingPanel extends AbstractPanel {

    private static final long serialVersionUID = 1L;
    private GridPanel defaultsPanel;
    
    @Override
    protected void createGui() {
        createGui(0);
    }

    protected void createGui(int columnOffset) {

        super.createGui();

        setColumnOffset(columnOffset);
        
        defaultsPanel = createDefaultsPanel();
        addComponent(defaultsPanel, Anchor.NORTHWEST);
    }

    private GridPanel createDefaultsPanel() {

        GridPanel panel = new GridPanel(ResourceServices.getString(res,"C_DEFAULT_VALUES"));
        panel.startSubPanel();
        
        return panel;
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

    }

}
