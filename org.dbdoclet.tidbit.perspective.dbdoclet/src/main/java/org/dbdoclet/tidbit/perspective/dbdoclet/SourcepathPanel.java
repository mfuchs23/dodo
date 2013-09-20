/* 
 * ### Copyright (C) 2007-2009 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.perspective.dbdoclet;

import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.project.Project;

public class SourcepathPanel extends PathPanel {

    private static final long serialVersionUID = 1L;

    public SourcepathPanel() {

        super("sourcepath");

        setLabelText("Sourcepath:");
        setHelpText(ResourceServices.getString(res,"C_HELP_SOURCEPATH"));

        pathList.setDefaultIncludeFilesFilter("**/*.java");
        pathList.setDefaultExcludeFilesFilter("**/*.java");

    }

    public void syncView(Project project) {
        super.syncView(project, project.getSourcepath());
    }

    public void syncModel(Project project) {
        super.syncModel(project, project.getSourcepath());
    }
}
