import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Energy extends Thread {
    Energy() {
        start();
    }

    private static JFrame En = new JFrame("Energy");
    static BufferedImage img;
    static double x = 0;
    static double y = 0;
    static double vx = 0;
    static double vy = 0;

    @Override
    public void run() {
        double u=70.0/Main.n;
        double u2=70.0*69/(Main.n*(Main.n-1));
        img = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.black);
        En.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        En.setLocation(0, 0);
        En.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height / 4));
        En.add(new Canvas2());
        En.setVisible(true);
        double E;
        double E2;
        int t = 0;
        g.drawLine(0, 300, 0, 800);

        g.drawLine(0, 300, 1000, 300);
        g.setColor(Color.GRAY);
        for (int i = 0; i < 400; ++i) {
            g.drawLine(20 * i, 0, 20 * i, 800);
        }
        double lastE=0;
        int k=Main.n-1;


        while (true) {
            E = 0;
            E2 = 0;
            vx=vy=x=y=0;
            for (int i = 0; i < Main.n; ++i) {
                E += (Main.ball[i].q.dqdt.x * Main.ball[i].q.dqdt.x + Main.ball[i].q.dqdt.y * Main.ball[i].q.dqdt.y) * Main.ball[i].m / 2;
            }
            //System.out.print(E + " ");
            g.setColor(Color.BLUE);
            g.fillOval(t, 300 + (int) (-E*u /10), 2, 2);
            for (int i = 0; i < Main.n; ++i) {
                for (int j = i + 1; j < Main.n; ++j) {
                    //E2 -= Main.gravity * 0.5 * Main.ball[i].m * Main.ball[j].m * (Math.log((Main.ball[i].q.q.x * Main.ball[i].q.q.x + Main.ball[i].q.q.y * Main.ball[i].q.q.y)));
                    E2 = E2 - Main.gravity * Main.ball[i].m * Main.ball[j].m*(1 / (Math.sqrt((Main.ball[i].q.q.x - Main.ball[j].q.q.x)*(Main.ball[i].q.q.x - Main.ball[j].q.q.x) + (Main.ball[i].q.q.y - Main.ball[j].q.q.y)*(Main.ball[i].q.q.y - Main.ball[j].q.q.y)))-1/30);
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
            //System.out.println(E2 + " ");

            //System.out.println((E2 + E) + " ");
            g.setColor(Color.black);
            g.drawOval(t, 300 + (int) (((vx * vx + vy * vy) / Main.M) * -10000), 1, 1);
            g.setColor(Color.RED);

            g.fillOval(t, 300 + (int) (-E2*u2 / (50000*Main.gravity)), 2, 2);
            g.setColor(Color.GREEN);
            g.fillOval(t, 300 + (int) (-(E2 + E*k)*u2 / (50000*Main.gravity)), 2, 2);
            g.drawLine(t-1,300+(int) (-lastE*u2/(50000*Main.gravity)),t, 300 + (int) (-(E2 + E*k)*u2 / (50000*Main.gravity)));
            lastE=E2 + E*k;
            En.repaint();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }
            x = 0;
            y = 0;
            t += 1;

        }
    }
}
