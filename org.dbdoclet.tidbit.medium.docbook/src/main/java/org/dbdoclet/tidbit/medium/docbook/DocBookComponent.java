package org.dbdoclet.tidbit.medium.docbook;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.medium.AbstractMediumService;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;
import org.w3c.dom.Element;

public class DocBookComponent extends AbstractMediumService implements MediumService {

    protected void activate(ComponentContext context) {

        Dictionary<String, Object> dictionary = context.getProperties();
        setCategory(dictionary.get("category"));
    }

    public Generator getGenerator(Settings settings) {

        DocBookGenerator generator = new DocBookGenerator();
        generator.setViewerCommand(settings.getProperty("docbook-editor"));
        return generator;
    }

    public Icon getIcon() {

        Icon icon = null;

        URL iconUrl = ResourceServices
                .getResourceAsUrl("/images/duck32.png", DocBookComponent.class.getClassLoader());

        if (iconUrl != null) {
            icon = new ImageIcon(iconUrl);
        }

        return icon;
    }

    public String getId() {
        return "docbook";
    }

    public String getName() {
        return "DocBook";
    }
    
    public JPanel getPanel(Context ctx) {
        return null;
    }


    public void write(AntFileWriter writer, Project project) throws IOException {

        // System.out.println("DocBook createAntTarget");

        Element target;

        target = writer.addTarget("dbdoclet.docbook");
        target.setAttribute("depends", "dbdoclet.prepare");

        writer.addProperty(target, "dbdoclet.media", "docbook");

        HashMap<String, String> paramMap = new HashMap<String, String>();
        createDbdocletTask(writer, target, project.getMediumTargetParams(), paramMap);
    }
}
