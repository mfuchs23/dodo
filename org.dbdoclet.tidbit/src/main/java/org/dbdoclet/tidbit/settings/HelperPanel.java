/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.dialog.settings.AbstractSettingsPanel;
import org.dbdoclet.service.JvmServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.common.StaticContext;

public class HelperPanel extends AbstractSettingsPanel implements
		ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int FIELD_WIDTH = 17;

	private JiveFactory wm;

	private ResourceBundle res;
	private JTextField browserEntry;
	private JTextField foViewerEntry;
	private JTextField pdfViewerEntry;
	private JTextField psViewerEntry;
	private JTextField rtfViewerEntry;
	private JTextField wmlViewerEntry;
	private JTextField docBookEditorEntry;

	public HelperPanel(Context ctx) {

		setName("settings.panel.helper");
		setBorder(new EmptyBorder(10, 10, 10, 10));

		wm = JiveFactory.getInstance();
		res = ctx.getResourceBundle();

		docBookEditorEntry = wm.createTextField(new Identifier("viewer.docbook"), FIELD_WIDTH);
		addHelperEntry(ResourceServices.getString(res, "C_DOCBOOK_EDITOR"),
				docBookEditorEntry, "chooseDocBookEditor");

		browserEntry = wm.createTextField(new Identifier("viewer.html"), FIELD_WIDTH);
		addHelperEntry(ResourceServices.getString(res, "C_BROWSER"),
				browserEntry, "chooseBrowser");

		pdfViewerEntry = wm.createTextField(new Identifier("viewer.pdf"), FIELD_WIDTH);
		addHelperEntry(ResourceServices.getString(res, "C_PDF_VIEWER"),
				pdfViewerEntry, "choosePdfViewer");

		rtfViewerEntry = wm.createTextField(new Identifier("viewer.rtf"), FIELD_WIDTH);
		addHelperEntry(ResourceServices.getString(res, "C_RTF_VIEWER"),
				rtfViewerEntry, "chooseRtfViewer");

		psViewerEntry = wm.createTextField(new Identifier("viewer.ps"), FIELD_WIDTH);
		addHelperEntry(ResourceServices.getString(res, "C_PS_VIEWER"),
				psViewerEntry, "choosePsViewer");

		foViewerEntry = wm.createTextField(new Identifier("viewer.fo"), FIELD_WIDTH);
		addHelperEntry(ResourceServices.getString(res, "C_FO_VIEWER"),
				foViewerEntry, "chooseFoViewer");

		wmlViewerEntry = wm.createTextField(new Identifier("viewer.wml"), FIELD_WIDTH);
		addHelperEntry(ResourceServices.getString(res, "C_WML_VIEWER"),
				wmlViewerEntry, "chooseWmlViewer");

		addVerticalGlue();
	}

	public static final String checkBrowserCmd(String browserCmd) {

		if (browserCmd == null || browserCmd.trim().length() == 0) {

			if (JvmServices.isWindows()) {
				browserCmd = "cmd /c ${file}";
			} else {
				browserCmd = "firefox ${url}";
			}
		}

		return browserCmd;
	}

	public static final String checkFoViewerCmd(String foViewerCmd) {

		if (foViewerCmd == null || foViewerCmd.trim().length() == 0) {
			foViewerCmd = "emacs ${file}";
		}

		return foViewerCmd;
	}

	public static final String checkDocBookEditorCmd(String docBookEditorCmd) {

		if (docBookEditorCmd == null || docBookEditorCmd.trim().length() == 0) {

			if (JvmServices.isWindows()) {
				docBookEditorCmd = "cmd /c ${file}";
			} else {
				docBookEditorCmd = "emacs ${file}";
			}
		}

		return docBookEditorCmd;
	}

	public static final String checkPdfViewerCmd(String pdfViewerCmd) {

		if (pdfViewerCmd == null || pdfViewerCmd.trim().length() == 0) {

			if (JvmServices.isWindows()) {
				pdfViewerCmd = "cmd /c ${file}";
			} else {
				pdfViewerCmd = "acroread ${file}";
			}
		}

		return pdfViewerCmd;
	}

	public static final String checkWmlViewerCmd(String wmlViewerCmd) {

		if (wmlViewerCmd == null || wmlViewerCmd.trim().length() == 0) {

			if (JvmServices.isWindows()) {
				wmlViewerCmd = "cmd /c ${file}";
			} else {
				wmlViewerCmd = "oowriter ${file}";
			}
		}

		return wmlViewerCmd;
	}

	public static final String checkPsViewerCmd(String psViewerCmd) {

		if (psViewerCmd == null || psViewerCmd.trim().length() == 0) {

			if (JvmServices.isWindows()) {
				psViewerCmd = "cmd /c ${file}";
			} else {
				psViewerCmd = "gv ${file}";
			}
		}

		return psViewerCmd;
	}

	public static final String checkRtfViewerCmd(String rtfViewerCmd) {

		if (rtfViewerCmd == null || rtfViewerCmd.trim().length() == 0) {

			if (JvmServices.isWindows()) {
				rtfViewerCmd = "cmd /c ${file}";
			} else {
				rtfViewerCmd = "oowriter ${file}";
			}
		}

		return rtfViewerCmd;
	}

	public void actionPerformed(ActionEvent event) {

		try {

			String cmd = event.getActionCommand();

			if (cmd == null) {
				return;
			}

			JFileChooser fc = new JFileChooser();

			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

			int rc = fc.showOpenDialog(this);

			File file;
			if (rc == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
			} else {
				return;
			}

			if (cmd.equals("chooseBrowser")) {
				browserEntry.setText("\"" + file.getCanonicalPath()
						+ "\" ${url}");
			}

			if (cmd.equals("chooseDocBookEditor")) {
				docBookEditorEntry.setText("\"" + file.getCanonicalPath()
						+ "\" ${file}");
			}

			if (cmd.equals("choosePdfViewer")) {
				pdfViewerEntry.setText("\"" + file.getCanonicalPath()
						+ "\" ${file}");
			}

			if (cmd.equals("chooseRtfViewer")) {
				rtfViewerEntry.setText("\"" + file.getCanonicalPath()
						+ "\" ${file}");
			}

			if (cmd.equals("choosePsViewer")) {
				psViewerEntry.setText("\"" + file.getCanonicalPath()
						+ "\" ${file}");
			}

			if (cmd.equals("chooseFoViewer")) {
				foViewerEntry.setText("\"" + file.getCanonicalPath()
						+ "\" ${file}");
			}

			if (cmd.equals("chooseWmlViewer")) {
				wmlViewerEntry.setText("\"" + file.getCanonicalPath()
						+ "\" ${file}");
			}

		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(StaticContext.getDialogOwner(), oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	private void addHelperEntry(String resourceKey, JTextField entry,
			String action) {

		addLabeledComponent(resourceKey, entry);

		JButton button = wm.createButton(new Identifier("button.browse"),
				ResourceServices.getString(res, "C_BROWSE"));
		button.setActionCommand(action);
		button.addActionListener(this);

		addComponent(button, Colspan.CS_2);
		incrRow();
	}

	public String getNamespace() {
		return "";
	}

	@Override
	public Properties getProperties() {

		Properties properties = new Properties();

		String browserCmd = browserEntry.getText();
		properties.setProperty("browser", checkBrowserCmd(browserCmd));

		String docBookEditorCmd = docBookEditorEntry.getText();
		properties.setProperty("docbook-editor",
				checkDocBookEditorCmd(docBookEditorCmd));

		String pdfViewerCmd = pdfViewerEntry.getText();
		properties.setProperty("pdf-viewer", checkPdfViewerCmd(pdfViewerCmd));

		String rtfViewerCmd = rtfViewerEntry.getText();
		properties.setProperty("rtf-viewer", checkRtfViewerCmd(rtfViewerCmd));

		String psViewerCmd = psViewerEntry.getText();
		properties.setProperty("ps-viewer", checkPsViewerCmd(psViewerCmd));

		String foViewerCmd = foViewerEntry.getText();
		properties.setProperty("fo-viewer", checkFoViewerCmd(foViewerCmd));

		String wmlViewerCmd = wmlViewerEntry.getText();
		properties.setProperty("wml-viewer", checkWmlViewerCmd(wmlViewerCmd));

		return properties;
	}

	@Override
	public void setProperties(Properties properties) {

		String buffer;

		buffer = properties.getProperty("browser");
		browserEntry.setText(checkBrowserCmd(buffer));

		buffer = properties.getProperty("docbook-editor");
		docBookEditorEntry.setText(checkDocBookEditorCmd(buffer));

		buffer = properties.getProperty("pdf-viewer");
		pdfViewerEntry.setText(checkPdfViewerCmd(buffer));

		buffer = properties.getProperty("rtf-viewer");
		rtfViewerEntry.setText(checkRtfViewerCmd(buffer));

		buffer = properties.getProperty("ps-viewer");
		psViewerEntry.setText(checkPsViewerCmd(buffer));

		buffer = properties.getProperty("fo-viewer");
		foViewerEntry.setText(checkFoViewerCmd(buffer));

		buffer = properties.getProperty("wml-viewer");
		wmlViewerEntry.setText(checkWmlViewerCmd(buffer));
	}
}
