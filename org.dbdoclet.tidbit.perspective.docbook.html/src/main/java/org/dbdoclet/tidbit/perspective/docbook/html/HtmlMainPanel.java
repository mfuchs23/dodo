package org.dbdoclet.tidbit.perspective.docbook.html;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.perspective.panel.docbook.AbstractPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlMainPanel extends AbstractPanel implements ActionListener {

	private static final int ENTRY_WIDTH = 48;
	private static final long serialVersionUID = 1L;

	private JTextField basedir;
	private JButton browseBaseDirButton;
	private JCheckBox chunkFirstSections;
	private JSpinner chunkSectionDepth;
	private JTextField rootId;
	private JTextField stylesheet;
	private JCheckBox useIdAsFilename;

	public HtmlMainPanel() {
		createGui();
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		if (cmd == null) {
			return;
		}

		if (cmd.equals("chooseBaseDir")) {
			chooseDirectory(basedir);
		}
	}

	public void setBaseDir(String path) {

		if (basedir != null) {
			basedir.setText(path);
		}
	}

	public void setBaseDirEnabled(boolean baseDirEnabled) {

		if (basedir != null) {
			basedir.setEditable(false);
			browseBaseDirButton.setEnabled(false);
		}
	}

	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_BASE_DIR, basedir.getText());
		driver.setParameter(Constants.PARAM_ROOTID, rootId.getText());
		driver.setParameter(Constants.PARAM_HTML_STYLESHEET,
				stylesheet.getText());
		driver.setParameter(Constants.PARAM_CHUNK_SECTION_DEPTH,
				chunkSectionDepth.getValue());
		driver.setParameter(Constants.PARAM_CHUNK_FIRST_SECTIONS,
				chunkFirstSections.isSelected());
		driver.setParameter(Constants.PARAM_USE_ID_AS_FILENAME,
				useIdAsFilename.isSelected());
	}

	public void syncView(Project project, AbstractDriver driver) {

		if (driver != null) {
			basedir.setText(driver.getParameter(Constants.PARAM_BASE_DIR));
			rootId.setText(driver.getParameter(Constants.PARAM_ROOTID));
			stylesheet.setText(driver
					.getParameter(Constants.PARAM_HTML_STYLESHEET));
			chunkSectionDepth.setValue(driver.getNumberParameter(
					Constants.PARAM_CHUNK_SECTION_DEPTH, 2));
			chunkFirstSections.setSelected(driver.isParameterEnabled(
					Constants.PARAM_CHUNK_FIRST_SECTIONS, false));
			useIdAsFilename.setSelected(driver.isParameterEnabled(
					Constants.PARAM_USE_ID_AS_FILENAME, false));
		}
	}

	private JPanel createChunkPanel() {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(ResourceServices
				.getString(res, "C_CHUNKING")));

		chunkSectionDepth = createNumberSpinner();
		panel.addLabeledComponent(Constants.PARAM_CHUNK_SECTION_DEPTH,
				chunkSectionDepth);

		chunkFirstSections = jf.createCheckBox(new Identifier(
				Constants.PARAM_CHUNK_FIRST_SECTIONS),
				Constants.PARAM_CHUNK_FIRST_SECTIONS);
		panel.addComponent(chunkFirstSections);

		useIdAsFilename = jf.createCheckBox(new Identifier(
				Constants.PARAM_USE_ID_AS_FILENAME),
				Constants.PARAM_USE_ID_AS_FILENAME);
		panel.addComponent(useIdAsFilename);

		return panel;
	}

	private JPanel createCommonPanel() {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(""));

		panel.startSubPanel();

		rootId = jf.createTextField(new Identifier(Constants.PARAM_ROOTID), 12);
		panel.addLabeledComponent(
				new JLabel(ResourceServices.getString(res, "C_ROOT_ID")),
				rootId);

		panel.startSubPanel();

		stylesheet = jf.createTextField(new Identifier(
				Constants.PARAM_HTML_STYLESHEET), ENTRY_WIDTH);
		panel.addLabeledComponent(new JLabel("Stylesheet"), stylesheet);

		panel.startSubPanel();

		basedir = jf.createTextField(new Identifier(Constants.PARAM_BASE_DIR),
				ENTRY_WIDTH);
		panel.addLabeledComponent(
				new JLabel(ResourceServices
						.getString(res, "C_OUTPUT_DIRECTORY")), basedir);

		browseBaseDirButton = jf.createButton(ResourceServices.getString(res,
				"C_BROWSE"));
		browseBaseDirButton.setActionCommand("chooseBaseDir");
		browseBaseDirButton.addActionListener(this);
		panel.addComponent(browseBaseDirButton);

		return panel;
	}

	protected void createGui() {

		super.createGui();

		JPanel commonPanel = createCommonPanel();
		addComponent(commonPanel, Anchor.NORTHWEST);

		incrRow();

		JPanel chunkPanel = createChunkPanel();
		addComponent(chunkPanel, Anchor.NORTHWEST);

		addVerticalGlue();
	}
}
