/* 
 * ### Copyright (C) 2005-2007 Michael Fuchs ###
 * ### All Rights Reserved     .             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.medium;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.Sfv;
import org.dbdoclet.io.EndsWithFilter;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.progress.InfoListener;
import org.dbdoclet.progress.MessageListener;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;
import org.dbdoclet.html.parser.HtmlParser;
import org.dbdoclet.html.parser.ParserException;
import org.dbdoclet.html.tokenizer.TokenizerException;
import org.dbdoclet.xiphias.XPathServices;
import org.dbdoclet.xiphias.XmlServices;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Superklasse für die Mediengeneratoren.
 */
public abstract class Generator extends Thread implements InfoListener {

	public static int IDLE = 1;

	public static int RUNNING = 2;
	private final static Log logger = LogFactory.getLog(Generator.class);

	protected final static String FSEP = System.getProperty("file.separator");
	protected final static String PSEP = System.getProperty("path.separator");
	private boolean killed = false;
	private int monitorPort;
	private Process process;
	private int idleStatus = IDLE;
	private Vector<String> targets;
	protected MessageListener messageListener;
	protected Project project;
	protected ResourceBundle res;
	private String viewerCommand;
	private final ArrayList<GenerationListener> listenerList;

	public Generator() {

		targets = new Vector<String>();
		res = StaticContext.getResourceBundle();
		listenerList = new ArrayList<GenerationListener>();
	}

	public static String processPlaceholders(String cmd, File file)
			throws IOException {

		String fileName = "\"" + file.getCanonicalPath() + "\"";

		cmd = StringServices.replace(cmd, "%u", fileName);
		cmd = StringServices.replace(cmd, "%f", fileName);
		cmd = StringServices.replace(cmd, "${f}", fileName);
		cmd = StringServices.replace(cmd, "${u}", fileName);
		cmd = StringServices.replace(cmd, "${file}", fileName);
		cmd = StringServices.replace(cmd, "${url}", fileName);

		cmd = StringServices.replace(cmd, "\"\"", "\"");

		return cmd;
	}

	public void addGenerationListener(GenerationListener listener) {

		if (listenerList.contains(listener) == false) {
			listenerList.add(listener);
		}
	}

	public void addTarget(String target) {

		if (target == null) {
			throw new IllegalArgumentException(
					"The argument target must not be null!");
		}

		targets.add(target);
	}

	public File getDestinationDir() {
		return project.getBuildDirectory();
	}

	public Process getProcess() {
		return process;
	}

	public int getStatus() {
		return idleStatus;
	}

	public String getViewerCommand() {
		return viewerCommand;
	}

	public void fatal(String msg, Throwable oops) {

		if (messageListener != null && msg != null) {
			messageListener.fatal(msg, oops);
		}
	}

	public void error(String msg) {

		if (messageListener != null && msg != null) {
			messageListener.error(msg);
		}
	}

	public void info(String msg) {

		if (messageListener != null && msg != null) {
			messageListener.info(msg);
		}
	}

	public boolean isCanceled() {
		return killed;
	}

	/**
	 * Liefert wahr, falls der Generator DocBook erzeugt und nicht
	 * weiterverarbeitet.
	 * 
	 * @return flag
	 */
	public boolean isSourceCreator() {
		return false;
	}

	public void kill() {

		if (process == null) {
			return;
		}

		try {
			process.exitValue();
		} catch (IllegalThreadStateException oops) {
			process.destroy();
		}

		setStatus(IDLE);
	}

	@Override
	public abstract void run();

	public void setMessageListener(MessageListener listener) {
		this.messageListener = listener;
	}

