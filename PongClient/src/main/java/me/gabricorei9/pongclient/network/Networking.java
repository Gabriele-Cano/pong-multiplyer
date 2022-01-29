package main.java.me.gabricorei9.pongclient.network;

import java.io.IOException;
import java.net.Socket;

import static main.java.me.gabricorei9.pongclient.Main.scanner;

public class Networking {

    public String ip;
    public OutputThread outputThread;
    public InputThread inputThread;

    public Socket socket;

    public void initConnections() {
        System.out.print("Enter the server IP: ");
        String ipDest = scanner.nextLine();
        int PORT = 65432;
        System.out.println(ipDest + ":" + PORT);
        try {
            socket = new Socket(ipDest, PORT);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        System.out.print("Enter a username: ");
        assert socket != null;
        outputThread = new OutputThread(socket, scanner.nextLine());
        inputThread = new InputThread(socket);
    }

    public void stopConnection() {
        try {
            outputThread.close();
            inputThread.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
