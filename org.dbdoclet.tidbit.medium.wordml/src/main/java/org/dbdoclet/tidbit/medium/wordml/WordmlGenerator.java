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
package org.dbdoclet.tidbit.medium.wordml;

import java.io.File;
import java.text.MessageFormat;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.Generator;

public class WordmlGenerator extends Generator {

	public WordmlGenerator(String cmd) {

		super();
		setTarget("dbdoclet.docbook.wordml");
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

			path = project.getFileManager().getDocBookFileBase()
					+ "-WordML.xml";
			File wordFile = new File(path);

			// System.out.println("WORD File = " + wordFile.getAbsolutePath());

			if (isCanceled() == false && wordFile.exists()) {

				String viewerCommand = getViewerCommand();

				if (viewerCommand == null || viewerCommand.trim().length() == 0) {

					ErrorBox.show(ResourceServices.getString(res, "C_ERROR"),
							ResourceServices.getString(res,
									"C_ERROR_NO_WML_VIEWER"));
					return;
				}

				viewerCommand = processPlaceholders(viewerCommand, wordFile);

				ExecServices.exec(viewerCommand, true);

				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_INFO_STARTED_COMMAND"), viewerCommand);
				InfoBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_INFORMATION"), msg,
						3000);
			}

		} catch (Throwable oops) {

			messageListener.fatal("WordGenerator", oops);

		} finally {

			finished();
		}
	}
}
