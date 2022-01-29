package me.gabricorei9.pongserver;

import java.io.IOException;
import java.util.ArrayList;

public class Utils {

    public static void sendMessage(ArrayList<ClientThread> list, String message) {
        if (list.size() > 0) {
            for (ClientThread thread: list) {
                try {
                    thread.dataOutputStream.writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendMessage(ArrayList<ClientThread> list, String message, String ipToEsclude) {
        if (list.size() > 0) {
            for (ClientThread thread: list) {
                if (!thread.ip.equals(ipToEsclude)) {
                    try {
                        thread.dataOutputStream.writeUTF(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
