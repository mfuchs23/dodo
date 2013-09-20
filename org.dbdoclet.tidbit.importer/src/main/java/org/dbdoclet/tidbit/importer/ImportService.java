package org.dbdoclet.tidbit.importer;

import org.dbdoclet.tidbit.project.Project;

public interface ImportService {

    public void importProject(Project project);
    public String getName();
    public String getResourceKey();
}
