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

import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class EbnfPanel extends AbstractPanel {

    private static final long serialVersionUID = 1L;
    protected GridPanel defaultsPanel;
    private JTextField ebnfAssignment;
    private JTextField ebnfStatementTerminator;
    
    @Override
    protected void createGui() {

        super.createGui();
        
        defaultsPanel = createDefaultsPanel();
        addComponent(defaultsPanel, Anchor.NORTHWEST, Fill.NONE);

        addVerticalGlue();
    }

    private GridPanel createDefaultsPanel() {

        GridPanel panel = new GridPanel(ResourceServices.getString(res,"C_DEFAULT_VALUES"));

        panel.startSubPanel();
        
        ebnfAssignment = jf.createTextField(new Identifier(Constants.PARAM_EBNF_ASSIGNMENT), 6);
        panel.addLabeledComponent(Constants.PARAM_EBNF_ASSIGNMENT, ebnfAssignment);
        
        ebnfStatementTerminator = jf.createTextField(new Identifier(Constants.PARAM_EBNF_STATEMENT_TERMINATOR), 6);
        panel.addLabeledComponent(Constants.PARAM_EBNF_STATEMENT_TERMINATOR, ebnfStatementTerminator);
        
        return panel;
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {
        
        ebnfAssignment.setText(driver.getParameter(Constants.PARAM_EBNF_ASSIGNMENT));
        ebnfStatementTerminator.setText(driver.getParameter(Constants.PARAM_EBNF_STATEMENT_TERMINATOR));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        driver.setParameter(Constants.PARAM_EBNF_ASSIGNMENT, ebnfAssignment.getText());
        driver.setParameter(Constants.PARAM_EBNF_STATEMENT_TERMINATOR,ebnfStatementTerminator.getText());
    }

}
