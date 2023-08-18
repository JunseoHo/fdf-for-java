package gui;

import transform.FdFMap;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class FdFFrame extends JFrame {

    // attributes
    private static final String FRAME_TITLE = "FdF";
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;
    private FdFMap map;

    // components
    private FdFCanvas canvas;

    public FdFFrame() {
        // set attributes
        setTitle(FRAME_TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        // create components
        canvas = new FdFCanvas();
        add(canvas);
    }

}
