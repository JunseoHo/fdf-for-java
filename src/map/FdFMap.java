package map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FdFMap implements Cloneable {

    private static final int SCALE_LOWER_LIMIT = 1;
    private static final int SCALE_UPPER_LIMIT = 300;

    public int width;
    public int height;
    public List<FdFVertex> vertices;
    public int scale;
    public int translateX;
    public int translateY;
    public int rotateX;
    public int rotateZ;

    public FdFMap(int width, int height, List<FdFVertex> vertices) {
        this.width = width;
        this.height = height;
        this.vertices = vertices;
        this.scale = 20;
        this.translateX = 0;
        this.translateY = 0;
        this.rotateX = 60;
        this.rotateZ = 45;
    }

    public void scale(int s) {
        if (scale + s > SCALE_LOWER_LIMIT && scale + s < SCALE_UPPER_LIMIT) scale += s;
    }

    public void translateX(int t) {
        translateX += t;
    }

    public void translateY(int t) {
        translateY += t;
    }

    public void rotateX(int ang) {
        rotateX += ang;
        if (rotateX < 0) rotateX += 360;
        if (rotateX >= 360) rotateX -= 360;
    }

    public void rotateZ(int ang) {
        rotateZ += ang;
        if (rotateZ < 0) rotateZ += 360;
        if (rotateZ >= 360) rotateZ -= 360;
    }

    private void paintSegmentsWithGradient(int x1, int y1, int x2, int y2, Color c1, Color c2, BufferedImage img) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;
        for (int i = 0; i <= Math.max(dx, dy); i++) {
            double fraction = (double) i / Math.max(dx, dy);
            Color currentColor = calculateGradientColor(c1, c2, fraction);

            if (x1 >= 0 && x1 < img.getWidth() && y1 >= 0 && y1 < img.getHeight()) {
                    img.setRGB(x1, y1, currentColor.getRGB());

            }


            if (x1 == x2 && y1 == y2) {
                break;
            }

            int err2 = 2 * err;
            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    private Color calculateGradientColor(Color startColor, Color endColor, double fraction) {
        int red = (int) (startColor.getRed() + fraction * (endColor.getRed() - startColor.getRed()));
        int green = (int) (startColor.getGreen() + fraction * (endColor.getGreen() - startColor.getGreen()));
        int blue = (int) (startColor.getBlue() + fraction * (endColor.getBlue() - startColor.getBlue()));

        return new Color(red, green, blue);
    }

    private void paintSegments(int x1, int y1, int x2, int y2, Color c1, Color c2, BufferedImage img) {
        paintSegmentsWithGradient(x1, y1, x2, y2, c1, c2, img);
    }

    public void paint(Graphics g, int imgWidth, int imgHeight) {
        List<FdFVertex> transformedVertices = new ArrayList<>();
        for (FdFVertex vertex : vertices)
            transformedVertices.add(FdFTransform.transform(vertex, translateX, translateY,
                    rotateX, rotateZ, scale));
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        FdFVertex u, v;
        for (int idx = 0; idx < width * height; idx++) {
            if ((idx + 1) % width != 0) {
                u = transformedVertices.get(idx);
                v = transformedVertices.get(idx + 1);
                paintSegments(u.x, u.y, v.x, v.y, new Color(u.c), new Color(v.c), img);
            }
            if (idx < width * (height - 1)) {
                u = transformedVertices.get(idx);
                v = transformedVertices.get(idx + width);
                paintSegments(u.x, u.y, v.x, v.y, new Color(u.c), new Color(v.c), img);
            }
        }
        g.drawImage(img, 0, 0, null);
    }

    @Override
    public FdFMap clone() {
        FdFMap clone = new FdFMap(width, height, new ArrayList<>());
        for (FdFVertex vertex : vertices) clone.vertices.add(vertex.clone());
        clone.scale = scale;
        clone.translateX = translateX;
        clone.translateY = translateY;
        clone.rotateX = rotateX;
        clone.rotateZ = rotateZ;
        return clone;
    }

}
