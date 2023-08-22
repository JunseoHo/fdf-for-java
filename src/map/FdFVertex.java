package map;

public class FdFVertex implements Cloneable {

    public int x;
    public int y;
    public int z;
    public int c;

    public FdFVertex(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.c = 0xFFFFFF;
    }

    public FdFVertex(int x, int y, int z, int c) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.c = c;
    }

    @Override
    public FdFVertex clone() {
        return new FdFVertex(x, y, z, c);
    }

}
