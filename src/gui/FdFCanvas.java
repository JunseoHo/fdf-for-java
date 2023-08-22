package gui;

import event.FdFMouseAdapter;
import map.FdFMap;
import javax.swing.*;
import java.awt.*;

public class FdFCanvas extends JPanel {

    private FdFMouseAdapter mouseAdapter;
    private FdFMap          map;

    public FdFCanvas() {
        setBackground(Color.BLACK);
        mouseAdapter = new FdFMouseAdapter(this);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    private void paintInit(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Press i to import the map", 30, 30);
    }

    private void paintTransform(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Transform", 15, 30);
        g.drawString("Scale : " + map.scale, 15, 45);
        g.drawString("TransformX : " + map.translateX, 15, 60);
        g.drawString("TransformY : " + map.translateY, 15, 75);
        g.drawString("RotateX : " + map.rotateX, 15, 90);
        g.drawString("RotateZ : " + map.rotateZ, 15, 105);
        g.drawRect(10, 15, 110, 95);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (map == null) paintInit(g);
        else {
            map.paint(g, getWidth(), getHeight());
            paintTransform(g);
        }
    }

    public void updateFdFMap(FdFMap map) {
        map.translateX = getWidth() / 2;
        map.translateY = getHeight() / 2;
        this.map = map;
        mouseAdapter.updateFdFMap(map);
        repaint();
    }
}
