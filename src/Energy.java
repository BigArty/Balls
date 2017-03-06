import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Energy extends Thread {
    Energy() {
        start();
    }

    private static JFrame En = new JFrame("Energy");
    static BufferedImage img;
    static Graphics2D g;
    static double x = 0;
    static double y = 0;

    @Override
    public void run() {
        img = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();
        g.setColor(Color.black);
        En.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        En.setLocation(0, 0);
        En.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height / 4));
        En.add(new Canvas2());
        En.setVisible(true);
        double E = 0;
        double E2 = 0;
        int t = 0;
        g.drawLine(0, 400, 0, 800);

        g.drawLine(0, 400, 400, 400);
        g.setColor(Color.GRAY);
        for (int i = 0; i < 400; ++i) {
            g.drawLine(20 * i, 0, 20 * i, 800);
        }

        while (true) {
            E = 0;
            E2 = 0;
            for (int i = 0; i < Main.n; ++i) {
                E += (Main.ball[i].q.dqdt.x * Main.ball[i].q.dqdt.x + Main.ball[i].q.dqdt.y * Main.ball[i].q.dqdt.y) * Main.ball[i].m / 2;
            }
            System.out.print(E + " ");
            g.setColor(Color.BLUE);
            g.fillOval(t, 400 + (int) (-E /10), 2, 2);
            for (int i = 0; i < Main.n; ++i) {
                for (int j = i + 1; j < Main.n; ++j) {
                    E2 -= Main.gravity * 0.5 * Main.ball[i].m * Main.ball[j].m * (Math.log((Main.ball[i].q.q.x * Main.ball[i].q.q.x + Main.ball[i].q.q.y * Main.ball[i].q.q.y)));
                }
            }
            for (int i = 0; i < Main.n; ++i) {
                x += Main.ball[i].m * Main.ball[i].q.dqdt.x;
                y += Main.ball[i].m * Main.ball[i].q.dqdt.y;
            }
            System.out.print(E2 + " ");

            System.out.println((E2 + E) + " ");
            g.setColor(Color.black);
            g.drawOval(t, 400 + (int) (((x * x + y * y) / Main.M) * -10000), 1, 1);
            g.setColor(Color.RED);

            g.fillOval(t, 400 + (int) (-E2 / 10000), 2, 2);
            g.setColor(Color.GREEN);
            g.fillOval(t, 400 + (int) (-(E2 + E) / 10000), 2, 2);
            En.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            x = 0;
            y = 0;
            t += 1;

        }
    }
}
