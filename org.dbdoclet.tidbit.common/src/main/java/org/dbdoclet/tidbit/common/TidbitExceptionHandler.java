package org.dbdoclet.tidbit.common;

import java.awt.Frame;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.service.ResourceServices;

public class TidbitExceptionHandler {

	public static void handleException(Context context, Throwable oops) {

		Frame dialogOwner = null;

		if (context != null) {
			dialogOwner = context.getDialogOwner();
		}

		handleException(dialogOwner, oops);
	}

	public static void handleException(Frame dialogOwner, Throwable oops) {

		ResourceBundle res = StaticContext.getResourceBundle();

		if (oops instanceof FileNotFoundException) {

			String msg = MessageFormat.format(
					res.getString("C_ERROR_FILE_DOESNT_EXIST"),
					oops.getMessage());
			ErrorBox.show(dialogOwner,
					ResourceServices.getString(res, "C_ERROR"), msg);
			return;
		}

		ExceptionBox ebox = new ExceptionBox(dialogOwner, oops);
		ebox.toFront();
		ebox.setVisible(true);
	}

	public static void handleException(Throwable oops) {

		Frame dialogOwner = null;
		handleException(dialogOwner, oops);
	}
}
