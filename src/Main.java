import javax.swing.*;
import java.awt.*;

public class Main {
    static double gravity = 0.03;
    static int n = 100;
    static Ball[] ball;
    static JFrame f = new JFrame("Balls");
    static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static double startH = 0.001;
    private static double eps = 0.001*(n*n/4900);
    private static double epsV = 0.001*n*n/4900;
    private static double h = startH;

    private static doblCoord aHelp = new doblCoord();
    private static doblCoord[] dV = new doblCoord[n];
    private static double E = 0;
    static double M = 0;

    public static void main(String[] args) {
        f.setLocation(0, 0);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(width / 4, height / 4));
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ball = new Ball[n];
        Color c;
        double e;
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
            ball[i].q = new ddoblCoord();
            while (!OK) {
                ball[i].r = (int) (Math.random() * 30) + 10;
                ball[i].q.q.x = (2 * ball[i].r + Math.random() * (height - 4 * ball[i].r));
                ball[i].q.q.y = (2 * ball[i].r + Math.random() * (width - 4 * ball[i].r));
                OK = true;
                for (int j = 0; j < i; ++j) {
                    if (((ball[i].r + ball[j].r + 4) * (ball[i].r + ball[j].r + 4)) > ((ball[i].q.q.x - ball[j].q.q.x) * (ball[i].q.q.x - ball[j].q.q.x) + (ball[i].q.q.y - ball[j].q.q.y) * (ball[i].q.q.y - ball[j].q.q.y))) {
                        OK = false;
                    }
                }
            }
            OK = false;
            ball[i].q.dqdt.x = ((Math.random() * 1)) / 100;
            ball[i].q.dqdt.y = ((Math.random() * 1)) / 100;
            int col = (int) (Math.random() * 10);
            ball[i].m = ball[i].r * ball[i].r;
            ball[i].m1 = 1.0 / ball[i].m;
            System.out.println(ball[i].m + " " + ball[i].m1);
            E += ball[i].m * (ball[i].q.dqdt.x * ball[i].q.dqdt.x + ball[i].q.dqdt.y * ball[i].q.dqdt.y);
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
            dV[i] = new doblCoord();
        }
        System.out.println("window");
        f.add(new Canvas1());
        f.setVisible(true);
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


        new Energy();


        doblCoord V1n = new doblCoord();
        doblCoord V2n = new doblCoord();
        doblCoord V1n2 = new doblCoord();
        doblCoord V2n2 = new doblCoord();
        doblCoord H = new doblCoord();
        double norm, t = 0;
        doblCoord iHelp = new doblCoord();

        while (true) {
            t = 0;
            /*try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }*/
            f.repaint();
            while (t < 0.09) {
                t += h;
                mersonStep();
            }
            /*for (int i = 0; i < n; ++i) {
                if ((ball[i].q.q.x > height - ball[i].r)) {
                    ball[i].q.dqdt.x = -ball[i].q.dqdt.x;
                    ball[i].q.q.x = ball[i].q.q.x - 1;
                } else {
                    if ((ball[i].q.q.x < ball[i].r)) {
                        ball[i].q.dqdt.x = -ball[i].q.dqdt.x;
                        ball[i].q.q.x = ball[i].q.q.x + 1;

                    }
                }
                if ((ball[i].q.q.y > width - ball[i].r)) {
                    ball[i].q.dqdt.y = -ball[i].q.dqdt.y;
                    ball[i].q.q.y = ball[i].q.q.y - 1;
                } else {
                    if ((ball[i].q.q.y < ball[i].r)) {

                        ball[i].q.dqdt.y = -ball[i].q.dqdt.y;
                        ball[i].q.q.y = ball[i].q.q.y + 1;
                    }
                }
            }*/

            /*e = 0;
            for (int i = 0; i < n; ++i) {
                dV[i].x = 0;
                dV[i].y = 0;
                ball[i].q.q.x = ball[i].q.q.x + ball[i].q.dqdt.x;
                ball[i].q.q.y = ball[i].q.q.y + ball[i].q.dqdt.y;
                if ((ball[i].q.q.x > height - ball[i].r) || (ball[i].q.q.x < ball[i].r)) {
                    ball[i].q.dqdt.x = -ball[i].q.dqdt.x;
                    ball[i].q.q.x = ball[i].q.q.x + ball[i].q.dqdt.x;
                }
                if ((ball[i].q.q.y > width - ball[i].r) || (ball[i].q.q.y < ball[i].r)) {
                    ball[i].q.dqdt.y = -ball[i].q.dqdt.y;
                    ball[i].q.q.y = ball[i].q.q.y + ball[i].q.dqdt.y;
                }

                e += ball[i].m * (ball[i].q.dqdt.x * ball[i].q.dqdt.x + ball[i].q.dqdt.y * ball[i].q.dqdt.y);

            }
            if (E > e * 1.2) {
                for (int i = 0; i < n; ++i) {
                    ball[i].q.dqdt.x *= 1.08;
                    ball[i].q.dqdt.y *= 1.08;
                    //System.out.println(1+"");
                }
            }
            if (e > E * 1.2) {
                for (int i = 0; i < n; ++i) {
                    ball[i].q.dqdt.x *= 0.91;
                    ball[i].q.dqdt.y *= 0.91;
                }
            }
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    if (((ball[i].q.q.x - ball[j].q.q.x) * (ball[i].q.q.x - ball[j].q.q.x) + (ball[i].q.q.y - ball[j].q.q.y) * (ball[i].q.q.y - ball[j].q.q.y)) <= ((ball[i].r + ball[j].r) * (ball[i].r + ball[j].r))) {

                        H.x = ball[i].q.q.x - ball[j].q.q.x;
                        H.y = ball[i].q.q.y - ball[j].q.q.y;
                        norm = Math.sqrt(H.x * H.x + H.y * H.y);
                        norm = 1.0 / norm;
                        H.x = H.x * norm;
                        H.y = H.y * norm;

                        mul((ball[i].q.dqdt.x * H.x + ball[i].q.dqdt.y * H.y), H);
                        V1n.x = aHelp.x;
                        V1n.y = aHelp.y;
                        mul((ball[j].q.dqdt.x * H.x + ball[j].q.dqdt.y * H.y), H);
                        V2n.x = aHelp.x;
                        V2n.y = aHelp.y;
                        iHelp.x = V2n.x - V1n.x;
                        iHelp.y = V2n.y - V1n.y;
                        if ((iHelp.x * H.x + iHelp.y * H.y) > 0) {
                            dV[i].x -= V1n.x;
                            dV[i].y -= V1n.y;
                            dV[j].x -= V2n.x;
                            dV[j].y -= V2n.y;

                            mul(2 * ball[i].m, V1n);
                            V2n2.x = aHelp.x;
                            V2n2.y = aHelp.y;
                            mul(ball[j].m - ball[i].m, V2n);
                            V2n2 = sum(V2n2, aHelp);
                            //System.out.println(V2n2.x+" "+V2n2.y);
                            mul(1.0 / (ball[j].m + ball[i].m), V2n2);
                            dV[j].x += aHelp.x;
                            dV[j].y += aHelp.y;
                            mul(2 * ball[j].m, V2n);
                            V1n2.x = aHelp.x;
                            V2n2.y = aHelp.y;
                            mul(ball[i].m - ball[j].m, V1n);
                            V1n2 = sum(V1n2, aHelp);
                            mul(1.0 / (ball[j].m + ball[i].m), V1n2);
                            dV[i].x += aHelp.x;
                            dV[i].y += aHelp.y;
                        }
                    }

                }
            }
            for (int i = 0; i < n; ++i) {
                if ((dV[i].x < 1000) && (dV[i].y < 1000)) {
                    ball[i].q.dqdt.x += dV[i].x;
                    dV[i].x = 0;
                    ball[i].q.dqdt.y += dV[i].y;
                    dV[i].y = 0;
                }
            }*/
        }
    }

    private static void mul(double t, doblCoord v) {
        aHelp.x = v.x * t;
        aHelp.y = v.y * t;
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

    private static doblCoord sum(doblCoord o1, doblCoord o2) {
        o1.x = o1.x + o2.x;
        o1.y = o1.y + o2.y;
        return o1;
    }

    private static ddoblCoord sum(ddoblCoord o1, ddoblCoord o2) {
        o1.q.x = o1.q.x + o2.q.x;
        o1.q.y = o1.q.y + o2.q.y;
        o1.dqdt.x = o1.dqdt.x + o2.dqdt.x;
        o1.dqdt.y = o1.dqdt.y + o2.dqdt.y;
        return o1;
    }

    private static double helpDouble;
    private static double helpDouble2;
    private static double helpDouble3;
    private static doblCoord N = new doblCoord();
    private static double dist2 = 0;

    private static ddoblCoord f(int i, int j, ddoblCoord F, ddoblCoord[] a) {
        dist2 = (a[i].q.x - a[j].q.x) * (a[i].q.x - a[j].q.x) + (a[i].q.y - a[j].q.y) * (a[i].q.y - a[j].q.y);
        if ((dist2) > ((ball[i].r + ball[j].r) * (ball[i].r + ball[j].r))) {
            N.x = a[i].q.x - a[j].q.x;
            N.y = a[i].q.y - a[j].q.y;
            if ((dist2) <= ((ball[i].r + ball[j].r + 8) * (ball[i].r + ball[j].r + 8))) {
                helpDouble2 = 1000.0 / (dist2 - (ball[i].r + ball[j].r) * (ball[i].r + ball[j].r));
            } else {
                helpDouble = 1/(dist2*dist2*dist2);
                helpDouble3 = Math.sqrt(helpDouble);
                helpDouble2 = -gravity * ball[i].m * ball[j].m * (helpDouble3);
            }
            mul(helpDouble2, N, F.dqdt);

        } else {
            F.dqdt.x = 0;
            F.dqdt.y = 0;
        }
        F.q.x = a[i].dqdt.x;
        F.q.y = a[i].dqdt.y;

        return F;
    }

    private static void mem(ddoblCoord dest, ddoblCoord from) {
        dest.q.x = from.q.x;
        dest.q.y = from.q.y;
        dest.dqdt.x = from.dqdt.x;
        dest.dqdt.y = from.dqdt.y;
    }

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

    static int mersonStep() {
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
