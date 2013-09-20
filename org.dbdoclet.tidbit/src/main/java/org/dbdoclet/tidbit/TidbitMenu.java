/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.JiveServices;
import org.dbdoclet.manager.RecentManager;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.action.ActionAbout;
import org.dbdoclet.tidbit.action.ActionApplyTemplate;
import org.dbdoclet.tidbit.action.ActionCloseProject;
import org.dbdoclet.tidbit.action.ActionExit;
import org.dbdoclet.tidbit.action.ActionImport;
import org.dbdoclet.tidbit.action.ActionLicense;
import org.dbdoclet.tidbit.action.ActionNewProject;
import org.dbdoclet.tidbit.action.ActionOpenProject;
import org.dbdoclet.tidbit.action.ActionSaveProject;
import org.dbdoclet.tidbit.action.ActionSaveTemplate;
import org.dbdoclet.tidbit.action.ActionSetLookAndFeel;
import org.dbdoclet.tidbit.action.ActionSettings;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.importer.ImportService;

public class TidbitMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private static final String MENU_PROJECTS_NAME = "menu.projects";

	private final RecentManager recent;
	private final ResourceBundle res;
	private final Context ctx;
	private final JiveFactory wm;
	private final Tidbit application;

	private JMenu projectMenu;

	private JMenu recentMenu;

	public TidbitMenu(Tidbit application) {

		if (application == null) {
			throw new IllegalArgumentException("Parameter application is null!");
		}

		this.application = application;

		ctx = application.getContext();
		res = ctx.getResourceBundle();
		wm = JiveFactory.getInstance();

		recent = application.getRecentManager();
	}

	public void addRecent(File file) throws IOException {

		if (recent != null) {

			recent.add(file);
			recent.save();
			updateRecent(recent);
		}
	}

	private void createLookAndFeelMenu() {

		JMenu menu = new JMenu(ResourceServices.getString(res,
				"C_LOOK_AND_FEEL"));

		LookAndFeelInfo[] infoList = UIManager.getInstalledLookAndFeels();

		for (int i = 0; i < infoList.length; i++) {

			JMenuItem menuItem = new JMenuItem(infoList[i].getName());
			menuItem.setAction(new ActionSetLookAndFeel(application,
					infoList[i].getName(), infoList[i]));
			menu.add(menuItem);
		}

		add(menu);
	}

	public void createMenu() throws IOException {

		JMenuItem menuItem;

		createProjectMenu(recent);
		createLookAndFeelMenu();

		JMenu menu = new JMenu(ResourceServices.getString(res, "C_HELP"));
		menu.setName("help");
		menu.setMnemonic(KeyEvent.VK_H);

		menuItem = new JMenuItem(ResourceServices.getString(res, "C_ABOUT"));
		menuItem.setAction(new ActionAbout(application, ResourceServices
				.getString(res, "C_ABOUT"), null));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.ALT_MASK));
		menu.add(menuItem);

		addContextSensitiveHelp(menu);
		addDocBookXslReferenceHelp(menu);
		addTutorial(menu);

		menuItem = new JMenuItem(ResourceServices.getString(res, "C_LICENSE"));
		menuItem.setAction(new ActionLicense(application, ResourceServices
				.getString(res, "C_LICENSE"), null));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));
		menu.add(menuItem);

		add(Box.createHorizontalGlue());
		add(menu);
	}

	private void addTutorial(JMenu menu) {

		JMenuItem menuItem;
		URL helpSetUrl;
		helpSetUrl = ResourceServices.getResourceAsUrl("/tutorial-"
				+ res.getLocale().getLanguage().toLowerCase() + ".hs",
				Tidbit.class.getClassLoader());

		if (helpSetUrl == null) {
			helpSetUrl = ResourceServices.getResourceAsUrl("/tutorial.hs",
					Tidbit.class.getClassLoader());
		}

		if (helpSetUrl != null) {

			HelpSet helpSet;

			try {

				helpSet = new HelpSet(null, helpSetUrl);
				HelpBroker tutorialHelpBroker = helpSet.createHelpBroker();

				menuItem = new JMenuItem(ResourceServices.getString(res,
						"C_HELP"));
				CSH.setHelpIDString(menuItem, "N10001");
				menuItem.addActionListener(new CSH.DisplayHelpFromSource(
						tutorialHelpBroker));
				menu.add(menuItem);

			} catch (HelpSetException e) {
				e.printStackTrace();
			}
		}
	}

	private void addContextSensitiveHelp(JMenu menu) {

		HelpBroker helpBroker = application.getHelpBroker();

		if (helpBroker != null) {

			JMenuItem menuItem = new JMenuItem(ResourceServices.getString(res,
					"C_CONTEXT_SENSITIVE_HELP"));
			menuItem.addActionListener(new CSH.DisplayHelpAfterTracking(
					helpBroker));
			menu.add(menuItem);
		}
	}

	private void addDocBookXslReferenceHelp(JMenu menu) {

		HelpBroker helpBroker = application.getHelpBroker();

		if (helpBroker != null) {

			JMenuItem menuItem = new JMenuItem(ResourceServices.getString(res,
					"C_DOCBOOK_XSL_REFERENCE"));
			CSH.setHelpIDString(menuItem, "d0e1");
			menuItem.addActionListener(new CSH.DisplayHelpFromSource(helpBroker));
			menu.add(menuItem);
		}
	}

	private void createProjectMenu(RecentManager recent) throws IOException {

		projectMenu = wm.createMenu(ResourceServices.getString(res,
				"C_PROJECTS"));
		projectMenu.setName(MENU_PROJECTS_NAME);

		projectMenu.setMnemonic(KeyEvent.VK_P);
		add(projectMenu, 0);

		JMenuItem menuItem;

		menuItem = new JMenuItem(ResourceServices.getString(res,
				"C_PROJECT_NEW"));
		menuItem.setName("menuitem.project-new");
		menuItem.setAction(new ActionNewProject(application, ResourceServices
				.getString(res, "C_PROJECT_NEW"), JiveServices.getJlfgrIcon(
				"general", "New16")));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		projectMenu.add(menuItem);
		menuItem = new JMenuItem(ResourceServices.getString(res,
				"C_PROJECT_OPEN"));
		menuItem.setName("menuitem.project-open");
		menuItem.setAction(new ActionOpenProject(application, ResourceServices
				.getString(res, "C_PROJECT_OPEN"), null));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		projectMenu.add(menuItem);
		menuItem = new JMenuItem(ResourceServices.getString(res,
				"C_PROJECT_SAVE"));
		menuItem.setName("menuitem.project-save");
		menuItem.setAction(new ActionSaveProject(application, ResourceServices
				.getString(res, "C_PROJECT_SAVE"), null));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menuItem.setEnabled(false);
		wm.addSaveWidget(menuItem);
		projectMenu.add(menuItem);
		wm.addSaveWidget(menuItem);

		menuItem = new JMenuItem(ResourceServices.getString(res,
				"C_PROJECT_SAVE_AS"));
		menuItem.setName("menuitem.project-save-as");
		menuItem.setAction(new ActionSaveProject(application, ResourceServices
				.getString(res, "C_PROJECT_SAVE_AS"), null, true));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		menuItem.setEnabled(false);
		wm.addSaveWidget(menuItem);
		projectMenu.add(menuItem);
		menuItem = new JMenuItem(ResourceServices.getString(res,
				"C_PROJECT_CLOSE"));
		menuItem.setName("menuitem.project-close");
		menuItem.setAction(new ActionCloseProject(application, ResourceServices
				.getString(res, "C_PROJECT_CLOSE"), null));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		projectMenu.add(menuItem);
		menuItem = new JMenuItem(ResourceServices.getString(res,
				"C_PROJECT_SAVE_AS_TEMPLATE"));
		menuItem.setName("menuitem.project-save-as-template");
		menuItem.setAction(new ActionSaveTemplate(application, ResourceServices
				.getString(res, "C_PROJECT_SAVE_AS_TEMPLATE"), null));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.CTRL_MASK));
		menuItem.setEnabled(false);
		projectMenu.add(menuItem);
		menuItem = new JMenuItem(ResourceServices.getString(res,
				"C_PROJECT_APPLY_TEMPLATE"));
		menuItem.setName("menuitem.project-apply-template");
		menuItem.setAction(new ActionApplyTemplate(application,
				ResourceServices.getString(res, "C_PROJECT_APPLY_TEMPLATE"),
				null));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		menuItem.setEnabled(false);
		projectMenu.add(menuItem);
		menuItem = new JMenuItem(ResourceServices.getString(res, "C_SETTINGS"));
		menuItem.setName("menuitem.project-settings");
		menuItem.setAction(new ActionSettings(application, ResourceServices
				.getString(res, "C_SETTINGS"), null));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.CTRL_MASK));
		menuItem.setEnabled(false);
		projectMenu.add(menuItem);
		for (ImportService service : application.getImportServiceList()) {

			menuItem = new JMenuItem();
			menuItem.setAction(new ActionImport(application, ResourceServices
					.getString(res, service.getResourceKey()), service));
			menuItem.setName("import." + service.getName());
			// menuItem.setEnabled(false);
			projectMenu.add(menuItem);
		}

		recentMenu = new JMenu(ResourceServices.getString(res,
				"C_RECENTLY_USED_PROJECTS"));
		projectMenu.add(recentMenu);
		createRecentMenuItems(recent, recentMenu);

		menuItem = new JMenuItem(ResourceServices.getString(res, "C_EXIT"));
		menuItem.setName("menuitem.project-settings");
		menuItem.setAction(new ActionExit(application, ResourceServices
				.getString(res, "C_EXIT"), null));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		projectMenu.add(menuItem);
	}

	private void createRecentMenuItems(RecentManager recent, JMenu menu)
			throws IOException {

		ArrayList<String> recentList = recent.getList();
		ArrayList<File> recentFileList = new ArrayList<File>();

		for (String projectFileName : recentList) {

			File file = new File(projectFileName);

			if (file.exists()) {
				recentFileList.add(file);
			}
		}

		// Collections.reverse(recentFileList);

		JMenuItem menuItem;
		int index = 0;

		if (recentFileList.size() > 0) {

			for (File file : recentFileList) {

				if (file.isFile() == false) {
					continue;
				}

				String label = file.getName();
				label += " [";

				String dirName = FileServices.getDirName(file
						.getCanonicalPath());

				if (dirName != null && dirName.length() > 21) {
					dirName = "..." + dirName.substring(dirName.length() - 18);
				}

				label += dirName;
				label += "]";

				menuItem = new JMenuItem(label);
				menuItem.setName("recent." + index++);
				menuItem.setAction(new ActionOpenProject(application, label,
						null, file));
				menu.add(menuItem);

				index++;
			}
		}
	}

	public void updateImportServices() {

		for (Component comp : projectMenu.getMenuComponents()) {

			if (comp instanceof JMenuItem) {

				String name = comp.getName();

				if (name != null && name.startsWith("import.")) {
					projectMenu.remove((JMenuItem) comp);
				}
			}
		}

		int pos = 5;
		int offset = 0;

		for (ImportService service : application.getImportServiceList()) {

			JMenuItem menuItem = new JMenuItem();
			menuItem.setAction(new ActionImport(application, ResourceServices
					.getString(res, service.getResourceKey()), service));
			projectMenu.setName("import." + service.getName());
			projectMenu.insert(menuItem, pos + offset);

			offset++;
		}
	}

	public void updateRecent(RecentManager recent) throws IOException {

		if (recent == null) {
			throw new IllegalArgumentException(
					"The argument recent must not be null!");
		}

		ArrayList<JMenuItem> removeList = new ArrayList<JMenuItem>();

		for (Component comp : recentMenu.getMenuComponents()) {

			if (comp instanceof JMenuItem) {

				JMenuItem menuItem = (JMenuItem) comp;
				removeList.add(menuItem);
			}
		}

		for (JMenuItem item : removeList) {
			recentMenu.remove(item);
		}

		createRecentMenuItems(recent, recentMenu);
	}

	public void unlock() {

		for (int i = 0; i < getMenuCount(); i++) {

			JMenu menu = getMenu(i);

			if (menu != null) {

				menu.setEnabled(true);

				for (Component comp : menu.getMenuComponents()) {

					if (comp != null && comp instanceof JMenuItem) {

						JMenuItem menuItem = (JMenuItem) comp;
						menuItem.setEnabled(true);
					}

				}
			}
		}
	}
}