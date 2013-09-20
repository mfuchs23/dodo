package org.dbdoclet.tidbit.perspective.docbook.html;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.perspective.panel.docbook.AdmonitionPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlAdmonitionPanel extends AdmonitionPanel {

    private JTextField admonStyle;

    public HtmlAdmonitionPanel() {
        createGui();
    }

    private static final long serialVersionUID = 1L;

    @Override
    protected void createGui() {
    
        super.createGui();

        defaultsPanel.startSubPanel();
        admonStyle = jf.createTextField(new Identifier(Constants.PARAM_ADMON_STYLE), 42);
        defaultsPanel.addLabeledComponent(new JLabel(ResourceServices.getString(res,"C_ADMON_STYLE")), admonStyle);

        addVerticalGlue();
    }
    
    @Override
    public void syncView(Project project, AbstractDriver driver) {
    
        super.syncView(project, driver);
        admonStyle.setText(driver.getParameter("admon.style"));
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {
        
        super.syncModel(project, driver);
        driver.setParameter("admon.style", admonStyle.getText());
    }
}
