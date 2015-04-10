package org.dbdoclet.tidbit.medium.herold;

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

public class HeroldComponent extends AbstractMediumService implements
		MediumService {

	protected void activate(ComponentContext context) {

		Dictionary<String, Object> dictionary = context.getProperties();
		setCategory(dictionary.get("category"));
	}

	public Generator getGenerator(Settings settings) {
		return null;
	}

	public Icon getIcon() {

		Icon icon = null;

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/html24.png",
				HeroldComponent.class.getClassLoader());

		if (iconUrl != null) {
			icon = new ImageIcon(iconUrl);
		}

		return icon;
	}

	public String getId() {
		return "herold";
	}

	public String getName() {
		return "Herold";
	}

	public void write(AntFileWriter writer, Project project) throws IOException {
	}
}
