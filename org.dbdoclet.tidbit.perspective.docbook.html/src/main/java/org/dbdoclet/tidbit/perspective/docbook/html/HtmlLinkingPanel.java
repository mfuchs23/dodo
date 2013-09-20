package org.dbdoclet.tidbit.perspective.docbook.html;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.perspective.panel.docbook.LinkingPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlLinkingPanel extends LinkingPanel {

    private static final long serialVersionUID = 1L;
    private JTextField ulinkTarget;
    private JCheckBox useRoleAsXrefstyle;
    private JCheckBox xrefWithNumberAndTitle;

    public HtmlLinkingPanel() {
        createGui();
    }

    @Override
    protected void createGui() {

        super.createGui(0);

        xrefWithNumberAndTitle = jf.createCheckBox(new Identifier(Constants.PARAM_XREF_WITH_NUMBER_AND_TITLE), Constants.PARAM_XREF_WITH_NUMBER_AND_TITLE);
        xrefPanel.addComponent(xrefWithNumberAndTitle);

        useRoleAsXrefstyle = jf.createCheckBox(new Identifier(Constants.PARAM_USE_ROLE_AS_XREFSTYLE), Constants.PARAM_USE_ROLE_AS_XREFSTYLE);
        xrefPanel.addComponent(useRoleAsXrefstyle);

        incrRow();

        addComponent(createHtmlPanel());

        addVerticalGlue();
    }

    private GridPanel createHtmlPanel() {

        GridPanel panel = new GridPanel("HTML");

        ulinkTarget = jf.createTextField(new Identifier(Constants.PARAM_ULINK_TARGET), 10);
        panel.addLabeledComponent(Constants.PARAM_ULINK_TARGET, ulinkTarget);

        return panel;
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        super.syncView(project, driver);

        useRoleAsXrefstyle.setSelected(driver.isParameterEnabled(Constants.PARAM_USE_ROLE_AS_XREFSTYLE, false));
        xrefWithNumberAndTitle.setSelected(driver.isParameterEnabled(Constants.PARAM_XREF_WITH_NUMBER_AND_TITLE, true));
        ulinkTarget.setText(driver.getParameter(Constants.PARAM_ULINK_TARGET));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        super.syncModel(project, driver);

        driver.setParameter(Constants.PARAM_USE_ROLE_AS_XREFSTYLE, useRoleAsXrefstyle.isSelected());
        driver.setParameter(Constants.PARAM_XREF_WITH_NUMBER_AND_TITLE, xrefWithNumberAndTitle.isSelected());
        driver.setParameter(Constants.PARAM_ULINK_TARGET, ulinkTarget.getText());
    }
}
