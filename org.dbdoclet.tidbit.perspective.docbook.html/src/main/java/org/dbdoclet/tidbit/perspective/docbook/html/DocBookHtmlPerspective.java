package org.dbdoclet.tidbit.perspective.docbook.html;

import java.awt.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.perspective.AbstractPerspective;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.perspective.panel.InfoPanel;
import org.dbdoclet.tidbit.perspective.panel.docbook.AbstractPanel;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;

public class DocBookHtmlPerspective extends AbstractPerspective implements Perspective {

    private GridPanel registerPanel;
    private JTabbedPane tabbedPane;
    public DocBookHtmlPerspective() {
		super();
		createPanel();
	}

	protected void activate(ComponentContext context) {
    }

    public Application getApplication() {
        return application;
    }

    public Icon getIcon() {

        Icon icon = null;

        URL iconUrl = ResourceServices.getResourceAsUrl("/images/dbdoclet.png", DocBookHtmlPerspective.class
                .getClassLoader());

        if (iconUrl != null) {
            icon = new ImageIcon(iconUrl);
        }

        return icon;
    }

    public String getId() {
        return "docbook-html";
    }

    public String getLocalizedName() {
        return "HTML";
    }

    public String getName() {
        return "HTML";
    }

    public JPanel getPanel() {
    	return registerPanel;
    }
    
    private void createPanel() {
    	
        if (registerPanel == null) {

            JiveFactory wm = JiveFactory.getInstance();
            ResourceBundle res = StaticContext.getResourceBundle();
            
            tabbedPane = wm.createTabbedPane();
            // tabbedPane.setBorder(new EtchedBorder());

            tabbedPane.addTab("HTML", new HtmlMainPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_SECTIONS"), new HtmlSectionPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_ADMONITIONS"), new HtmlAdmonitionPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_BIBLIOGRAPHY"), new HtmlBibliographyPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_CALLOUTS"), new HtmlCalloutPanel());
            tabbedPane.addTab("EBNF", new HtmlEbnfPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_GLOSSARY"), new HtmlGlossaryPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_GRAPHICS"), new HtmlGraphicPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_HEADER_FOOTER"), new HtmlHeaderFooterPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_INDEX"), new HtmlIndexPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_LINKING"), new HtmlLinkingPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_LISTS"), new HtmlListPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_LOCALIZATION"), new HtmlLocalizationPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_META_INFO"), new HtmlMetaInfoPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_MISCELLANEOUS"), new HtmlMiscellaneousPanel());
            // tabbedPane.addTab(ResourceServices.getString(res,"C_PROFILING"), new HtmlProfilingPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_QANDA"), new HtmlQandaPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_REFERENCE_PAGE"), new HtmlReferencePagePanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_TABLES"), new HtmlTablePanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_TOC"), new HtmlTocPanel());
            tabbedPane.addTab(ResourceServices.getString(res,"C_VERBATIM"), new HtmlVerbatimPanel());
            
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
    }

    public Float getWeight() {
        return 0.9F;
    }

    public void syncView(Project project) {

        if (project != null) {

            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                
                Component comp = tabbedPane.getComponentAt(i);
                
                if (comp instanceof AbstractPanel) {
                    ((AbstractPanel) comp).syncView(project, project.getDriver(Output.html));
                }
            }
        }
    }

    public void syncModel(Project project) {

        if (project != null) {

            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                
                Component comp = tabbedPane.getComponentAt(i);
        
                if (comp instanceof AbstractPanel) {
                    ((AbstractPanel) comp).syncModel(project, project.getDriver(Output.html));
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

            for (MediumService service : application.getMediumServiceList("docbook-html")) {
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
