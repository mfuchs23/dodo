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

public class LocalizationPanel extends AbstractPanel {

    private static final long serialVersionUID = 1L;
    private GridPanel defaultsPanel;
    private JCheckBox l10nGentextUseXrefLanguage;
    
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

        l10nGentextUseXrefLanguage = jf.createCheckBox(new Identifier(Constants.PARAM_L10N_GENTEXT_USE_XREF_LANGUAGE), Constants.PARAM_L10N_GENTEXT_USE_XREF_LANGUAGE);
        panel.addComponent(l10nGentextUseXrefLanguage);
        return panel;
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        l10nGentextUseXrefLanguage.setSelected(driver.isParameterEnabled(Constants.PARAM_L10N_GENTEXT_USE_XREF_LANGUAGE, false));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {
        
        driver.setParameter(Constants.PARAM_L10N_GENTEXT_USE_XREF_LANGUAGE, l10nGentextUseXrefLanguage.isSelected());
    }

}
