package org.dbdoclet.tidbit.perspective.herold;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.text.IConsole;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.medium.herold.HeroldAction;
import org.dbdoclet.tidbit.perspective.AbstractPerspective;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;

public class HeroldPerspective extends AbstractPerspective implements
		Perspective {

	private GridPanel registerPanel;
	private Context context;
	private IConsole console;
	private HeroldPanel heroldPanel;

	protected void activate(ComponentContext context) {
		createPanel();
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

	@Override
	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/dbdoclet.png",
				HeroldPerspective.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	@Override
	public String getId() {
		return "herold";
	}

	@Override
	public String getLocalizedName() {
		return "Herold";
	}

	@Override
	public String getName() {
		return "Herold";
	}

	@Override
	public JPanel getPanel() {
		return registerPanel;
	}

	public void createPanel() {

		if (registerPanel == null) {

			heroldPanel = new HeroldPanel();
			registerPanel = new GridPanel();
			registerPanel
					.addComponent(heroldPanel, Anchor.NORTHWEST, Fill.BOTH);
		}
	}

	@Override
	public Float getWeight() {
		return 0.7F;
	}

	@Override
	public void syncView(Project project) {

		if (heroldPanel != null) {
			heroldPanel.syncView(project);
		}
	}

	@Override
	public void syncModel(Project project) {

		if (heroldPanel != null) {
			heroldPanel.syncModel(project);
		}
	}

	@Override
	public void onEnter() {
		refresh();
	}

	@Override
	public void onLeave() {

		application.removeMenu(getId() + ".menu");

		for (MediumService service : application.getMediumServiceList()) {
			application.removeToolBarButton(getId() + ".generate."
					+ service.getId());
		}
	}

	@Override
	public void refresh() {

		if (registerPanel == null) {
			getPanel();
		}

		if (application != null && registerPanel != null && heroldPanel != null
				&& isActive()) {

			ResourceBundle res = context.getResourceBundle();

			JMenu menu = new JMenu(ResourceServices.getString(res, "C_BUILD"));
			menu.setName(getId() + ".menu");

			for (MediumService service : application
					.getMediumServiceList("herold")) {

				HeroldAction action = new HeroldAction(application);
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
	public void reset() {

		if (heroldPanel != null) {
			heroldPanel.reset();
		}
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

	@Override
	public boolean validate() {
		return true;
	}
}
