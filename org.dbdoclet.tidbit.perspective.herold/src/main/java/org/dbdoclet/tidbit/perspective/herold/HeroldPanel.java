package org.dbdoclet.tidbit.perspective.herold;

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
import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.filter.ExtensionFileFilter;
import org.dbdoclet.jive.widget.EncodingChooser;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.LanguageListBox;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.common.TidbitExceptionHandler;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.trafo.TrafoConstants;
import org.dbdoclet.trafo.TrafoException;
import org.dbdoclet.trafo.TrafoScriptManager;
import org.dbdoclet.trafo.script.Script;

public class HeroldPanel extends GridPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String HEROLD_NAMESPACE = "herold";

	private final EncodingChooser sourceEncodingChooser;
	private final LanguageListBox languageChooser;
	private JTextField heroldInFileEntry;
	private JTextField heroldOutFileEntry;
	private final JCheckBox useAbsoluteImagePath;
	private final JComboBox documentTypeComboBox;
	private final JComboBox profileComboBox;
	private final JiveFactory jf;
	private final ResourceBundle res;
	private File lastDirectory;

	public HeroldPanel() {

		jf = JiveFactory.getInstance();
		res = StaticContext.getResourceBundle();

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		/*
		 * ftp = new FileSystemPanel(new File(System.getProperty("user.home")));
		 * ftp.setTitle(ResourceServices.getString(res,
		 * "C_CHOOSE_HTML_INPUT_FILES"));
		 * ftp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		 * 
		 * addComponent(ftp, Colspan.CS_1, Rowspan.RS_4, Anchor.NORTHWEST,
		 * Fill.BOTH);
		 * 
		 * setColumnOffset(1);
		 */

		addSeparator(4, "Herold");

		createHeroldSection();

		startSubPanel();

		sourceEncodingChooser = new EncodingChooser();
		sourceEncodingChooser.addActionListener(this);
		// sourceEncodingChooser.addActionListener(jf);
		sourceEncodingChooser.addItemListener(jf);

		addLabeledComponent(jf.createLabel(ResourceServices.getString(res,
				"C_ENCODING_SOURCE")), sourceEncodingChooser);

		incrRow();

		languageChooser = new LanguageListBox(StaticContext.getLocale());
		languageChooser.addActionListener(jf);

		addLabeledComponent(
				jf.createLabel(ResourceServices.getString(res, "C_LANGUAGE")),
				languageChooser);

		incrRow();

		useAbsoluteImagePath = jf.createCheckBox(new Identifier(
				"use.absolute.imagepath"), ResourceServices.getString(res,
				"C_USE_ABSOLUTE_IMAGE_PATH"));
		addComponent(useAbsoluteImagePath);

		incrRow();

		String[] documentElementList = { "article", "book", "part" };
		documentTypeComboBox = jf.createComboBox(
				new Identifier("document.type"), documentElementList);
		documentTypeComboBox.addActionListener(this);

		addLabeledComponent(jf.createLabel(ResourceServices.getString(res,
				"C_DOCUMENT_ELEMENT")), documentTypeComboBox);

		String[] profileList = { "default", "winword" };
		profileComboBox = jf.createComboBox(new Identifier("profile"),
				profileList);
		profileComboBox.addActionListener(this);

		addLabeledComponent(
				jf.createLabel(ResourceServices.getString(res, "C_PROFILE")),
				profileComboBox);

		leaveSubPanel();

		addVerticalGlue();
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		try {

			String cmd = event.getActionCommand();

			if (cmd != null) {

				if (cmd.equals("chooseHeroldInFile")) {
					chooseHeroldInFile();
				}

				if (cmd.equals("chooseHeroldOutFile")) {
					chooseHeroldOutFile();
				}
			}

		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(
					StaticContext.getDialogOwner(), oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	private void chooseHeroldInFile() throws IOException {

		JFileChooser fc = new JFileChooser(lastDirectory);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new ExtensionFileFilter(new String[] {
				"html", "htm" }, "HTML"));

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();
			lastDirectory = file.getParentFile();
			heroldInFileEntry.setText(file.getCanonicalPath());
			heroldOutFileEntry.setText(FileServices.getFileBase(file
					.getCanonicalPath()) + ".xml");
		}
	}

	private void chooseHeroldOutFile() throws IOException {

		JFileChooser fc = new JFileChooser();

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new ExtensionFileFilter(new String[] { "xml",
				"dbk" }, "DocBook XML"));

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();
			heroldOutFileEntry.setText(file.getCanonicalPath());
		}
	}

	private void createHeroldSection() {

		JLabel label;
		JButton button;

		startSubPanel();

		label = jf.createLabel(ResourceServices.getString(res, "C_HTML_FILE"));
		heroldInFileEntry = jf.createTextField(new Identifier("html.file"), 32);
		addLabeledComponent(label, heroldInFileEntry);
		button = jf.createButton(ResourceServices.getString(res, "C_BROWSE"));
		button.setActionCommand("chooseHeroldInFile");
		button.addActionListener(this);
		addComponent(button);
		incrRow();

		label = jf.createLabel(ResourceServices
				.getString(res, "C_DOCBOOK_FILE"));
		heroldOutFileEntry = jf.createTextField(new Identifier("docbook.file"),
				32);
		addLabeledComponent(label, heroldOutFileEntry);
		button = jf.createButton(ResourceServices.getString(res, "C_BROWSE"));
		button.setActionCommand("chooseHeroldOutFile");
		button.addActionListener(this);
		addComponent(button);

		leaveSubPanel();
	}

	private Script createScript(Project project) {

		File scriptFile = getScriptFile(project);

		Script script = new Script();
		TrafoScriptManager mgr = new TrafoScriptManager();

		if (scriptFile.exists()) {
			try {
				mgr.parseScript(script, scriptFile, HEROLD_NAMESPACE);
			} catch (TrafoException oops) {
				oops.printStackTrace();
			}
		}

		return script;
	}

	public File getHeroldInFile() {
		return new File(heroldInFileEntry.getText());
	}

	public File getHeroldOutFile() {
		return new File(heroldOutFileEntry.getText());
	}

	private File getScriptFile(Project project) {

		File file = new File(project.getProjectPath(), "herold.conf");
		return file;
	}

	public void reset() {

		heroldInFileEntry.setText("");
		heroldOutFileEntry.setText("");
	}

	public void setHeroldInFile(File file) {

		if (file != null) {
			heroldInFileEntry.setText(file.getPath());
		}
	}

	public void setHeroldOutFile(File file) {

		if (file != null) {
			heroldOutFileEntry.setText(file.getPath());
		}
	}

	public void syncModel(Project project) {

		if (project == null) {
			return;
		}

		lastDirectory = new File(project.getProjectPath());
		TrafoScriptManager mgr = new TrafoScriptManager();

		Script script = createScript(project);

		script.setSystemParameter(HEROLD_NAMESPACE,
				Script.SYSPARAM_TRANSFORMATION_NAME, "herold");

		script.selectSection(TrafoConstants.SECTION_INPUT);
		script.setTextParameter(TrafoConstants.PARAM_FILE, getHeroldInFile()
				.getPath());
		script.selectSection(TrafoConstants.SECTION_OUTPUT);
		script.setTextParameter(TrafoConstants.PARAM_FILE, getHeroldOutFile()
				.getPath());

		script.selectSection(HEROLD_NAMESPACE, TrafoConstants.SECTION_IMPORT);

		String profileName = (String) profileComboBox.getSelectedItem();
		File profileFile = new File(project.getProjectPath(), "profiles");
		profileFile = new File(profileFile, profileName + ".her");

		script.setTextParameter(TrafoConstants.PARAM_FILE, profileName);

		script.selectSection(HEROLD_NAMESPACE, TrafoConstants.SECTION_HTML);

		script.setTextParameter(TrafoConstants.PARAM_ENCODING,
				sourceEncodingChooser.getEncoding());

		Object value = documentTypeComboBox.getSelectedItem();
		String bookType = "book";

		if (value != null) {
			bookType = value.toString();
		}

		script.selectSection(HEROLD_NAMESPACE, TrafoConstants.SECTION_DOCBOOK);
		script.setTextParameter(TrafoConstants.PARAM_DOCUMENT_ELEMENT,
				bookType);

		script.setTextParameter(TrafoConstants.PARAM_LANGUAGE,
				languageChooser.getSelectedLocale().getLanguage());

		script.setBoolParameter(
				TrafoConstants.PARAM_USE_ABSOLUTE_IMAGE_PATH,
				useAbsoluteImagePath.isSelected());

		try {
			File scriptFile = getScriptFile(project);
			mgr.writeScript(HEROLD_NAMESPACE, script, scriptFile);
		} catch (IOException oops) {
			TidbitExceptionHandler.handleException(
					StaticContext.getDialogOwner(), oops);
		}
	}

	public void syncView(Project project) {

		Script script = createScript(project);

		setHeroldInFile(new File(script.getTextParameter(HEROLD_NAMESPACE,
				TrafoConstants.SECTION_INPUT, TrafoConstants.PARAM_FILE)));
		setHeroldOutFile(new File(script.getTextParameter(HEROLD_NAMESPACE,
				TrafoConstants.SECTION_OUTPUT, TrafoConstants.PARAM_FILE)));

		sourceEncodingChooser.setEncoding(script.getTextParameter(
				HEROLD_NAMESPACE, TrafoConstants.SECTION_HTML,
				TrafoConstants.PARAM_ENCODING, "UTF-8"));

		languageChooser.setSelectedLocale(new Locale(script.getTextParameter(
				HEROLD_NAMESPACE, TrafoConstants.SECTION_DOCBOOK,
				TrafoConstants.PARAM_LANGUAGE, "en")));

		documentTypeComboBox.setSelectedItem(script.getTextParameter(
				HEROLD_NAMESPACE, TrafoConstants.SECTION_DOCBOOK,
				TrafoConstants.PARAM_DOCUMENT_ELEMENT, "book"));

		profileComboBox.setSelectedItem(script.getTextParameter(
				HEROLD_NAMESPACE, TrafoConstants.SECTION_IMPORT,
				TrafoConstants.PARAM_FILE, "default"));

		useAbsoluteImagePath.setSelected(script.getParameterValue(
				HEROLD_NAMESPACE, TrafoConstants.SECTION_DOCBOOK,
				TrafoConstants.PARAM_USE_ABSOLUTE_IMAGE_PATH, false));

	}
}
