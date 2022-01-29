package me.gabricorei9.pongserver.game;

import me.gabricorei9.pongserver.Main;
import me.gabricorei9.pongserver.Utils;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

   /*
    Pong Protocol:

    Start Game:
    /start
    %s     --> (ip of first player) (at the left)
    %s     --> (ip of second player) (at the right)

    Request:
    "GET UPDATE"

    Response:
    "/update
    $(y)
    $(y)
    $(x) $(y)"

    Request:
    "PUT /127.0.0.1:65432 UP"
    "PUT /127.0.0.1:65432 DOWN"
    "PUT /127.0.0.1:65432 NOP"

    Response:
    None

     */

    public final TimerTask timerTask;
    final int TICK_TIME = 33;
    public final ArrayList<Wall> walls;
    public final Ball ball;
    public Timer timer;
    public int readyCount = 0;

    public Game() {

        walls = new ArrayList<>(2);
        ball = new Ball(25, 25);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        };
    }

    public void startGame() {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(timerTask, 0, TICK_TIME);
    }

    private void reset() {
        for (Wall wall: walls) {
            wall.init();
        }
        ball.init();
        readyCount = 0;
    }

    private void checkCollisions() {
        Ellipse2D ballBounds = ball.getBounds();

        for (int i = 0; i < walls.size(); i++) {
            Rectangle wallBounds = walls.get(i).getBounds();
            if (ballBounds.intersects(wallBounds)) {
                double ballVX = (-Math.signum(Math.cos(ball.getAngle())))*(Math.abs(Math.cos(ball.getAngle())) + 0.07 * walls.get(i).getDirX()*walls.get(i).getSpeed());
                double ballVY = Math.sin(ball.getAngle());
                ball.setAngle(Math.atan2(ballVY, ballVX));
                ball.incrementSpeed();
                if (i == 0) Utils.sendMessage(Main.connections, "/beep 950");
                if (i == 1) Utils.sendMessage(Main.connections, "/beep 1020");
            }
        }

        if (ballBounds.intersects(0,0,1920,1) || ballBounds.intersects(0,1080,1920,1)){
            double ballVX = Math.cos(ball.getAngle());
            double ballVY = -Math.sin(ball.getAngle());
            ball.setAngle(Math.atan2(ballVY, ballVX));
        }

        if (ballBounds.intersects(0,0,1,1080)) {
            //GameOver
            walls.get(1).incrementPoint();
            Utils.sendMessage(Main.connections, String.format("/reset %d %d", walls.get(0).getPoints(), walls.get(1).getPoints()));
            reset();
        }

        if (ballBounds.intersects(1920, 0, 1, 1080)) {
            //GameOver
            walls.get(0).incrementPoint();
            Utils.sendMessage(Main.connections, String.format("/reset %d %d", walls.get(0).getPoints(), walls.get(1).getPoints()));
            reset();
        }

    }


    //TODO: walls can't go too up or too down
    private void tick() {
        checkCollisions();
        if (readyCount >= 2) {
            for (Wall wall : walls) {
                wall.move();
            }
            ball.move();
        }
    }
}

