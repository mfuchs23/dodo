package org.dbdoclet.tidbit.application;

import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JMenu;

import org.dbdoclet.jive.text.IConsole;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.importer.ImportService;
import org.dbdoclet.tidbit.medium.Generator;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.trafo.TrafoService;

public interface Application {

    public void addGenerator(Generator generator);
    public void addMenu(String string, JMenu menu);
    public void addRecent(File file);
    public void addToolBarButton(String id, AbstractAction action);
    public Context getContext();
    public List<ImportService> getImportServiceList();
    public MediumService getMediumService(String string);
    public TrafoService getTrafoService(String string);
    public List<MediumService> getMediumServiceList();
    public List<MediumService> getMediumServiceList(String category);
    public Perspective getPerspective();
    public List<Perspective> getPerspectiveList();
    public Project getProject();
    public void lock();
    public AbstractAction newGenerateAction(IConsole console, MediumService service);
    public void refresh();
    public void removeGenerator(Generator generator);
    public void removeMenu(String string);
    public void removeToolBarButton(String string);
    public void reset();
    public void setDefaultCursor();
    public void setFrameTitle(String title);
    public void setProject(Project project);
    public void setWaitCursor();
    public void shutdown();
	public void unlock();
}
