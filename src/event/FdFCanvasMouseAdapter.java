package event;

import gui.FdFCanvas;
import transform.FdFMap;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;

public class FdFCanvasMouseAdapter extends MouseAdapter {

    private FdFCanvas canvas;
    private boolean rotating;
    private boolean translating;
    private int originX;
    private int originY;

    public FdFCanvasMouseAdapter(FdFCanvas canvas) {
        this.canvas = canvas;
        this.rotating = false;
        this.translating = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            if (!translating) {
                rotating = true;
                originX = e.getX();
                originY = e.getY();
            }
        } else if (e.getButton() == 3) {
            if (!rotating) {
                translating = true;
                originX = e.getX();
                originY = e.getY();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (rotating) {
            int rotateX = canvas.getRotateX();
            int rotateZ = canvas.getRotateZ();
            int deltaX = e.getX() - originX;
            int deltaY = e.getY() - originY;
            originX = e.getX();
            originY = e.getY();
            rotateX -= deltaY;
            rotateZ -= deltaX;
            if (rotateX > 360 || rotateX < -360) rotateX %= 360;
            if (rotateZ > 360 || rotateZ < -360) rotateZ %= 360;
            canvas.setRotateX(rotateX);
            canvas.setRotateZ(rotateZ);
        }
        if (translating) {
            int translateX = canvas.getTranslateX();
            int translateY = canvas.getTranslateY();
            int deltaX = e.getX() - originX;
            int deltaY = e.getY() - originY;
            originX = e.getX();
            originY = e.getY();
            canvas.setTranslateX(translateX + deltaX);
            canvas.setTranslateY(translateY + deltaY);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) rotating = false;
        else if (e.getButton() == 3) translating = false;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scale = e.getWheelRotation() * (-3);
        int originScale = canvas.getScale();
        scale = originScale + scale;
        if (scale > 10 && scale < 500) canvas.setScale(scale);
    }

}
