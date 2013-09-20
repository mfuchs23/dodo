package org.dbdoclet.tidbit.perspective.docbook.html;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.perspective.panel.docbook.HeaderFooterPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlHeaderFooterPanel extends HeaderFooterPanel implements
		ActionListener {

	private static final long serialVersionUID = 1L;

	private GridPanel navigationPanel;
	private JCheckBox suppressNavigation;
	private JCheckBox suppressHeaderNavigation;
	private JCheckBox suppressFooterNavigation;
	private JCheckBox navigGraphics;
	private JCheckBox navigShowTitles;
	private JTextField navigGraphicsExtension;
	private JTextField navigGraphicsPath;

	public HtmlHeaderFooterPanel() {
		createGui();
	}

	@Override
	protected void createGui() {

		super.createGui();

		incrRow();

		navigationPanel = createNavigationPanel();
		addComponent(navigationPanel, Colspan.CS_2, Anchor.NORTHWEST, Fill.NONE);

		addVerticalGlue();
	}

	private GridPanel createNavigationPanel() {

        GridPanel panel = new GridPanel(ResourceServices.getString(res,"C_NAVIGATION"));

        panel.startSubPanel();

        suppressNavigation = jf.createCheckBox(new Identifier(Constants.PARAM_SUPPRESS_NAVIGATION), Constants.PARAM_SUPPRESS_NAVIGATION);
        panel.addComponent(suppressNavigation);

        suppressHeaderNavigation = jf.createCheckBox(new Identifier(Constants.PARAM_SUPPRESS_HEADER_NAVIGATION), Constants.PARAM_SUPPRESS_HEADER_NAVIGATION);
        panel.addComponent(suppressHeaderNavigation);

        suppressFooterNavigation = jf.createCheckBox(new Identifier(Constants.PARAM_SUPPRESS_FOOTER_NAVIGATION), Constants.PARAM_SUPPRESS_FOOTER_NAVIGATION);
        panel.addComponent(suppressFooterNavigation);

        panel.startSubPanel();

        navigGraphics = jf.createCheckBox(new Identifier(Constants.PARAM_NAVIG_GRAPHICS), Constants.PARAM_NAVIG_GRAPHICS);
        panel.addComponent(navigGraphics);

        navigShowTitles = jf.createCheckBox(new Identifier(Constants.PARAM_NAVIG_SHOWTITLES), Constants.PARAM_NAVIG_SHOWTITLES);
        panel.addComponent(navigShowTitles);

        panel.startSubPanel();

        navigGraphicsExtension = jf.createTextField(new Identifier(Constants.PARAM_NAVIG_GRAPHICS_EXTENSION), 8);
        panel.addLabeledComponent(Constants.PARAM_NAVIG_GRAPHICS_EXTENSION, navigGraphicsExtension);
        panel.startSubPanel();

        navigGraphicsPath = jf.createTextField(new Identifier(Constants.PARAM_NAVIG_GRAPHICS_PATH), 21);
        panel.addLabeledComponent(Constants.PARAM_NAVIG_GRAPHICS_PATH, navigGraphicsPath);

        JButton navigGraphicsPathBrowseButton = jf.createButton(ResourceServices.getString(res,"C_BROWSE"));
        navigGraphicsPathBrowseButton.addActionListener(this);
        navigGraphicsPathBrowseButton.setActionCommand("chooseGraphicsPath");
        panel.addComponent(navigGraphicsPathBrowseButton);

        return panel;
    }

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		super.syncView(project, driver);

		suppressNavigation.setSelected(driver.isParameterEnabled(
				Constants.PARAM_SUPPRESS_NAVIGATION, false));
		suppressHeaderNavigation.setSelected(driver.isParameterEnabled(
				Constants.PARAM_SUPPRESS_HEADER_NAVIGATION, false));
		suppressFooterNavigation.setSelected(driver.isParameterEnabled(
				Constants.PARAM_SUPPRESS_FOOTER_NAVIGATION, false));
		navigGraphics.setSelected(driver.isParameterEnabled(
				Constants.PARAM_NAVIG_GRAPHICS, true));
		navigShowTitles.setSelected(driver.isParameterEnabled(
				"navig.show.titles", true));
		navigGraphicsExtension.setText(driver
				.getParameter(Constants.PARAM_NAVIG_GRAPHICS_EXTENSION));
		navigGraphicsPath.setText(driver
				.getParameter(Constants.PARAM_NAVIG_GRAPHICS_PATH));
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		super.syncModel(project, driver);

		driver.setParameter(Constants.PARAM_SUPPRESS_NAVIGATION,
				suppressNavigation.isSelected());
		driver.setParameter(Constants.PARAM_SUPPRESS_HEADER_NAVIGATION,
				suppressHeaderNavigation.isSelected());
		driver.setParameter(Constants.PARAM_SUPPRESS_FOOTER_NAVIGATION,
				suppressFooterNavigation.isSelected());
		driver.setParameter(Constants.PARAM_NAVIG_GRAPHICS,
				navigGraphics.isSelected());
		driver.setParameter("navig.show.titles", navigShowTitles.isSelected());
		driver.setParameter(Constants.PARAM_NAVIG_GRAPHICS_EXTENSION,
				navigGraphicsExtension.getText());
		driver.setParameter(Constants.PARAM_NAVIG_GRAPHICS_PATH,
				navigGraphicsPath.getText());
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		if (cmd == null) {
			return;
		}

		if (cmd.equals("chooseGraphicsPath")) {
			chooseDirectory(navigGraphicsPath);
		}

	}
}
