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
package org.dbdoclet.tidbit.perspective.docbook.eclipse;

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
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.StaticContext;

public class EclipseHelpPanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    private JiveFactory wm;
    private ResourceBundle res;
    private JTextField pluginNameEntry;
    private JTextField pluginIdEntry;
    private JTextField pluginVersionEntry;
    private JTextField pluginProviderEntry;

    public EclipseHelpPanel() {

        res = StaticContext.getResourceBundle();
        wm = JiveFactory.getInstance();


        JTextComponent helpArea = wm.createHelpArea(this, ResourceServices.getString(res,"C_HELP_ECLIPSE_PANEL"));
        addComponent(helpArea, Colspan.CS_4, Anchor.NORTHWEST, Fill.HORIZONTAL);
        incrRow();
        
        String toolTipText;
        JLabel label;
        int width = 42;

        toolTipText = wm.createToolTipText(ResourceServices.getString(res,"C_HELP_ENTRY_ECLIPSE_PLUGIN_NAME"));
        label = wm.createLabel(ResourceServices.getString(res,"C_ECLIPSE_PLUGIN_NAME") + Constants.MANDATORY_SUFFIX);
        label.setForeground(Constants.MANDATORY_COLOR);
        pluginNameEntry = addLabeledComponent(label, toolTipText, width);
        incrRow();
        
        toolTipText = wm.createToolTipText(ResourceServices.getString(res,"C_HELP_ENTRY_ECLIPSE_PLUGIN_ID"));
        label = wm.createLabel(ResourceServices.getString(res,"C_ECLIPSE_PLUGIN_ID") + Constants.MANDATORY_SUFFIX);
        label.setForeground(Constants.MANDATORY_COLOR);
        pluginIdEntry = addLabeledComponent(label, toolTipText, width);
        incrRow();
        
        toolTipText = wm.createToolTipText(ResourceServices.getString(res,"C_HELP_ENTRY_ECLIPSE_PLUGIN_VERSION"));
        label = wm.createLabel(ResourceServices.getString(res,"C_ECLIPSE_PLUGIN_VERSION") + Constants.MANDATORY_SUFFIX);
        label.setForeground(Constants.MANDATORY_COLOR);
        pluginVersionEntry = addLabeledComponent(label, toolTipText, width);
        incrRow();
        
        toolTipText = wm.createToolTipText(ResourceServices.getString(res,"C_HELP_ENTRY_ECLIPSE_PLUGIN_PROVIDER"));
        label = wm.createLabel(ResourceServices.getString(res,"C_ECLIPSE_PLUGIN_PROVIDER"));
        pluginProviderEntry = addLabeledComponent(label, toolTipText, width);

        addVerticalGlue();
    }

    public JTextField addLabeledComponent(JLabel label, String toolTipText, int width) {

        label.setToolTipText(toolTipText);
        JTextField entry = wm.createTextField(new Identifier(label.getText()), width);
        entry.setToolTipText(toolTipText);
        addLabeledComponent(label, entry);

        return entry;
    }

    public String getPluginId() {

        if (pluginIdEntry != null) {
            return pluginIdEntry.getText();
        }

        return null;
    }

    public String getPluginName() {

        if (pluginNameEntry != null) {
            return pluginNameEntry.getText();
        }

        return null;
    }

    public String getPluginProvider() {

        if (pluginProviderEntry != null) {
            return pluginProviderEntry.getText();
        }

        return null;
    }

    public String getPluginVersion() {

        if (pluginVersionEntry != null) {
            return pluginVersionEntry.getText();
        }

        return null;
    }

    public void setPluginId(String id) {

        if (pluginIdEntry != null) {
            pluginIdEntry.setText(id);
        }
    }

    public void setPluginName(String name) {

        if (pluginNameEntry != null) {
            pluginNameEntry.setText(name);
        }
    }

    public void setPluginProvider(String provider) {

        if (pluginProviderEntry != null) {
            pluginProviderEntry.setText(provider);
        }
    }

    public void setPluginVersion(String version) {

        if (pluginVersionEntry != null) {
            pluginVersionEntry.setText(version);
        }
    }

    public void reset() {
    
        setPluginName("");
        setPluginId("");
        setPluginVersion("");
        setPluginProvider("");
    }
}
