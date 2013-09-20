package org.dbdoclet.tidbit.perspective.docbook.html;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.dbdoclet.tidbit.perspective.panel.docbook.MiscellaneousPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlMiscellaneousPanel extends MiscellaneousPanel implements ActionListener {

    public HtmlMiscellaneousPanel() {
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

    @Override
    public void actionPerformed(ActionEvent event) {

        String cmd = event.getActionCommand();

        if (cmd == null) {
            return;
        }

        super.actionPerformed(event);
    }
}
