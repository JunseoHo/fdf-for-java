package gui;

import event.FdFFrameKeyAdapter;
import event.FdFCanvasMouseAdapter;
import transform.FdFMap;

import javax.swing.*;
import java.awt.*;

public class FdFCanvas extends JPanel {

    // objects
    private FdFMap map;

    // event handler
    private FdFCanvasMouseAdapter mouseAdapter;

    public FdFCanvas() {
        // set attributes
        setBackground(Color.BLACK);
        // add event listener
        mouseAdapter = new FdFCanvasMouseAdapter(this);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    public void updateFdFMap(FdFMap map) {
        this.map = map;
        mouseAdapter.updateFdFMap(map);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        if (map != null) map.paint((Graphics2D) g);
        else g.drawString("Press 'i' to import the map", 30, 30);
    }

}
