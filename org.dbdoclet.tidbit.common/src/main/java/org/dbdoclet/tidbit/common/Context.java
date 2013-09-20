/* 
 * ### Copyright (C) 2007-2011 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.common;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.model.LabelItem;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.JvmServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.tidbit.project.Project;

public class Context {

	private Project project;
	private Settings settings;
	private String tidbitUserPath;

	public Context() {

		File file;
		String fileName;

		try {

			tidbitUserPath = System.getProperty("user.home");
			tidbitUserPath = FileServices.appendPath(tidbitUserPath,
					".tidbit.d");

			FileServices.createPath(tidbitUserPath);

			Properties defProperties = new Properties();

			if (JvmServices.isWindows()) {

				defProperties.put("browser", "cmd /c %u");
				defProperties.put("fo-viewer", "cmd /c \"%f\"");
				defProperties.put("pdf-viewer", "cmd /c \"%f\"");
				defProperties.put("ps-viewer", "cmd /c \"%f\"");
				defProperties.put("rtf-viewer", "cmd /c \"%f\"");
				defProperties.put("wml-viewer", "cmd /c \"%f\"");

			} else {

				defProperties.put("browser", "firefox %u");
				defProperties.put("fo-viewer", "gnome-open \"%f\"");
				defProperties.put("pdf-viewer", "gnome-open \"%f\"");
				defProperties.put("ps-viewer", "gnome-open \"%f\"");
				defProperties.put("rtf-viewer", "oowriter \"%f\"");
				defProperties.put("wml-viewer", "oowriter \"%f\"");
			}

			fileName = FileServices.appendFileName(tidbitUserPath,
					"tidbit.properties");
			file = new File(fileName);
			settings = new Settings(file, defProperties);

			if (file.exists()) {
				settings.load();
			}

		} catch (IOException oops) {

			ExceptionBox ebox = new ExceptionBox(oops);
			ebox.setVisible(true);
		}
	}

	public ArrayList<LabelItem> getAvailableJdkList() {

		if (settings == null) {
			throw new IllegalStateException(
					"The field settings must not be null!");
		}

		String name;
		String value;
		LabelItem item;

		String namespace = "java.jdk.";

		ArrayList<LabelItem> itemList = new ArrayList<LabelItem>();
		Enumeration<?> nameList = settings.propertyNames();

		while (nameList.hasMoreElements()) {

			name = (String) nameList.nextElement();

			if (name.startsWith(namespace)) {

				value = settings.getProperty(name);

				name = StringServices.cutPrefix(name, namespace);

				item = new LabelItem(name, value);
				itemList.add(item);
			}
		}

		return itemList;
	}

	public Frame getDialogOwner() {
		return StaticContext.getDialogOwner();
	}

	public Project getProject() {
		return project;
	}

	public Settings getSettings() {
		return settings;
	}

	public String getXsltPath() {

		String path = FileServices.appendPath(StaticContext.getHome(), "xslt");
		return path;
	}

	public ResourceBundle getResourceBundle() {
		return StaticContext.getResourceBundle();
	}

	public String getHome() {
		return StaticContext.getHome();
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Locale getLocale() {
		return StaticContext.getLocale();
	}
}
