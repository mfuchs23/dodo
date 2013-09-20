package org.dbdoclet.tidbit.medium.herold;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.jive.dialog.StepProgressDialog;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressVetoListener;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.common.TidbitExceptionHandler;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.trafo.TrafoConstants;
import org.dbdoclet.trafo.TrafoResult;
import org.dbdoclet.trafo.TrafoScriptManager;
import org.dbdoclet.trafo.TrafoService;
import org.dbdoclet.trafo.script.Script;

public class HeroldAction extends AbstractTidbitAction implements
		ProgressVetoListener {

	private static final long serialVersionUID = 1L;
	private int max;
	private StepProgressDialog spd;
	private long startTime;

	public HeroldAction(Application application) {
		super(application, "DocBook", new ImageIcon(
				ResourceServices.getResourceAsUrl("/images/html24.png",
						HeroldAction.class.getClassLoader())));
	}

	@Override
	public void action(ActionEvent e) {

		final TrafoService trafo = application.getTrafoService("html2docbook");

		if (trafo != null) {

			final Context context = application.getContext();
			final Project project = application.getProject();

			application.getPerspective().syncModel(project);

			ResourceBundle res = context.getResourceBundle();

			spd = new StepProgressDialog(context.getDialogOwner(),
					res.getString("C_HTML_TO_DOCBOOK_TRANSFORMATION"));
			spd.setVisible(true);

			Thread t = new Thread() {

				@Override
				public void run() {

					try {

						Script script = new Script();
						TrafoScriptManager mgr = new TrafoScriptManager();

						File scriptFile = new File(project.getProjectPath(),
								"herold.conf");

						if (scriptFile.exists()) {
							mgr.parseScript(script, scriptFile);
						}

						FileInputStream fis = new FileInputStream(
								script.getTextParameter(
										TrafoConstants.SECTION_INPUT,
										TrafoConstants.PARAM_FILE, null));

						trafo.setInputStream(fis);

						File docBookFile = new File(script.getTextParameter(
								TrafoConstants.SECTION_OUTPUT,
								TrafoConstants.PARAM_FILE, null));

						FileOutputStream fos = new FileOutputStream(docBookFile);

						trafo.setOutputStream(fos);

						String profile = script.getParameterValue(
								TrafoConstants.SECTION_IMPORT,
								TrafoConstants.PARAM_FILE, "default");

						if (profile != null) {

							String fileName = FileServices.appendPath(
									project.getHomeDir(), "profiles");
							fileName = FileServices.appendFileName(fileName,
									profile + ".her");
							File profileFile = new File(fileName);

							if (profileFile.exists()) {
								script = mgr.parseScript(profileFile);
								mgr.parseScript(script, scriptFile, "herold");
								mgr.mergeNamespaces(script);
							}
						}

						trafo.addProgressListener(spd);
						TrafoResult result = trafo.transform(script);

						if (result.getThrowable() != null) {

							Throwable oops = result.getThrowable();
							String buffer = result.getBuffer();

							if (buffer != null && buffer.trim().length() > 0) {

								ErrorBox.show("ERROR", result.getBuffer());

							} else {

								ExceptionBox ebox = new ExceptionBox(
										application.getContext()
												.getDialogOwner(), oops);
								ebox.setVisible(true);
								ebox.toFront();
							}
						}

						spd.setVisible(false);

						if (spd.isCanceled() == false && docBookFile.exists()) {

							Settings settings = context.getSettings();
							String cmd = settings.getProperty("docbook-editor");

							ResourceBundle res = context.getResourceBundle();

							if (cmd == null || cmd.trim().length() == 0) {
								ErrorBox.show(ResourceServices.getString(res,
										"C_ERROR"), ResourceServices.getString(
										res, "C_ERROR_NO_DOCBOOK_VIEWER"));
								return;
							}

							cmd = Generator.processPlaceholders(cmd,
									docBookFile);
							ExecServices.exec(cmd, true);
							String msg = MessageFormat.format(ResourceServices
									.getString(res, "C_INFO_STARTED_COMMAND"),
									cmd);
							InfoBox.show(context.getDialogOwner(),
									ResourceServices.getString(res,
											"C_INFORMATION"), msg, 3000);

						}

					} catch (Throwable oops) {

						TidbitExceptionHandler.handleException(context, oops);
					}
				}
			};

			t.start();
		}
	}

	public int getProgressMaximum() {
		return max;
	}

	public long getProgressStartTime() {
		return startTime;
	}

	public boolean isCanceled() {
		return false;
	}

	public boolean progress(ProgressEvent event) {

		spd.setText(event.getAction());
		spd.setStepCount(event.getIndex());

		return false;
	}

	public int progressIncr() {
		return 0;
	}

	public void setProgressMaximum(int max) {
		spd.setProgressMaximum(max);
	}

	public void setProgressStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean veto(ProgressEvent event) {
		return false;
	}
}
