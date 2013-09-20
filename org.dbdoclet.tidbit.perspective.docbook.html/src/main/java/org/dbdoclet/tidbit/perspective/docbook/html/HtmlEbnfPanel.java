 package org.dbdoclet.tidbit.perspective.docbook.html;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;

import org.dbdoclet.Identifier;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.perspective.panel.docbook.EbnfPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlEbnfPanel extends EbnfPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JCheckBox ebnfTableBorder;
    private JButton ebnfTableBgcolorButton;
    private Color ebnfTableBgColor;

    public HtmlEbnfPanel() {

        createGui();
    }

    @Override
    protected void createGui() {

        super.createGui();

        ebnfTableBorder = jf.createCheckBox(new Identifier("ebnf.table.border"), "ebnf.table.border");
        defaultsPanel.addComponent(ebnfTableBorder);

        ebnfTableBgcolorButton = jf.createButton(Constants.COLOR_CHAR + " ebnf.table.bgcolor...");
        ebnfTableBgcolorButton.addActionListener(this);
        defaultsPanel.addComponent(ebnfTableBgcolorButton);

        addVerticalGlue();
    }

    @Override
    public void syncView(Project project, AbstractDriver driver) {

        super.syncView(project, driver);

        ebnfTableBorder.setSelected(driver.isParameterEnabled("ebnf.table.border", true));
        ebnfTableBgColor = driver.getColorParameter("ebnf.table.bgcolor");
        ebnfTableBgcolorButton.setForeground(ebnfTableBgColor);
    }

    @Override
    public void syncModel(Project project, AbstractDriver driver) {

        super.syncModel(project, driver);
        driver.setParameter("ebnf.table.border", ebnfTableBorder.isSelected());
        driver.setColorParameter("ebnf.table.bgcolor", ebnfTableBgColor);
    }

    public void actionPerformed(ActionEvent event) {

        Object source = event.getSource();
        
        if (source != null && source == ebnfTableBgcolorButton) {

            Color color = JColorChooser.showDialog(this, ResourceServices.getString(res,"C_COLOR"), ebnfTableBgColor);
        
            if (color != null) {
                ebnfTableBgColor = color;
                ebnfTableBgcolorButton.setForeground(color);
            }
        } 

    }
}
