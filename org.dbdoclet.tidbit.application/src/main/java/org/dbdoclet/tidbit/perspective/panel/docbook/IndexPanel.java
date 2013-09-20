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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class IndexPanel extends AbstractPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JCheckBox generateIndexCheckBox;
    private JCheckBox makeIndexMarkup;
    private JCheckBox indexOnRole;
    private JCheckBox indexOnType;
    private JComboBox indexMethod;
    private JTextField indexTermSeparator;
    private JTextField indexNumberSeparator;
    private JTextField indexRangeSeparator;

    @Override
    protected void createGui() {
        createGui(0);
    }
    
    protected void createGui(final int columnOffset) {

        super.createGui();

        setColumnOffset(columnOffset);
        
        final GridPanel defaultsPanel = createDefaultsPanel();
        addComponent(defaultsPanel, Anchor.NORTHWEST);
        incrRow();

    }

    private GridPanel createDefaultsPanel() {

        final GridPanel panel = new GridPanel();
        panel.setBorder(BorderFactory.createTitledBorder(ResourceServices.getString(res,"C_DEFAULT_VALUES")));
        panel.startSubPanel();
        
        generateIndexCheckBox = jf.createCheckBox(new Identifier(Constants.PARAM_GENERATE_INDEX), Constants.PARAM_GENERATE_INDEX);
        panel.addComponent(generateIndexCheckBox);

        makeIndexMarkup = jf.createCheckBox(new Identifier(Constants.PARAM_MAKE_INDEX_MARKUP), Constants.PARAM_MAKE_INDEX_MARKUP);
        panel.addComponent(makeIndexMarkup);
        
        indexOnRole = jf.createCheckBox(new Identifier(Constants.PARAM_INDEX_ON_ROLE), Constants.PARAM_INDEX_ON_ROLE);
        panel.addComponent(indexOnRole);
        
        indexOnType = jf.createCheckBox(new Identifier(Constants.PARAM_INDEX_ON_TYPE), Constants.PARAM_INDEX_ON_TYPE);
        panel.addComponent(indexOnType);
        
        indexMethod = jf.createComboBox(new Identifier(Constants.PARAM_INDEX_METHOD), new String[] { "basic", "kosek", "kimber" });
        panel.addLabeledComponent(Constants.PARAM_INDEX_METHOD, indexMethod);
        
        panel.startSubPanel();
        
        indexTermSeparator = jf.createTextField(new Identifier(Constants.PARAM_INDEX_TERM_SEPARATOR), 2);
        panel.addLabeledComponent(Constants.PARAM_INDEX_TERM_SEPARATOR, indexTermSeparator);
        
        indexNumberSeparator = jf.createTextField(new Identifier(Constants.PARAM_INDEX_NUMBER_SEPARATOR), 2);
        panel.addLabeledComponent(Constants.PARAM_INDEX_NUMBER_SEPARATOR, indexNumberSeparator);
        
        indexRangeSeparator = jf.createTextField(new Identifier(Constants.PARAM_INDEX_RANGE_SEPARATOR), 2);
        panel.addLabeledComponent(Constants.PARAM_INDEX_RANGE_SEPARATOR, indexRangeSeparator);
        
        return panel;
    }

    @Override
    public void syncView(Project project, final AbstractDriver driver) {

        generateIndexCheckBox.setSelected(driver.isParameterEnabled("generate.index", true));
        makeIndexMarkup.setSelected(driver.isParameterEnabled(Constants.PARAM_MAKE_INDEX_MARKUP, false));
        indexOnRole.setSelected(driver.isParameterEnabled(Constants.PARAM_INDEX_ON_ROLE, false));
        indexOnType.setSelected(driver.isParameterEnabled(Constants.PARAM_INDEX_ON_TYPE, false));
        indexMethod.setSelectedItem(driver.getParameter(Constants.PARAM_INDEX_METHOD));
        indexNumberSeparator.setText(driver.getParameter(Constants.PARAM_INDEX_NUMBER_SEPARATOR));
        indexRangeSeparator.setText(driver.getParameter(Constants.PARAM_INDEX_RANGE_SEPARATOR));
        indexTermSeparator.setText(driver.getParameter(Constants.PARAM_INDEX_TERM_SEPARATOR));
    }

    @Override
    public void syncModel(Project project, final AbstractDriver driver) {

        driver.setParameter(Constants.PARAM_GENERATE_INDEX, generateIndexCheckBox.isSelected());
        driver.setParameter(Constants.PARAM_MAKE_INDEX_MARKUP, makeIndexMarkup.isSelected());
        driver.setParameter(Constants.PARAM_INDEX_ON_ROLE, indexOnRole.isSelected());
        driver.setParameter(Constants.PARAM_INDEX_ON_TYPE, indexOnType.isSelected());
        driver.setParameter(Constants.PARAM_INDEX_METHOD, indexMethod.getSelectedItem());
        driver.setParameter(Constants.PARAM_INDEX_NUMBER_SEPARATOR, indexNumberSeparator.getText());
        driver.setParameter(Constants.PARAM_INDEX_RANGE_SEPARATOR, indexRangeSeparator.getText());
        driver.setParameter(Constants.PARAM_INDEX_TERM_SEPARATOR, indexTermSeparator.getText());
    }

    public void actionPerformed(final ActionEvent event) {

        final String cmd = event.getActionCommand();

        if (cmd == null) {
            return;
        }
    }
}
