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
package org.dbdoclet.tidbit.medium.eclipsehelp;

import java.io.File;
import java.text.MessageFormat;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.ZipServices;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class EclipseHelpGenerator extends Generator {

	public EclipseHelpGenerator() {

		super();
		setTarget("dbdoclet.docbook.eclipse");
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

			AbstractDriver driver = project.getDriver(Output.eclipse);
			File baseDir = getBaseDir(driver);
			relocateHtml(baseDir, Output.eclipse);

			messageListener.info("Relocation of images finished.");

			String pluginId = driver.getParameter("eclipse.plugin.id");
			String pluginVersion = driver
					.getParameter("eclipse.plugin.version");
			String fileName = pluginId + "_" + pluginVersion + ".jar";

			File pluginFile = new File(FileServices.appendFileName(
					project.getBuildDirectory(), fileName));

			messageListener.info("Packing " + baseDir.getAbsolutePath()
					+ " into zip archive " + pluginFile.getAbsolutePath()
					+ "...");
			ZipServices.zip(pluginFile, baseDir);

			if (isCanceled() == false) {

				messageListener.info("Creation of zip archive finished.");
				if (pluginFile.exists()) {

					String msg = MessageFormat.format(ResourceServices
							.getString(res, "C_GENERATED_ECLIPSE_PLUGIN"),
							pluginFile.getAbsolutePath());
					InfoBox.show(StaticContext.getDialogOwner(),
							ResourceServices.getString(res, "C_INFORMATION"),
							msg);

				} else {
					String msg = MessageFormat.format(
							ResourceServices.getString(res,
									"C_ERROR_GENERATING_ECLIPSE_PLUGIN"),
							pluginFile.getAbsolutePath());
					ErrorBox.show(StaticContext.getDialogOwner(),
							ResourceServices.getString(res, "C_ERROR"), msg);
				}
			}

		} catch (Throwable oops) {
			messageListener.fatal("EclipseHelpGenerator", oops);
		} finally {
			finished();
		}
	}
}
