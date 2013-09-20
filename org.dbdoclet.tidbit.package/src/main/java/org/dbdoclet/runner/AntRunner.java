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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.dbdoclet.antol.AntBuildListener;
import org.dbdoclet.option.BooleanOption;
import org.dbdoclet.option.FileOption;
import org.dbdoclet.option.IntegerOption;
import org.dbdoclet.option.Option;
import org.dbdoclet.option.OptionException;
import org.dbdoclet.option.OptionList;
import org.dbdoclet.option.StringOption;

public class AntRunner {

	private static Log logger = LogFactory.getLog(AntRunner.class);
	private static MonitorThread monitorThread;

	public static void main(String[] args) {

		int rc = 0;

		OptionList options = null;

		try {

			Option<?> option;
			options = new OptionList(args);

			option = new BooleanOption("help", "h");
			options.add(option);

			FileOption optFile = new FileOption("file", "f");
			optFile.isRequired(true);
			options.add(optFile);

			StringOption optTarget = new StringOption("target", "t");
			optTarget.isRequired(true);
			optTarget.isUnique(false);
			options.add(optTarget);

			IntegerOption optPort = new IntegerOption("port", "p");
			optPort.isRequired(true);
			options.add(optPort);

			if (options.validate() == false) {
				throw new OptionException(options.getError());
			}

			if (options.getFlag("help", false)) {
				usage();
				return;
			}

			AntRunner arun = new AntRunner();

			monitorThread = arun.startMonitorThread(optPort.getValue());
			arun.execute(optFile.getValue(), optTarget.getValues());

		} catch (OptionException oops) {

			if ((options != null) && (options.getFlag("help", false) == false)) {
				System.out.println(oops.getMessage());
			}

			usage();
			rc = 1;

		} catch (Throwable oops) {

			oops.printStackTrace();
			System.out.println("BUILD FAILED");
			rc = 2;

		} finally {

			if (monitorThread != null) {
				monitorThread.halt();
				monitorThread.interrupt();
			}

			System.exit(rc);
		}
	}

	private MonitorThread startMonitorThread(int port)
			throws UnknownHostException, IOException {

		if (port > 0) {

			MonitorThread t = new MonitorThread(port);
			t.start();
			return t;
		}

		return null;
	}

	public static void usage() {

		System.out.println("Syntax: ");
	}

	public void execute(File file, ArrayList<String> targets)
			throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		String fileName = file.getCanonicalPath();

		Project project = new Project();

		AntBuildListener listener = new AntBuildListener();
		listener.setVerbose(false);
		project.addBuildListener(listener);

		project.init();
		project.setUserProperty("ant.file", file.getAbsolutePath());

		logger.debug("fileName='" + fileName + "'.");

		ProjectHelper helper = ProjectHelper.getProjectHelper();
		helper.parse(project, file);

		for (Iterator<String> i = targets.iterator(); i.hasNext();) {

			String target = i.next();
			project.executeTarget(target);
		}
	}

}

class MonitorThread extends Thread {

	private static Log logger = LogFactory.getLog(MonitorThread.class);

	private boolean halt = false;
	private final Socket socket;
	private final PrintWriter writer;

	public MonitorThread(int port) throws UnknownHostException, IOException {

		socket = new Socket("127.0.0.1", port);
		writer = new PrintWriter(new OutputStreamWriter(
				socket.getOutputStream()));
	}

	@Override
	public void run() {

		Runtime runtime = Runtime.getRuntime();

		while (halt == false) {

			try {

				sleep(6000);
				long totalMemory = runtime.totalMemory();
				long freeMemory = runtime.freeMemory();

				writer.println(String.format("memory-used, %d", totalMemory
						- freeMemory));
				writer.flush();

			} catch (Exception oops) {
				logger.error(oops.getMessage());
			}
		}

		try {

			writer.close();
			socket.close();

		} catch (IOException oops) {
			oops.printStackTrace();
		}
	}

	public void halt() {
		halt = true;
	}
}
