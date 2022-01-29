package main.java.me.gabricorei9.pongclient.network;

import main.java.me.gabricorei9.pongclient.Main;
import main.java.me.gabricorei9.pongclient.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class InputThread extends Thread {

    public DataInputStream dataInputStream;
    public final Socket socket;

    public InputThread(Socket socket) {
        this.socket = socket;
        try {
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        String line = "";
        while (!line.equals("/exit")) {
            try {
                line = dataInputStream.readUTF();
                if (line.startsWith("/start")) {
                    Main.game.startGame();
                } else if (line.startsWith("/update")) {
                    line = line.replace("/update\n", "");
                    String[] objects = line.split("\n");
                    for (int i = 0; i < objects.length; i++) {
                        if (i == 0 || i == 1) { //update of two walls
                            Main.game.walls.get(i).setY(Double.parseDouble(objects[i].trim().replace(",", ".")));
                        } else {
                            String[] ballVal = objects[i].split(" ");
                            Main.game.ball.setX(Double.parseDouble(ballVal[0].trim().replace(",", ".")));
                            Main.game.ball.setY(Double.parseDouble(ballVal[1].trim().replace(",", ".")));
                        }
                    }
                } else if (line.startsWith("/ip")) {
                    Main.network.ip = line.replace("/ip ", "");
                    System.out.println(Main.network.ip);
                } else if (line.startsWith("/reset")) {
                    line = line.replace("/reset ", "");
                    String[] points = line.split(" ");
                    for (int i = 0; i < points.length; i++) {
                        Main.game.walls.get(i).setPoints(Integer.parseInt(points[i]));
                    }
                } else if (line.startsWith("/beep")) {
                    Utils.beep(Integer.parseInt(line.split(" ")[1].trim()), 100, 0.4);
                } else {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Closing the connection...");
                return;
            }
        }

        Main.network.stopConnection();
    }

    public void close() throws IOException {
        dataInputStream.close();
        this.interrupt();
    }


}
