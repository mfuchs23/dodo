/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.runner;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.help.MainWindow;
import javax.help.Presentation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.option.BooleanOption;
import org.dbdoclet.option.FileOption;
import org.dbdoclet.option.Option;
import org.dbdoclet.option.OptionException;
import org.dbdoclet.option.OptionList;

public class JavaHelpRunner {

	private static Log logger = LogFactory.getLog(JavaHelpRunner.class);

	public static void main(String[] args) {

		OptionList options = null;

		try {

			Option<?> option;
			options = new OptionList(args);

			option = new BooleanOption("help", "h");
			options.add(option);

			FileOption optFile = new FileOption("file", "f");
			optFile.isRequired(true);
			options.add(optFile);

			if (options.validate() == false) {
				throw new OptionException(options.getError());
			}

			if (options.getFlag("help", false)) {
				usage();
				return;
			}

			logger.info("Starting javahelp...");
			JavaHelpRunner jhrun = new JavaHelpRunner();
			jhrun.execute(optFile.getValue());

		} catch (OptionException oops) {

			if ((options != null) && (options.getFlag("help", false) == false)) {
				System.out.println(oops.getMessage());
			}

			usage();

		} catch (Throwable oops) {

			oops.printStackTrace();
			System.out.println("JAVAHELP FAILED");
		}
	}

	public static void usage() {

		System.out.println("Syntax: java -jar jhrun.jar --file=<HELPSETFILE>");
	}

	public void execute(File hsFile) throws IOException, HelpSetException {

		if (hsFile == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		HelpSet hs;
		URL hsURL = hsFile.toURI().toURL();

		if (hsURL != null) {

			hs = new HelpSet(null, hsURL);

			Presentation presentation = MainWindow.getPresentation(hs,
					"default");
			presentation.setDisplayed(true);
		}

	}

}
