package org.dbdoclet.tidbit.perspective.dbdoclet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.dbdoclet.doclet.docbook.DbdScript;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.text.IConsole;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.common.TidbitExceptionHandler;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.perspective.AbstractPerspective;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.perspective.panel.InfoPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.trafo.TrafoException;
import org.dbdoclet.trafo.TrafoScriptManager;
import org.dbdoclet.trafo.script.Script;
import org.osgi.service.component.ComponentContext;

public class DbdocletPerspective extends AbstractPerspective implements
		Perspective {

	private GridPanel registerPanel;
	private JTabbedPane tabbedPane;
	private SourcepathPanel sourcepathPanel;
	private ClasspathPanel classpathPanel;
	private JavadocPanel javadocPanel;
	private DocletPanel docletPanel;
	private DocbookPanel docbookPanel;

	public DbdocletPerspective() {
		createPanel();
	}

	protected void activate(ComponentContext context) {
		// System.out.println("dbdoclet Perspective activate");
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/dbdoclet.png",
				DbdocletPerspective.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "dbdoclet";
	}

	public String getLocalizedName() {
		return "dbdoclet";
	}

	public String getName() {
		return "dbdoclet";
	}

	public JPanel getPanel() {
		return registerPanel;
	}

	private void createPanel() {

		if (registerPanel == null) {

			JiveFactory wm = JiveFactory.getInstance();
			ResourceBundle res = StaticContext.getResourceBundle();

			tabbedPane = wm.createTabbedPane();

			sourcepathPanel = new SourcepathPanel();
			tabbedPane.addTab(
					ResourceServices.getString(res, "C_SOURCE_FILES"),
					sourcepathPanel);

			classpathPanel = new ClasspathPanel();
			tabbedPane.addTab("Classpath", classpathPanel);

			javadocPanel = new JavadocPanel(this);
			tabbedPane.addTab("Javadoc", javadocPanel);

			docletPanel = new DocletPanel();
			tabbedPane.addTab("Doclet", docletPanel);

			docbookPanel = new DocbookPanel(this);
			tabbedPane.addTab("DocBook", docbookPanel);

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

	private Script createScript(Project project) {

		File scriptFile = new File(project.getProjectPath(), "dbdoclet.conf");
		Script script = new Script();

		TrafoScriptManager mgr = new TrafoScriptManager();

		if (scriptFile.exists()) {
			try {
				mgr.parseScript(script, scriptFile);
			} catch (TrafoException oops) {
				oops.printStackTrace();
			}
		}

		return script;
	}

	public void syncView(Project project) {

		if (project == null) {
			return;
		}

		Script script = createScript(project);
		DbdScript dbdScript = new DbdScript();
		dbdScript.setScript(script);

		if (sourcepathPanel != null) {
			sourcepathPanel.syncView(project);
		}

		if (classpathPanel != null) {
			classpathPanel.syncView(project);
		}

		if (javadocPanel != null) {
			javadocPanel.syncView(project, dbdScript);
		}

		if (docletPanel != null) {
			docletPanel.syncView(project, dbdScript);
		}

		if (docbookPanel != null) {
			docbookPanel.syncView(project, dbdScript);
		}
	}

	public void syncModel(Project project) {

		if (project == null) {
			return;
		}

		TrafoScriptManager mgr = new TrafoScriptManager();
		Script script = createScript(project);

		script.setSystemParameter(Script.SYSPARAM_TRANSFORMATION_NAME,
				"dbdoclet");

		DbdScript dbdScript = new DbdScript();
		dbdScript.setScript(script);

		if (sourcepathPanel != null) {
			sourcepathPanel.syncModel(project);
		}

		if (classpathPanel != null) {
			classpathPanel.syncModel(project);
		}

		if (javadocPanel != null) {
			javadocPanel.syncModel(project, dbdScript);
		}

		if (docletPanel != null) {
			docletPanel.syncModel(project, dbdScript);
		}

		if (docbookPanel != null) {
			docbookPanel.syncModel(project, dbdScript);
		}

		try {

			File scriptFile = new File(project.getProjectPath(),
					"dbdoclet.conf");
			mgr.writeScript(script, scriptFile);

		} catch (IOException oops) {
			TidbitExceptionHandler.handleException(
					StaticContext.getDialogOwner(), oops);
		}
	}

	public void onEnter() throws IOException {
		refresh();
	}

	public void onLeave() {

		application.removeMenu(getId() + ".menu");

		for (MediumService mediumService : application
				.getMediumServiceList("doclet")) {
			application.removeToolBarButton(getId() + ".generate."
					+ mediumService.getId());
		}
	}

	public void refresh() throws IOException {

		if (registerPanel == null) {
			getPanel();
		}

		if (registerPanel != null && tabbedPane != null && isActive()) {

			for (MediumService mediumService : application
					.getMediumServiceList("doclet")) {

				application.newGenerateAction(
						getConsole(), mediumService, this);
			}
		}
	}

	public void reset() {

		IConsole console = getConsole();

		if (console != null) {
			console.clear();
		}
	}

	public boolean validate() {

		if (sourcepathPanel.getPathCount() == 0) {

			ResourceBundle res = StaticContext.getResourceBundle();
			ErrorBox.show(ResourceServices.getString(res, "C_ERROR"),
					ResourceServices.getString(res,
							"C_ERROR_UNDEFINED_SOURCE_PACKAGES"));
			return false;
		}

		return true;
	}

	@Override
	protected JSplitPane createBottomArea() {
		return null;
	}
}
