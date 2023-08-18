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
            this.r = 0;
            this.g = 0;
            this.b = 0;
            this.a = 0;
        }

        public FdFPoint(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.r = 0;
            this.g = 0;
            this.b = 0;
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
    public int rotateY = 0;
    public int rotateZ = 45;

    private void scaleX(FdFPoint point, int scale) {
        point.x *= scale;
    }

    private void scaleY(FdFPoint point, int scale) {
        point.y *= scale;
    }

    private void scaleZ(FdFPoint point, int scale) {
        point.z *= scale;
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

    public void rotateX(FdFPoint point, double rad) {
        double originY = point.y;
        double originZ = point.z;
        point.y = (int) (originY * Math.cos(rad) - originZ * Math.sin(rad));
        point.z = (int) (originY * Math.sin(rad) + originZ * Math.cos(rad));
    }

    public void rotateY(FdFPoint point, double rad) {
        double originX = point.x;
        double originZ = point.z;
        point.x = (int) (originX * Math.cos(rad) + originZ * Math.sin(rad));
        point.z = (int) (originX * Math.sin(rad) * (-1) + originZ * Math.cos(rad));
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

    public void paint(Graphics g) {
        List<List<FdFPoint>> transformedPoints = getTransform(points);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                if (y != height - 1) {
                    FdFPoint p1 = transformedPoints.get(y).get(x);
                    FdFPoint p2 = transformedPoints.get(y + 1).get(x);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
                if (x != width - 1) {
                    FdFPoint p1 = transformedPoints.get(y).get(x);
                    FdFPoint p2 = transformedPoints.get(y).get(x + 1);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
    }

    public void isometricProjection() {
        rotateX = 60;
        rotateY = 0;
        rotateZ = 45;
    }

    public boolean load(String filePath) {
        width = 0;
        height = 0;
        points = new ArrayList<>();
        scale = 50;
        translateX = 640;
        translateY = 320;
        isometricProjection();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(" ");
                if (width == 0) width = values.length;
                List<FdFPoint> list = new ArrayList<>();
                for (int index = 0; index < width; index++) {
                    FdFPoint point = new FdFPoint(index, height, Integer.parseInt(values[index]));
                    list.add(point);
                }
                points.add(list);
                ++height;
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

}
