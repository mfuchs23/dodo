package org.dbdoclet.tidbit.perspective.docbook.htmlhelp;

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
import org.dbdoclet.tidbit.common.Constants;
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
import org.dbdoclet.tidbit.project.driver.AbstractDriver;
import org.osgi.service.component.ComponentContext;

public class DocBookHtmlhelpPerspective extends AbstractPerspective implements
		Perspective {

	private static final String ID = "docbook-html-htmlhelp";
	private static final String HTMLHELP_BASE_DIR = "./build/htmlhelp/";
	
	private GridPanel registerPanel;
	private JTabbedPane tabbedPane;
	private HtmlMainPanel htmlMainPanel;
	
	public DocBookHtmlhelpPerspective() {
		super();
		createPanel();
	}

	protected void activate(ComponentContext context) {
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl(
				"/images/dbdoclet.png",
				DocBookHtmlhelpPerspective.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return ID;
	}

	public String getLocalizedName() {
		return "HtmlHelp";
	}

	public String getName() {
		return "HTMLHELP";
	}

	public JPanel getPanel() {
		return registerPanel;
	}
	
	public void createPanel() {
		
		if (registerPanel == null) {

			JiveFactory wm = JiveFactory.getInstance();
			ResourceBundle res = StaticContext.getResourceBundle();

			tabbedPane = wm.createTabbedPane();
			htmlMainPanel = new HtmlMainPanel();

			HtmlGraphicPanel htmlGraphicPanel = new HtmlGraphicPanel();
			
			tabbedPane.addTab("HTML", htmlMainPanel);
			tabbedPane.addTab(ResourceServices.getString(res, "C_SECTIONS"),
					new HtmlSectionPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_ADMONITIONS"),
					new HtmlAdmonitionPanel());
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_BIBLIOGRAPHY"),
					new HtmlBibliographyPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_CALLOUTS"),
					new HtmlCalloutPanel());
			tabbedPane.addTab("EBNF", new HtmlEbnfPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_GLOSSARY"),
					new HtmlGlossaryPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_GRAPHICS"),
					htmlGraphicPanel);
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_HEADER_FOOTER"),
					new HtmlHeaderFooterPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_INDEX"),
					new HtmlIndexPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_LINKING"),
					new HtmlLinkingPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_LISTS"),
					new HtmlListPanel());
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_LOCALIZATION"),
					new HtmlLocalizationPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_META_INFO"),
					new HtmlMetaInfoPanel());
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_MISCELLANEOUS"),
					new HtmlMiscellaneousPanel());
			// tabbedPane.addTab(ResourceServices.getString(res,"C_PROFILING"),
			// new HtmlProfilingPanel(context));
			tabbedPane.addTab(ResourceServices.getString(res, "C_QANDA"),
					new HtmlQandaPanel());
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_REFERENCE_PAGE"),
					new HtmlReferencePagePanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_TABLES"),
					new HtmlTablePanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_TOC"),
					new HtmlTocPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_VERBATIM"),
					new HtmlVerbatimPanel());

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

		if (project != null) {

			for (int i = 0; i < tabbedPane.getTabCount(); i++) {

				Component comp = tabbedPane.getComponentAt(i);

				if (comp instanceof AbstractPanel) {
					((AbstractPanel) comp).syncView(project, project.getDriver(Output.htmlhelp));
				}
			}
			
			
			htmlMainPanel.setBaseDir(HTMLHELP_BASE_DIR);
		}
	}

	public void syncModel(Project project) {

		if (project != null) {

			AbstractDriver htmlhelpDriver = project.getDriver(Output.htmlhelp);

			for (int i = 0; i < tabbedPane.getTabCount(); i++) {

				Component comp = tabbedPane.getComponentAt(i);

				if (comp instanceof AbstractPanel) {
					((AbstractPanel) comp).syncModel(project, project.getDriver(Output.htmlhelp));
				}
			}

			htmlhelpDriver.setParameter(Constants.PARAM_BASE_DIR, HTMLHELP_BASE_DIR);
			htmlhelpDriver.setParameter(Constants.PARAM_IMG_SRC_PATH, "");
			htmlhelpDriver.setParameter(Constants.PARAM_KEEP_RELATIVE_IMAGE_URIS, 1);
			htmlhelpDriver.setParameter(Constants.PARAM_EPUB_METAINF_DIR, "build/htmlhelp/META-INF/");
			htmlhelpDriver.setParameter(Constants.PARAM_EPUB_OEBPS_DIR, "build/htmlhelp/OEBPS/");
		}
	}

	public void onEnter() throws IOException {
		refresh();
	}

	public void onLeave() {

		application.removeMenu(getId() + ".menu");

		for (MediumService service : application.getMediumServiceList()) {
			application.removeToolBarButton(getId() + ".generate."
					+ service.getId());
		}
	}

	public void refresh() throws IOException {

		if (registerPanel == null) {
			getPanel();
		}

		if (registerPanel != null && tabbedPane != null && isActive()) {

			for (MediumService service : application
					.getMediumServiceList("docbook-htmlhelp")) {

				application.newGenerateAction(getConsole(),
						service, this);
			}
		}
	}

	public void reset() {

	}

	public boolean validate() {
		return true;
	}
}
