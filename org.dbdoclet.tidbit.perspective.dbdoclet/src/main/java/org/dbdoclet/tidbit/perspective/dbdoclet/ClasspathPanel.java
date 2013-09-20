/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.perspective.dbdoclet;

import java.util.ResourceBundle;

import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;

public class ClasspathPanel extends PathPanel {

    private static final long serialVersionUID = 1L;

    public ClasspathPanel() {

        super("classpath");

        ResourceBundle res = StaticContext.getResourceBundle();

        setLabelText("Classpath:");
        setHelpText(ResourceServices.getString(res,"C_HELP_CLASSPATH"));

        pathList.setDefaultIncludeFilesFilter("**/*.jar");
        pathList.setDefaultExcludeFilesFilter("**/*.jar");

        setClasspathMode();
    }

    public void syncView(Project project) {
        super.syncView(project, project.getClasspath());
    }

    public void syncModel(Project project) {
        super.syncModel(project, project.getClasspath());
    }
}
