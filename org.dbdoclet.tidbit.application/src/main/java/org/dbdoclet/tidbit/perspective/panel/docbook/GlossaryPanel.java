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

public class GlossaryPanel extends AbstractPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private GridPanel defaultsPanel;
    private JCheckBox glosstermAutoLink;
    private JTextField glossaryCollection;
    
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
        
        glosstermAutoLink = jf.createCheckBox(new Identifier(Constants.PARAM_GLOSSTERM_AUTO_LINK), Constants.PARAM_GLOSSTERM_AUTO_LINK);
        panel.addComponent(glosstermAutoLink);

        panel.incrRow();
        
        glossaryCollection = jf.createTextField(new Identifier(Constants.PARAM_GLOSSARY_COLLECTION), 29);
        panel.addLabeledComponent(Constants.PARAM_GLOSSARY_COLLECTION, glossaryCollection);
        
        JButton glossaryCollectionBrowseButton = jf.createButton(ResourceServices.getString(res,"C_BROWSE"));
        glossaryCollectionBrowseButton.addActionListener(this);
        glossaryCollectionBrowseButton.setActionCommand("chooseGlossaryCollection");
        panel.addComponent(glossaryCollectionBrowseButton);

        return panel;
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        glosstermAutoLink.setSelected(driver.isParameterEnabled(Constants.PARAM_GLOSSTERM_AUTO_LINK, false));
        glossaryCollection.setText(driver.getParameter(Constants.PARAM_GLOSSARY_COLLECTION));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        driver.setParameter(Constants.PARAM_GLOSSTERM_AUTO_LINK, glosstermAutoLink.isSelected());
        driver.setParameter(Constants.PARAM_GLOSSARY_COLLECTION, glossaryCollection.getText());
    }

    public void actionPerformed(ActionEvent event) {

        String cmd = event.getActionCommand();

        if (cmd == null) {
            return;
        }

        if (cmd.equals("chooseGlossaryCollection")) {
             chooseFileAsUrl(glossaryCollection);
        }
    }

}
