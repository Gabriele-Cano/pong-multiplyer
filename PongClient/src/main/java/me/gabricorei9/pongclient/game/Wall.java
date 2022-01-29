package main.java.me.gabricorei9.pongclient.game;

public class Wall {

    private final int x;
    private double y;
    private final int w;
    private final int h;
    private int points = 0;

    public Wall(int x) {
        this.x = x;
        this.w = 20;
        this.h = 200;
        this.y = 1080f/2f-h/2f;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }



}
