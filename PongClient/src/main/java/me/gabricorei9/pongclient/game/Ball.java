package main.java.me.gabricorei9.pongclient.game;

public class Ball {

    private double x;
    private double y;
    private final int w;
    private final int h;

    public Ball() {
        w = 25;
        h = 25;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
