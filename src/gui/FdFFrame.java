package gui;

import event.FdFKeyAdapter;
import javax.swing.*;
import java.awt.*;

public class FdFFrame extends JFrame {

    private static final String TITLE = "FdF for Java";
    private static final int    WIDTH = 1280;
    private static final int    HEIGHT = 720;
    private static final Color  BACKGROUND_COLOR = Color.BLACK;

    private FdFCanvas           canvas = null;

    public FdFFrame() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        add(canvas = new FdFCanvas());
        addKeyListener(new FdFKeyAdapter(canvas));
    }

}
