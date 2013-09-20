 package org.dbdoclet.tidbit.perspective.docbook.html;

import javax.swing.JCheckBox;

import org.dbdoclet.Identifier;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.perspective.panel.docbook.CalloutPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlCalloutPanel extends CalloutPanel {

    private static final long serialVersionUID = 1L;
    private JCheckBox calloutListTable;

    public HtmlCalloutPanel() {

    	createGui();
    }

    @Override
    protected void createGui() {

        super.createGui();
        
        calloutListTable = jf.createCheckBox(new Identifier(Constants.PARAM_CALLOUT_LIST_TABLE), Constants.PARAM_CALLOUT_LIST_TABLE);
        defaultsPanel.appendToRow(0, calloutListTable);
        
        addVerticalGlue();
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        super.syncView(project, driver);
        calloutListTable.setSelected(driver.isParameterEnabled(Constants.PARAM_CALLOUT_LIST_TABLE, false));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        super.syncModel(project, driver);
        driver.setParameter(Constants.PARAM_CALLOUT_LIST_TABLE, calloutListTable.isSelected());
    }
}
