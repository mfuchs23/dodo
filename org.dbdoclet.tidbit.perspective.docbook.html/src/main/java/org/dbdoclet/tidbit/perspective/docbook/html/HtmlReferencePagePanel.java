package org.dbdoclet.tidbit.perspective.docbook.html;

import org.dbdoclet.tidbit.perspective.panel.docbook.ReferencePagePanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlReferencePagePanel extends ReferencePagePanel {

    public HtmlReferencePagePanel() {
        createGui();
    }

    private static final long serialVersionUID = 1L;

    @Override
    protected void createGui() {

        super.createGui();
        addVerticalGlue();
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        super.syncView(project, driver);
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        super.syncModel(project, driver);
    }
}
