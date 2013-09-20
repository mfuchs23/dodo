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
package org.dbdoclet.tidbit.perspective.dbdoclet;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

import org.dbdoclet.Identifier;
import org.dbdoclet.doclet.docbook.DbdScript;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.dialog.ImageChooser;
import org.dbdoclet.jive.model.LabelItem;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.LanguageListBox;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.common.TidbitExceptionHandler;
import org.dbdoclet.tidbit.perspective.panel.BookTypeItem;
import org.dbdoclet.tidbit.project.Project;

public class DocletPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	private JCheckBox additionalInformationCheckBox;
	private JCheckBox appendixCheckBox;
	private final JComboBox bookTypeComboBox;
	private final JComboBox docBookVersionComboBox;
	private final JTextField documentationIdEntry;
	private JCheckBox exceptionsCheckBox;
	private JCheckBox fieldsCheckBox;
	private final JComboBox hyphCharComboBox;
	private JCheckBox ignoreAnnotationDocumentedCheckBox;
	private JCheckBox indexCheckBox;
	private JCheckBox inheritanceCheckBox;
	private JCheckBox inheritedFromCheckBox;
	private final LanguageListBox languageComboBox;
	private final JTextField logoFileEntry;
	private JCheckBox methodsCheckBox;
	private JCheckBox parametersCheckBox;
	private JCheckBox qualifiedNamesCheckBox;
	private JCheckBox seeAlsoCheckBox;
	private JCheckBox serialFieldsCheckBox;
	private JCheckBox splitOutputCheckBox;
	private JCheckBox statisticsCheckBox;
	private JCheckBox synopsisCheckBox;
	private final JComboBox tableFrameComboBox;

	protected ResourceBundle res;
	protected JiveFactory widgetMap;

	public DocletPanel() {

		super();

		JLabel label;
		JButton button;

		res = StaticContext.getResourceBundle();

		widgetMap = JiveFactory.getInstance();

		JTextComponent helpArea = widgetMap.createHelpArea(this,
				ResourceServices.getString(res, "C_HELP_DOCLET_PANEL"));
		addComponent(helpArea, Colspan.CS_6, Anchor.NORTHWEST, Fill.HORIZONTAL);
		incrRow();

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_DOCUMENTATION_ID"));
		documentationIdEntry = widgetMap.createIdentifierTextField(5);
		addLabeledComponent(label, documentationIdEntry);

		incrRow();

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_BOOK_TYPE"));
		bookTypeComboBox = widgetMap.createComboBox(new Identifier(
				"document.type"));

		bookTypeComboBox.setEditable(false);

		bookTypeComboBox.addItem(new BookTypeItem(res, "article"));
		bookTypeComboBox.addItem(new BookTypeItem(res, "book"));
		bookTypeComboBox.addItem(new BookTypeItem(res, "part"));
		bookTypeComboBox.addItem(new BookTypeItem(res, "reference"));

		addLabeledComponent(label, bookTypeComboBox);

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_PARAM_TABLE_FRAME"));
		tableFrameComboBox = widgetMap.createComboBox(new Identifier(
				Constants.PARAM_DEFAULT_TABLE_FRAME));
		tableFrameComboBox.setEditable(false);
		tableFrameComboBox.addItem("all");
		tableFrameComboBox.addItem("bottom");
		tableFrameComboBox.addItem("none");
		tableFrameComboBox.addItem("sides");
		tableFrameComboBox.addItem("top");
		tableFrameComboBox.addItem("topbot");

		addLabeledComponent(label, tableFrameComboBox);

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_PARAM_HYPHENATION_CHAR"));
		hyphCharComboBox = widgetMap.createComboBox(new Identifier(
				"hyphenation.char"));
		hyphCharComboBox.setEditable(false);
		hyphCharComboBox.addItem("soft-hyphen");
		hyphCharComboBox.addItem("zero-width-space");

		addLabeledComponent(label, hyphCharComboBox);

		incrRow();

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_LANGUAGE"));
		languageComboBox = new LanguageListBox(res.getLocale());
		addLabeledComponent(label, languageComboBox);

		label = widgetMap.createLabel("DocBook");
		docBookVersionComboBox = widgetMap.createComboBox(new Identifier(
				"docbook.version"));
		docBookVersionComboBox.setEditable(false);

		docBookVersionComboBox.addItem("5.0");
		docBookVersionComboBox.addItem("4.5");

		addLabeledComponent(label, docBookVersionComboBox);
		incrRow();

		GridPanel buttonPanel = new GridPanel();

		button = widgetMap.createButton(ResourceServices.getString(res,
				"C_LOGO") + "...");
		buttonPanel.addComponent(button);

		logoFileEntry = widgetMap.createTextField(new Identifier("logo.file"),
				64);
		buttonPanel.addComponent(logoFileEntry);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseLogo();
			}
		});

		addComponent(buttonPanel, Colspan.CS_6);
		incrRow();

		GridPanel gridPanel = new GridPanel();

		gridPanel.addComponent(createShowPanel(widgetMap, res), Colspan.CS_3,
				Anchor.NORTH);
		gridPanel.addComponent(createOptionPanel(widgetMap, res), Anchor.NORTH);

		addComponent(gridPanel, Colspan.CS_6);

		addVerticalGlue();
	}

	public void syncModel(Project project, DbdScript script) {

		if (project == null) {
			return;
		}

		script.setDocumentationId(documentationIdEntry.getText());
		script.setDocumentElement((String) ((BookTypeItem) bookTypeComboBox
				.getSelectedItem()).getValue());
		script.setTableStyle((String) tableFrameComboBox.getSelectedItem());
		script.setHyphenationChar((String) hyphCharComboBox.getSelectedItem());
		script.setLanguage(((Locale) ((LabelItem) languageComboBox
				.getSelectedItem()).getValue()).getLanguage());
		script.setDocBookVersion((String) docBookVersionComboBox
				.getSelectedItem());
		project.setDocBookVersion((String) docBookVersionComboBox
				.getSelectedItem());

		String logoPath = logoFileEntry.getText();
		if (FileServices.isAbsolutePath(logoPath)) {
			try {
				logoPath = FileServices.relativePath(new File(project
						.getFileManager().getDocBookFileDirName()), new File(
						logoPath));
			} catch (IOException oops) {
				TidbitExceptionHandler.handleException(oops);
			}
		}
		script.setLogoPath(logoPath);

		script.setCreateMethodInfoEnabled(methodsCheckBox.isSelected());
		script.setCreateMethodInfoEnabled(fieldsCheckBox.isSelected());
		script.setCreateSynopsisEnabled(synopsisCheckBox.isSelected());
		script.setCreateParameterInfoEnabled(parametersCheckBox.isSelected());
		script.setCreateMetaInfoEnabled(additionalInformationCheckBox
				.isSelected());
		script.setCreateExceptionInfoEnabled(exceptionsCheckBox.isSelected());
		script.setCreateSeeAlsoInfoEnabled(seeAlsoCheckBox.isSelected());
		script.setCreateSerialFieldInfoEnabled(serialFieldsCheckBox
				.isSelected());

		script.setCreateAppendixEnabled(appendixCheckBox.isSelected());
		script.setCreateInheritanceInfoEnabled(inheritanceCheckBox.isSelected());
		script.setCreateStatisticsEnabled(statisticsCheckBox.isSelected());
		script.setCreateInheritedFromInfoEnabled(inheritedFromCheckBox
				.isSelected());
		script.setCreateFullyQualifiedNamesEnabled(qualifiedNamesCheckBox
				.isSelected());
		script.setChunkDocBookEnabled(splitOutputCheckBox.isSelected());
		script.setCreateIndexEnabled(indexCheckBox.isSelected());
		script.setForceAnnotationDocumentationEnabled(ignoreAnnotationDocumentedCheckBox
				.isSelected());
	}

	public void syncView(Project project, DbdScript script) {

		documentationIdEntry.setText(script.getDocumentationId());
		bookTypeComboBox.setSelectedItem(new BookTypeItem(res, script
				.getDocumentElement()));
		tableFrameComboBox.setSelectedItem(script.getTableStyle());
		hyphCharComboBox.setSelectedItem(script.getHyphenationCharAsText());
		languageComboBox.setSelectedItem(script.getLanguage());
		docBookVersionComboBox.setSelectedItem(script.getDocBookVersion());
		logoFileEntry.setText(script.getLogoPath());

		methodsCheckBox.setSelected(script.isCreateMethodInfoEnabled());
		fieldsCheckBox.setSelected(script.isCreateFieldInfoEnabled());
		synopsisCheckBox.setSelected(script.isCreateSynopsisEnabled());
		parametersCheckBox.setSelected(script.isCreateParameterInfoEnabled());
		additionalInformationCheckBox.setSelected(script
				.isCreateMetaInfoEnabled());
		exceptionsCheckBox.setSelected(script.isCreateExceptionInfoEnabled());
		seeAlsoCheckBox.setSelected(script.isCreateSeeAlsoInfoEnabled());
		serialFieldsCheckBox.setSelected(script
				.isCreateSerialFieldInfoEnabled());

		appendixCheckBox.setSelected(script.isCreateAppendixEnabled());
		inheritanceCheckBox
				.setSelected(script.isCreateInheritanceInfoEnabled());
		statisticsCheckBox.setSelected(script.isCreateStatisticsEnabled());
		inheritedFromCheckBox.setSelected(script
				.isCreateInheritedFromInfoEnabled());
		qualifiedNamesCheckBox.setSelected(script
				.isCreateFullyQualifiedNamesEnabled());
		splitOutputCheckBox.setSelected(script.isChunkDocBookEnabled());
		indexCheckBox.setSelected(script.isAddIndexEnabled());
		ignoreAnnotationDocumentedCheckBox.setSelected(script
				.isForceAnnotationDocumentationEnabled());

	}

	private void chooseLogo() {

		try {

			String path;
			File file;

			ImageChooser ic = new ImageChooser();

			path = logoFileEntry.getText();

			if (path != null && path.trim().length() > 0) {

				file = new File(path);

				if (file.exists() == true) {
					ic.setSelectedFile(file);
				}
			}

			int rc = ic.showOpenDialog(this);

			if (rc == JFileChooser.APPROVE_OPTION) {

				file = ic.getSelectedFile();
				logoFileEntry.setText(file.getCanonicalPath());
			}

		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	private JPanel createOptionPanel(JiveFactory widgetMap, ResourceBundle res) {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				res.getString("C_OPTIONS")));

		appendixCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_USE_APPENDIX")),
				ResourceServices.getString(res, "C_USE_APPENDIX"));
		appendixCheckBox.setSelected(true);
		appendixCheckBox.setToolTipText(ResourceServices.getString(res,
				"C_TOOLTIP_USE_APPENDIX"));
		panel.addComponent(appendixCheckBox);

		inheritanceCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_INHERITANCE_PATH")),
				ResourceServices.getString(res, "C_INHERITANCE_PATH"));
		inheritanceCheckBox.setSelected(true);
		panel.addComponent(inheritanceCheckBox);

		statisticsCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_STATISTICS")),
				ResourceServices.getString(res, "C_STATISTICS"));
		statisticsCheckBox.setSelected(true);
		panel.addComponent(statisticsCheckBox);
		panel.incrRow();

		inheritedFromCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_INHERITED_FROM")),
				ResourceServices.getString(res, "C_INHERITED_FROM"));
		inheritedFromCheckBox.setSelected(true);
		panel.addComponent(inheritedFromCheckBox);

		qualifiedNamesCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_USE_FULL_QUALIFIED_NAMES")),
				ResourceServices.getString(res, "C_USE_FULL_QUALIFIED_NAMES"));
		qualifiedNamesCheckBox.setSelected(false);
		panel.addComponent(qualifiedNamesCheckBox);

		splitOutputCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_SPLIT_OUTPUT")),
				ResourceServices.getString(res, "C_SPLIT_OUTPUT"));
		splitOutputCheckBox.setSelected(false);
		splitOutputCheckBox.setToolTipText(ResourceServices.getString(res,
				"C_TOOLTIP_SPLIT_OUTPUT"));
		panel.addComponent(splitOutputCheckBox);
		panel.incrRow();

		indexCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_INDEX")), ResourceServices
				.getString(res, "C_INDEX"));
		indexCheckBox.setSelected(true);
		panel.addComponent(indexCheckBox);

		ignoreAnnotationDocumentedCheckBox = widgetMap.createCheckBox(
				new Identifier(ResourceServices.getString(res,
						"C_IGNORE_ANNOTATION_DOCUMENTED")), ResourceServices
						.getString(res, "C_IGNORE_ANNOTATION_DOCUMENTED"));
		ignoreAnnotationDocumentedCheckBox.setSelected(false);
		panel.addComponent(ignoreAnnotationDocumentedCheckBox);

		return panel;
	}

	private JPanel createShowPanel(JiveFactory widgetMap, ResourceBundle res) {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				res.getString("C_CREATE_MODULE")));

		methodsCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_METHODS")), ResourceServices
				.getString(res, "C_METHODS"));
		methodsCheckBox.setSelected(true);
		panel.addComponent(methodsCheckBox);

		fieldsCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_FIELDS")), ResourceServices
				.getString(res, "C_FIELDS"));
		fieldsCheckBox.setSelected(true);
		panel.addComponent(fieldsCheckBox);

		synopsisCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_SYNOPSIS")),
				ResourceServices.getString(res, "C_SYNOPSIS"));
		synopsisCheckBox.setSelected(true);
		panel.addComponent(synopsisCheckBox);
		panel.incrRow();

		parametersCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_PARAMETERS")),
				ResourceServices.getString(res, "C_PARAMETERS"));
		parametersCheckBox.setSelected(true);
		panel.addComponent(parametersCheckBox);

		additionalInformationCheckBox = widgetMap.createCheckBox(
				new Identifier(ResourceServices.getString(res,
						"C_ADDITIONAL_INFORMATION")), ResourceServices
						.getString(res, "C_ADDITIONAL_INFORMATION"));
		additionalInformationCheckBox.setSelected(true);
		panel.addComponent(additionalInformationCheckBox);

		exceptionsCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_EXCEPTIONS")),
				ResourceServices.getString(res, "C_EXCEPTIONS"));
		exceptionsCheckBox.setSelected(true);
		panel.addComponent(exceptionsCheckBox);
		panel.incrRow();

		seeAlsoCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_SEE_ALSO")),
				ResourceServices.getString(res, "C_SEE_ALSO"));
		seeAlsoCheckBox.setSelected(true);
		panel.addComponent(seeAlsoCheckBox);

		serialFieldsCheckBox = widgetMap.createCheckBox(new Identifier(
				ResourceServices.getString(res, "C_SERIAL_FIELDS")),
				ResourceServices.getString(res, "C_SERIAL_FIELDS"));
		serialFieldsCheckBox.setSelected(true);
		panel.addComponent(serialFieldsCheckBox);

		return panel;
	}
}

class ImageFilter extends FileFilter {

	private String description = "Images";

	@Override
	public boolean accept(File file) {

		if (file.isDirectory()) {
			return true;
		}

		String extension = FileServices.getExtension(file.getName());

		if (extension != null && extension.equals("gif")
				&& extension.equals("png") && extension.equals("svg")
				&& extension.equals("jpg")) {
			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
