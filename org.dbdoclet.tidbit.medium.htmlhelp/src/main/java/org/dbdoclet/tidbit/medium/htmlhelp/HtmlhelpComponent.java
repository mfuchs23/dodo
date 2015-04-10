package org.dbdoclet.tidbit.medium.htmlhelp;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.medium.AbstractMediumService;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;
import org.w3c.dom.Element;

public class HtmlhelpComponent extends AbstractMediumService implements
		MediumService {

	protected void activate(ComponentContext context) {

		Dictionary<String, Object> dictionary = context.getProperties();
		setCategory(dictionary.get("category"));
	}

	@Override
	public Generator getGenerator(Settings settings) throws IOException {

		HtmlhelpGenerator htmlhelpGenerator = new HtmlhelpGenerator();
		htmlhelpGenerator.setViewerCommand(settings.getProperty("browser"));
		return htmlhelpGenerator;
	}

	@Override
	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/htmlhelp.png",
				HtmlhelpComponent.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	@Override
	public String getId() {
		return "htmlhelp";
	}

	@Override
	public String getName() {
		return "HTMLHelp";
	}

	@Override
	public void write(AntFileWriter writer, Project project) throws IOException {

		// String baseDir =
		// project.getHtmlDestDir(project.getDriver(Output.htmlhelp));

		saveDocBookXslt(writer, "htmlhelp", "htmlhelp.xsl");

		Element target = writer.addTarget("dbdoclet.docbook.htmlhelp");
		writer.addProperty(target, "dbdoclet.media", "htmlhelp");

		Element xsltTarget = writer
				.addAntCall(target, "dbdoclet.xslt.htmlhelp");
		writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
		writer.addParam(xsltTarget, "out",
				"${dbdoclet.docbook.file.base}.htmlhelp");

	}
}
