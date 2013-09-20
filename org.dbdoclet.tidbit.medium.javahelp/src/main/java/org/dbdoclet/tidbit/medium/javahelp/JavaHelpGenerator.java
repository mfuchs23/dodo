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
package org.dbdoclet.tidbit.medium.javahelp;

import java.io.File;
import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.Generator;

public class JavaHelpGenerator extends Generator {

	private static Log logger = LogFactory.getLog(JavaHelpGenerator.class);

	public JavaHelpGenerator() {

		super();
		setTarget("dbdoclet.docbook.javahelp");
	}

	@Override
	public void run() {

		File tsFile = null;

		try {

			String path = project.getFileManager().getDocBookFileName();
			File file = new File(path);

			if (file.exists() == false) {

				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_ERROR_FILE_DOESNT_EXIST"), file
						.getAbsolutePath());
				ErrorBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_ERROR"), msg);
				return;
			}

			tsFile = File.createTempFile("dbd-javahelp", ".ts");

			ExecResult result = executeAntTarget();

			if (buildFailed(result)) {
				return;
			}

			File baseDir = getBaseDir(project.getDriver(Output.javahelp));
			relocateHtml(baseDir, Output.javahelp);

			String hsPath = FileServices.appendPath(baseDir, "jhelpset.hs");

			if (hsPath != null) {

				final File hsFile = new File(hsPath);

				if (hsFile.exists() == true
						&& FileServices.newer(hsFile, tsFile)) {

					File jdkHome = project.getJdkHome();
					String javaCmd = FileServices.appendPath(jdkHome, "bin");
					javaCmd = FileServices.appendFileName(javaCmd, "java");

					String jarFileName = FileServices.appendPath(
							StaticContext.getHome(), "jars");
					jarFileName = FileServices.appendFileName(jarFileName,
							"jhrun.jar");

					String[] cmd = new String[4];

					cmd[0] = javaCmd;
					cmd[1] = "-jar";
					cmd[2] = jarFileName;
					cmd[3] = "--file=" + hsFile.getCanonicalPath();

					ExecServices.exec(cmd, null, null, true, this);

				} else {
					logger.warn("Couldn't find helpSet " + hsPath);
				}
			}

		} catch (Throwable oops) {

			messageListener.fatal("JavaHelpGenerator", oops);

		} finally {

			if (tsFile != null) {
				tsFile.delete();
			}

			finished();
		}
	}
}
