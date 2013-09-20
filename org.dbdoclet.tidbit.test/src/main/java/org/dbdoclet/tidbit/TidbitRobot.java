package org.dbdoclet.tidbit;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class TidbitRobot implements AWTEventListener, ContainerListener {

    private static final int AUTO_DELAY = 1000;

    private ArrayList<Window> windowList;
    private HashMap<String, JComponent> componentMap;
    private Robot robot;
    
    public TidbitRobot() throws AWTException {

        robot = new Robot();
        windowList = new ArrayList<Window>();
        componentMap = new HashMap<String, JComponent>();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.addAWTEventListener(this, AWTEvent.WINDOW_EVENT_MASK | AWTEvent.CONTAINER_EVENT_MASK);
    }

    private void addComponent(Object obj) {

        if (obj instanceof JComponent) {

            JComponent comp = (JComponent) obj;

            String name = comp.getName();

            if (name != null) {

                if (comp instanceof JButton || comp instanceof JTextField) {
                    System.out.println("addComponent name=" + name);
                    componentMap.put(name, comp);
                }
            }
        }
    }

    private void autoDelay() {
        delay(AUTO_DELAY);
    }

    public void componentAdded(ContainerEvent e) {
        addComponent(e.getChild());
    }

    public void componentRemoved(ContainerEvent e) {
    }

    public void delay(int millis) {
        robot.delay(millis);
    }

    public void eventDispatched(AWTEvent event) {

        Object obj;

        switch (event.getID()) {

        case ContainerEvent.COMPONENT_ADDED:

            obj = event.getSource();

            if (obj instanceof Container) {
                ((Container) obj).addContainerListener(this);
            }

            addComponent(obj);

            break;

        case WindowEvent.WINDOW_OPENED:

            obj = event.getSource();
            // System.out.println(obj);

            if (obj instanceof Window) {
                windowList.add((Window) obj);
            }

            break;
        }
    }

    private JButton findButton(String name) {

        JComponent comp = componentMap.get(name);

        if (comp != null && comp instanceof JButton) {
            return (JButton) comp;
        }

        return null;
    }

    public JFrame findFrame(String name) {

        if (name == null) {
            return null;
        }

        for (Window window : windowList) {

            if (window instanceof JFrame) {

                String otherName = window.getName();

                if (otherName != null && name.equals(otherName)) {
                    return (JFrame) window;
                }
            }
        }

        return null;
    }

    private JMenu findMenu(JFrame frame, String name) {

        if (frame == null) {
            throw new IllegalArgumentException("The argument frame must not be null!");
        }

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        JMenuBar menuBar = frame.getJMenuBar();

        for (int i = 0; i < menuBar.getMenuCount(); i++) {

            JMenu menu = menuBar.getMenu(i);
            String otherName = menu.getName();

            if (otherName != null && name.equals(otherName)) {
                return menu;
            }
        }

        return null;
    }

    private JMenuItem findMenuItem(JMenu menu, String name) {

        if (menu == null) {
            throw new IllegalArgumentException("The argument menu must not be null!");
        }

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        for (int i = 0; i < menu.getItemCount(); i++) {

            JMenuItem item = menu.getItem(i);

            String otherName = item.getName();

            if (otherName != null && name.equals(otherName)) {
                return item;
            }
        }

        return null;
    }

    private JTextField findTextField(String name) {

        JComponent comp = componentMap.get(name);

        if (comp != null && comp instanceof JTextField) {
            return (JTextField) comp;
        }

        return null;
    }

    public Window getActiveWindow() {

        for (Window window : windowList) {

            if (window.isActive()) {
                return window;
            }
        }

        return null;
    }

    public JMenu openMenu(JFrame frame, String name) {

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        JMenu menu = findMenu(frame, name);

        if (menu != null) {
            menu.doClick(300);
        }

        return menu;
    }

    public void pressButton(String name) {

        JButton button = findButton(name);
        System.out.println("Button: " + button);

        if (button != null) {
            doClick(button);
        }
    }

    public void setText(String name, String text) {

        JTextField entry = findTextField(name);

        if (entry != null) {
            entry.setText(text);
        }

        autoDelay();
    }

    public void triggerMenuItem(JMenu menu, String name) {

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        JMenuItem item = findMenuItem(menu, name);

        if (item != null) {
            doRobotClick(item);
        }
    }

    private void doClick(final AbstractButton comp) {

        Point point = comp.getLocationOnScreen();
        Dimension dim = comp.getSize();

        int xpos = (int) (point.x + (dim.getWidth() / 2));
        int ypos = (int) (point.y + (dim.getHeight() / 2));

        robot.mouseMove(xpos, ypos);
        autoDelay();
        comp.doClick();
        autoDelay();
    }

    private void doRobotClick(final JComponent comp) {

        Point point = comp.getLocationOnScreen();
        Dimension dim = comp.getSize();

        int xpos = (int) (point.x + (dim.getWidth() / 2));
        int ypos = (int) (point.y + (dim.getHeight() / 2));

        robot.mouseMove(xpos, ypos);
        autoDelay();
        robot.mousePress(InputEvent.BUTTON1_MASK);
        autoDelay();
    }
}
