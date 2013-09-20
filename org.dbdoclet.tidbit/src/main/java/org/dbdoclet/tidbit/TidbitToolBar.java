package org.dbdoclet.tidbit;

import java.awt.Component;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.JiveServices;
import org.dbdoclet.tidbit.action.ActionNewProject;
import org.dbdoclet.tidbit.action.ActionOpenBuild;
import org.dbdoclet.tidbit.action.ActionOpenProject;
import org.dbdoclet.tidbit.action.ActionSaveProject;

public class TidbitToolBar extends JToolBar {

    private static final long serialVersionUID = 1L;
    private final Tidbit application;
    private JiveFactory wm;

    public TidbitToolBar(Tidbit application) {

        this.application = application;
        wm = JiveFactory.getInstance();
    }

    public void createInitialToolBar() {

        JButton button;
        ImageIcon icon;

        icon = JiveServices.getJlfgrIcon("general", "New24.gif");
        button = new JButton(icon);
        button.setAction(new ActionNewProject(application, null, icon));
        add(button);

        icon = JiveServices.getJlfgrIcon("general", "Open24.gif");
        button = new JButton(icon);
        button.setAction(new ActionOpenProject(application, null, icon));
        add(button);

        icon = JiveServices.getJlfgrIcon("general", "Save24.gif");
        button = new JButton(icon);
        button.setAction(new ActionSaveProject(application, null, icon));
        button.setEnabled(false);
        add(button);
        wm.addSaveWidget(button);

        icon = JiveServices.getJlfgrIcon("general", "SaveAs24.gif");
        button = new JButton(icon);
        button.setAction(new ActionSaveProject(application, null, icon, true));
        button.setEnabled(false);
        wm.addSaveWidget(button);
        add(button);

        addSeparator();

        icon = JiveServices.getJlfgrIcon("media", "Movie24.gif");
        button = new JButton(icon);
        button.setAction(new ActionOpenBuild(application, icon));
        button.setEnabled(false);
        add(button);

        HelpBroker helpBroker = application.getHelpBroker();
        
        if (helpBroker != null) {
        
            icon = JiveServices.getJlfgrIcon("general", "ContextualHelp24.gif");
            button = new JButton(icon);
            button.addActionListener(new CSH.DisplayHelpAfterTracking(helpBroker.getHelpSet(), "javax.help.Popup", "popup"));
            add(button);
        }

        addSeparator();
    }

    public void unlock() {
        
        for (Component comp : getComponents()) {
            
            if (comp != null && comp instanceof JButton) {
                
                JButton button = (JButton) comp;
                button.setEnabled(true);
            }
        }
    }
}
