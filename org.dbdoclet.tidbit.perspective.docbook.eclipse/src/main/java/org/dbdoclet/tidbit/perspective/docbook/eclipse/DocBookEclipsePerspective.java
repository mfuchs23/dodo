package org.dbdoclet.tidbit.perspective.docbook.eclipse;

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

public class DocBookEclipsePerspective extends AbstractPerspective implements
		Perspective {

	private GridPanel registerPanel;
	private JTabbedPane tabbedPane;
	private EclipseHelpPanel eclipseHelpPanel;

	protected void activate(ComponentContext context) {
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl(
				"/images/eclipseHelp.png",
				DocBookEclipsePerspective.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "docbook-html-eclipse";
	}

	public String getLocalizedName() {
		return "Eclipse";
	}

	public String getName() {
		return "Eclipse";
	}

	public JPanel getPanel() {

		if (application == null) {
			throw new IllegalStateException(
					"The argument application must not be null!");
		}

		if (registerPanel == null) {

			JiveFactory wm = JiveFactory.getInstance();
			ResourceBundle res = StaticContext.getResourceBundle();

			tabbedPane = wm.createTabbedPane();
			tabbedPane.setName("tp.eclipse");
			// tabbedPane.setBorder(new EtchedBorder());

			eclipseHelpPanel = new EclipseHelpPanel();
			tabbedPane.addTab("Eclipse", eclipseHelpPanel);

			tabbedPane.addTab("HTML", new HtmlMainPanel());
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
					new HtmlGraphicPanel());
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
			// new HtmlProfilingPanel());
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

		return registerPanel;
	}

	public Float getWeight() {
		return 0.7F;
	}

	public void syncView(Project project) {

		if (eclipseHelpPanel != null && project != null) {

			AbstractDriver eclipseDriver = project.getDriver(Output.eclipse);

			String id = eclipseDriver
					.getParameter(Constants.PARAM_ECLIPSE_PLUGIN_ID);

			if (id == null || id.trim().length() == 0) {
				id = project.getProjectName();
			}

			String name = eclipseDriver
					.getParameter(Constants.PARAM_ECLIPSE_PLUGIN_NAME);

			if (name == null || name.trim().length() == 0) {
				name = project.getProjectName();
			}

			String version = eclipseDriver
					.getParameter(Constants.PARAM_ECLIPSE_PLUGIN_VERSION);

			if (version == null || version.trim().length() == 0) {
				version = "1.0.0";
			}

			eclipseHelpPanel.setPluginId(id);
			eclipseHelpPanel.setPluginName(name);
			eclipseHelpPanel.setPluginVersion(version);
			eclipseHelpPanel.setPluginProvider(eclipseDriver
					.getParameter(Constants.PARAM_ECLIPSE_PLUGIN_PROVIDER));

			for (int i = 0; i < tabbedPane.getTabCount(); i++) {

				Component comp = tabbedPane.getComponentAt(i);

				if (comp instanceof AbstractPanel) {
					((AbstractPanel) comp).syncView(project, eclipseDriver);
				}
			}
		}
	}

	public void syncModel(Project project) {

		if (eclipseHelpPanel != null && project != null) {

			AbstractDriver eclipseDriver = project.getDriver(Output.eclipse);

			eclipseDriver.setParameter("eclipse.plugin.id",
					eclipseHelpPanel.getPluginId());
			eclipseDriver.setParameter("eclipse.plugin.version",
					eclipseHelpPanel.getPluginVersion());
			eclipseDriver.setParameter("eclipse.plugin.name",
					eclipseHelpPanel.getPluginName());
			eclipseDriver.setParameter("eclipse.plugin.provider",
					eclipseHelpPanel.getPluginProvider());

			for (int i = 0; i < tabbedPane.getTabCount(); i++) {

				Component comp = tabbedPane.getComponentAt(i);

				if (comp instanceof AbstractPanel) {
					((AbstractPanel) comp).syncModel(project, eclipseDriver);
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
			application.removeToolBarButton(getId() + ".generate."
					+ service.getId());
		}
	}

	public void refresh() {

		if (registerPanel == null) {
			getPanel();
		}

		if (registerPanel != null && tabbedPane != null && isActive()) {

			ResourceBundle res = StaticContext.getResourceBundle();

			JMenu menu = new JMenu(ResourceServices.getString(res, "C_BUILD"));
			menu.setName(getId() + ".menu");

			for (MediumService service : application
					.getMediumServiceList("docbook-eclipse")) {

				AbstractAction action = application.newGenerateAction(
						getConsole(), service);

				application.addToolBarButton(
						getId() + ".generate." + service.getId(), action);

				JMenuItem menuItem = new JMenuItem();
				menuItem.setAction(action);
				// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				// ActionEvent.ALT_MASK));
				menu.add(menuItem);
			}

			application.addMenu(getId() + ".menu", menu);
		}
	}

	@Override
	public boolean isReadyForUse() {

		if (eclipseHelpPanel != null) {

			String pluginId = eclipseHelpPanel.getPluginId();
			String pluginVersion = eclipseHelpPanel.getPluginVersion();

			if (pluginId == null || pluginId.trim().length() == 0
					|| pluginVersion == null
					|| pluginVersion.trim().length() == 0) {
				return false;
			}
		}

		return true;
	}

	public void reset() {

		if (eclipseHelpPanel != null) {
			eclipseHelpPanel.reset();
		}
	}

	public boolean validate() {
		return true;
	}

	@Override
	protected JSplitPane createBottomArea() {
		return null;
	}
}
