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
package org.dbdoclet.tidbit.medium.rtf;

import java.io.File;
import java.text.MessageFormat;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.Generator;

public class RtfGenerator extends Generator {

	public RtfGenerator() {

		super();
		setTarget("dbdoclet.docbook.rtf");
	}

	@Override
	public void run() {

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

			ExecResult result = executeAntTarget();

			if (buildFailed(result)) {
				return;
			}

			File rtfFile = new File(project.getFileManager()
					.getDocBookFileBase() + ".rtf");

			// System.out.println("RTF File = " + rtfFile.getAbsolutePath());

			if (isCanceled() == false && rtfFile.exists()) {

				String cmd = getViewerCommand();

				if (cmd == null || cmd.trim().length() == 0) {

					ErrorBox.show(ResourceServices.getString(res, "C_ERROR"),
							ResourceServices.getString(res,
									"C_ERROR_NO_RTF_VIEWER"));
					return;
				}

				cmd = processPlaceholders(cmd, rtfFile);

				ExecServices.exec(cmd, true);

				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_INFO_STARTED_COMMAND"), cmd);
				InfoBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_INFORMATION"), msg,
						3000);
			}

		} catch (Throwable oops) {

			messageListener.fatal("RtfGenerator", oops);

		} finally {

			finished();
		}
	}
}
