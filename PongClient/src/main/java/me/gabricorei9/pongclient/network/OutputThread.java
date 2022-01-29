package main.java.me.gabricorei9.pongclient.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OutputThread extends Thread {

    public DataOutputStream dataOutputStream;

    public OutputThread(Socket socket, String username) {
        try {
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setDaemon(true);

        this.sendMessage("/con " + username);
        System.out.println("Connected as " + username);

    }

    public void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException ignored) {
        }
    }

    public void close() throws IOException {
        dataOutputStream.close();
        this.interrupt();
    }

}
