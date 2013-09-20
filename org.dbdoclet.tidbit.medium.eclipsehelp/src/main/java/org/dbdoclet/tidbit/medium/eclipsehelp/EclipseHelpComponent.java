package org.dbdoclet.tidbit.medium.eclipsehelp;

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

public class EclipseHelpComponent extends AbstractMediumService implements
		MediumService {

	@SuppressWarnings("unchecked")
	protected void activate(ComponentContext context) {

		Dictionary<String, String> dictionary = context.getProperties();
		setCategory(dictionary.get("category"));
	}

	public Generator getGenerator(Settings settings) {

		EclipseHelpGenerator eclipseHelpGenerator = new EclipseHelpGenerator();
		return eclipseHelpGenerator;
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/eclipse.gif",
				EclipseHelpComponent.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "eclipse";
	}

	public String getName() {
		return "Eclipse";
	}

	public JPanel getPanel(Context context) {
		return null;
	}

	public boolean isComplete() {

		return true;
	}

	public void write(AntFileWriter writer, Project project) throws IOException {

		saveDocBookXslt(writer, "eclipse", "eclipse.xsl");

		Element target = writer.addTarget("dbdoclet.docbook.eclipse");
		writer.addProperty(target, "dbdoclet.media", "eclipse");

		Element xsltTarget = writer.addAntCall(target, "dbdoclet.xslt.eclipse");
		writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
		writer.addParam(xsltTarget, "out",
				"${dbdoclet.docbook.file.base}.eclipse");

		writer.addCopy(target, "${dbdoclet.home}/xslt/eclipse/dbdoclet.css",
				project.getHtmlDestDir(project.getDriver(Output.eclipse)));

		Element copy = writer.addCopy(target, FileServices.appendPath(
				project.getHtmlDestDir(project.getDriver(Output.eclipse)),
				"images"));
		writer.addFileset(copy, "${dbdoclet.home}/docbook/xsl/images");
		writer.addFileset(copy, "${dbdoclet.home}/xslt/eclipse/images");
	}
}
