package transform;

import javax.lang.model.util.Elements;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FdFMap {

    public class FdFPoint implements Cloneable {

        int x;
        int y;
        int z;
        int r;
        int g;
        int b;
        int a;

        public FdFPoint() {
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.r = 255;
            this.g = 255;
            this.b = 255;
            this.a = 0;
        }

        public FdFPoint(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.r = 255;
            this.g = 255;
            this.b = 255;
            this.a = 0;
        }

        public FdFPoint(int x, int y, int z, int r, int g, int b, int a) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        @Override
        public FdFPoint clone() {
            return new FdFPoint(x, y, z, r, g, b, a);
        }

    }

    // attributes
    public int width = 0;
    public int height = 0;
    public List<List<FdFPoint>> points = null;
    // transform
    public int scale = 50;
    public int translateX = 640;
    public int translateY = 320;
    public int rotateX = 60;
    public int rotateZ = 45;

    public void scale(int deltaScale) {
        scale += deltaScale;
        if (scale < 5) scale = 5;
        if (scale > 500) scale = 500;
    }

    private void scaleX(FdFPoint point, int scale) {
        point.x *= scale;
    }

    private void scaleY(FdFPoint point, int scale) {
        point.y *= scale;
    }

    private void scaleZ(FdFPoint point, int scale) {
        point.z *= scale;
    }

    public void translateX(int translate) {
        translateX += translate;
    }

    public void translateY(int translate) {
        translateY += translate;
    }

    private void translateX(FdFPoint point, int translate) {
        point.x += translate;
    }

    private void translateY(FdFPoint point, int translate) {
        point.y += translate;
    }

    private void translateZ(FdFPoint point, int translate) {
        point.z += translate;
    }

    public void rotateX(int angle) {
        rotateX += angle;
        if (rotateX > 360 || rotateX < -360) rotateX %= 360;
    }

    public void rotateZ(int angle) {
        rotateZ += angle;
        if (rotateZ > 360 || rotateZ < -360) rotateZ %= 360;
    }

    public void rotateX(FdFPoint point, double rad) {
        double originY = point.y;
        double originZ = point.z;
        point.y = (int) (originY * Math.cos(rad) - originZ * Math.sin(rad));
        point.z = (int) (originY * Math.sin(rad) + originZ * Math.cos(rad));
    }

    public void rotateZ(FdFPoint point, double rad) {
        double originX = point.x;
        double originY = point.y;
        point.x = (int) (originX * Math.cos(rad) - originY * Math.sin(rad));
        point.y = (int) (originX * Math.sin(rad) + originY * Math.cos(rad));
    }

    private FdFPoint getTransform(FdFPoint point) {
        point = point.clone();
        scaleX(point, scale);
        scaleY(point, scale);
        scaleZ(point, scale);
        translateX(point, width * scale / 2 * (-1));
        translateY(point, height * scale / 2 * (-1));
        rotateZ(point, Math.toRadians(rotateZ));
        rotateX(point, Math.toRadians(rotateX));
        translateX(point, translateX);
        translateY(point, translateY);
        return point;
    }

    private List<List<FdFPoint>> getTransform(List<List<FdFPoint>> points) {
        List<List<FdFPoint>> transformedPoints = new ArrayList<>();
        for (List<FdFPoint> list : points) {
            List<FdFPoint> transformedList = new ArrayList<>();
            for (FdFPoint point : list)
                transformedList.add(getTransform(point));
            transformedPoints.add(transformedList);
        }
        return transformedPoints;
    }

    public void paint(Graphics2D g) {
        List<List<FdFPoint>> transformedPoints = getTransform(points);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                if (y != height - 1) {
                    FdFPoint p1 = transformedPoints.get(y).get(x);
                    FdFPoint p2 = transformedPoints.get(y + 1).get(x);
                    GradientPaint gradientPaint = new GradientPaint(p1.x, p1.y, new Color(p1.r, p1.g, p1.b), p2.x, p2.y, new Color(p2.r, p2.g, p2.b));
                    g.setPaint(gradientPaint);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
                if (x != width - 1) {
                    FdFPoint p1 = transformedPoints.get(y).get(x);
                    FdFPoint p2 = transformedPoints.get(y).get(x + 1);
//                    g.setColor(new Color(p2.r, p2.g, p2.b));
                    GradientPaint gradientPaint = new GradientPaint(p1.x, p1.y, new Color(p1.r, p1.g, p1.b), p2.x, p2.y, new Color(p2.r, p2.g, p2.b));
                    g.setPaint(gradientPaint);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
    }

    public void isometricProjection() {
        rotateX = 60;
        rotateZ = 45;
    }

    public int toHex(char a, char b) {
        if (a >= 'A' && a <= 'F') a -= 'A';
        if (a >= 'a' && a <= 'f') a -= 'a';
        if (a >= '0' && a <= '9') a -= '0';
        if (b >= 'A' && b <= 'F') b -= 'A';
        if (b >= 'a' && b <= 'f') b -= 'a';
        if (b >= '0' && b <= '9') b -= '0';
        return a * 16 + b;
    }

    public String load(File file) {
        if (!file.getName().endsWith(".fdf")) return "Extension is incorrect.";
        width = 0;
        height = 0;
        points = new ArrayList<>();
        scale = 20;
        translateX = 0;
        translateY = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split("\\s+");
                if (width == 0) width = values.length;
                else if (width != values.length) return "Map format is invalid.";
                List<FdFPoint> list = new ArrayList<>();
                for (int index = 0; index < width; index++) {
                    String value = values[index];
                    String[] tokens = value.split(",");
                    if (tokens.length == 1) list.add(new FdFPoint(index, height, Integer.parseInt(values[index])));
                    else if (tokens.length == 2) {
                        String color = tokens[1];
                        if (!color.startsWith("0x") && !color.startsWith("0X")) return "Map format is invalid.";
                        char[] colorArr = color.toCharArray();
                        int r = toHex(colorArr[2], colorArr[3]);
                        int g = toHex(colorArr[4], colorArr[5]);
                        int b = toHex(colorArr[6], colorArr[7]);
                        list.add(new FdFPoint(index, height, Integer.parseInt(tokens[0]), r, g, b, 0));
                    } else return "Map format is invalid.";
                }
                points.add(list);
                ++height;
            }
        } catch (FileNotFoundException e) {
            return "File not found.";
        }
        return null;
    }

}
