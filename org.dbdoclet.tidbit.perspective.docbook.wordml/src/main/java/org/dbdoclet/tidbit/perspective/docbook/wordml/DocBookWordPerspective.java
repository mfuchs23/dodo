package org.dbdoclet.tidbit.perspective.docbook.wordml;

import java.awt.Component;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.perspective.AbstractPerspective;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.perspective.panel.InfoPanel;
import org.dbdoclet.tidbit.perspective.panel.docbook.AbstractPanel;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;

public class DocBookWordPerspective extends AbstractPerspective implements Perspective {

    private GridPanel registerPanel;
    private JTabbedPane tabbedPane;

    protected void activate(ComponentContext context) {
    }

    public Application getApplication() {
        return application;
    }

    public Icon getIcon() {

        Icon icon = null;

        URL iconUrl = ResourceServices.getResourceAsUrl("/images/dbdoclet.png", DocBookWordPerspective.class
                .getClassLoader());

        if (iconUrl != null) {
            icon = new ImageIcon(iconUrl);
        }

        return icon;
    }

    public String getId() {
        return "docbook-wordml";
    }

    public String getLocalizedName() {
        return "WordML (\u03b1)";
    }

    public String getName() {
        return "WordML";
    }

    public JPanel getPanel() {

        if (application == null) {
            throw new IllegalStateException("The argument application must not be null!");
        }

        if (registerPanel == null) {

            JiveFactory wm = JiveFactory.getInstance();
            tabbedPane = wm.createTabbedPane();
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane.setTopComponent(tabbedPane);

            InfoPanel infoPanel = new InfoPanel();
            infoPanel.setConsole(getConsole());
            infoPanel.createGui();

            if (infoPanel != null) {
                splitPane.setBottomComponent(infoPanel);
            }

            registerPanel = new GridPanel();
            registerPanel.addComponent(splitPane, Anchor.NORTHWEST, Fill.BOTH);
        }

        return registerPanel;
    }

    public Float getWeight() {
        return 0.0F;
    }

    public void syncView(Project project) {

        if (project != null && tabbedPane != null) {

            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                
                Component comp = tabbedPane.getComponentAt(i);
                
                if (comp instanceof AbstractPanel) {
                    ((AbstractPanel) comp).syncView(project, project.getDriver(Output.wordml));
                }
            }
        }
    }

    public void syncModel(Project project) {

        if (project != null && tabbedPane != null) {

            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                
                Component comp = tabbedPane.getComponentAt(i);
        
                if (comp instanceof AbstractPanel) {
                    ((AbstractPanel) comp).syncModel(project,project.getDriver(Output.wordml));
                }
            }
        }
    }

    public void onEnter() throws IOException {
        refresh();
    }

    public void onLeave() {

        application.removeMenu(getId() + ".menu");

        for (MediumService service : application.getMediumServiceList()) {
            application.removeToolBarButton(getId() + ".generate." + service.getId());
        }
    }

    public void refresh() throws IOException {

        if (registerPanel == null) {
            getPanel();
        }

        if (registerPanel != null && tabbedPane != null && isActive()) {

            for (MediumService service : application.getMediumServiceList("docbook-wordml")) {
                application.newGenerateAction(getConsole(), service, this);
            }
        }
    }

    public void reset() {

    }

    public boolean validate() {
        return true;
    }

	protected JSplitPane createBottomArea() {
		return null;
	}
}
