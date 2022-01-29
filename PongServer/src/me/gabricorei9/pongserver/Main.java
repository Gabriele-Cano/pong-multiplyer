package me.gabricorei9.pongserver;

import me.gabricorei9.pongserver.game.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    static final int PORT = 65432;
    public static final ArrayList<ClientThread> connections = new ArrayList<>(0);
    public static final Game game = new Game();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port: " + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                assert serverSocket != null;
                socket = serverSocket.accept();
                connections.add(new ClientThread(socket));
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                break;
            }
        }
    }
}