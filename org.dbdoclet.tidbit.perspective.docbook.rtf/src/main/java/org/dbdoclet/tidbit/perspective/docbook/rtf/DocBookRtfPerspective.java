package org.dbdoclet.tidbit.perspective.docbook.rtf;

import java.awt.Component;
import java.io.File;
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
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.perspective.AbstractPerspective;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoAdmonitionPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoBibliographyPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoCalloutPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoEbnfPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoGlossaryPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoGraphicPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoHeaderFooterPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoIndexPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoLayoutPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoLinkingPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoListPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoLocalizationPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoMetaInfoPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoMiscellaneousPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoPagePanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoQandaPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoReferencePagePanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoRevHistoryPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoSectionPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoTablePanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoTocPanel;
import org.dbdoclet.tidbit.perspective.docbook.fo.FoVerbatimPanel;
import org.dbdoclet.tidbit.perspective.panel.docbook.AbstractPanel;
import org.dbdoclet.tidbit.project.FileManager;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;

/**
 * Perspektive für die Generierung von RTF.
 *  
 * @author michael
 */
public class DocBookRtfPerspective extends AbstractPerspective implements
		Perspective {

	private GridPanel registerPanel;
	private JTabbedPane tabbedPane;
	
	protected void activate(ComponentContext context) {
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl(
				"/images/dbdoclet.png",
				DocBookRtfPerspective.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "docbook-fo-rtf";
	}

	public String getLocalizedName() {
		return "RTF";
	}

	public String getName() {
		return "RTF";
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
			// tabbedPane.setBorder(new EtchedBorder());

			tabbedPane.addTab(ResourceServices.getString(res, "C_PAGE"),
					new FoPagePanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_PAGE_LAYOUT"),
					new FoLayoutPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_SECTIONS"),
					new FoSectionPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_ADMONITIONS"),
					new FoAdmonitionPanel());
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_BIBLIOGRAPHY"),
					new FoBibliographyPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_CALLOUTS"),
					new FoCalloutPanel());
			tabbedPane.addTab("EBNF", new FoEbnfPanel());
			// tabbedPane.addTab(ResourceServices.getString(res,"C_FONT_FAMILIES"),
			// new FoFontFamilyPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_GLOSSARY"),
					new FoGlossaryPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_GRAPHICS"),
					new FoGraphicPanel());
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_HEADER_FOOTER"),
					new FoHeaderFooterPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_INDEX"),
					new FoIndexPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_LINKING"),
					new FoLinkingPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_LISTS"),
					new FoListPanel());
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_LOCALIZATION"),
					new FoLocalizationPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_META_INFO"),
					new FoMetaInfoPanel());
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_MISCELLANEOUS"),
					new FoMiscellaneousPanel());
			// tabbedPane.addTab(ResourceServices.getString(res,"C_PROFILING"),
			// new FoProfilingPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_QANDA"),
					new FoQandaPanel());
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_REFERENCE_PAGE"),
					new FoReferencePagePanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_REVHISTORY"),
					new FoRevHistoryPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_TABLES"),
					new FoTablePanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_TOC"),
					new FoTocPanel());
			tabbedPane.addTab(ResourceServices.getString(res, "C_VERBATIM"),
					new FoVerbatimPanel());

			JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			splitPane.setTopComponent(tabbedPane);

			JSplitPane bottomSplitPane = createBottomArea();
			splitPane.setBottomComponent(bottomSplitPane);

			registerPanel = new GridPanel();
			registerPanel.addComponent(splitPane, Anchor.NORTHWEST, Fill.BOTH);
		}

		return registerPanel;
	}

	public Float getWeight() {
		return 1.0F;
	}

	public void syncView(Project project) {

		if (project != null && tabbedPane != null) {

			for (int i = 0; i < tabbedPane.getTabCount(); i++) {

				Component comp = tabbedPane.getComponentAt(i);

				if (comp instanceof AbstractPanel) {
					((AbstractPanel) comp).syncView(project, project.getDriver(Output.rtf));
				}
			}
			
			monitorPanel.clear();
			
			FileManager fileManager = project.getFileManager();			
			for (File file : fileManager.getRtfArtifacts()) {
				monitorPanel.addFile(file);
			}

		}
	}

	public void syncModel(Project project) {

		if (project != null && tabbedPane != null) {

			for (int i = 0; i < tabbedPane.getTabCount(); i++) {

				Component comp = tabbedPane.getComponentAt(i);

				if (comp instanceof AbstractPanel) {
					((AbstractPanel) comp).syncModel(project, project.getDriver(Output.rtf));
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
					.getMediumServiceList("docbook-rtf")) {

				application.newGenerateAction(getConsole(),
						service, this);
			}
		}
	}

	public void reset() {
		// 
	}

	public boolean validate() {
		return true;
	}
}
