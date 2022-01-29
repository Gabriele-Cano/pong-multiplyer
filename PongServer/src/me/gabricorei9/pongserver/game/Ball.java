package me.gabricorei9.pongserver.game;

import java.awt.geom.Ellipse2D;

public class Ball {

    private double x;
    private double y;
    private final int w;
    private final int h;
    private double angle;
    private double currentSpeed = 6.5;

    public Ball(int w, int h) {
        this.w = w;
        this.h = h;
        this.init();
    }

    public void init() {
        x = 960-12;
        y = 540-12;
        //TODO: make the ball spawn only in good angles
        angle = Math.toRadians(Math.random() * 360);
        currentSpeed = 5;
    }

    public void move() {
        x += Math.cos(angle)*currentSpeed;
        y -= Math.sin(angle)*currentSpeed;
    }

    public void incrementSpeed() {
        currentSpeed += 0.4;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Ellipse2D getBounds() {
        return new Ellipse2D.Double(x, y, w, h);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

}
