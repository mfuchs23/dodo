package org.dbdoclet.tidbit.perspective.docbook.html;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.perspective.panel.docbook.TocPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlTocPanel extends TocPanel implements ActionListener {

    private JCheckBox annotateToc;
    private JCheckBox autotocLabelInHyperlink;
    private JComboBox tocListType;
    private JTextField manualToc;
    
    public HtmlTocPanel() {
        createGui();
    }

    private static final long serialVersionUID = 1L;

    @Override
    protected void createGui() {

        super.createGui();

        incrRow();
        addComponent(createTocPanel());

        addVerticalGlue();
    }

    private GridPanel createTocPanel() {

        GridPanel tocPanel = new GridPanel("HTML");
        tocPanel.startSubPanel();

        annotateToc = jf.createCheckBox(new Identifier(Constants.PARAM_ANNOTATE_TOC), Constants.PARAM_ANNOTATE_TOC);
        tocPanel.addComponent(annotateToc);

        autotocLabelInHyperlink = jf.createCheckBox(new Identifier(Constants.PARAM_AUTOTOC_LABEL_IN_HYPERLINK), Constants.PARAM_AUTOTOC_LABEL_IN_HYPERLINK);
        tocPanel.addComponent(autotocLabelInHyperlink);

        tocListType = jf.createComboBox(new Identifier(Constants.PARAM_TOC_LIST_TYPE), new String[] { "dl", "ol", "ul"});
        tocPanel.addLabeledComponent(Constants.PARAM_TOC_LIST_TYPE, tocListType);
        
        tocPanel.startSubPanel();
        
        manualToc = jf.createTextField(new Identifier(Constants.PARAM_MANUAL_TOC), 23);
        tocPanel.addLabeledComponent(Constants.PARAM_MANUAL_TOC, manualToc);

        JButton button = jf.createButton(ResourceServices.getString(res,"C_BROWSE"));
        button.setActionCommand("chooseManualToc");
        button.addActionListener(this);
        tocPanel.addComponent(button);

        return tocPanel;
    }


    @Override
    public void syncView(Project project, AbstractDriver driver) {

        super.syncView(project, driver);
        
        annotateToc.setSelected(driver.isParameterEnabled(Constants.PARAM_ANNOTATE_TOC, true));
        autotocLabelInHyperlink.setSelected(driver.isParameterEnabled(Constants.PARAM_AUTOTOC_LABEL_IN_HYPERLINK, true));
        tocListType.setSelectedItem(driver.getParameter(Constants.PARAM_TOC_LIST_TYPE));
        manualToc.setText(driver.getParameter(Constants.PARAM_MANUAL_TOC));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        super.syncModel(project, driver);
        driver.setParameter(Constants.PARAM_ANNOTATE_TOC, annotateToc.isSelected());
        driver.setParameter(Constants.PARAM_AUTOTOC_LABEL_IN_HYPERLINK, autotocLabelInHyperlink.isSelected());
        driver.setParameter(Constants.PARAM_TOC_LIST_TYPE, tocListType.getSelectedItem());
        driver.setParameter(Constants.PARAM_MANUAL_TOC, manualToc.getText());
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        String cmd = event.getActionCommand();

        if (cmd == null) {
            return;
        }

        if (cmd.equals("chooseManualToc")) {
            chooseFileAsUrl(manualToc);
        }

        super.actionPerformed(event);
    }
}
