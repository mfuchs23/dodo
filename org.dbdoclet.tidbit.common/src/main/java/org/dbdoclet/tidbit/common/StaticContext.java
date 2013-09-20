package org.dbdoclet.tidbit.common;

import java.awt.Frame;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.dbdoclet.Sfv;
import org.dbdoclet.service.FileServices;

public class StaticContext {

	private static Locale locale = Locale.getDefault();
	private static ResourceBundle resourceBundle = ResourceBundle
			.getBundle("org/dbdoclet/tidbit/common/Resources");
	private static Frame dialogOwner;
	private static OsgiRuntime osgiRuntime;

	public static Locale getLocale() {
		return locale;
	}

	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public static void setLocale(Locale locale) {
		StaticContext.locale = locale;
		resourceBundle = ResourceBundle.getBundle(
				"org/dbdoclet/tidbit/common/Resources", locale);
	}

	public static String getHome() {

		File home = getHomeDirectory();
		String path = home.getPath();

		if (path.endsWith(Sfv.FSEP) == false) {
			path += Sfv.FSEP;
		}

		return FileServices.normalizePath(path);
	}

	public static File getHomeDirectory() {
		return new File(System.getProperty("org.dbdoclet.doclet.home"));
	}

	public static Frame getDialogOwner() {
		return dialogOwner;
	}

	public static void setDialogOwner(Frame dialogOwner) {
		StaticContext.dialogOwner = dialogOwner;
	}

	public static String getDate() {

		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM, StaticContext.getLocale());
		return df.format(new Date());
	}

	public static OsgiRuntime getOsgiRuntime() {
		return osgiRuntime;
	}

	public static void setOsgiRuntime(OsgiRuntime osgiRuntime) {
		StaticContext.osgiRuntime = osgiRuntime;
	}
}
