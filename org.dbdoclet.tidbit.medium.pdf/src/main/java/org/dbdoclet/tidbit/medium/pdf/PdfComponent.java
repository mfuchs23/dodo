package org.dbdoclet.tidbit.medium.pdf;

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

public class PdfComponent extends AbstractMediumService implements
		MediumService {

	@SuppressWarnings("unchecked")
	protected void activate(ComponentContext context) {

		Dictionary<String, String> dictionary = context.getProperties();
		setCategory(dictionary.get("category"));
	}

	public Generator getGenerator(Settings settings) {

		Generator generator = new PdfGenerator();
		generator.setMonitorPort(23023);
		generator.setViewerCommand(settings.getProperty("pdf-viewer"));

		return generator;
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/pdf32.png",
				PdfComponent.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "pdf";
	}

	public String getName() {
		return "PDF";
	}

	public void write(AntFileWriter writer, Project project) throws IOException {

		// System.out.println("PDF createAntTarget");

		Element target;
		Element xsltTarget;
		Element fopTarget;

		saveDocBookXslt(writer, "pdf", "pdf.xsl");

		target = writer.addTarget("dbdoclet.docbook.pdf");
		writer.addProperty(target, "dbdoclet.media", "pdf");

		writer.addMessage(target, "DocBook File: ${dbdoclet.docbook.file}");

		xsltTarget = writer.addAntCall(target, "dbdoclet.xslt.pdf");
		writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
		writer.addParam(xsltTarget, "out", "${dbdoclet.docbook.file.base}.fo");

		fopTarget = writer.addAntCall(target, "dbdoclet.fop.pdf");
		writer.addParam(fopTarget, "in", "${dbdoclet.docbook.file.base}.fo");
		writer.addParam(fopTarget, "out", "${dbdoclet.docbook.file.base}.pdf");
	}
}
