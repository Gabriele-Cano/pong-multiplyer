package main.java.me.gabricorei9.pongclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Objects;

public class Renderer extends JPanel implements ActionListener {

    Font customFont = null;

    public Renderer() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getResourceAsStream("/fonts/bit5x3.ttf"))).deriveFont(140f);
        } catch (IOException | FontFormatException ignored) {}
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);

        setFocusable(true);
        Timer timer = new Timer(10, this);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        Ellipse2D circle = new Ellipse2D.Double(Main.game.ball.getX(), Main.game.ball.getY(), Main.game.ball.getW(), Main.game.ball.getH());
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.WHITE);
        g2d.fill(circle);

        Rectangle2D centerWall = new Rectangle2D.Double(960-5, 0, 10, 20);
        for (int i = 0; i < 1080; i+=50) {
            AffineTransform at = AffineTransform.getTranslateInstance(0, i);
            g2d.fill(at.createTransformedShape(centerWall));
        }

        g2d.setFont(customFont);
        g2d.drawString(String.valueOf(Main.game.walls.get(0).getPoints()), 480, 180);
        g2d.drawString(String.valueOf(Main.game.walls.get(1).getPoints()), 1440, 180);

        for (int i = 0; i < 2; i++) {
            g.fillRect(Main.game.walls.get(i).getX(), (int) Main.game.walls.get(i).getY(), Main.game.walls.get(i).getW(), Main.game.walls.get(i).getH());
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private static class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                Main.network.outputThread.sendMessage("/put " + Main.network.ip + " UP");
            }

            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                Main.network.outputThread.sendMessage("/put " + Main.network.ip + " DOWN");
            }

            if (key == KeyEvent.VK_ENTER) {
                Main.network.outputThread.sendMessage("/ready");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                Main.network.outputThread.sendMessage("/put " + Main.network.ip + " NOP");
            }
        }
    }
}

