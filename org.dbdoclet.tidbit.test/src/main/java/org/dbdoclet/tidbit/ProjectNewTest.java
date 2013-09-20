package org.dbdoclet.tidbit;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JMenu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectNewTest {

    private TidbitRobot robot;
    private JFrame frame;

    @Before
    public void setUp() throws Exception {

        robot = new TidbitRobot();

        System.setProperty("org.dbdoclet.doclet.home", "/usr/share/dbdoclet");
        //TidbitHost.main(new String[] {});

        frame = null;

        while (frame == null) {
            
            frame = robot.findFrame("frame.tidbit");
            Thread.sleep(1000);
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testProjectNew_1() throws InterruptedException {

        System.out.println(frame);
        JMenu menu = robot.openMenu(frame, "menu.projects");
        robot.triggerMenuItem(menu, "menuitem.project-new");
        
        robot.setText("textfield.project-name", "project-name");
        robot.pressButton("button.choose-project-file");
        robot.delay(5000);
        menu = robot.openMenu(frame, "menu.projects");
        robot.triggerMenuItem(menu, "menuitem.project-save");
        robot.delay(1000);
        
        Window wnd = robot.getActiveWindow();
        System.out.println("Active Window: " + wnd);
        
        robot.triggerMenuItem(menu, "menuitem.exit");
    }
}
