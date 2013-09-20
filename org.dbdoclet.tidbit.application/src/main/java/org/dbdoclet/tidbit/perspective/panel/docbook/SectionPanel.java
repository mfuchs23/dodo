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
package org.dbdoclet.tidbit.perspective.panel.docbook;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.model.LabelItem;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.NumberTextField;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class SectionPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	protected GridPanel variableListPanel;
	private JComboBox chapterAutolabel;
	private JComboBox appendixAutolabel;
	private JComboBox partAutolabel;
	private JComboBox referenceAutolabel;
	private JComboBox prefaceAutolabel;
	private JCheckBox quandadivAutolabel;
	private JComboBox sectionAutolabel;
	private JCheckBox sectionLabelIncludesComponentLabel;
	private JCheckBox componentLabelIncludesPartLabel;
	private JCheckBox labelFromPart;
	private NumberTextField sectionAutoLabelMaxDepth;

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);

		GridPanel panel = createAutolabelPanel();
		addComponent(panel, Anchor.NORTHWEST, Fill.NONE);
	}

	private GridPanel createAutolabelPanel() {

		GridPanel panel = new GridPanel();
		panel.setBorder(BorderFactory.createTitledBorder(ResourceServices
				.getString(res, "C_AUTOMATIC_LABELLING")));

		panel.startSubPanel();

		chapterAutolabel = createLabellingComboBox(Constants.PARAM_CHAPTER_AUTOLABEL);
		panel.addLabeledComponent(Constants.PARAM_CHAPTER_AUTOLABEL,
				chapterAutolabel);

		sectionAutolabel = createLabellingComboBox(Constants.PARAM_SECTION_AUTOLABEL);
		panel.addLabeledComponent(Constants.PARAM_SECTION_AUTOLABEL,
				sectionAutolabel);

		panel.startSubPanel();

		appendixAutolabel = createLabellingComboBox(Constants.PARAM_APPENDIX_AUTOLABEL);
		panel.addLabeledComponent(Constants.PARAM_APPENDIX_AUTOLABEL,
				appendixAutolabel);
		
		partAutolabel = createLabellingComboBox(Constants.PARAM_PART_AUTOLABEL);
		panel.addLabeledComponent(Constants.PARAM_PART_AUTOLABEL, partAutolabel);

		panel.startSubPanel();

		referenceAutolabel = createLabellingComboBox(Constants.PARAM_REFERENCE_AUTOLABEL);
		panel.addLabeledComponent(Constants.PARAM_REFERENCE_AUTOLABEL,
				referenceAutolabel);


		prefaceAutolabel = createLabellingComboBox(Constants.PARAM_PREFACE_AUTOLABEL);
		panel.addLabeledComponent(Constants.PARAM_PREFACE_AUTOLABEL,
				prefaceAutolabel);

		panel.startSubPanel();

		quandadivAutolabel = jf.createCheckBox(new Identifier(
				Constants.PARAM_QUANDADIV_AUTOLABEL),
				Constants.PARAM_QUANDADIV_AUTOLABEL);
		panel.addComponent(quandadivAutolabel);

		sectionLabelIncludesComponentLabel = jf.createCheckBox(new Identifier(
				Constants.PARAM_SECTION_LABEL_INCLUDES_COMPONENT_LABEL),
				Constants.PARAM_SECTION_LABEL_INCLUDES_COMPONENT_LABEL);
		panel.addComponent(sectionLabelIncludesComponentLabel);

		panel.startSubPanel();

		componentLabelIncludesPartLabel = jf.createCheckBox(new Identifier(
				Constants.PARAM_COMPONENT_LABEL_INCLUDES_PART_LABEL),
				Constants.PARAM_COMPONENT_LABEL_INCLUDES_PART_LABEL);
		panel.addComponent(componentLabelIncludesPartLabel);

		labelFromPart = jf.createCheckBox(new Identifier(
				Constants.PARAM_LABEL_FROM_PART),
				Constants.PARAM_LABEL_FROM_PART);
		panel.addComponent(labelFromPart);

		sectionAutoLabelMaxDepth = jf.createNumberTextField(new Identifier(
				Constants.PARAM_SECTION_AUTOLABEL_MAX_DEPTH), 2);
		panel.addLabeledComponent(Constants.PARAM_SECTION_AUTOLABEL_MAX_DEPTH,
				sectionAutoLabelMaxDepth);

		return panel;
	}

	private JComboBox createLabellingComboBox(String key) {

		LabelItem[] itemList = new LabelItem[6];
		itemList[0] = new LabelItem(
				ResourceServices.getString(res, "C_DIGITS"), "1");
		itemList[1] = new LabelItem(ResourceServices.getString(res,
				"C_UPPERCASE_LETTERS"), "A");
		itemList[2] = new LabelItem(ResourceServices.getString(res,
				"C_LOWERCASE_LETTERS"), "a");
		itemList[3] = new LabelItem(ResourceServices.getString(res,
				"C_UPPERCASE_ROMAN_DIGITS"), "I");
		itemList[4] = new LabelItem(ResourceServices.getString(res,
				"C_LOWERCASE_ROMAN_DIGITS"), "i");
		itemList[5] = new LabelItem(
				ResourceServices.getString(res, "C_OFF"), "0");

		return jf.createComboBox(new Identifier(key), itemList);
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		if (driver == null) {
			return;
		}
		
		chapterAutolabel.setSelectedItem(new LabelItem("", driver
				.getParameter(Constants.PARAM_CHAPTER_AUTOLABEL)));
		sectionAutolabel.setSelectedItem(new LabelItem("", driver
				.getParameter(Constants.PARAM_SECTION_AUTOLABEL)));
		appendixAutolabel.setSelectedItem(new LabelItem("", driver
				.getParameter(Constants.PARAM_APPENDIX_AUTOLABEL)));
		partAutolabel.setSelectedItem(new LabelItem("", driver
				.getParameter(Constants.PARAM_PART_AUTOLABEL)));
		referenceAutolabel.setSelectedItem(new LabelItem("", driver
				.getParameter(Constants.PARAM_REFERENCE_AUTOLABEL)));
		prefaceAutolabel.setSelectedItem(new LabelItem("", driver
				.getParameter(Constants.PARAM_PREFACE_AUTOLABEL)));
		quandadivAutolabel.setSelected(driver.isParameterEnabled(
				Constants.PARAM_QUANDADIV_AUTOLABEL, false));
		sectionLabelIncludesComponentLabel.setSelected(driver
				.isParameterEnabled(
						Constants.PARAM_SECTION_LABEL_INCLUDES_COMPONENT_LABEL,
						false));
		componentLabelIncludesPartLabel.setSelected(driver.isParameterEnabled(
				Constants.PARAM_COMPONENT_LABEL_INCLUDES_PART_LABEL, false));
		labelFromPart.setSelected(driver.isParameterEnabled(
				Constants.PARAM_LABEL_FROM_PART, false));
		sectionAutoLabelMaxDepth.setText(driver
				.getParameter(Constants.PARAM_SECTION_AUTOLABEL_MAX_DEPTH));
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		driver.setParameter(Constants.PARAM_CHAPTER_AUTOLABEL,
				((LabelItem) chapterAutolabel.getSelectedItem()).getValue());
		driver.setParameter(Constants.PARAM_SECTION_AUTOLABEL,
				((LabelItem) sectionAutolabel.getSelectedItem()).getValue());
		driver.setParameter(Constants.PARAM_APPENDIX_AUTOLABEL,
				((LabelItem) appendixAutolabel.getSelectedItem()).getValue());
		driver.setParameter(Constants.PARAM_PART_AUTOLABEL,
				((LabelItem) partAutolabel.getSelectedItem()).getValue());
		driver.setParameter(Constants.PARAM_REFERENCE_AUTOLABEL,
				((LabelItem) referenceAutolabel.getSelectedItem()).getValue());
		driver.setParameter(Constants.PARAM_PREFACE_AUTOLABEL,
				((LabelItem) prefaceAutolabel.getSelectedItem()).getValue());
		driver.setParameter(Constants.PARAM_QUANDADIV_AUTOLABEL,
				quandadivAutolabel.isSelected());
		driver.setParameter(
				Constants.PARAM_SECTION_LABEL_INCLUDES_COMPONENT_LABEL,
				sectionLabelIncludesComponentLabel.isSelected());
		driver.setParameter(
				Constants.PARAM_COMPONENT_LABEL_INCLUDES_PART_LABEL,
				componentLabelIncludesPartLabel.isSelected());
		driver.setParameter(Constants.PARAM_LABEL_FROM_PART,
				labelFromPart.isSelected());
		driver.setParameter(Constants.PARAM_SECTION_AUTOLABEL_MAX_DEPTH,
				sectionAutoLabelMaxDepth.getText());
	}
}
