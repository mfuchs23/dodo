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

import java.awt.Font;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.Rowspan;
import org.dbdoclet.jive.fo.FoAttributeSet;
import org.dbdoclet.jive.fo.FoAttributeSetChooser;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;
import org.dbdoclet.unit.Length;

public abstract class AbstractPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	protected static final Font STANDARD_FONT = new Font("Serif", Font.PLAIN,
			12);

	protected JiveFactory jf;
	protected ResourceBundle res;

	public AbstractPanel() {

		jf = JiveFactory.getInstance();
		res = StaticContext.getResourceBundle();
	}

	protected void createGui() {

		int padding = 3;
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(padding, padding, padding, padding),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
	}

	// public abstract void reset();

	public abstract void syncView(Project project, AbstractDriver driver);

	public abstract void syncModel(Project project, AbstractDriver driver);

	protected void chooseDirectory(JTextField textField) {

		String path = textField.getText();

		JFileChooser fc = new JFileChooser(path);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File dir = fc.getSelectedFile();
			textField.setText(FileServices.normalizePath(dir.getAbsolutePath())
					+ "/");
		}
	}

	protected void chooseFileAsUrl(JTextField textField) {

		String path = textField.getText();

		JFileChooser fc = new JFileChooser(path);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();
			try {
				textField.setText(file.toURI().toURL().toString());
			} catch (MalformedURLException e) {
				textField.setText(file.getAbsolutePath());
			}
		}
	}

	protected void chooseFile(JTextField textField) {

		String path = textField.getText();
		JFileChooser fc = new JFileChooser(path);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();
			textField.setText(file.getPath());
		}
	}

	/**
	 * Füllen eines Objektes vom Typ FoAttributeSet mit den Wertern aus der
	 * XSL-Driver Datei.
	 * 
	 * @param driver
	 * @param aset
	 * @param attributeSetName
	 */
	protected void fillFoProperties(AbstractDriver driver, FoAttributeSet aset,
			String attributeSetName) {

		if (driver == null || driver.getAttributeSet(attributeSetName) == null) {
			aset.reset();
			return;
		}

		aset.setFont(driver.getFontAttributes(attributeSetName));
		aset.setForeground(driver.getColorAttribute(attributeSetName));
		aset.setBackground(driver.getColorAttribute(attributeSetName,
				"background-color"));
		aset.setSpaceBeforeOptimum(driver.getAttribute(attributeSetName,
				"space-before.optimum"));
		aset.setSpaceBeforeMinimum(driver.getAttribute(attributeSetName,
				"space-before.minimum"));
		aset.setSpaceBeforeMaximum(driver.getAttribute(attributeSetName,
				"space-before.maximum"));
		aset.setSpaceAfterMinimum(driver.getAttribute(attributeSetName,
				"space-after.minimum"));
		aset.setSpaceAfterOptimum(driver.getAttribute(attributeSetName,
				"space-after.optimum"));
		aset.setSpaceAfterMaximum(driver.getAttribute(attributeSetName,
				"space-after.maximum"));

		aset.setPadding(driver.getAttribute(attributeSetName, "padding"));

		aset.setTextIndent(driver.getAttribute(attributeSetName, "text-indent"));
		aset.setStartIndent(driver.getAttribute(attributeSetName,
				"start-indent"));
		aset.setEndIndent(driver.getAttribute(attributeSetName, "end-indent"));

		fillFrameProperties(driver, aset, attributeSetName);
		fillLineProperties(driver, aset, attributeSetName);

		aset.autoEnable();
	}

	private void fillFrameProperties(AbstractDriver driver,
			FoAttributeSet aset, String attributeSetName) {

		String[] sides = { "top", "bottom", "right", "left" };

		for (String side : sides) {

			String frameStyle = driver.getAttribute(attributeSetName, "border-"
					+ side + "-style");

			if (frameStyle != null) {

				aset.setFrameSideEnabled(side);
				aset.setFrameWidth(driver.getAttribute(attributeSetName,
						"border-" + side + "-width"));
				aset.setFrameColor(driver.getColorAttribute(attributeSetName,
						"border-" + side + "-color"));
			}
		}
	}

	private void fillLineProperties(AbstractDriver driver, FoAttributeSet aset,
			String attributeSetName) {

		aset.setWrapOption(driver.getAttribute(attributeSetName, "wrap-option"));
		aset.setTextAlign(driver.getAttribute(attributeSetName, "text-align"));
		aset.setLineWidth(driver.getAttribute(attributeSetName, "width"));
		aset.setLineHeight(driver.getAttribute(attributeSetName, "line-height"));
	}

	/**
	 * Füllen eines Objektes vom Typ AbstractDriver (XSL) mit den Werten aus
	 * einem FoAttributeSet (GUI).
	 * 
	 * @param driver
	 * @param aset
	 * @param attributeSetName
	 */
	protected void fillDriverAttributeSet(AbstractDriver driver,
			FoAttributeSet aset, String attributeSetName) {

		if (aset.isActivated()) {

			if (aset.isFontEnabled()) {

				driver.setFontAttributes(attributeSetName, aset.getFont(),
						aset.getForeground(), aset.getBackground());
			} else {
				driver.removeFontAttributes(attributeSetName);
			}

			if (aset.isSpacingEnabled()) {
				driver.setSpacingAttributes(attributeSetName, aset);
			} else {
				driver.removeSpacingAttributes(attributeSetName);
			}

			if (aset.isFrameEnabled()) {

				if (aset.isFrameTop()) {
					driver.setFrameAttributes(attributeSetName, "top", aset);
				} else {
					driver.removeFrameAttributes(attributeSetName, "top");
				}

				if (aset.isFrameBottom()) {
					driver.setFrameAttributes(attributeSetName, "bottom", aset);
				} else {
					driver.removeFrameAttributes(attributeSetName, "bottom");
				}

				if (aset.isFrameRight()) {
					driver.setFrameAttributes(attributeSetName, "right", aset);
				} else {
					driver.removeFrameAttributes(attributeSetName, "right");
				}

				if (aset.isFrameLeft()) {
					driver.setFrameAttributes(attributeSetName, "left", aset);
				} else {
					driver.removeFrameAttributes(attributeSetName, "left");
				}

			} else {

				driver.removeFrameAttributes(attributeSetName, "top");
				driver.removeFrameAttributes(attributeSetName, "bottom");
				driver.removeFrameAttributes(attributeSetName, "right");
				driver.removeFrameAttributes(attributeSetName, "left");
			}

			if (aset.isLineEnabled()) {
				driver.setLineAttributes(attributeSetName, aset);
			} else {
				driver.removeLineAttributes(attributeSetName);
			}

		} else {

			driver.removeAttributeSet(attributeSetName);
		}
	}

	protected String getDistance(JSpinner spinner) {

		if (spinner == null) {
			return "";
		}

		Length distance = (Length) spinner.getValue();
		return distance.toNormalizedString();
	}

	protected JSpinner createNumberSpinner() {

		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
		// editor.getTextField().setEditable(false);
		spinner.setEditor(editor);
		return spinner;
	}

	protected void addFoPropertiesPanel(FoAttributeSet[] attributeSets) {

		FoAttributeSetChooser attributeSetList = new FoAttributeSetChooser(
				attributeSets, Constants.FONT_FAMILY_LIST);
		GridPanel formatPanel = new GridPanel();
		setCell(0, 0);
		addComponent(formatPanel, Rowspan.RS_5, Anchor.NORTHWEST, Fill.VERTICAL);

		formatPanel.addComponent(new JLabel(ResourceServices.getString(res,
				"C_FORMAT")));
		formatPanel.startSubPanel();

		JScrollPane scrollPane = new JScrollPane(attributeSetList);
		// scrollPane.setPreferredSize(new
		// Dimension(Constants.FO_ATTRIBUTE_LIST_WIDTH,
		// Constants.FO_ATTRIBUTE_LIST_HEIGHT));
		// scrollPane.setMinimumSize(new
		// Dimension(Constants.FO_ATTRIBUTE_LIST_WIDTH,
		// Constants.FO_ATTRIBUTE_LIST_HEIGHT));

		formatPanel.addComponent(scrollPane, Anchor.NORTHWEST, Fill.BOTH);
	}

	protected void syncFoPropertiesView(AbstractDriver driver,
			FoAttributeSet[] attributeSets, String[] foPropertiesList) {

		int index = 0;
		for (String props : foPropertiesList) {
			fillFoProperties(driver, attributeSets[index], props);
			index++;
		}
	}

	protected void syncFoPropertiesModel(AbstractDriver driver,
			FoAttributeSet[] attributeSets, String[] foPropertiesList) {

		int index = 0;
		for (String props : foPropertiesList) {
			fillDriverAttributeSet(driver, attributeSets[index++], props);
		}
	}

}
