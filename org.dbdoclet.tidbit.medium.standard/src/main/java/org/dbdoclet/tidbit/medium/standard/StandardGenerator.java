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
package org.dbdoclet.tidbit.medium.standard;

import java.io.File;
import java.text.MessageFormat;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.jive.dialog.ProgressBox;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.project.FileManager;

public class StandardGenerator extends Generator {

	public StandardGenerator() {

		super();
		setTarget("dbdoclet.standard");
	}

	@Override
	public boolean isSourceCreator() {
		return true;
	}

	@Override
	public void run() {

		try {

			ProgressBox pbox = new ProgressBox(StaticContext.getDialogOwner(),
					ResourceServices.getString(res, "C_DELETING_FILES"), null,
					false);
			pbox.setVisible(true);

			FileManager fileManager = project.getFileManager();

			for (File artefact : fileManager.getJavadocArtifacts()) {
				FileServices.delete(artefact, pbox,
						ResourceServices.getString(res, "C_DELETING_FILE"));
			}

			pbox.setVisible(false);

			ExecResult result = executeAntTarget();

			if (buildFailed(result)) {
				return;
			}

			String indexPath = FileServices.appendPath(getDestinationDir(),
					"standard");
			indexPath = FileServices.appendFileName(indexPath, "index.html");
			File indexFile = new File(indexPath);

			if (indexFile.exists()) {

				String cmd = getViewerCommand();

				if (cmd == null || cmd.trim().length() == 0) {

					ErrorBox.show(ResourceServices.getString(res, "C_ERROR"),
							ResourceServices.getString(res,
									"C_ERROR_NO_BROWSER"));
					return;
				}

				cmd = StringServices.replace(cmd, "%u", indexPath);
				cmd = StringServices.replace(cmd, "${url}", indexPath);
				cmd = StringServices.replace(cmd, "%f", indexPath);
				cmd = StringServices.replace(cmd, "${file}", indexPath);

				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_INFO_STARTED_COMMAND"), cmd);
				InfoBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_INFORMATION"), msg,
						3000);

				ExecServices.exec(cmd, true);
			}

		} catch (Throwable oops) {

			messageListener.fatal("StandardGenerator", oops);

		} finally {

			finished();
		}
	}
}
