import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

class Listner2 implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON2) {
            Energy.t = 0;
            Energy.img = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
            Energy.g = (Graphics2D) Energy.img.getGraphics();
            Energy.g.setColor(Color.black);
            Energy.g.drawLine(0, 300, 0, 800);

            Energy.g.drawLine(0, 300, 1000, 300);
            Energy.g.setColor(Color.GRAY);
            for (int i = 0; i < 400; ++i) {
                Energy.g.drawLine(20 * i, 0, 20 * i, 800);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

public class Energy extends Thread {
    Energy() {
        start();
    }

    private static JFrame En = new JFrame("Energy");
    static BufferedImage img;
    static Graphics2D g;
    static double x = 0;
    static double y = 0;
    static double vx = 0;
    static double vy = 0;
    static boolean first = true;
    static boolean work = true;
    static int t = 0;

    @Override
    public void run() {
        double u = 70.0 / Main.n;
        double u2 = 70.0 * 69 / (Main.n * (Main.n - 1));
        En.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        En.setLocation(0, 0);
        En.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height / 4));
        En.add(new Canvas2());
        En.setVisible(true);
        En.addMouseListener(new Listner2());
        img = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
        double E;
        double E2;
        g = (Graphics2D) img.getGraphics();
        g.setColor(Color.black);
        g.drawLine(0, 300, 0, 800);

        g.drawLine(0, 300, 1000, 300);
        g.setColor(Color.GRAY);
        for (int i = 0; i < 400; ++i) {
            g.drawLine(20 * i, 0, 20 * i, 800);
        }
        double lastE = 0;
        int k = Main.n - 1;


        while (work) {
            E = 0;
            E2 = 0;
            vx = vy = x = y = 0;
            for (int i = 0; i < Main.n; ++i) {
                E += (Main.ball[i].q.dqdt.x * Main.ball[i].q.dqdt.x + Main.ball[i].q.dqdt.y * Main.ball[i].q.dqdt.y) * Main.ball[i].m / 2;
            }
            //System.out.print(E + " ");
            g.setColor(Color.BLUE);
            g.fillOval(t, 300 + (int) (-E * u / 10), 2, 2);
            for (int i = 0; i < Main.n; ++i) {
                for (int j = i + 1; j < Main.n; ++j) {
                    //E2 -= Main.gravity * 0.5 * Main.ball[i].m * Main.ball[j].m * (Math.log((Main.ball[i].q.q.x * Main.ball[i].q.q.x + Main.ball[i].q.q.y * Main.ball[i].q.q.y)));
                    E2 = E2 - Main.gravity * Main.ball[i].m * Main.ball[j].m * (1 / (Math.sqrt((Main.ball[i].q.q.x - Main.ball[j].q.q.x) * (Main.ball[i].q.q.x - Main.ball[j].q.q.x) + (Main.ball[i].q.q.y - Main.ball[j].q.q.y) * (Main.ball[i].q.q.y - Main.ball[j].q.q.y))) - 1.0 / 800);
                }
            }
            for (int i = 0; i < Main.n; ++i) {
                vx += Main.ball[i].m * Main.ball[i].q.dqdt.x;
                vy += Main.ball[i].m * Main.ball[i].q.dqdt.y;
            }
            for (int i = 0; i < Main.n; ++i) {
                x += Main.ball[i].m * Main.ball[i].q.q.x;
                y += Main.ball[i].m * Main.ball[i].q.q.y;
            }
            //System.out.print(E2 + " ");

            //System.out.println((E2 + E) + " ");
            g.setColor(Color.black);
            g.drawOval(t, 300 + (int) (((vx * vx + vy * vy) / Main.M) * -10000), 1, 1);
            g.setColor(Color.RED);

            g.fillOval(t, 300 + (int) (-E2 * u2 / (8000 * Main.gravity)), 2, 2);
            g.setColor(Color.GREEN);
            g.fillOval(t, 300 + (int) (-(E2 + E * k) * u2 / (8000 * Main.gravity)), 2, 2);
            g.drawLine(t - 1, 300 + (int) (-lastE * u2 / (8000 * Main.gravity)), t, 300 + (int) (-(E2 + E * k) * u2 / (8000 * Main.gravity)));
            lastE = E2 + E * k;
            En.repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            x = 0;
            y = 0;
            t += 1;

        }
    }
}
