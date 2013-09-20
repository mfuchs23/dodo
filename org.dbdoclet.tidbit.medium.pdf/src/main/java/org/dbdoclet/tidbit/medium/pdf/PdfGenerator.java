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
package org.dbdoclet.tidbit.medium.pdf;

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
import org.dbdoclet.tidbit.project.FileManager;

public class PdfGenerator extends Generator {

	public PdfGenerator() {

		super();
		setTarget("dbdoclet.docbook.pdf");
	}

	@Override
	public void run() {

		try {

			FileManager fileManager = project.getFileManager();
			String path = fileManager.getDocBookFileName();

			File file = new File(path);

			if (file.exists() == false) {

				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_ERROR_FILE_DOESNT_EXIST"), file
						.getAbsolutePath());
				ErrorBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_ERROR"), msg);
				return;
			}

			for (File artefact : fileManager.getPdfArtifacts()) {
				FileServices.delete(artefact);
			}

			ExecResult result = executeAntTarget();

			if (buildFailed(result)) {
				return;
			}

			File pdfFile = fileManager.getPdfFile();

			if (isCanceled() == false && pdfFile.exists()) {

				String viewerCommand = getViewerCommand();

				if (viewerCommand == null || viewerCommand.trim().length() == 0) {

					ErrorBox.show(ResourceServices.getString(res, "C_ERROR"),
							ResourceServices.getString(res,
									"C_ERROR_NO_PDF_VIEWER"));
					return;
				}

				viewerCommand = processPlaceholders(viewerCommand, pdfFile);

				ExecServices.exec(viewerCommand, true);

				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_INFO_STARTED_COMMAND"), viewerCommand);
				InfoBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_INFORMATION"), msg,
						3000);
			}

		} catch (Throwable oops) {

			messageListener.fatal("EclipseHelpGenerator", oops);

		} finally {

			finished();
		}
	}
}
