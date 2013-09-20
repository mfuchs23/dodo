 package org.dbdoclet.tidbit.perspective.docbook.html;

import javax.swing.JCheckBox;

import org.dbdoclet.Identifier;
import org.dbdoclet.tidbit.perspective.panel.docbook.TablePanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlTablePanel extends TablePanel {

    private static final long serialVersionUID = 1L;
    private JCheckBox tableBordersWithCssCheckBox;

    public HtmlTablePanel() {
        createGui();
    }

    @Override
    protected void createGui() {

        super.createGui();

        tableBordersWithCssCheckBox = jf.createCheckBox(new Identifier("table.borders.with.css"), "table.borders.with.css");
        framePanel.addComponent(tableBordersWithCssCheckBox);
        addVerticalGlue();
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        super.syncView(project, driver);
        tableBordersWithCssCheckBox.setSelected(driver.isParameterEnabled("table.borders.with.css", true));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        super.syncModel(project, driver);
        driver.setParameter("table.borders.with.css", tableBordersWithCssCheckBox.isSelected());
    }
}
