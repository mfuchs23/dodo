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
package org.dbdoclet.tidbit.medium.docbook;

import java.io.File;
import java.text.MessageFormat;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.Generator;

public class DocBookGenerator extends Generator {

	public DocBookGenerator() {

		super();
		setTarget("dbdoclet.docbook");
	}

	@Override
	public void run() {

		try {

			ExecResult result = executeAntTarget();

			if (buildFailed(result)) {
				// ErrorBox.show(ResourceServices.getString(res,"C_ERROR"),
				// result.getOutput());
				return;
			}

			String path = FileServices.appendFileName(getDestinationDir(),
					"Reference.xml");
			path = FileServices.normalizePath(path);

			File pdfFile = new File(path);

			if (isCanceled() == false && pdfFile.exists()) {

				String cmd = getViewerCommand();

				if (cmd == null || cmd.trim().length() == 0) {

					ErrorBox.show(ResourceServices.getString(res, "C_ERROR"),
							ResourceServices.getString(res,
									"C_ERROR_NO_DOCBOOK_VIEWER"));
					return;
				}

				cmd = processPlaceholders(cmd, pdfFile);

				ExecServices.exec(cmd, true);

				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_INFO_STARTED_COMMAND"), cmd);
				InfoBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_INFORMATION"), msg,
						3000);
			}

		} catch (Throwable oops) {

			messageListener.fatal("DocBookGenerator", oops);

		} finally {

			finished();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSourceCreator() {
		return true;
	}
}
