package main;

import gui.FdFFrame;

import javax.swing.*;
import java.awt.*;

public class FdFMain {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            return;
        }
        new FdFFrame().setVisible(true);
    }

}
