package org.dbdoclet.tidbit.action;

import java.awt.event.ActionEvent;

import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.application.action.AbstractTidbitAction;
import org.dbdoclet.tidbit.importer.ImportService;

public class ActionImport extends AbstractTidbitAction {

    private final ImportService importer;

    public ActionImport(Application application, String name, ImportService importer) {

        super(application, name, null);
        this.importer = importer;
    }

    private static final long serialVersionUID = 1L;

    @Override
    protected void action(ActionEvent event) throws Exception {

        importer.importProject(application.getProject());
    }

    public ImportService getImporter() {
        return importer;
    }

}
