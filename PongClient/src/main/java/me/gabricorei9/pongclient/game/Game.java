package main.java.me.gabricorei9.pongclient.game;

import main.java.me.gabricorei9.pongclient.Main;

import java.awt.*;
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

    public TimerTask timerTask;
    public final ArrayList<Wall> walls;
    public final Ball ball;
    public Timer timer;

    final int TICK_TIME = 10;

    public Game() {
        walls = new ArrayList<>(2);
        ball = new Ball();
    }

    public void startGame() {
        walls.add(new Wall(20));
        walls.add(new Wall(1880));

        timerTask = new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        };
        timer = new Timer(false);
        timer.scheduleAtFixedRate(timerTask, 0, TICK_TIME);

        EventQueue.invokeLater(() -> {
            Main ex = new Main("Pong");
            ex.setVisible(true);

        });

    }

    private void tick() {
        Main.network.outputThread.sendMessage("/update");
    }
}

