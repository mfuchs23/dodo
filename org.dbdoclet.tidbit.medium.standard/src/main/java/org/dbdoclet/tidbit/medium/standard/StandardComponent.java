package org.dbdoclet.tidbit.medium.standard;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.io.FileSet;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.service.FileSetServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.MediumTargetParams;
import org.dbdoclet.tidbit.common.Visibility;
import org.dbdoclet.tidbit.medium.AbstractMediumService;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.project.Project;
import org.osgi.service.component.ComponentContext;
import org.w3c.dom.Element;

public class StandardComponent extends AbstractMediumService implements MediumService {

    @SuppressWarnings("unchecked")
    protected void activate(ComponentContext context) {

        Dictionary<String, String> dictionary = context.getProperties();
        setCategory(dictionary.get("category"));
    }

    public Generator getGenerator(Settings settings) {

        StandardGenerator generator = new StandardGenerator();
        generator.setViewerCommand(settings.getProperty("browser"));
        return generator;
    }

    public Icon getIcon() {

        Icon icon = null;

        URL iconUrl = ResourceServices
                .getResourceAsUrl("/images/cup32.png", StandardComponent.class.getClassLoader());

        if (iconUrl != null) {
            icon = new ImageIcon(iconUrl);
        }

        return icon;
    }

    public String getId() {
        return "standard";
    }

    public String getName() {
        return "Javadoc";
    }

    public void write(AntFileWriter writer, Project project) throws IOException {

        String path;
        String str;
        Element target;
        Element javadoc;

        target = writer.addTarget("dbdoclet.standard");
        writer.addProperty(target, "dbdoclet.media", "standard");

        javadoc = writer.addJavadoc(target);
        javadoc.setAttribute("useexternalfile", "true");

        MediumTargetParams javadocParams = project.getMediumTargetParams();
        
        path = javadocParams.getOverview();

        if ((path != null) && (path.length() > 0)) {

            str = path.toLowerCase();

            if (str.endsWith(".html") || str.endsWith(".htm")) {
                javadoc.setAttribute("overview", "${dbdoclet.overview.file}");
            }
        }

        javadoc.setAttribute("destdir", "${dbdoclet.destination.path}/${dbdoclet.media}");

        str = javadocParams.getDestinationEncoding();

        if ((str != null) && (str.length() > 0)) {

            javadoc.setAttribute("docencoding", str);
            javadoc.setAttribute("charset", str);
        }

        str = javadocParams.getSourceEncoding();

        if ((str != null) && (str.length() > 0)) {
            javadoc.setAttribute("encoding", str);
        }

        if (javadocParams.isLinkSourceEnabled()) {
            javadoc.setAttribute("linksource", "yes");
        }
        
        Visibility visibility = javadocParams.getVisibility();

        if ((str != null) && (str.length() > 0)) {
            javadoc.setAttribute("access", visibility.getValue());
        }

        str = javadocParams.getSource();

        if ((str != null) && (str.length() > 0) && (str.equals("---") == false)) {
            javadoc.setAttribute("source", str);
        }

        str = javadocParams.getMemory();

        if ((str != null) && (str.length() > 0)) {
            javadoc.setAttribute("maxmemory", str + "m");
        }

        ArrayList<FileSet> list = javadocParams.getSourcepath();
        FileSetServices.createAntFileSets(writer.getDocument(), javadoc, list);

        writer.addClasspath(javadoc, "dbdoclet.classpath");

        Element zip = writer.addElement(target, "zip");
        zip.setAttribute("basedir", "${dbdoclet.destination.path}/${dbdoclet.media}");
        zip.setAttribute("destfile",
                "${dbdoclet.destination.path}/javadoc.zip");

    }
}
