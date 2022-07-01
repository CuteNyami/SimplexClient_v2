package tk.simplexclient.animations.util;

public class V2 {

    private double x;
    private double y;

    public V2() {}

    public V2(double x, double y) {
        setX(x);
        setY(y);
    }

    public V2(int x, int y) {
        setX(x);
        setY(y);
    }

    public V2(float x, float y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}