package main.java.me.gabricorei9.pongclient;

import main.java.me.gabricorei9.pongclient.game.Game;
import main.java.me.gabricorei9.pongclient.network.Networking;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main extends JFrame {

    public static final Networking network = new Networking();
    public static final Scanner scanner = new Scanner(System.in);
    public static final Game game = new Game();

    public Main(String title) {
        //TODO: background music
        this.setSize(1920, 1080);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        ImageIcon imageIcon = new ImageIcon(String.valueOf(Main.class.getResourceAsStream("pong.jpg")));
        System.out.println(Main.class.getResourceAsStream("pong.jpg") == null);
        this.setIconImage(imageIcon.getImage());
        this.add(new Renderer());
    }

    public static void main(String[] args) {
        network.initConnections();

        while (true) {
            String line = scanner.nextLine();
            if (line.equals("/help")) {
                System.out.println("List of all commands: \n- /help -> Shows this\n- /list -> Shows all connected players\n- /exit -> Quit from the app (theoretically)");
            } else {
                network.outputThread.sendMessage(line);
            }
            if (line.equals("/exit")) {
                return;
            }
        }
    }
}
