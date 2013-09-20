package org.dbdoclet.tidbit.perspective.docbook.javahelp;

import java.awt.Component;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlAdmonitionPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlBibliographyPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlCalloutPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlEbnfPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlGlossaryPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlGraphicPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlHeaderFooterPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlIndexPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlLinkingPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlListPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlLocalizationPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlMainPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlMetaInfoPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlMiscellaneousPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlQandaPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlReferencePagePanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlSectionPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlTablePanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlTocPanel;
import org.dbdoclet.tidbit.perspective.docbook.html.HtmlVerbatimPanel;
import org.dbdoclet.tidbit.perspective.panel.InfoPanel;
import org.dbdoclet.tidbit.perspective.panel.docbook.AbstractPanel;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;

public class DocBookJavaHelpPerspective extends AbstractPerspective implements Perspective {

    private GridPanel registerPanel;
    private JTabbedPane tabbedPane;

    public DocBookJavaHelpPerspective() {
		super();
		createPanel();
	}

	protected void activate(ComponentContext context) {
        //
    }

    public Application getApplication() {
        return application;
    }

    public Icon getIcon() {

        Icon icon = null;

        URL iconUrl = ResourceServices.getResourceAsUrl("/images/dbdoclet.png", DocBookJavaHelpPerspective.class
                .getClassLoader());

        if (iconUrl != null) {
            icon = new ImageIcon(iconUrl);
        }

        return icon;
    }

    public String getId() {
        return "docbook-html-javahelp";
    }

    public String getLocalizedName() {
        return "JavaHelp";
    }

    public String getName() {
        return "JavaHelp";
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
        return 0.8F;
    }

    public void syncView(Project project) {
    
        if (project != null && tabbedPane != null) {

        	if (tabbedPane == null) {
        		getPanel();
        	}

        	for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                
                Component comp = tabbedPane.getComponentAt(i);
                
                if (comp instanceof AbstractPanel) {
                    ((AbstractPanel) comp).syncView(project, project.getDriver(Output.javahelp));
                }
            }
        }
    }

    public void syncModel(Project project) {
        
        if (project != null) {

            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                
                Component comp = tabbedPane.getComponentAt(i);
                
                if (comp instanceof AbstractPanel) {
                    ((AbstractPanel) comp).syncModel(project, project.getDriver(Output.javahelp));
                }
            }
        }
    }

    public void onEnter() {
        refresh();
    }

    public void onLeave() {

        application.removeMenu(getId() + ".menu");

        for (MediumService service : application.getMediumServiceList()) {
            application.removeToolBarButton(getId() + ".generate." + service.getId());
        }
    }

    public void refresh() {

        if (registerPanel == null) {
            getPanel();
        }

        if (registerPanel != null && tabbedPane != null && isActive()) {

            ResourceBundle res = StaticContext.getResourceBundle();

            JMenu menu = new JMenu(ResourceServices.getString(res,"C_BUILD"));
            menu.setName(getId() + ".menu");

            for (MediumService service : application.getMediumServiceList("docbook-javahelp")) {

                AbstractAction action = application.newGenerateAction(getConsole(), service);
                
                application.addToolBarButton(getId() + ".generate." + service.getId(), action);

                JMenuItem menuItem = new JMenuItem();
                menuItem.setAction(action);
                // menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                // ActionEvent.ALT_MASK));
                menu.add(menuItem);
            }

            application.addMenu(getId() + ".menu", menu);
        }
    }

    public void reset() {
        //
    }

    public void setApplication(Application application) {

        if (application == null) {
            throw new IllegalArgumentException("The argument application must not be null!");
        }

        this.application = application;
    }

    public boolean validate() {
        return true;
    }
}
