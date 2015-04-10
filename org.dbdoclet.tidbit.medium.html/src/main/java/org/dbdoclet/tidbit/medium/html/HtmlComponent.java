package org.dbdoclet.tidbit.medium.html;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.common.Output;
import org.dbdoclet.tidbit.medium.AbstractMediumService;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;
import org.w3c.dom.Element;

public class HtmlComponent extends AbstractMediumService implements
		MediumService {

	protected void activate(ComponentContext context) {

		Dictionary<String, Object> dictionary = context.getProperties();
		setCategory(dictionary.get("category"));
	}

	public Generator getGenerator(Settings settings) {

		Generator htmlGenerator = new HtmlGenerator();
		htmlGenerator.setViewerCommand(settings.getProperty("browser"));

		return htmlGenerator;
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/html24.gif",
				HtmlComponent.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "html";
	}

	public String getName() {
		return "html";
	}

	public JPanel getPanel(Context ctx) {
		return null;
	}

	public void write(AntFileWriter writer, Project project) throws IOException {

		Element target;

		saveDocBookXslt(writer, "html", "html.xsl");

		target = writer.addTarget("dbdoclet.docbook.html");
		writer.addProperty(target, "dbdoclet.media", "html");

		writer.addDelete(target,
				project.getHtmlDestDir(project.getDriver(Output.html)));

		Element xsltTarget = writer.addAntCall(target, "dbdoclet.xslt.html");
		writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
		writer.addParam(xsltTarget, "out",
				"${dbdoclet.docbook.file.base}.html.log");

		writer.addCopy(target, "${dbdoclet.home}/xslt/html/dbdoclet.css",
				project.getHtmlDestDir(project.getDriver(Output.html)));

		Element copy = writer.addCopy(target, FileServices.appendPath(
				project.getHtmlDestDir(project.getDriver(Output.html)),
				"images"));
		writer.addFileset(copy, "${dbdoclet.home}/docbook/xsl/images");
		writer.addFileset(copy, "${dbdoclet.home}/xslt/html/images");
		writer.addFileset(copy, "${dbdoclet.home}/xslt/html", "*.css");
	}
}
