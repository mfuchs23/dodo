package org.dbdoclet.tidbit.perspective.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.text.IConsole;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;

/**
 * Die Klasse <code>InfoPanel</code> realisiert einen Pultordner mit
 * Informationsregistern.
 * 
 * @author mfuchs
 */
public class InfoPanel extends JTabbedPane {

    private static final long serialVersionUID = 1L;

    private ResourceBundle res;
    private IConsole console;

    public InfoPanel() {
        res = StaticContext.getResourceBundle();
    }

    public IConsole getConsole() {
        return console;
    }

    public void setConsole(IConsole console) {
        this.console = console;
    }

    public void createGui() {

        setPreferredSize(new Dimension(600, 131));

        GridPanel consolePanel = new GridPanel();
        consolePanel.addComponent(new JScrollPane((Component) console), Anchor.CENTER, Fill.BOTH);
        addTab(ResourceServices.getString(res, "C_CONSOLE"), consolePanel);

        console.info(new Date().toString());
    }
}