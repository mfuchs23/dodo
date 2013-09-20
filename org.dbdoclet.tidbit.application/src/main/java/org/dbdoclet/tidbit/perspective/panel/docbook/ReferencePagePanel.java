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

import javax.swing.JCheckBox;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class ReferencePagePanel extends AbstractPanel {

    private static final long serialVersionUID = 1L;
    private GridPanel defaultsPanel;
    private JCheckBox funcsynopsisDecoration;
    
    @Override
    protected void createGui() {
        createGui(0);
    }

    protected void createGui(int columnOffset) {

        super.createGui();

        setColumnOffset(columnOffset);
        
        defaultsPanel = createDefaultsPanel();
        addComponent(defaultsPanel, Anchor.NORTHWEST);

        addVerticalGlue();
    }

    private GridPanel createDefaultsPanel() {

        GridPanel panel = new GridPanel(ResourceServices.getString(res,"C_DEFAULT_VALUES"));
        panel.startSubPanel();
        
		funcsynopsisDecoration = jf.createCheckBox(new Identifier(
				Constants.PARAM_FUNCSYNOPSIS_DECORATION),
				Constants.PARAM_FUNCSYNOPSIS_DECORATION);
        panel.addComponent(funcsynopsisDecoration);
        
        return panel;
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        funcsynopsisDecoration.setSelected(driver.isParameterEnabled(Constants.PARAM_FUNCSYNOPSIS_DECORATION, true));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        driver.setParameter(Constants.PARAM_FUNCSYNOPSIS_DECORATION, funcsynopsisDecoration.isSelected());
    }

}
