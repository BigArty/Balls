import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    private static void f(int i, int j, ddoblCoord F, ddoblCoord[] a) {
        fmol(i, j, F, a);
    }

    static double gravity = 0.1;
    private static int minR = 1;
    private static int rndR = 0;
    static int n = 500;
    static Ball[] ball;
    static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static double startH = 0.00001;
    private static double eps = 0.0001 * n * n / 4900;
    private static double epsV = 0.0001 * n * n / 4900;
    private static double h = startH;
    static double M = 0;
    private static double helpDouble;
    private static double helpDouble2;
    private static double helpDouble3;
    private static doblCoord N = new doblCoord();
    private static double dist2 = 0;
    private static double dist = 0;
    private static ddoblCoord[] k1 = new ddoblCoord[n], k2 = new ddoblCoord[n], k3 = new ddoblCoord[n], k4 = new ddoblCoord[n], k5 = new ddoblCoord[n], k0 = new ddoblCoord[n];
    private static double o3;
    private static double o32 = 1.0 / 32;
    private static ddoblCoord F = new ddoblCoord();
    private static ddoblCoord dVHelp = new ddoblCoord();
    private static double[] er = new double[n];
    private static double[] erV = new double[n];
    private static boolean isHChenge = false;
    private static ddoblCoord nul = new ddoblCoord();
    private static boolean correct = false;

    public static void main(String[] args) {
        if (eps > 0.01) {
            eps = 0.01;
            epsV = 0.01;
        }
        ball = new Ball[n];
        Color c;
        boolean OK = false;
        o3 = 1.0 / 3;
        for (int i = 0; i < n; i++) {
            k0[i] = new ddoblCoord();
            k1[i] = new ddoblCoord();
            k2[i] = new ddoblCoord();
            k3[i] = new ddoblCoord();
            k4[i] = new ddoblCoord();
            k5[i] = new ddoblCoord();
            ball[i] = new Ball();
            while (!OK) {
                ball[i].r = (int) (Math.random() * rndR) + minR;
                ball[i].q.q.x = (2 * ball[i].r + Math.random() * (height / 1.34 - 4 * ball[i].r));
                ball[i].q.q.y = (2 * ball[i].r + Math.random() * (width / 1.34 - 4 * ball[i].r));
                OK = true;
                for (int j = 0; j < i; ++j) {
                    if (((ball[i].r + ball[j].r + 10) * (ball[i].r + ball[j].r + 10)) > ((ball[i].q.q.x - ball[j].q.q.x) * (ball[i].q.q.x - ball[j].q.q.x) + (ball[i].q.q.y - ball[j].q.q.y) * (ball[i].q.q.y - ball[j].q.q.y))) {
                        OK = false;
                    }
                }
            }
            OK = false;
            ball[i].q.dqdt.x = ((Math.random() * 1)) / 2;
            ball[i].q.dqdt.y = ((Math.random() * 1)) / 2;
            int col = (int) (Math.random() * 10);
            ball[i].m = ball[i].r * ball[i].r;
            ball[i].m1 = 1.0 / ball[i].m;
            System.out.println(ball[i].m + " " + ball[i].m1);
            switch (col) {
                case 1:
                    c = Color.BLACK;
                    break;
                case 2:
                    c = Color.RED;
                    break;
                case 3:
                    c = Color.blue;
                    break;
                case 4:
                    c = Color.GREEN;
                    break;
                case 5:
                    c = Color.cyan;
                    break;
                case 6:
                    c = Color.darkGray;
                    break;
                case 7:
                    c = Color.orange;
                    break;
                case 8:
                    c = Color.magenta;
                    break;
                case 9:
                    c = Color.pink;
                    break;
                case 0:
                    c = Color.yellow;
                    break;
                default:
                    c = Color.blue;
            }
            ball[i].c = c;
        }
        double Vx = 0;
        double Vy = 0;
        for (int i = 0; i < Main.n; ++i) {
            M += Main.ball[i].m;
        }
        for (int i = 0; i < Main.n; ++i) {
            Vx += ball[i].m * ball[i].q.dqdt.x;
            Vy += ball[i].m * ball[i].q.dqdt.y;
        }
        Vx = Vx / M;
        Vy = Vy / M;
        for (int i = 0; i < n; ++i) {
            ball[i].q.dqdt.x -= Vx;
            ball[i].q.dqdt.y -= Vy;
        }
        double t;
        //Новые потоки
        new Velosity();
        new Energy();
        new Temperature();
        new Check();
        new Drowing();

        while (true) {
            /*try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }*/
            mersonStep();
        }
    }

    private static void mul(double t, doblCoord v, doblCoord V1) {
        V1.x = v.x * t;
        V1.y = v.y * t;
    }

    private static void mul(double t, ddoblCoord v, ddoblCoord V1) {
        V1.q.x = v.q.x * t;
        V1.q.y = v.q.y * t;
        V1.dqdt.x = v.dqdt.x * t;
        V1.dqdt.y = v.dqdt.y * t;
    }

    private static ddoblCoord sum(ddoblCoord o1, ddoblCoord o2) {
        o1.q.x = o1.q.x + o2.q.x;
        o1.q.y = o1.q.y + o2.q.y;
        o1.dqdt.x = o1.dqdt.x + o2.dqdt.x;
        o1.dqdt.y = o1.dqdt.y + o2.dqdt.y;
        return o1;
    }

    private static void fbord(int i, int j, ddoblCoord F, ddoblCoord[] a) {
        helpDouble2 = 0;
        dist2 = (a[i].q.x - a[j].q.x) * (a[i].q.x - a[j].q.x) + (a[i].q.y - a[j].q.y) * (a[i].q.y - a[j].q.y);
        F.dqdt.x = 0;
        F.dqdt.y = 0;

        if ((dist2) > ((ball[i].r + ball[j].r) * (ball[i].r + ball[j].r))) {
            if ((dist2) <= ((ball[i].r + ball[j].r + 8) * (ball[i].r + ball[j].r + 8))) {
                N.x = a[i].q.x - a[j].q.x;
                N.y = a[i].q.y - a[j].q.y;
                helpDouble2 = 1000.0 / (dist2 - (ball[i].r + ball[j].r) * (ball[i].r + ball[j].r));
                mul(helpDouble2, N, F.dqdt);
            }

        }
        if (a[i].q.x - ball[i].r < 4) {
            F.dqdt.x += 1000.0 / (a[i].q.x - ball[i].r) - 250;
        }
        if (a[i].q.y - ball[i].r < 4) {
            F.dqdt.y += 1000.0 / (a[i].q.y - ball[i].r) - 250;
        }
        if (a[i].q.x + ball[i].r > height - 62) {
            F.dqdt.x -= 1000.0 / (height - 58 - a[i].q.x - ball[i].r) - 250;
        }
        if (a[i].q.y + ball[i].r > width) {
            F.dqdt.y -= 1000.0 / (width + 4 - a[i].q.y - ball[i].r) - 250;
        }
        F.q.x = a[i].dqdt.x;
        F.q.y = a[i].dqdt.y;
    }

    private static void fmol(int i, int j, ddoblCoord F, ddoblCoord[] a) {
        helpDouble2 = 0;
        dist2 = (a[i].q.x - a[j].q.x) * (a[i].q.x - a[j].q.x) + (a[i].q.y - a[j].q.y) * (a[i].q.y - a[j].q.y);
        F.dqdt.x = 0;
        F.dqdt.y = 0;

        dist = Math.sqrt(dist2);
        N.x = a[i].q.x - a[j].q.x;
        N.y = a[i].q.y - a[j].q.y;
        helpDouble = 1 / (dist2 * dist);
        helpDouble3 = 1000000.0 / (dist2 * dist2 * dist2 * dist);
        helpDouble2 = 100.0 * dist * helpDouble3 * helpDouble3;
        helpDouble2 += -gravity * 20 * ball[i].m * ball[j].m * (helpDouble3 + helpDouble);
        mul(helpDouble2, N, F.dqdt);
        //Затухание
        F.dqdt.y -= eps * a[i].dqdt.y * 0.1;
        F.dqdt.x -= eps * a[i].dqdt.x * 0.1;

        F.q.x = a[i].dqdt.x;
        F.q.y = a[i].dqdt.y;
    }

    private static void fgrav(int i, int j, ddoblCoord F, ddoblCoord[] a) {
        helpDouble2 = 0;
        dist2 = (a[i].q.x - a[j].q.x) * (a[i].q.x - a[j].q.x) + (a[i].q.y - a[j].q.y) * (a[i].q.y - a[j].q.y);
        F.dqdt.x = 0;
        F.dqdt.y = 0;
        if ((dist2) > ((ball[i].r + ball[j].r) * (ball[i].r + ball[j].r))) {
            dist = Math.sqrt(dist2);
            N.x = a[i].q.x - a[j].q.x;
            N.y = a[i].q.y - a[j].q.y;
            if ((dist) <= ((ball[i].r + ball[j].r + 8))) {
                helpDouble2 = 1000.0 / (dist2 - (ball[i].r + ball[j].r) * (ball[i].r + ball[j].r));
            }
            helpDouble3 = 1 / (dist2 * dist);
            helpDouble2 += -gravity * ball[i].m * ball[j].m * (helpDouble3);
            mul(helpDouble2, N, F.dqdt);
        }
        F.q.x = a[i].dqdt.x;
        F.q.y = a[i].dqdt.y;
    }

    private static void mem(ddoblCoord dest, ddoblCoord from) {
        dest.q.x = from.q.x;
        dest.q.y = from.q.y;
        dest.dqdt.x = from.dqdt.x;
        dest.dqdt.y = from.dqdt.y;
    }

    private static int mersonStep() {
        while (!correct) {
            isHChenge = false;
            for (int i = 0; i < n; ++i) {
                mem(k0[i], ball[i].q);
                mem(k1[i], nul);
                mem(k2[i], nul);
                mem(k3[i], nul);
                mem(k4[i], nul);
                mem(k5[i], nul);
            }

            // Step 1

            mem(dVHelp, nul);
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i != j) {
                        f(i, j, F, k0);
                        mul(ball[i].m1, F.dqdt, F.dqdt);
                        dVHelp = sum(dVHelp, F);
                    }
                }
                mul(h * o3, dVHelp, dVHelp);
                k1[i] = sum(k1[i], dVHelp);
            }

            //Step 2

            mem(dVHelp, nul);
            for (int j = 0; j < n; ++j) {
                k0[j] = sum(k0[j], k1[j]);
            }
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i != j) {
                        f(i, j, F, k0);
                        mul(ball[i].m1, F.dqdt, F.dqdt);
                        dVHelp = sum(dVHelp, F);
                    }
                }
                mul(h * o3, dVHelp, dVHelp);
                k2[i] = sum(k2[i], dVHelp);
            }

            //Step 3

            mem(dVHelp, nul);
            for (int j = 0; j < n; ++j) {
                k0[j].q.x = ball[j].q.q.x + 0.5 * (k1[j].q.x + k2[j].q.x);
                k0[j].q.y = ball[j].q.q.y + 0.5 * (k1[j].q.y + k2[j].q.y);

                k0[j].dqdt.x = ball[j].q.dqdt.x + 0.5 * (k1[j].dqdt.x + k2[j].dqdt.x);
                k0[j].dqdt.y = ball[j].q.dqdt.y + 0.5 * (k1[j].dqdt.y + k2[j].dqdt.y);
            }
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i != j) {
                        f(i, j, F, k0);
                        mul(ball[i].m1, F.dqdt, F.dqdt);
                        dVHelp = sum(dVHelp, F);
                    }
                }
                mul(h, dVHelp, dVHelp);
                k3[i] = sum(k3[i], dVHelp);
            }

            //Step 4

            mem(dVHelp, nul);
            for (int j = 0; j < n; ++j) {
                k0[j].q.x = ball[j].q.q.x + 0.375 * (k1[j].q.x + k3[j].q.x);
                k0[j].q.y = ball[j].q.q.y + 0.375 * (k1[j].q.y + k3[j].q.y);

                k0[j].dqdt.x = ball[j].q.dqdt.x + 0.375 * (k1[j].dqdt.x + k3[j].dqdt.x);
                k0[j].dqdt.y = ball[j].q.dqdt.y + 0.375 * (k1[j].dqdt.y + k3[j].dqdt.y);
            }
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i != j) {
                        f(i, j, F, k0);
                        mul(ball[i].m1, F.dqdt, F.dqdt);
                        dVHelp = sum(dVHelp, F);
                    }
                }
                mul(o3 * h * 4, dVHelp, dVHelp);
                k4[i] = sum(sum(k4[i], k1[i]), dVHelp);
            }

            //Step 5

            mem(dVHelp, nul);
            for (int j = 0; j < n; ++j) {
                k0[j].q.x = ball[j].q.q.x + 1.5 * (k4[j].q.x - k3[j].q.x);
                k0[j].q.y = ball[j].q.q.y + 1.5 * (k4[j].q.y - k3[j].q.y);

                k0[j].dqdt.x = ball[j].q.dqdt.x + 1.5 * (k4[j].dqdt.x - k3[j].dqdt.x);
                k0[j].dqdt.y = ball[j].q.dqdt.y + 1.5 * (k4[j].dqdt.y - k3[j].dqdt.y);
            }
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i != j) {
                        f(i, j, F, k0);
                        mul(ball[i].m1, F.dqdt, F.dqdt);
                        dVHelp = sum(dVHelp, F);
                    }
                }
                mul(o3 * h, dVHelp, dVHelp);
                k5[i] = sum(k5[i], dVHelp);
            }

            //Error

            correct = true;

            for (int i = 0; i < n; ++i) {
                if (!isHChenge) {
                    er[i] = ((k4[i].q.x * 2 - k3[i].q.x * 3 - k5[i].q.x) * (k4[i].q.x * 2 - k3[i].q.x * 3 - k5[i].q.x) + (k4[i].q.y * 2 - k3[i].q.y * 3 - k5[i].q.y) * (k4[i].q.y * 2 - k3[i].q.y * 3 - k5[i].q.y));
                    if (er[i] > (eps * eps)) {
                        h = h * 0.5;
                        isHChenge = true;
                        correct = false;
                    }
                    erV[i] = (k4[i].dqdt.x * 2 - k3[i].dqdt.x * 3 - k5[i].dqdt.x) * (k4[i].dqdt.x * 2 - k3[i].dqdt.x * 3 - k5[i].dqdt.x) + (k4[i].dqdt.y * 2 - k3[i].dqdt.y * 3 - k5[i].dqdt.y) * (k4[i].dqdt.y * 2 - k3[i].dqdt.y * 3 - k5[i].dqdt.y);
                    if (erV[i] > (epsV * epsV)) {
                        h = h * 0.5;
                        isHChenge = true;
                        correct = false;
                    }
                }
            }
        }
        correct = false;

        //Final

        for (int i = 0; i < n; ++i) {
            ball[i].q.q.x += (k4[i].q.x + k5[i].q.x) * 0.5;
            ball[i].q.q.y += (k4[i].q.y + k5[i].q.y) * 0.5;
            ball[i].q.dqdt.x += (k4[i].dqdt.x + k5[i].dqdt.x) * 0.5;
            ball[i].q.dqdt.y += (k4[i].dqdt.y + k5[i].dqdt.y) * 0.5;
        }

        //Error

        for (int i = 0; i < n; ++i) {
            if (!isHChenge) {
                if (er[i] > (o32 * o32 * eps * eps)) {
                    isHChenge = true;
                }
            }
        }
        if (!isHChenge) {
            h = h * 2;
        }
        isHChenge = false;
        return 0;
    }

}

class doblCoord {
    double x;
    double y;

    doblCoord() {
        x = 0;
        y = 0;
    }
}

class ddoblCoord {
    doblCoord q;
    doblCoord dqdt;

    ddoblCoord() {
        q = new doblCoord();
        dqdt = new doblCoord();
    }
}

class Listner implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private double x = 0;
    private double y = 0;
    private boolean pressed = false;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            x = e.getX();
            y = e.getY();
            pressed = true;
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            for (int i = 0; i < Main.n; ++i) {
                Main.ball[i].q.dqdt = new doblCoord();
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            Canvas1.dX = 0;
            Canvas1.dY = 0;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            pressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pressed) {
            x = x - e.getX();
            y = y - e.getY();
            Canvas1.dX += x * 1.0 / Canvas1.h;
            Canvas1.dY += y * 1.0 / Canvas1.h;
            x = e.getX();
            y = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        Canvas1.h *= Math.pow(1.05, -notches);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            Main.gravity *= 1.01;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Main.gravity *= 1.0 / 1.01;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
