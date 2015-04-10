/*
 * ### Copyright (C) 2006-2009 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.Rowspan;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.dialog.Splash;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.jive.text.IConsole;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.SideBar;
import org.dbdoclet.jive.widget.TopPanel;
import org.dbdoclet.jive.widget.sidebar.SideBarButton;
import org.dbdoclet.jive.widget.sidebar.SideBarGroup;
import org.dbdoclet.manager.RecentManager;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.action.ActionGenerate;
import org.dbdoclet.tidbit.action.ActionOpenPerspective;
import org.dbdoclet.tidbit.action.ActionOpenProject;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.common.OsgiRuntime;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.common.TidbitExceptionHandler;
import org.dbdoclet.tidbit.importer.ImportService;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.trafo.TrafoService;

public class Tidbit extends JFrame implements Application, ChangeListener,
		VetoableChangeListener {

	class TidbitWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent event) {

			shutdown();
			System.exit(0);
		}
	}

	private static Application application;

	private static Log logger = LogFactory.getLog(Tidbit.class);
	private static final long serialVersionUID = 1L;

	private static Splash splash = null;

	private final Context context;
	private final ArrayList<Generator> generatorList;
	private HelpBroker helpBroker;
	private boolean locked;
	private TidbitMenu menuBar;
	private Perspective perspective;
	private JPanel perspectivePanel;
	private final TidbitComponent serviceComponent;
	private SideBar sideBar;
	private final String startPerspective = "project";
	private TidbitToolBar toolBar;
	private TopPanel topPanel;
	private JiveFactory wm;

	private RecentManager recentManager;

	private Tidbit(TidbitComponent serviceComponent, Context context) {

		super();

		this.serviceComponent = serviceComponent;
		this.context = context;

		generatorList = new ArrayList<Generator>();

		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("F5"), "generate");

		StaticContext.setDialogOwner(this);
		updatePerspectives();
	}

	public static Application getApplication(TidbitComponent serviceComponent,
			Context context) {

		if (application == null) {
			application = new Tidbit(serviceComponent, context);
		}

		return application;
	}

	@Override
	public void addGenerator(Generator generator) {

		synchronized (generatorList) {
			generatorList.add(generator);
		}
	}

	public void addImportService(ImportService importer) {

		if (menuBar != null) {
			menuBar.updateImportServices();
		}
	}

	@Override
	public void addMenu(String id, JMenu menu) {

		if (id == null) {
			throw new IllegalArgumentException(
					"The argument id must not be null!");
		}

		if (menu == null) {
			throw new IllegalArgumentException(
					"The argument menu must not be null!");
		}

		if (menuBar != null) {

			for (int i = 0; i < menuBar.getMenuCount(); i++) {

				JMenu m = menuBar.getMenu(i);

				if (m != null) {

					String name = m.getName();

					if (name != null && id.equals(name)) {
						return;
					}
				}
			}

			menuBar.add(menu, 2);
		}
	}

	public void addPerspective(Perspective perspective) {

		if (perspective == null) {
			return;
		}

		perspective.setApplication(this);

		if (perspective.isAbstract()) {
			return;
		}

		try {

			if (sideBar != null) {

				if (sideBar.findButton(perspective.getId()) == null) {
					refreshSideBar();
				}

				sideBar.setSelected(startPerspective);
			}

			openPerspective(findPerspective(startPerspective));

		} catch (IOException oops) {
			logger.error("addPerspective", oops);
		}
	}

	/**
	 * Fügt die Datei der Liste der zuletzt geöffneten Projekte hinzu.
	 */
	@Override
	public void addRecent(File file) {

		try {

			if (file.isFile()) {
				menuBar.addRecent(file);
			}

		} catch (IOException oops) {
			logger.error("addRecent", oops);
		}
	}

	@Override
	public void addToolBarButton(String id, AbstractAction action) {

		if (id == null) {
			throw new IllegalArgumentException(
					"The argument id must not be null!");
		}

		if (toolBar != null) {

			for (Component comp : toolBar.getComponents()) {

				if (comp != null && comp instanceof JButton) {

					JButton button = (JButton) comp;

					if (id.equals(button.getName())) {
						return;
					}
				}
			}

			JButton button = new JButton();
			button.setName(id);
			button.setAction(action);
			toolBar.add(button);
		}
	}

	public void createAndShowGUI() {

		try {

			logger.debug("createAndShowGui...");
			splash.setMessage("GUI");

			Settings settings = context.getSettings();
			String lnfClassName = settings.getProperty("look-and-feel");

			if (lnfClassName != null && lnfClassName.trim().length() > 0) {
				try {
					UIManager.setLookAndFeel(lnfClassName);
					SwingUtilities.updateComponentTreeUI(this);

					for (Perspective perspective : application
							.getPerspectiveList()) {

						if (perspective != null
								&& perspective.getPanel() != null) {
							SwingUtilities.updateComponentTreeUI(perspective
									.getPanel());
						}
					}

				} catch (Throwable oops) {
					logger.fatal("Can't create Look and Feel " + lnfClassName,
							oops);
				}
			}

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;

			setTitle(Constants.WND_NEW_PROJECT_TITLE);

			addWindowListener(new TidbitWindowListener());

			menuBar = new TidbitMenu(this);
			setJMenuBar(menuBar);

			menuBar.createMenu();

			toolBar = new TidbitToolBar(this);
			toolBar.createInitialToolBar();

			GridPanel contentPanel = new GridPanel();
			getContentPane().add(contentPanel);

			topPanel = new TopPanel();
			topPanel.setBackground(Color.white);
			topPanel.setLeftImage(ResourceServices.getResourceAsUrl(
					"/images/handschrift.png", Tidbit.class.getClassLoader()));
			topPanel.setRightImage(ResourceServices.getResourceAsUrl(
					"/images/head.gif", Tidbit.class.getClassLoader()));

			contentPanel.addComponent(topPanel, Colspan.CS_2, Rowspan.RS_1,
					Anchor.NORTHWEST, Fill.HORIZONTAL, new Insets(0, 0, 0, 0));
			contentPanel.incrRow();

			contentPanel.addComponent(toolBar, Colspan.CS_2, Anchor.NORTHWEST,
					Fill.HORIZONTAL);
			contentPanel.incrRow();

			perspectivePanel = new JPanel();
			perspectivePanel.setLayout(new GridLayout());

			sideBar = new SideBar();
			sideBar.setName("sb.perspective");
			refreshSideBar();

			contentPanel.addComponent(sideBar, Anchor.NORTHWEST, Fill.VERTICAL);
			contentPanel.addComponent(new JScrollPane(perspectivePanel),
					Anchor.NORTHWEST, Fill.BOTH);
			// contentPanel.addComponent(perspectivePanel,
			// Anchor.NORTHWEST, Fill.BOTH);
			contentPanel.incrRow();

			openPerspective(findPerspective(startPerspective));
			sideBar.setSelected(startPerspective);

			wm.disableSaveWidgets();

			pack();
			restoreSizeAndPosition();

			lock();

		} catch (Exception oops) {

			shutdown();
			ExceptionBox ebox = new ExceptionBox("Can't create GUI!", oops);
			ebox.setVisible(true);
		}
	}

	@Override
	public Context getContext() {
		return context;
	}

	public HelpBroker getHelpBroker() {
		return helpBroker;
	}

	@Override
	public List<ImportService> getImportServiceList() {
		return serviceComponent.getImportServiceList();
	}

	@Override
	public MediumService getMediumService(String id) {

		if (id == null) {
			throw new IllegalArgumentException(
					"The argument id must not be null!");
		}

		for (MediumService mediumService : getMediumServiceList()) {
			if (id.equals(mediumService.getId())) {
				return mediumService;
			}
		}

		return null;
	}

	@Override
	public ArrayList<MediumService> getMediumServiceList() {
		return serviceComponent.getMediumServiceList();
	}

	@Override
	public List<MediumService> getMediumServiceList(String category) {

		List<MediumService> serviceList = serviceComponent
				.getMediumServiceList(category);

		if (serviceList == null) {
			return new ArrayList<MediumService>();
		}

		return serviceList;
	}

	@Override
	public Perspective getPerspective() {
		return perspective;
	}

	public Perspective getPerspective(String name) {
		return serviceComponent.getPerspective(name);
	}

	@Override
	public List<Perspective> getPerspectiveList() {
		return serviceComponent.getPerspectiveList();
	}

	@Override
	public Project getProject() {
		return context.getProject();
	}

	public RecentManager getRecentManager() {

		if (recentManager == null) {
			recentManager = new RecentManager(".tidbit.d");
		}

		return recentManager;
	}

	@Override
	public TrafoService getTrafoService(String id) {

		if (id == null) {
			throw new IllegalArgumentException(
					"The argument id must not be null!");
		}

		for (TrafoService trafoService : serviceComponent
				.getTrafoServiceList(id)) {
			return trafoService;
		}

		return null;
	}

	/**
	 * Das Programm <code>TiDBiT</code> bietet eine grafische Oberfläche zur
	 * Erstellung und Verwaltung von JavaDoc-Projekten. Die Projekte werden als
	 * Ant-Dateien gespeichert und stehen so auch zut automatisierten Ausführung
	 * zur Verfügung.
	 * 
	 * Die Klasse <code>Tidbit</code> erzeugt die Oberfläche der Anwendung.
	 * 
	 * @throws IOException
	 * @throws HelpSetException
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public void initialize() throws IOException, HelpSetException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		Toolkit toolkit = Toolkit.getDefaultToolkit();

		splash = Splash.getSplash();

		URL imageURL = ResourceServices.getResourceAsUrl("/images/splash.gif",
				Tidbit.class.getClassLoader());

		if (imageURL != null) {
			splash.splashImage(toolkit.createImage(imageURL), "TiDBiT");
		} else {
			logger.error("Couldn't find images!");
		}

		wm = JiveFactory.getInstance();
		wm.setHelpAreaBackground(new Color(240, 224, 208));
		wm.setHelpAreaBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.black),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		URL helpSetUrl = ResourceServices.getResourceAsUrl("jhelpset.hs",
				Tidbit.class.getClassLoader());

		if (helpSetUrl != null) {

			HelpSet helpSet = new HelpSet(null, helpSetUrl);
			helpBroker = helpSet.createHelpBroker();
			helpBroker.enableHelpKey(getRootPane(), "d0e1", helpSet);
			wm.setHelpBroker(helpBroker);
			wm.setHelpSet(helpSet);

		} else {

			logger.warn("Couldn't find help set for javahelp system. Help will not be available.");
		}

		context.getResourceBundle();

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/icon.png",
				Tidbit.class.getClassLoader());
		ImageIcon icon = new ImageIcon(iconUrl, "The dbdoclet icon.");
		setIconImage(icon.getImage());
	}

	public boolean isLocked() {
		return locked;
	}

	/**
	 * Sperren der Applikation für die Zeit, in der kein Projekt aktiv ist.
	 */
	@Override
	public void lock() {

		setLocked(true);
		sideBar.setEnabled(false);
		perspectivePanel.setVisible(false);
	}

	@Override
	public AbstractAction newGenerateAction(IConsole console,
			MediumService service, Perspective perspective) throws IOException {

		ResourceBundle res = StaticContext.getResourceBundle();
		ActionGenerate action = new ActionGenerate(this, console, service);
		
		JPanel panel = perspective.getPanel();
		
		panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"), "generate");
		panel.getActionMap().put("generate", action);
				
		application.addToolBarButton(
						perspective.getId() + ".generate." + service.getId(), action);

		JMenu menu = new JMenu(ResourceServices.getString(res, "C_BUILD"));
		menu.setName(perspective.getId() + ".menu");
		JMenuItem menuItem = new JMenuItem();
		menuItem.setAction(action);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		menu.add(menuItem);
	
		addMenu(perspective.getId() + ".menu", menu);

		return action;
	}

	public void openPerspective(Perspective next) throws IOException {

		if (next == null) {
			return;
		}

		if (perspective != null) {
			perspective.onLeave();
			perspective.setActive(false);
		}

		next.setActive(true);
		next.onEnter();

		perspectivePanel.removeAll();

		JPanel nextPanel = next.getPanel();
		perspectivePanel.add(nextPanel);
		perspectivePanel.revalidate();
		perspectivePanel.repaint();
		nextPanel.revalidate();
		
		perspective = next;
	}

	@Override
	public void refresh() {
		//
	}

	@Override
	public void removeGenerator(Generator generator) {
		synchronized (generatorList) {
			generatorList.remove(generator);
		}
	}

	public void removeImportService(ImportService importer) throws IOException {

		for (Perspective perspective : serviceComponent.getPerspectiveList()) {
			perspective.refresh();
		}
	}

	public void removeMediumService(MediumService service) throws IOException {

		for (Perspective perspective : serviceComponent.getPerspectiveList()) {
			perspective.refresh();
		}
	}

	@Override
	public void removeMenu(String id) {

		if (menuBar != null) {

			for (MenuElement menu : menuBar.getSubElements()) {

				String name = menu.getComponent().getName();

				if (name != null && id.equals(name)) {
					menuBar.remove(menu.getComponent());
					menuBar.repaint();
					return;
				}
			}
		}
	}

	public void removePerspective(Perspective perspective) {

		for (Perspective p : serviceComponent.getPerspectiveList()) {

			AbstractAction action = new ActionOpenPerspective(this, p);
			sideBar.addButton(new SideBarButton(action));
		}

		sideBar.prepare();

	}

	@Override
	public void removeToolBarButton(String id) {

		if (id == null) {
			throw new IllegalArgumentException(
					"The argument id must not be null!");
		}

		if (toolBar != null) {

			for (Component comp : toolBar.getComponents()) {

				if (comp != null && comp instanceof JButton) {

					JButton button = (JButton) comp;

					if (id.equals(button.getName())) {
						toolBar.remove(comp);
						toolBar.repaint();
					}
				}
			}
		}
	}

	@Override
	public void reset() {

		for (Perspective perspective : serviceComponent.getPerspectiveList()) {
			perspective.reset();
		}

		try {
			openPerspective(findPerspective(startPerspective));
			sideBar.setSelected(startPerspective);
		} catch (IOException oops) {
			logger.fatal("reset", oops);
		}
	}

	@Override
	public void setDefaultCursor() {

		setCursor(Cursor.getDefaultCursor());
		setFocusable(true);
		setFocusableWindowState(true);
	}

	@Override
	public void setFrameTitle(String title) {
		setTitle(Constants.WND_PROJECT_TITLE + title);
		topPanel.setTitle(title);
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public void setProject(Project project) {
		context.setProject(project);
	}

	public void settingsChanged(Settings settings) {
		//
	}

	/**
	 * Sperrt die Applikation für Änderungen an den Daten und setzt den
	 * Wait-Cursor.
	 */
	@Override
	public void setWaitCursor() {

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setFocusable(false);
		// setEnabled(false);
		setFocusableWindowState(false);
	}

	@Override
	public void shutdown() {

		for (Generator generator : generatorList) {
			generator.kill();
		}

		Settings settings = context.getSettings();

		if (isVisible()) {

			Point point = getLocationOnScreen();

			if (point.x < 0) {
				point.x = 10;
			}

			if (point.y < 0) {
				point.y = 10;
			}

			settings.setProperty("frame.position.x", String.valueOf(point.x));
			settings.setProperty("frame.position.y", String.valueOf(point.y));

			Dimension size = getSize();

			if (size.width < 640) {
				size.width = 640;
			}

			if (size.height < 380) {
				size.height = 380;
			}

			settings.setProperty("frame.width", String.valueOf(size.width));
			settings.setProperty("frame.height", String.valueOf(size.height));
		}

		settings.setProperty("look-and-feel", UIManager.getLookAndFeel()
				.getClass().getName());

		if (getProject() != null && getProject().getProjectFile() != null) {
			settings.setProperty("project.file", getProject().getProjectFile()
					.getAbsolutePath());
		}

		if (getPerspective() != null) {
			settings.setProperty("perspective", getPerspective().getName());
		}

		try {

			settings.store();

		} catch (IOException oops) {
			oops.printStackTrace();
		}

		OsgiRuntime osgiRuntime = StaticContext.getOsgiRuntime();

		if (osgiRuntime != null) {
			osgiRuntime.shutdown();
		}
	}

	public void start(final String[] args, final TidbitComponent host) {

		try {

			logger.info("Starting Dodo...");

			final Tidbit app = this;

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {

					try {

						app.createAndShowGUI();
						app.setVisible(true);
						app.toFront();
						app.requestFocus();

					} catch (Throwable oops) {

						ExceptionBox ebox = new ExceptionBox(
								"Error creating GUI!", oops);
						ebox.setVisible(true);
						ebox.toFront();

						return;
					}
				}
			});

			if (splash != null) {
				splash.close();
			}

			setWaitCursor();

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {

					try {

						while (app.isReady() == false) {
							Thread.sleep(300);
						}

						Settings settings = context.getSettings();
						String lastProjectPath = settings
								.getProperty("project.file");

						if (lastProjectPath != null) {

							File lastProjectFile = new File(lastProjectPath);

							if (lastProjectFile.exists() == true
									&& lastProjectFile.isFile()) {

								try {
									ActionOpenProject.openProject(app,
											lastProjectFile);
								} catch (Throwable oops) {

									RecentManager recentManager = app
											.getRecentManager();
									recentManager.remove(lastProjectFile
											.getAbsolutePath());

									settings.remove("project.file");
									TidbitExceptionHandler
											.handleException(oops);
								}
							}
						}

						String lastPerspective = settings
								.getProperty("perspective");

						if (lastPerspective != null) {

							Perspective perspective = app
									.getPerspective(lastPerspective);

							if (perspective != null) {
								sideBar.setSelected(perspective.getId());
								app.openPerspective(perspective);
							}
						}

						app.setDefaultCursor();

					} catch (Throwable oops) {

						ExceptionBox ebox = new ExceptionBox(
								"Error creating GUI!", oops);
						ebox.setVisible(true);
						ebox.toFront();

						return;
					}
				}
			});

		} catch (Throwable oops) {

			shutdown();
			oops.printStackTrace();

			if (splash != null) {
				splash.close();
			}

			ExceptionBox ebox = new ExceptionBox("Can't startup GUI!", oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	@Override
	public void stateChanged(ChangeEvent event) {

		try {

			Validator validator = new Validator(this, "tab.changed");
			validator.check();

		} catch (Exception oops) {

			ExceptionBox ebox = new ExceptionBox(context.getDialogOwner(), oops);
			ebox.setVisible(true);
			ebox.toFront();
		}

	}

	@Override
	public synchronized void unlock() {

		setLocked(false);

		sideBar.setEnabled(true);
		perspectivePanel.setVisible(true);
		toolBar.unlock();
		menuBar.unlock();
	}

	@Override
	public void vetoableChange(PropertyChangeEvent event)
			throws PropertyVetoException {

		logger.debug("vetableChangeEvent=" + event);
	}

	private Perspective findPerspective(String id) throws IOException {

		for (Perspective perspective : serviceComponent.getPerspectiveList()) {

			if (perspective.getId().equals(id)) {
				return perspective;
			}
		}

		return null;
	}

	private boolean isReady() {

		if (System.getProperty("org.dbdoclet.doclet.debug", "false").equals(
				"true")) {
			return true;
		}

		if (serviceComponent.getPerspective("project") != null
				&& serviceComponent.getPerspective("Herold") != null
				&& serviceComponent.getPerspective("dbdoclet") != null
				&& serviceComponent.getPerspective("RTF") != null
				&& serviceComponent.getPerspective("PDF") != null
				&& serviceComponent.getPerspective("HTML") != null
				&& serviceComponent.getPerspective("EPUB") != null
				&& serviceComponent.getPerspective("Eclipse") != null
				&& serviceComponent.getPerspective("JavaHelp") != null
				&& serviceComponent.getPerspective("WordML") != null) {
			return true;
		}

		return false;
	}

	private void refreshSideBar() throws IOException {

		sideBar.clear();
		ResourceBundle res = context.getResourceBundle();

		SideBarGroup docbookGroup = new SideBarGroup("DocBook", 0);
		SideBarGroup htmlGroup = new SideBarGroup("HTML", 1);
		SideBarGroup foGroup = new SideBarGroup("XSL:FO", 2);
		SideBarGroup othersGroup = new SideBarGroup(ResourceServices.getString(
				res, "C_OTHERS"), 3);

		for (Perspective perspective : serviceComponent.getPerspectiveList()) {

			if (perspective.isAbstract()) {
				continue;
			}

			AbstractAction action = new ActionOpenPerspective(this, perspective);
			String id = perspective.getId();

			if (id.startsWith("docbook")) {

				if (id.startsWith("docbook-html")) {
					sideBar.addButton(htmlGroup, new SideBarButton(action),
							perspective.getId());
				} else if (id.startsWith("docbook-fo")) {
					sideBar.addButton(foGroup, new SideBarButton(action),
							perspective.getId());
				} else {
					sideBar.addButton(othersGroup, new SideBarButton(action),
							perspective.getId());
				}

			} else if (id.equals("project")) {

				SideBarButton button = new SideBarButton(action, 0);
				sideBar.addButton(docbookGroup, button, perspective.getId());

			} else {
				sideBar.addButton(docbookGroup, new SideBarButton(action),
						perspective.getId());
			}

		}

		sideBar.prepare();

		if (isLocked()) {
			sideBar.setEnabled(false);
		}
	}

	private void restoreSizeAndPosition() {

		Settings settings = context.getSettings();
		String property;

		try {

			int x, y, width, height;

			property = settings.getProperty("frame.width", "-1");
			width = Integer.parseInt(property);

			property = settings.getProperty("frame.height", "-1");
			height = Integer.parseInt(property);

			if (width > 1024 && height > 768) {
				setSize(new Dimension(width, height));
			}

			property = settings.getProperty("frame.position.x", "112");

			if (property != null) {

				x = Integer.parseInt(property);

				property = settings.getProperty("frame.position.y", "34");

				if (property != null) {

					y = Integer.parseInt(property);
					setLocation(x, y);
				}
			}

		} catch (NumberFormatException oops) {

			ExceptionBox ebox = new ExceptionBox(this, oops);
			ebox.setVisible(true);
			ebox.toFront();

			setLocationRelativeTo(null);
		}
	}

	private void updatePerspectives() {

		for (Perspective perspective : serviceComponent.getPerspectiveList()) {

			if (perspective.isAbstract()) {
				continue;
			}

			perspective.setApplication(this);
		}
	}
}
