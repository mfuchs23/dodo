/* 
 * ### Copyright (C) 2005-2008 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.dialog.ContinueDialog;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.text.IConsole;
import org.dbdoclet.progress.MessageListener;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.Tidbit;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.GenerationListener;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.FileManager;
import org.dbdoclet.tidbit.project.Project;

public class ActionGenerate extends AbstractTidbitAction implements
		MessageListener, GenerationListener {

	public static final int GENERATE = 0;
	public static final int GENERATE_DOCBOOK_XML = 1;
	public static final int GENERATE_DOCBOOK_SGML = 2;
	public static final int GENERATE_ECLIPSEHELP = 14;
	public static final int GENERATE_FO = 8;
	public static final int GENERATE_HTML = 3;
	public static final int GENERATE_JAVAHELP = 6;
	public static final int GENERATE_MIF = 11;
	public static final int GENERATE_ODT = 13;
	public static final int GENERATE_PDF = 4;
	public static final int GENERATE_POSTSCRIPT = 5;
	public static final int GENERATE_RTF = 9;
	public static final int GENERATE_TEXT = 10;
	public static final int GENERATE_WORDML = 7;
	public static final int GENERATE_XMI = 12;
	public static final int TRANSFORM_PDF = 20;
	private static Generator generator = null;

	private static Log logger = LogFactory.getLog(ActionGenerate.class);

	private static final long serialVersionUID = 1L;
	private final IConsole console;

	private final MediumService service;
	private final Tidbit tidbit;

	public ActionGenerate(Tidbit tidbit, IConsole console, MediumService service) {

		super(tidbit, service.getName(), service.getIcon());
		this.tidbit = tidbit;

		this.console = console;
		this.service = service;
		
		putValue(SHORT_DESCRIPTION, "Generate " + service.getName());
		putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F5));
	}

	@Override
	public void action(ActionEvent event) throws Exception {

		Project project = application.getProject();
		Context context = application.getContext();

		if (project == null) {

			ActionSaveProject action = new ActionSaveProject(tidbit, "save",
					null);

			if (action.save() == false) {
				ErrorBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_ERROR"),
						res.getString("C_ERROR_PROJECT_SAVE_FAILED"));
				return;
			}
		}

		FileManager fm = project.getFileManager();

		if (generator != null && generator.isAlive()
				&& generator.getStatus() == Generator.RUNNING) {

			ContinueDialog dlg = new ContinueDialog(
					StaticContext.getDialogOwner(), ResourceServices.getString(
							res, "C_TITLE_TERMINATE_GENERATION"),
					ResourceServices.getString(res,
							"C_QUESTION_TERMINATE_GENERATION"));

			dlg.setContinueButtonText(ResourceServices.getString(res,
					"C_TERMINATE_GENERATION"));
			dlg.setCancelButtonText(ResourceServices.getString(res,
					"C_DONT_TERMINATE_GENERATION"));

			dlg.setVisible(true);

			if (dlg.doContinue() == false) {
				return;
			}

			generator.kill();
			generator = null;
		}

		ActionSaveProject action = new ActionSaveProject(tidbit, "save", null);

		if (action.save() == false) {

			ErrorBox.show(StaticContext.getDialogOwner(), ResourceServices
					.getString(res, "C_ERROR"), ResourceServices.getString(res,
					"C_ERROR_PROJECT_SAVE_FAILED"));
			return;
		}

		Perspective perspective = tidbit.getPerspective();

		if (perspective.isReadyForUse()) {

			generator = service.getGenerator(context.getSettings());
			generator.setProject(project);
			generator.setMonitorPort(perspective.getMonitorPort());
			generator.setMessageListener(this);
			generator.addGenerationListener(this);

		} else {

			String msg = MessageFormat.format(ResourceServices.getString(res,
					"C_ERROR_SERVICE_NOT_READY_FOR_USE"), service.getName());
			ErrorBox.show(StaticContext.getDialogOwner(),
					ResourceServices.getString(res, "C_ERROR"), msg);
			return;
		}

		if (generator.isSourceCreator() == false) {

			File docBookFile = new File(fm.getDocBookFileName());

			if (docBookFile.exists() == false) {
				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_ERROR_DOCBOOK_FILE_DOES_NOT_EXIST"),
						docBookFile.getAbsolutePath());
				ErrorBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_ERROR"), msg);
				return;
			}

			if (docBookFile.isFile() == false) {
				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_ERROR_DOCBOOK_FILE_NOT_REGULAR"), docBookFile
						.getAbsolutePath());
				ErrorBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_ERROR"), msg);
				return;
			}
		}

		console.clear();
		String msg = MessageFormat.format(
				ResourceServices.getString(res, "C_GENERATION_STARTED"),
				StaticContext.getDate());
		console.section(msg);
		application.addGenerator(generator);
		generator.start();
	}

	@Override
	public void debug(String msg) {
		logger.debug(msg);
	}

	@Override
	public void error(String msg) {

		if (console != null) {
			console.error(msg);
		}

		ErrorBox.show(ResourceServices.getString(res, "C_ERROR"), msg);
	}

	@Override
	public void fatal(String msg, Throwable oops) {

		if (console != null) {
			console.exception(oops);
		}

		ExceptionBox ebox = new ExceptionBox(StaticContext.getDialogOwner(),
				oops);
		ebox.setVisible(true);
		ebox.toFront();
	}

	@Override
	public void finished() {
		application.setDefaultCursor();
		logger.debug("Generation finished");
	}

	@Override
	public void generationFinished(int rc) {

	}

	@Override
	public void info(String msg) {

		if (console != null) {
			console.info(msg);
		}
	}
}
