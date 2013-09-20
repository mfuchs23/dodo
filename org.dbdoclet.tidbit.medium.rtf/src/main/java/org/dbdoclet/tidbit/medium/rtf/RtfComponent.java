package org.dbdoclet.tidbit.medium.rtf;

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

public class RtfComponent extends AbstractMediumService implements
		MediumService {

	@SuppressWarnings("unchecked")
	protected void activate(ComponentContext context) {

		Dictionary<String, String> dictionary = context.getProperties();
		setCategory(dictionary.get("category"));
	}

	public Generator getGenerator(Settings settings) {

		Generator generator = new RtfGenerator();
		generator.setViewerCommand(settings.getProperty("rtf-viewer"));
		return generator;
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/rtf32.png",
				RtfComponent.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "rtf";
	}

	public String getName() {
		return "RTF";
	}

	public void write(AntFileWriter writer, Project project) throws IOException {

		// System.out.println("RTF createAntTarget");

		Element target;
		Element xsltTarget;
		Element fopTarget;

		saveDocBookXslt(writer, "rtf", "rtf.xsl");

		target = writer.addTarget("dbdoclet.docbook.rtf");
		writer.addProperty(target, "dbdoclet.media", "rtf");

		writer.addMessage(target, "DocBook File: ${dbdoclet.docbook.file}");

		xsltTarget = writer.addAntCall(target, "dbdoclet.xslt.rtf");
		writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
		writer.addParam(xsltTarget, "out", "${dbdoclet.docbook.file.base}.fo");

		fopTarget = writer.addAntCall(target, "dbdoclet.fop.rtf");
		writer.addParam(fopTarget, "in", "${dbdoclet.docbook.file.base}.fo");
		writer.addParam(fopTarget, "out", "${dbdoclet.docbook.file.base}.rtf");
	}
}
