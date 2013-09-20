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
package org.dbdoclet.tidbit.perspective.panel;

import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Context;

public class EclipsePanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    private JiveFactory wm;
    private ResourceBundle res;

    public EclipsePanel(Context ctx) {

        res = ctx.getResourceBundle();
        wm = JiveFactory.getInstance();
        
        JTextComponent helpArea = wm.createHelpArea(this, ResourceServices.getString(res,"C_HELP_ECLIPSE_PANEL"));
        addComponent(helpArea, Colspan.CS_4, Anchor.NORTHWEST, Fill.HORIZONTAL);
        incrRow();
        
        String toolTipText;
        JLabel label;
        int width = 42;

        toolTipText = wm.createToolTipText(ResourceServices.getString(res,"C_HELP_ENTRY_ECLIPSE_PLUGIN_NAME"));
        label = wm.createLabel(ResourceServices.getString(res,"C_ECLIPSE_PLUGIN_NAME"));
        addLabeledComponent("eclipse.plugin.name", label, toolTipText, width);

        toolTipText = wm.createToolTipText(ResourceServices.getString(res,"C_HELP_ENTRY_ECLIPSE_PLUGIN_ID"));
        label = wm.createLabel(ResourceServices.getString(res,"C_ECLIPSE_PLUGIN_ID"));
        addLabeledComponent("eclipse.plugin.id", label, toolTipText, width);

        toolTipText = wm.createToolTipText(ResourceServices.getString(res,"C_HELP_ENTRY_ECLIPSE_PLUGIN_PROVIDER"));
        label = wm.createLabel(ResourceServices.getString(res,"C_ECLIPSE_PLUGIN_PROVIDER"));
        addLabeledComponent("eclipse.plugin.provider", label, toolTipText, width);

        addVerticalGlue();
    }

    public void addLabeledComponent(String name, JLabel label, String toolTipText, int width) {

        label.setToolTipText(toolTipText);
        JTextField entry = wm.createTextField(new Identifier(name), width);
        entry.setToolTipText(toolTipText);
        addLabeledComponent(label, entry);
    }
}
