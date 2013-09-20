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
package org.dbdoclet.tidbit.medium.htmlhelp;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class HtmlhelpGenerator extends Generator {

	public HtmlhelpGenerator() throws IOException {

		super();
		setTarget("dbdoclet.docbook.htmlhelp");
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

			// tsFile = File.createTempFile("dbd-htmlhelp", ".ts");

			ExecResult result = executeAntTarget();

			if (buildFailed(result)) {
				return;
			}

			File baseDir = getBaseDir(project.getDriver(Output.htmlhelp));
			String imgDirName = FileServices.appendPath(baseDir, "images");
			File imgDir = new File(imgDirName);
			FileServices.createPath(imgDir);

			messageListener.info("Relocating images..");
			relocateHtml(baseDir, Output.htmlhelp);
			messageListener.info("Relocation of images finished.");

			File indexFile = getIndexFile();

			if (isCanceled() == false && indexFile.exists()) {

				String cmd = getViewerCommand();

				if (cmd == null || cmd.trim().length() == 0) {
					return;
				}

				cmd = processPlaceholders(cmd, indexFile);

				ExecServices.exec(cmd, true);

				String msg = MessageFormat.format(ResourceServices.getString(
						res, "C_INFO_STARTED_COMMAND"), cmd);
				InfoBox.show(StaticContext.getDialogOwner(),
						ResourceServices.getString(res, "C_INFORMATION"), msg,
						3000);
			}

		} catch (Throwable oops) {

			messageListener.fatal("HtmlhelpGenerator", oops);

		} finally {

			if (tsFile != null) {
				tsFile.delete();
			}

			finished();
		}
	}

	private File getIndexFile() throws IOException {

		AbstractDriver driver = project.getDriver(Output.htmlhelp);
		File baseDir = getBaseDir(driver);
		File indexFile = new File(FileServices.appendFileName(baseDir,
				"index.html"));
		return indexFile;
	}
}
