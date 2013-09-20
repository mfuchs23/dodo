package org.dbdoclet.tidbit.perspective.docbook.html;

import javax.swing.JCheckBox;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.perspective.panel.docbook.IndexPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlIndexPanel extends IndexPanel{

    private JCheckBox indexLinksToSection;
    private JCheckBox indexPreferTitleabbrev;

    public HtmlIndexPanel() {
        createGui();
    }

    private static final long serialVersionUID = 1L;

    @Override
    protected void createGui() {

        super.createGui();
        
        incrRow();
        addComponent(createIndexPanel());
        
        addHorizontalGlue();
        addVerticalGlue();
    }

    private GridPanel createIndexPanel() {

        GridPanel indexPanel = new GridPanel("HTML");
        indexPanel.startSubPanel();

        indexLinksToSection = jf.createCheckBox(new Identifier(Constants.PARAM_INDEX_LINKS_TO_SECTION), Constants.PARAM_INDEX_LINKS_TO_SECTION);
        indexPanel.addComponent(indexLinksToSection);

        indexPreferTitleabbrev = jf.createCheckBox(new Identifier(Constants.PARAM_INDEX_PREFER_TITLE_ABBREV), Constants.PARAM_INDEX_PREFER_TITLE_ABBREV);
        indexPanel.addComponent(indexPreferTitleabbrev);

        return indexPanel;
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        super.syncView(project, driver);
        indexLinksToSection.setSelected(driver.isParameterEnabled(Constants.PARAM_INDEX_LINKS_TO_SECTION, false));
        indexPreferTitleabbrev.setSelected(driver.isParameterEnabled(Constants.PARAM_INDEX_PREFER_TITLE_ABBREV, false));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        super.syncModel(project, driver);
        driver.setParameter(Constants.PARAM_INDEX_LINKS_TO_SECTION, indexLinksToSection.isSelected());
        driver.setParameter(Constants.PARAM_INDEX_PREFER_TITLE_ABBREV, indexPreferTitleabbrev.isSelected());
    }
}
