package org.dbdoclet.tidbit.medium.webhelp;

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

public class WebhelpComponent extends AbstractMediumService implements MediumService {

    @SuppressWarnings("unchecked")
    protected void activate(ComponentContext context) {

        Dictionary<String, String> dictionary = context.getProperties();
        setCategory(dictionary.get("category"));
    }

    public Generator getGenerator(Settings settings) throws IOException {

        WebhelpGenerator webhelpGenerator = new WebhelpGenerator();
        webhelpGenerator.setViewerCommand(settings.getProperty("browser"));
        return webhelpGenerator;
    }

    public Icon getIcon() {

        Icon icon = null;

        URL iconUrl = ResourceServices.getResourceAsUrl("/images/webhelp.png", WebhelpComponent.class
                .getClassLoader());

        if (iconUrl != null) {
            icon = new ImageIcon(iconUrl);
        }

        return icon;
    }

    public String getId() {
        return "webhelp";
    }

    public String getName() {
        return "WebHelp";
    }

    public void write(AntFileWriter writer, Project project) throws IOException {

        Element target;

        target = writer.addTarget("dbdoclet.docbook.webhelp");
        writer.addProperty(target, "dbdoclet.media", "webhelp");

        Element xsltTarget = writer.addAntCall(target, "dbdoclet.xslt");
        writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
        writer.addParam(xsltTarget, "out", "${dbdoclet.docbook.file}.db5");
        writer.addParam(xsltTarget, "stylesheet", "${dbdoclet.home}/docbook/xsl/common/stripns.xsl");
        
        writer.addProperty(target, "input-xml", "${dbdoclet.docbook.file}.db5");
        writer.addProperty(target, "output-dir", "${dbdoclet.destination.path}/webhelp");
        writer.addProperty(target, "validate-against-dtd", "false");
        writer.addProperty(target, "xslt-processor-classpath", "${dbdoclet.home}/jars/saxon-6.5.5.jar");
        
        writer.addAnt(target, "${dbdoclet.home}/docbook/xsl/webhelp", "clean");
        writer.addAnt(target, "${dbdoclet.home}/docbook/xsl/webhelp", "webhelp");
        writer.addAnt(target, "${dbdoclet.home}/docbook/xsl/webhelp", "index");
//        writer.addParam(xsltTarget, "out", "${dbdoclet.docbook.file.base}.webhelp");
//        saveDocBookXslt(writer, project.getJavadocParams(), "webhelp", "webhelp.xsl");
//
//        target = writer.addTarget("dbdoclet.docbook.webhelp");
//        writer.addProperty(target, "dbdoclet.media", "webhelp");
//
//        Element xsltTarget = writer.addAntCall(target, "dbdoclet.xslt.webhelp");
//        writer.addParam(xsltTarget, "in", "${dbdoclet.docbook.file}");
//        writer.addParam(xsltTarget, "out", "${dbdoclet.docbook.file.base}.webhelp");
//
//        writer.addCopy(target, "${dbdoclet.home}/xslt/webhelp/dbdoclet.css", project.getHtmlDestDir(project.getDriver(Output.webhelp)));
//        
//        Element copy = writer.addCopy(target, FileServices.appendPath(project.getHtmlDestDir(project.getDriver(Output.webhelp)), "images"));
//        writer.addFileset(copy, "${dbdoclet.home}/docbook/xsl/images");
    }
}
