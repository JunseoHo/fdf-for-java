package main;

import gui.FdFFrame;
import javax.swing.*;

public class FdFMain {

    private static final String LOOK_AND_FEEL_CLASS_PATH = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    public static void main(String[] args) {
        setLookAndFeel(LOOK_AND_FEEL_CLASS_PATH);
        new FdFFrame().setVisible(true);
    }

    private static void setLookAndFeel(String classPath) {
        try {
            UIManager.setLookAndFeel(classPath);
        } catch (Exception e) {
            System.err.println("Failed to load Look and Feel.");
        }
    }

}
