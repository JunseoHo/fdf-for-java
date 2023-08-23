package event;

import gui.FdFCanvas;
import map.FdFMap;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class FdFMouseAdapter extends MouseAdapter {

    private FdFCanvas       canvas;
    private FdFMap          map;

    private boolean         rotating;
    private boolean         translating;

    private int             originX;
    private int             originY;
    private int             deltaX;
    private int             deltaY;

    public FdFMouseAdapter(FdFCanvas canvas) {
        this.canvas = canvas;
        this.map = null;
        this.rotating = false;
        this.translating = false;
    }

    public void updateFdFMap(FdFMap map) {
        this.map = map;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (map == null) return;
        if (e.getButton() == MouseEvent.BUTTON1 && !translating) rotating = true;
        else if (e.getButton() == MouseEvent.BUTTON3 && !rotating) translating = true;
        originX = e.getX();
        originY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (map == null) return;
        deltaX = e.getX() - originX;
        deltaY = e.getY() - originY;
        originX = e.getX();
        originY = e.getY();
        if (rotating) {
            map.rotateX(deltaY * (-1));
            map.rotateZ(deltaX * (-1));
        }
        if (translating) {
            map.translateX(deltaX);
            map.translateY(deltaY);
        }
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (map == null) return;
        if (e.getButton() == MouseEvent.BUTTON1) rotating = false;
        else if (e.getButton() == MouseEvent.BUTTON3) translating = false;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (map == null) return;
        map.scale(e.getWheelRotation() * (-2));
        canvas.repaint();
    }
}
