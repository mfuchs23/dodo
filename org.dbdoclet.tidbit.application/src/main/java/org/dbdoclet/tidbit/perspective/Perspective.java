package org.dbdoclet.tidbit.perspective;

import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JPanel;

import org.dbdoclet.jive.text.IConsole;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.project.Project;

public interface Perspective extends Comparable<Perspective> {

	public Application getApplication();
    public IConsole getConsole();
    public Icon getIcon();
    public String getId();
    public String getLocalizedName();
    public int getMonitorPort();
    public String getName();
    public JPanel getPanel() throws IOException;
    public Float getWeight();
    public boolean isAbstract();
    public boolean isReadyForUse();
    public void onEnter();
    public void onLeave();
    public void refresh();
    public void reset();
    public void setActive(boolean b);
    public void setApplication(Application application);
    public void syncModel(Project project);
    public void syncView(Project project);
	public boolean validate();
}
