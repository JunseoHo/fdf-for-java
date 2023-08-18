package gui;

import event.FdFFrameKeyAdapter;
import transform.FdFMap;

import javax.swing.*;
import java.io.File;

public class FdFFrame extends JFrame {

    // attributes
    private static final String FRAME_TITLE = "FdF";
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;
    // objects
    private FdFMap map = null;
    // components
    public FdFCanvas canvas;

    public FdFFrame() {
        // set attributes
        setTitle(FRAME_TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // create components
        add(canvas = new FdFCanvas());
        // add event listener
        addKeyListener(new FdFFrameKeyAdapter(this));
    }

    public void importMap() {
        JFileChooser fileChooser = new JFileChooser("maps/");
        if (fileChooser.showOpenDialog(this) == 0) {
            File file = fileChooser.getSelectedFile();
            map = new FdFMap();
            String message;
            if ((message = map.load(file)) == null) {
                map.translateX(FRAME_WIDTH / 2);
                map.translateY(FRAME_HEIGHT / 2);
                canvas.updateFdFMap(map);
            } else
                JOptionPane.showMessageDialog(null, message,
                        "Load failed.", JOptionPane.ERROR_MESSAGE);
        }
    }
}
