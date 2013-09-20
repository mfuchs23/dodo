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

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class ListPanel extends AbstractPanel {

    private static final long serialVersionUID = 1L;
    
    protected GridPanel variableListPanel;
    private JTextField variablelistTermSeparatorEntry;
    private JCheckBox variablelistTermBreakAfterCheckBox;
    private JCheckBox segmentedlistAsTableCheckBox;

    @Override
    protected void createGui() {
        createGui(0);
    }

    protected void createGui(final int columnOffset) {

        super.createGui();

        setColumnOffset(columnOffset);

        variableListPanel = createVariableListPanel();
        addComponent(variableListPanel, Anchor.NORTHWEST, Fill.NONE);
    }

    private GridPanel createVariableListPanel() {

        final GridPanel panel = new GridPanel();
        panel.setBorder(BorderFactory.createTitledBorder("segmented-/variablelist"));

        segmentedlistAsTableCheckBox = jf.createCheckBox(new Identifier(Constants.PARAM_SEGMENTEDLIST_AS_TABLE), Constants.PARAM_SEGMENTEDLIST_AS_TABLE);
        panel.addComponent(segmentedlistAsTableCheckBox);

        variablelistTermBreakAfterCheckBox = jf.createCheckBox(new Identifier(Constants.PARAM_VARIABLELIST_TERM_BREAK_AFTER), Constants.PARAM_VARIABLELIST_TERM_BREAK_AFTER);
        panel.addComponent(variablelistTermBreakAfterCheckBox);

        variablelistTermSeparatorEntry = jf.createTextField(new Identifier(Constants.PARAM_VARIABLELIST_TERM_SEPARATOR), 6);
        panel.addLabeledComponent(Constants.PARAM_VARIABLELIST_TERM_SEPARATOR, variablelistTermSeparatorEntry);

        panel.addHorizontalGlue();

        return panel;
    }

    @Override
    public void syncView(Project project, final AbstractDriver driver) {

        segmentedlistAsTableCheckBox.setSelected(driver.isParameterEnabled(Constants.PARAM_SEGMENTEDLIST_AS_TABLE, false));
        variablelistTermBreakAfterCheckBox.setSelected(driver
                .isParameterEnabled(Constants.PARAM_VARIABLELIST_TERM_BREAK_AFTER, false));
        variablelistTermSeparatorEntry.setText(driver.getParameter(Constants.PARAM_VARIABLELIST_TERM_SEPARATOR));
    }

    @Override
    public void syncModel(Project project, final AbstractDriver driver) {
    
        driver.setParameter(Constants.PARAM_SEGMENTEDLIST_AS_TABLE, segmentedlistAsTableCheckBox.isSelected());
        driver.setParameter(Constants.PARAM_VARIABLELIST_TERM_BREAK_AFTER, variablelistTermBreakAfterCheckBox.isSelected());
        driver.setParameter(Constants.PARAM_VARIABLELIST_TERM_SEPARATOR, variablelistTermSeparatorEntry.getText());
    }
}
