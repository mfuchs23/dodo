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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class LinkingPanel extends AbstractPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    
    private JTextField targetDatabaseDocument;
    private JButton targetDatabaseDocumentBrowseButton;
    private JTextField targetsFilename;
    private JButton targetsFilenameBrowseButton;
    private JTextField olinkBaseUri;
    private JComboBox insertXrefPageNumber;
    private JTextField xrefLabelPageSeparator;
    private JTextField xrefLabelTitleSeparator;
    private JTextField xrefTitlePageSeparator;
    protected GridPanel xrefPanel;
    private JComboBox collectXrefTargets;
    private JCheckBox olinkDebug;

    private JTextField currentDocid;

    @Override
    protected void createGui() {
        createGui(0);
    }

    protected void createGui(int columnOffset) {

        super.createGui();

        setColumnOffset(columnOffset);

        GridPanel targetsPanel = createTargetsPanel();
        addComponent(targetsPanel, Anchor.NORTHWEST, Fill.NONE);

        incrRow();
        
        GridPanel olinkPanel = createOlinkPanel();
        addComponent(olinkPanel, Anchor.NORTHWEST, Fill.NONE);

        incrRow();
        
        xrefPanel = createXrefPanel();
        addComponent(xrefPanel, Anchor.NORTHWEST, Fill.NONE);

    }

    private GridPanel createTargetsPanel() {

        GridPanel panel = new GridPanel();
        panel.setBorder(BorderFactory.createTitledBorder("targets"));
        
        currentDocid = jf.createTextField(new Identifier(Constants.PARAM_CURRENT_DOCID), 29);
        panel.addLabeledComponent(Constants.PARAM_CURRENT_DOCID, currentDocid);

        panel.incrRow();

        targetDatabaseDocument = jf.createTextField(new Identifier(Constants.PARAM_TARGET_DATABASE_DOCUMENT), 29);
        panel.addLabeledComponent(Constants.PARAM_TARGET_DATABASE_DOCUMENT, targetDatabaseDocument);

        targetDatabaseDocumentBrowseButton = jf.createButton(ResourceServices.getString(res,"C_BROWSE"));
        targetDatabaseDocumentBrowseButton.addActionListener(this);
        targetDatabaseDocumentBrowseButton.setActionCommand("chooseTargetDatabaseDocument");
        panel.addComponent(targetDatabaseDocumentBrowseButton);

        panel.incrRow();
        
        targetsFilename = jf.createTextField(new Identifier(Constants.PARAM_TARGETS_FILENAME), 29);
        panel.addLabeledComponent(Constants.PARAM_TARGETS_FILENAME, targetsFilename);

        targetsFilenameBrowseButton = jf.createButton(ResourceServices.getString(res,"C_BROWSE"));
        targetsFilenameBrowseButton.addActionListener(this);
        targetsFilenameBrowseButton.setActionCommand("chooseTargetsFilename");
        panel.addComponent(targetsFilenameBrowseButton);

        return panel;
    }

    private GridPanel createOlinkPanel() {

        GridPanel panel = new GridPanel();
        panel.setBorder(BorderFactory.createTitledBorder("olink"));

        panel.startSubPanel();

        olinkDebug = jf.createCheckBox(new Identifier(Constants.PARAM_OLINK_DEBUG), Constants.PARAM_OLINK_DEBUG);
        panel.addComponent(olinkDebug);

        olinkBaseUri = jf.createTextField(new Identifier(Constants.PARAM_OLINK_BASE_URI), 29);
        panel.addLabeledComponent(Constants.PARAM_OLINK_BASE_URI, olinkBaseUri);

        return panel;
    }

    private GridPanel createXrefPanel() {

        GridPanel panel = new GridPanel();
        panel.setBorder(BorderFactory.createTitledBorder(ResourceServices.getString(res,"C_CROSS_REFERENCES")));

        panel.startSubPanel();

        insertXrefPageNumber = jf.createComboBox(new Identifier(Constants.PARAM_INSERT_XREF_PAGE_NUMBER), new String[] { "no", "yes", "maybe" });
        panel.addLabeledComponent(Constants.PARAM_INSERT_XREF_PAGE_NUMBER, insertXrefPageNumber);

        collectXrefTargets = jf.createComboBox(new Identifier(Constants.PARAM_COLLECT_XREF_TARGETS), new String[] { "no", "yes", "only" });
        panel.addLabeledComponent(Constants.PARAM_COLLECT_XREF_TARGETS, collectXrefTargets);

        xrefLabelPageSeparator = jf.createTextField(new Identifier(Constants.PARAM_XREF_LABEL_PAGE_SEPARATOR), 3);
        panel.addLabeledComponent(Constants.PARAM_XREF_LABEL_PAGE_SEPARATOR, xrefLabelPageSeparator);
        
        panel.startSubPanel();
        
        xrefLabelTitleSeparator = jf.createTextField(new Identifier(Constants.PARAM_XREF_LABEL_TITLE_SEPARATOR), 3);
        panel.addLabeledComponent(Constants.PARAM_XREF_LABEL_TITLE_SEPARATOR, xrefLabelTitleSeparator);
        
        xrefTitlePageSeparator = jf.createTextField(new Identifier(Constants.PARAM_XREF_TITLE_PAGE_SEPARATOR), 3);
        panel.addLabeledComponent(Constants.PARAM_XREF_TITLE_PAGE_SEPARATOR, xrefTitlePageSeparator);
        
        return panel;
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        currentDocid.setText(driver.getParameter(Constants.PARAM_CURRENT_DOCID));
        targetDatabaseDocument.setText(driver.getParameter(Constants.PARAM_TARGET_DATABASE_DOCUMENT));
        targetsFilename.setText(driver.getParameter(Constants.PARAM_TARGETS_FILENAME));
        olinkBaseUri.setText(driver.getParameter(Constants.PARAM_OLINK_BASE_URI));
        insertXrefPageNumber.setSelectedItem(driver.getParameter(Constants.PARAM_INSERT_XREF_PAGE_NUMBER));
        xrefLabelPageSeparator.setText(driver.getParameter(Constants.PARAM_XREF_LABEL_PAGE_SEPARATOR));
        xrefLabelTitleSeparator.setText(driver.getParameter(Constants.PARAM_XREF_LABEL_TITLE_SEPARATOR));
        xrefTitlePageSeparator.setText(driver.getParameter(Constants.PARAM_XREF_TITLE_PAGE_SEPARATOR));
        collectXrefTargets.setSelectedItem(driver.getParameter(Constants.PARAM_COLLECT_XREF_TARGETS));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {
    
        driver.setParameter(Constants.PARAM_CURRENT_DOCID, currentDocid.getText());
        driver.setParameter(Constants.PARAM_TARGET_DATABASE_DOCUMENT, targetDatabaseDocument.getText());
        driver.setParameter(Constants.PARAM_TARGETS_FILENAME, targetsFilename.getText());
        driver.setParameter(Constants.PARAM_OLINK_BASE_URI, olinkBaseUri.getText());
        driver.setParameter(Constants.PARAM_INSERT_XREF_PAGE_NUMBER, insertXrefPageNumber.getSelectedItem());
        driver.setParameter(Constants.PARAM_XREF_LABEL_PAGE_SEPARATOR, xrefLabelPageSeparator.getText());
        driver.setParameter(Constants.PARAM_XREF_LABEL_TITLE_SEPARATOR, xrefLabelTitleSeparator.getText());
        driver.setParameter(Constants.PARAM_XREF_TITLE_PAGE_SEPARATOR, xrefTitlePageSeparator.getText());
        driver.setParameter(Constants.PARAM_COLLECT_XREF_TARGETS, collectXrefTargets.getSelectedItem());
    }

    public void actionPerformed(ActionEvent event) {

        String cmd = event.getActionCommand();

        if (cmd == null) {
            return;
        }

        if (cmd.equals("chooseTargetDatabaseDocument")) {
             chooseFile(targetDatabaseDocument);
        }
       
        if (cmd.equals("chooseTargetsFilename")) {
             chooseFileAsUrl(targetsFilename);
        }
    }
}
