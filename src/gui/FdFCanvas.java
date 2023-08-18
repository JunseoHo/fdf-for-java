package gui;

import event.FdFFrameKeyAdapter;
import event.FdFCanvasMouseAdapter;
import transform.FdFMap;

import javax.swing.*;
import java.awt.*;

public class FdFCanvas extends JPanel {

    // objects
    private FdFMap map;

    // status
    private boolean printLabels;

    // event handler
    private FdFCanvasMouseAdapter mouseAdapter;

    public FdFCanvas() {
        // set attributes
        setBackground(Color.BLACK);
        printLabels = true;
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

    public void switchPrintLabels() {
        this.printLabels = !printLabels;
        repaint();
    }

    public void paintTransform(Graphics g) {
        g.drawString("Transform", 15, 30);
        g.drawString("Scale : " + map.scale, 15, 45);
        g.drawString("TransformX : " + map.translateX, 15, 60);
        g.drawString("TransformY : " + map.translateY, 15, 75);
        g.drawString("RotateX : " + map.rotateX, 15, 90);
        g.drawString("RotateZ : " + map.rotateZ, 15, 105);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        if (map != null) {
            map.paint((Graphics2D) g);
            if (printLabels) paintTransform(g);
        } else g.drawString("Press 'i' to import the map", 30, 30);
    }

}
