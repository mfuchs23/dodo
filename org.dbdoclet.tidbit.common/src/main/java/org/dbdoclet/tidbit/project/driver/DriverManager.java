package org.dbdoclet.tidbit.project.driver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.dbdoclet.service.FileServices;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.project.ConfigurationException;
import org.xml.sax.SAXException;

public class DriverManager {

	private final File driverDirectory;
	private final File homeDir;
	private final HashMap<Output, AbstractDriver> driverMap;
	private final HashMap<Output, File> driverTemplateFileMap;

	public DriverManager(File homeDir, File driverDirectory) {

		if (homeDir == null || homeDir.exists() == false
				|| homeDir.isDirectory() == false) {
			throw new IllegalArgumentException(String.format(
					"Invalid home directory %s !", homeDir));
		}

		if (driverDirectory == null) {
			throw new IllegalArgumentException(String.format(
					"Invalid driver directory %s !", driverDirectory));
		}

		if (driverDirectory.exists() == false) {

			driverDirectory.mkdirs();

		} else if (driverDirectory.isDirectory() == false) {

			throw new IllegalArgumentException(String.format(
					"Invalid driver directory %s !", driverDirectory));
		}

		this.homeDir = homeDir;
		this.driverDirectory = driverDirectory;

		driverMap = new HashMap<Output, AbstractDriver>();
		driverTemplateFileMap = new HashMap<Output, File>();
	}

	public void createDriver(Output output) throws ConfigurationException,
			ParserConfigurationException, SAXException, IOException {

		File driverFile = getDriverFile(output);
		File driverTemplateFile = getDriverTemplateFile(output);

		if (driverFile == null || driverFile.exists() == false) {
			driverFile = driverTemplateFile;
		}

		AbstractDriver driver = null;

		switch (output) {

		case eclipse:
			driver = new HtmlDriver(homeDir, driverFile,
					getDriverParamFile(Output.html));
			break;

		case epub:
			driver = new HtmlDriver(homeDir, driverFile,
					getDriverParamFile(Output.xhtml));
			break;

		case fo:
			driver = new FoDriver(homeDir, driverFile,
					getDriverParamFile(Output.fo));
			break;

		case html:
			driver = new HtmlDriver(homeDir, driverFile,
					getDriverParamFile(Output.html));
			break;

		case htmlhelp:
			driver = new HtmlDriver(homeDir, driverFile,
					getDriverParamFile(Output.html));
			break;

		case manpages:
			driver = new HtmlDriver(homeDir, driverFile,
					getDriverParamFile(Output.manpages));
			break;

		case javahelp:
			driver = new HtmlDriver(homeDir, driverFile,
					getDriverParamFile(Output.html));
			break;

		case pdf:
			driver = new FoDriver(homeDir, driverFile,
					getDriverParamFile(Output.fo));
			break;

		case rtf:
			driver = new FoDriver(homeDir, driverFile,
					getDriverParamFile(Output.fo));
			break;

		case webhelp:
			driver = new HtmlDriver(homeDir, driverFile,
					getDriverParamFile(Output.html));
			break;

		case wordml:
			driver = new WordmlDriver(homeDir, driverFile);
			break;

		case xhtml:
			driver = new HtmlDriver(homeDir, driverFile,
					getDriverParamFile(Output.xhtml));
			break;
		}

		setDriver(driver, output);
		setDriverTemplateFile(driverTemplateFile, output);
	}

	protected AbstractDriver createDriverTemplate(Output output,
			Properties properties) throws ParserConfigurationException,
			SAXException, IOException {

		File driverTemplateFile = getDriverTemplateFile(output);
		AbstractDriver driverTemplate = null;

		switch (output) {

		case eclipse:
			driverTemplate = new HtmlDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.html));
			break;

		case epub:
			driverTemplate = new HtmlDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.xhtml));
			break;

		case fo:
			driverTemplate = new FoDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.fo));
			break;

		case html:
			driverTemplate = new HtmlDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.html));
			break;

		case htmlhelp:
			driverTemplate = new HtmlDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.html));
			break;

		case manpages:
			driverTemplate = new HtmlDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.manpages));
			break;

		case javahelp:
			driverTemplate = new HtmlDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.html));
			break;

		case pdf:
			driverTemplate = new FoDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.fo));
			break;

		case rtf:
			driverTemplate = new FoDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.fo));
			break;

		case webhelp:
			driverTemplate = new HtmlDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.html));
			break;

		case wordml:
			driverTemplate = new WordmlDriver(homeDir, driverTemplateFile);
			break;

		case xhtml:
			driverTemplate = new HtmlDriver(homeDir, driverTemplateFile,
					getDriverParamFile(Output.xhtml));
			break;
		}

		return driverTemplate;
	}

	public File getDefaultDriverTemplate(String medium) {

		String driverTemplatePath = FileServices.appendPath(
				getDriverTemplateDirectory(), medium);
		driverTemplatePath = FileServices.appendFileName(driverTemplatePath,
				"dbdoclet.xsl");

		return new File(driverTemplatePath);
	}

	private File getDriverDirectory() {
		return driverDirectory;
	}

	public File getDriverFile(Output output) {

		if (output == null) {
			return null;
		}

		String path = FileServices.appendFileName(getDriverDirectory(),
				output.toString() + ".xsl");
		return new File(path);
	}

	public File getDriverParamFile(Output output) {

		String path = FileServices.appendPath(homeDir, "docbook");
		path = FileServices.appendPath(path, "xsl");
		path = FileServices.appendPath(path, output.toString());
		path = FileServices.appendFileName(path, "param.xsl");
		return new File(path);
	}

	public File getDriverTemplateDirectory() {

		String path = FileServices.appendPath(homeDir, "xslt");
		return new File(path);
	}

	public File getDriverTemplateFile(Output output) {

		String driverTemplatePath = FileServices.appendPath(
				getDriverTemplateDirectory(), output.toString());
		driverTemplatePath = FileServices.appendFileName(driverTemplatePath,
				"dbdoclet.xsl");

		return new File(driverTemplatePath);
	}

	public void resetFoDriver(File foDriverTemplateFile)
			throws ConfigurationException, ParserConfigurationException,
			SAXException, IOException {

		driverTemplateFileMap.put(Output.pdf, foDriverTemplateFile);

		FoDriver foDriver = new FoDriver(homeDir, foDriverTemplateFile,
				getDriverParamFile(Output.fo));
		setDriver(foDriver, Output.pdf);
	}

	public void setDriver(AbstractDriver driver, Output output) {
		driverMap.put(output, driver);
	}

	public void setDriverTemplateFile(File driverTemplateFile, Output output) {
		driverTemplateFileMap.put(output, driverTemplateFile);
	}

	public AbstractDriver getDriver(Output output) {
		return driverMap.get(output);
	}

	public void save() throws Exception {

		for (Output output : Output.values()) {

			AbstractDriver driver = getDriver(output);

			if (driver != null) {
				driver.save(getDriverFile(output));
			}
		}
	}

	public void createDrivers() throws ConfigurationException,
			ParserConfigurationException, SAXException, IOException {

		for (Output output : Output.values()) {
			createDriver(output);
		}
	}
}
