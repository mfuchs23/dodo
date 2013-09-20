package org.dbdoclet.tidbit.perspective.project;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.dbdoclet.jive.text.IConsole;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.perspective.AbstractPerspective;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.FileManager;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;

public class ProjectPerspective extends AbstractPerspective implements
		Perspective {

	public static final String ID = "project";
	private Context context;
	private IConsole console;
	private ProjectPanel projectPanel;

	protected void activate(ComponentContext context) {
		//
	}

	public void createMenuBar(JMenuBar menuBar) {
		//
	}

	@Override
	public Application getApplication() {
		return application;
	}

	@Override
	public IConsole getConsole() {
		return console;
	}

	public Context getContext() {
		return context;
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/dbdoclet.png",
				ProjectPerspective.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return ID;
	}

	public String getLocalizedName() {

		if (context == null) {
			throw new IllegalStateException(
					"The argument context must not be null!");
		}

		ResourceBundle res = context.getResourceBundle();
		return ResourceServices.getString(res, "C_PROJECT");
	}

	public String getName() {
		return ID;
	}

	public JPanel getPanel() {

		if (application == null) {
			throw new IllegalStateException(
					"The argument application must not be null!");
		}

		if (projectPanel == null) {
			try {
				projectPanel = new ProjectPanel(context);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return projectPanel;
	}

	public Float getWeight() {
		return 1.0F;
	}

	public void onEnter() {

		if (projectPanel != null) {
			projectPanel.refresh();
		}
	}

	public void onLeave() {
		//
	}

	public void refresh() {

		if (projectPanel != null) {
			projectPanel.refresh();
		}
	}

	public void reset() {
		// TODO
	}

	@Override
	public void setApplication(Application application) {

		if (application == null) {
			throw new IllegalArgumentException(
					"The argument application must not be null!");
		}

		this.application = application;
		context = application.getContext();

		if (context == null) {
			throw new IllegalStateException(
					"The argument context must not be null!");
		}
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void syncModel(Project project) {

		if (project == null) {
			throw new IllegalArgumentException(
					"The argument project must not be null!");
		}

		project.setProjectName(projectPanel.getProjectName());
		project.setProjectFile(new File(projectPanel.getProjectFile()));
		// project.setProjectDirectory(new
		// File(projectPanel.getProjectDirectory()));
		project.setBuildDirectory(new File(projectPanel.getBuildDirectory()));
		project.setJdkHome(new File(projectPanel.getJdkHome()));
		project.setDocBookFileName(projectPanel.getDocBookFile());
	}

	public void syncView(Project project) {

		FileManager fm = project.getFileManager();
		projectPanel.setProjectName(project.getProjectName());
		projectPanel.setProjectFile(project.getProjectFile());
		projectPanel.setProjectDirectory(project.getProjectDirectory());
		projectPanel.setBuildDirectory(project.getBuildDirectory());
		projectPanel.setDocBookFile(fm.getDocBookFileName());
	}

	public boolean validate() {
		return true;
	}
}
