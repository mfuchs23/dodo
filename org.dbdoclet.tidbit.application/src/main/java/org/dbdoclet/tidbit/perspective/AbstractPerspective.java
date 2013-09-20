package org.dbdoclet.tidbit.perspective;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;

import org.dbdoclet.jive.monitor.MonitorPanel;
import org.dbdoclet.jive.text.IConsole;
import org.dbdoclet.jive.text.TextConsole;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.perspective.panel.InfoPanel;

public abstract class AbstractPerspective implements Perspective {

    private boolean active;
	private IConsole console;
	protected MonitorPanel monitorPanel;
	protected Application application;

    public int compareTo(Perspective other) {
        Float thisWeight = getWeight() * -1;
        Float otherWeight = other.getWeight() * -1;
        return thisWeight.compareTo(otherWeight);
    }

    public int getMonitorPort() {
    	
    	if (monitorPanel != null) {
    		return monitorPanel.getLocalPort();
    	}
    	
    	return 0;
    }
    
    public boolean isAbstract() {
        return false;
    }

    public boolean isReadyForUse() {
        return true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    protected JSplitPane createBottomArea() {
	
    	JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
		console = new TextConsole(25, 80);
		console.setBackground(Color.white);

		InfoPanel infoPanel = new InfoPanel();
		infoPanel.setConsole(console);
		infoPanel.createGui();
	
		if (infoPanel != null) {
			bottomSplitPane.setLeftComponent(infoPanel);
		}
	
		monitorPanel = new MonitorPanel();
		monitorPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		// monitorPanel.setPreferredSize(new Dimension(300,100));

		bottomSplitPane.setRightComponent(new JScrollPane(monitorPanel));
		bottomSplitPane.setResizeWeight(1);
		return bottomSplitPane;
	}

	protected boolean isActive() {
        return active;
    }

	public Application getApplication() {
	    return application;
	}

	public void setApplication(Application application) {
	
	    if (application == null) {
	        throw new IllegalArgumentException("The argument application must not be null!");
	    }
	
	    this.application = application;
	}

	/**
	 * Liefert eine Textkonsole für die Ausgaben der Ant-Läufe.
	 */
	public IConsole getConsole() {

		if (console == null) {
			console = new TextConsole(25, 80);
		}
		
	    return console;
	}
}
