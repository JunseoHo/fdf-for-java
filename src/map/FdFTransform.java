package map;

import java.util.List;

public class FdFTransform {

    public static void scaleAll(FdFVertex vertex, int i) {
        vertex.x *= i;
        vertex.y *= i;
        vertex.z *= i;
    }

    public static void translateX(FdFVertex vertex, int i) {
        vertex.x += i;
    }

    public static void translateX(List<FdFVertex> vertices, int i) {
        for (FdFVertex vertex : vertices) translateX(vertex, i);
    }

    public static void translateY(FdFVertex vertex, int i) {
        vertex.y += i;
    }

    public static void translateY(List<FdFVertex> vertices, int i) {
        for (FdFVertex vertex : vertices) translateY(vertex, i);
    }

    public static void rotateX(FdFVertex vertex, int ang) {
        double rad = Math.toRadians(ang);
        int originY = vertex.y;
        int originZ = vertex.z;
        vertex.y = (int) (originY * Math.cos(rad) - originZ * Math.sin(rad));
        vertex.z = (int) (originY * Math.sin(rad) + originZ * Math.cos(rad));
    }

    public static void rotateZ(FdFVertex vertex, int ang) {
        double rad = Math.toRadians(ang);
        int originX = vertex.x;
        int originY = vertex.y;
        vertex.x = (int) (originX * Math.cos(rad) - originY * Math.sin(rad));
        vertex.y = (int) (originX * Math.sin(rad) + originY * Math.cos(rad));
    }

    public static FdFVertex transform(FdFVertex u, int tx, int ty, int rx, int rz, int sc) {
        FdFVertex v = u.clone();
        scaleAll(v, sc);
        rotateZ(v, rz);
        rotateX(v, rx);
        translateX(v, tx);
        translateY(v, ty);
        return v;
    }

}
