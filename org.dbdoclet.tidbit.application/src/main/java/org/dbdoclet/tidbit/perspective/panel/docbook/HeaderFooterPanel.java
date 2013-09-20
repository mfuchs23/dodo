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
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HeaderFooterPanel extends AbstractPanel {

    private static final long serialVersionUID = 1L;
    protected GridPanel headerPanel;
    private JCheckBox headerRule;
    protected GridPanel footerPanel;
    private JCheckBox footerRule;

    @Override
    protected void createGui() {
        createGui(0, false);
    }
    
    protected void createGui(final int columnOffset, final boolean split) {
        
        super.createGui();
        setColumnOffset(columnOffset);

        headerPanel = createHeaderPanel();
        addComponent(headerPanel, Anchor.NORTHWEST, Fill.NONE);

        if (split == true) {
            incrRow();
        }
        
        footerPanel = createFooterPanel();
        addComponent(footerPanel, Anchor.NORTHWEST, Fill.NONE);

    }

    private GridPanel createHeaderPanel() {

        final GridPanel panel = new GridPanel(ResourceServices.getString(res,"C_HEADER"));

        panel.startSubPanel();
        
        headerRule = jf.createCheckBox(new Identifier("header.rule"), "header.rule");
        panel.addComponent(headerRule);
        
        return panel;
    }

    private GridPanel createFooterPanel() {

        final GridPanel panel = new GridPanel(ResourceServices.getString(res,"C_FOOTER"));

        panel.startSubPanel();
        
        footerRule = jf.createCheckBox(new Identifier("footer.rule"), "footer.rule");
        panel.addComponent(footerRule);
        
        return panel;
    }

    @Override
    public void syncView(Project project, final AbstractDriver driver) {
        
        headerRule.setSelected(driver.isParameterEnabled("header.rule", true));
        footerRule.setSelected(driver.isParameterEnabled("footer.rule", true));
    }

    @Override
    public void syncModel(Project project, final AbstractDriver driver) {

        driver.setParameter("header.rule", headerRule.isSelected());
        driver.setParameter("footer.rule", footerRule.isSelected());
    }
}
