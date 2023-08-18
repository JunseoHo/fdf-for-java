package gui;

import event.FdFCanvasMouseAdapter;
import transform.FdFMap;

import javax.swing.*;
import java.awt.*;

public class FdFCanvas extends JPanel {

    private FdFMap map;

    public FdFCanvas() {
        // set attributes
        setBackground(Color.BLACK);
        // add event listener
        this.map = new FdFMap();
        map.load("maps/plat.fdf");
        FdFCanvasMouseAdapter mouseAdapter = new FdFCanvasMouseAdapter(this);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    public int getScale() {
        return map.scale;
    }

    public int getRotateX() {
        return map.rotateX;
    }

    public int getRotateZ() {
        return map.rotateZ;
    }

    public int getTranslateX() {
        return map.translateX;
    }

    public int getTranslateY() {
        return map.translateY;
    }

    public void setScale(int scale) {
        map.scale = scale;
        repaint();
    }

    public void setTranslateX(int translateX) {
        map.translateX = translateX;
        repaint();
    }

    public void setTranslateY(int translateY) {
        map.translateY = translateY;
        repaint();
    }

    public void setRotateX(int rotateX) {
        map.rotateX = rotateX;
        repaint();
    }

    public void setRotateZ(int rotateZ) {
        map.rotateZ = rotateZ;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        map.paint(g);
        g.drawString("Transform", 10, 15);
        g.drawString("Scale : " + getScale(), 10, 30);
        g.drawString("TranslateX : " + (getTranslateX() - 640), 10, 45);
        g.drawString("TranslateY : " + (getTranslateY() - 320), 10, 60);
        g.drawString("RotateX : " + getRotateX(), 10, 75);
        g.drawString("RotateZ : " + getRotateZ(), 10, 90);
        g.drawString("Help", 10, 615);
        g.drawString("MouseWheelMove : Scale Up/Down", 10, 630);
        g.drawString("MouseLeftClick + Drag : Rotation", 10, 645);
        g.drawString("MouseRightClick + Drag: Translation", 10, 660);
    }

}