	public void setMonitorPort(int monitorPort) {
		this.monitorPort = monitorPort;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setTarget(String target) {

		if (target == null) {
			throw new IllegalArgumentException(
					"The argument target must not be null!");
		}

		targets = new Vector<String>();
		addTarget(target);
	}

	public void setViewerCommand(String viewerCommand) {
		this.viewerCommand = viewerCommand;
	}

	private int getMonitorPort() {
		return monitorPort;
	}

	private void relocateRelativeHtmlImages(File htmlFile, Output output)
			throws IOException, ParserException, TokenizerException,
			ParserConfigurationException, SAXException {

		File docBookFileDir = new File(project.getFileManager()
				.getDocBookFileDirName());
		AbstractDriver driver = project.getDriver(output);
		String imgSrcPath = driver.getParameter(Constants.PARAM_IMG_SRC_PATH,
				"");

		ArrayList<Node> imgNodeList = new ArrayList<Node>();

		if (output == Output.webhelp) {
			Document doc = XmlServices.loadDocument(htmlFile);
			imgNodeList = XPathServices.getNodes(doc, "xhtml", Sfv.NS_XHTML,
					"//xhtml:img");
		} else {
			HtmlParser parser = new HtmlParser();
			Document doc = parser.parseDocument(htmlFile);
			imgNodeList = XPathServices.getNodes(doc, "//img");
		}

		for (Node node : imgNodeList) {

			Element img = (Element) node;
			String srcAttr = img.getAttribute("src");

			if (srcAttr == null || srcAttr.trim().length() == 0) {
				continue;
			}

			String toFileName = FileServices.appendPath(
					htmlFile.getParentFile(), srcAttr);
			File toFile = new File(toFileName);

			if (toFile.exists() == true) {
				continue;
			}

			File fromFile;

			if (FileServices.isAbsolutePath(srcAttr) == true) {
				continue;
			}

			if (imgSrcPath.trim().length() > 0) {
				srcAttr = FileServices.removeParentPath(srcAttr, imgSrcPath);
			}

			String path = FileServices.appendFileName(docBookFileDir, srcAttr);
			fromFile = new File(path);

			if (fromFile.exists() == false) {
				continue;
			}

			info("Relocating image " + srcAttr + "...");
			FileServices.copyFileToFile(fromFile, toFile);
		}
	}

	protected boolean buildFailed(ExecResult result) {

		if (result == null) {
			return true;
		}

		if (result.getOutput() != null
				&& result.getOutput().trim().endsWith("BUILD FAILED")) {
			return true;

		}

		if (result.getExitCode() > 0) {
			return true;
		}

		return false;
	}

	protected ExecResult executeAntTarget() {

		if (targets == null) {
			throw new IllegalStateException(
					"The fields target must not be null!");
		}

		ExecResult result = new ExecResult();
		result.setExitCode(0);

		File file = project.getProjectFile();
		File jdkHome = project.getJdkHome();
		String memory = project.getJvmMaxMemory();

		ResourceBundle res = StaticContext.getResourceBundle();

		if (file == null) {

			error(ResourceServices.getString(res,
					"C_ERROR_PROJECT_FILE_IS_NOT_DEFINED"));
			result.setOutput(new StringBuffer(ResourceServices.getString(res,
					"C_ERROR_PROJECT_FILE_IS_NOT_DEFINED")));
			return result;
		}

		try {

			String javaCmd = FileServices.appendPath(jdkHome, "bin");
			javaCmd = FileServices.appendFileName(javaCmd, "java");

			String jarFileName = FileServices.appendPath(
					StaticContext.getHome(), "jars");
			jarFileName = FileServices.appendFileName(jarFileName, "arun.jar");

			if (memory == null || memory.trim().length() == 0) {
				memory = "2048";
			}

			String[] cmd = new String[targets.size() + 6];

			cmd[0] = javaCmd;
			cmd[1] = "-Xmx" + memory + "m";

			cmd[2] = "-jar";
			cmd[3] = jarFileName;
			cmd[4] = "--file=" + file.getCanonicalPath();
			cmd[5] = "--port=" + getMonitorPort();

			for (int i = 0; i < targets.size(); i++) {

				String target = targets.get(i);
				cmd[i + 6] = "--target=" + target;
			}

			if (getStatus() == RUNNING) {

				result.setOutput(new StringBuffer(ResourceServices.getString(
						res, "C_ERROR_GENERATION_ALREADY_STARTED")));
				return result;
			}

			setStatus(RUNNING);

			ExecServices.exec(javaCmd + " -version", this);

			StringBuilder cmdline = new StringBuilder();

			for (String token : cmd) {
				cmdline.append(token);
				cmdline.append(' ');
			}

			info(cmdline.toString());

			result = ExecServices.exec(cmd, null, null, true, this);

			killed = false;
			process = result.getProcess();

			if (process == null) {

				StringBuilder buffer = new StringBuilder();

				buffer.append("Execution of generator failed: ");
				buffer.append("\nOutput: ");
				buffer.append(result.getOutput());
				result.setExitCode(2);

				if (result.getThrowable() != null) {

					buffer.append("\nThrowable: ");
					buffer.append(result.getStackTrace());
				}

				error(buffer.toString());

			} else {

				try {
					process.waitFor();
				} catch (InterruptedException oops) {
					fatal("Generator was interrupted: ", oops);
				}
			}

			String output = result.getOutput();

			if (output != null && output.trim().endsWith("BUILD FAILED")) {
				result.setExitCode(1);
			}

			String msg = MessageFormat.format(
					ResourceServices.getString(res, "C_GENERATION_FINISHED"),
					StaticContext.getDate());
			info(msg);

		} catch (Throwable oops) {

			fatal("Unexcepted exception while execting generator: ", oops);

			String output = oops.getMessage();

			if (output == null) {
				output = "";
			}

			result.setOutput(new StringBuffer(output));
			result.setThrowable(oops);
			return result;

		} finally {

			setStatus(IDLE);
		}

		if (buildFailed(result)) {

			String buffer = result.getOutput() + "\n" + result.getStackTrace();

			if (buffer.length() > 4096) {
				buffer = buffer.substring(0, 4093) + "...";
			}

			ErrorBox.show(ResourceServices.getString(res, "C_ERROR"),
					buffer.trim());
		}

		return result;
	}

	protected void finished() {

		for (GenerationListener listener : listenerList) {
			listener.generationFinished(0);
		}
	}

	protected File getBaseDir(AbstractDriver driver) {

		String baseDirName = driver.getParameter("base.dir", "./build/");

		if (baseDirName == null || baseDirName.trim().length() == 0) {
			baseDirName = "./build/";
		}

		if (FileServices.isAbsolutePath(baseDirName) == false) {

			String path = project.getFileManager().getDocBookFileDirName();
			baseDirName = FileServices.appendPath(path, baseDirName);
		}

		return new File(baseDirName);
	}

	protected void relocateHtml(File baseDir, Output output) {

		for (File htmlFile : baseDir.listFiles(new EndsWithFilter(".html",
				".htm"))) {

			try {
				relocateRelativeHtmlImages(htmlFile, output);
			} catch (TokenizerException oops) {
				logger.error("Tokenizer error\n" + htmlFile.getAbsolutePath()
						+ "\n" + oops.getMessage());
			} catch (Exception oops) {

				oops.printStackTrace();
			}
		}
	}

	protected void setStatus(int status) {
		this.idleStatus = status;
	}
}
