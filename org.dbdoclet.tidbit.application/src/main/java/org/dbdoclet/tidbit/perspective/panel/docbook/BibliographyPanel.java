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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class BibliographyPanel extends AbstractPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private GridPanel defaultsPanel;
    private JCheckBox bibliographyNumbered;
    private JTextField bibliographyCollection;
    
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
        
        bibliographyNumbered = jf.createCheckBox(new Identifier(Constants.PARAM_BIBLIOGRAPHY_NUMBERED), Constants.PARAM_BIBLIOGRAPHY_NUMBERED);
        panel.addComponent(bibliographyNumbered);
        
        panel.incrRow();
        
        bibliographyCollection = jf.createTextField(new Identifier(Constants.PARAM_BIBLIOGRAPHY_COLLECTION), 29);
        panel.addLabeledComponent(Constants.PARAM_BIBLIOGRAPHY_COLLECTION, bibliographyCollection);
        
        JButton bibliographyCollectionBrowseButton = jf.createButton(ResourceServices.getString(res,"C_BROWSE"));
        bibliographyCollectionBrowseButton.addActionListener(this);
        bibliographyCollectionBrowseButton.setActionCommand("chooseBibliographyCollection");
        panel.addComponent(bibliographyCollectionBrowseButton);
        return panel;
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        bibliographyNumbered.setSelected(driver.isParameterEnabled(Constants.PARAM_BIBLIOGRAPHY_NUMBERED, false));
        bibliographyCollection.setText(driver.getParameter(Constants.PARAM_BIBLIOGRAPHY_COLLECTION));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

       driver.setParameter(Constants.PARAM_BIBLIOGRAPHY_NUMBERED, bibliographyNumbered.isSelected());
       driver.setParameter(Constants.PARAM_BIBLIOGRAPHY_COLLECTION, bibliographyCollection.getText());
    }

    public void actionPerformed(ActionEvent event) {

        String cmd = event.getActionCommand();

        if (cmd == null) {
            return;
        }

        if (cmd.equals("chooseBibliographyCollection")) {
             chooseFileAsUrl(bibliographyCollection);
        }
    }

}
