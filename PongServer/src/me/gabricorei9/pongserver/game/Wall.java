package me.gabricorei9.pongserver.game;

import java.awt.*;

public class Wall {

    private int dirX;
    private final int x;
    private double y;
    private final int w;
    private final int h;
    private final String ip;
    private int points = 0;
    private final double speed = 6.5;

    public Wall(int x, String ip) {
        this.x = x;
        this.ip = ip;
        this.w = 20;
        this.h = 200;
        this.y = 1080f/2f-h/2f;
    }

    public void init() {
        y = 1080f/2f-h/2f;
        dirX = 0;
    }

    public void move() {
        y += dirX * speed;
    }

    public double getY() {
        return y;
    }

    public String getIp() {
        return ip;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, (int) y, w, h);
    }

    public int getPoints() {
        return points;
    }

    public int getDirX() {
        return dirX;
    }

    public double getSpeed() {
        return speed;
    }

    public void incrementPoint() {
        this.points++;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

}
