package org.dbdoclet.tidbit.perspective.docbook.html;

import javax.swing.JCheckBox;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.tidbit.perspective.panel.docbook.ListPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlListPanel extends ListPanel {

    private static final long serialVersionUID = 1L;
    private JCheckBox variablelistAsTableCheckBox;

    public HtmlListPanel() {
        createGui();
    }

    @Override
    protected void createGui() {

        super.createGui(0);

        variableListPanel.incrRow();
        
        variablelistAsTableCheckBox = jf.createCheckBox(new Identifier("variablelist.as.table"), "variablelist.as.table");
        variableListPanel.addComponent(variablelistAsTableCheckBox, Anchor.NORTHWEST);

        addVerticalGlue();
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        super.syncView(project, driver);
        variablelistAsTableCheckBox.setSelected(driver.isParameterEnabled("variablelist.as.table", false));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        super.syncModel(project, driver);
        driver.setParameter("variablelist.as.table", variablelistAsTableCheckBox.isSelected());
    }
}
