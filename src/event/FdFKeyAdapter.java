package event;

import gui.FdFCanvas;
import map.FdFMap;
import parser.FdFParser;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FdFKeyAdapter extends KeyAdapter {

    private static final String DEFAULT_IMPORT_PATH = "maps/";

    private FdFCanvas           canvas;

    public FdFKeyAdapter(FdFCanvas canvas) {
        this.canvas = canvas;
    }

    private void importMap() {
        JFileChooser fileChooser = new JFileChooser(DEFAULT_IMPORT_PATH);
        if (fileChooser.showOpenDialog(null) == 0) {
            FdFMap map = FdFParser.parse(fileChooser.getSelectedFile());
            if (map != null) canvas.updateFdFMap(map);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> System.exit(0);
            case KeyEvent.VK_I -> importMap();
        }
    }
}
