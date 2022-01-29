package me.gabricorei9.pongserver;

import me.gabricorei9.pongserver.game.Wall;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

import static me.gabricorei9.pongserver.Main.game;

public class ClientThread extends Thread {
    public String username;
    public String ip;
    public DataOutputStream dataOutputStream;
    protected final Socket socket;

    public ClientThread(Socket clientSocket) {
        this.socket = clientSocket;
        this.start();
    }

    public void run() {
        DataInputStream dataInputStream;

        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            ip = socket.getRemoteSocketAddress().toString();
            dataOutputStream.writeUTF("/ip " + ip);
        } catch (IOException e) {
            return;
        }

        String line = "";
        while (!line.equals("/exit")) {
            try {
                line = dataInputStream.readUTF();
                if (line.startsWith("/con ")) {
                    username = line.replace("/con ", "");
                    System.out.println("Player " + username + " has connected, with ip: '" + ip + "'");
                } else if (line.startsWith("/start")) {
                    System.out.println("Player " + username + " issued command START");

                    if (Main.connections.size() == 2) {
                        Utils.sendMessage(Main.connections, "/start");
                        game.walls.add(new Wall(20, Main.connections.get(0).ip));
                        game.walls.add(new Wall(1880, Main.connections.get(1).ip));
                        game.startGame();
                    } else {
                        Utils.sendMessage(Main.connections, "You must be in 2 players");
                    }

                } else if (line.equals("/list")) {
                    System.out.println("Player " + username + " issued command LIST");
                    StringBuilder players = new StringBuilder();
                    for (ClientThread thread : Main.connections) {
                        players.append("- ").append(thread.username).append(" (").append(thread.ip).append(")\n");
                    }
                    Utils.sendMessage(Main.connections, players.toString());
                } else if (line.equals("/update")) {
                    Utils.sendMessage(Main.connections, String.format("/update\n%f\n%f\n%f %f", game.walls.get(0).getY(), game.walls.get(1).getY(), game.ball.getX(), game.ball.getY()));
                } else if (line.startsWith("/put ")) {
                    String[] command = line.split(" ");
                    for (int i = 0; i < game.walls.size(); i++) {
                        if (Objects.equals(command[1], game.walls.get(i).getIp())) {
                            switch (command[2]) {
                                case "UP" -> game.walls.get(i).setDirX(-1);
                                case "DOWN" -> game.walls.get(i).setDirX(1);
                                case "NOP" -> game.walls.get(i).setDirX(0);
                            }
                            break;
                        }
                    }
                } else if (line.equals("/ready")) {
                    game.readyCount++;
                } else {
                    System.out.println("[" + username + "]: " + line);
                    Utils.sendMessage(Main.connections, "[" + username + "]: " + line, ip);
                }
            } catch (IOException e) {
                System.out.println("Error with player: " + username + " ip: " + ip + "\t\t" + e.getMessage());
                return;
            }
        }

        try {
            dataOutputStream.writeUTF("/exit");
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}