package org.dbdoclet.tidbit.medium.wordml;

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

public class WordmlComponent extends AbstractMediumService implements
		MediumService {

	public Generator getGenerator(Settings settings) {

		WordmlGenerator generator = new WordmlGenerator(
				settings.getProperty("wml-viewer"));
		return generator;
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/word32.png",
				WordmlComponent.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "word";
	}

	public String getName() {
		return "WordML";
	}

	public void write(AntFileWriter writer, Project project) throws IOException {

		Element target;
		Element xsltTarget;

		saveDocBookXslt(writer, "wordml", "wordml.xsl");

		target = writer.addTarget("dbdoclet.docbook.wordml");
		writer.addProperty(target, "dbdoclet.media", "wordml");

		writer.addMessage(target, "DocBook File: ${dbdoclet.docbook.file}");

		xsltTarget = writer.addAntCall(target, "dbdoclet.xslt.wordml");
		writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
		writer.addParam(xsltTarget, "out",
				"${dbdoclet.docbook.file.base}-WordML.xml");
	}

	protected void activate(ComponentContext context) {

		Dictionary<String, Object> dictionary = context.getProperties();
		setCategory(dictionary.get("category"));
	}
}
