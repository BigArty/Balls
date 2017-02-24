import javax.swing.*;
import java.awt.*;

public class Main {
    static int n = 30;
    static Ball[] ball;
    static JFrame f = new JFrame("Balls");
    static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    static doblCoord aHelp = new doblCoord();
    static doblCoord[] dV=new doblCoord[n];
    static double E=0;
    public static void main(String[] args) {
        f.setLocation(0, 0);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(width / 4, height / 4));
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ball = new Ball[n];
        Color c;
        double e=0;

        for (int i = 0; i < n; i++) {
            ball[i] = new Ball();
            ball[i].r = (int) (Math.random() * 40) + 20;
            ball[i].x = (2 * ball[i].r + Math.random() * (height - 4 * ball[i].r));
            ball[i].y = (2 * ball[i].r + Math.random() * (width - 4 * ball[i].r));

            ball[i].vX = (Math.random() * 8) - 4;
            ball[i].vY = (Math.random() * 8) - 4;
            int col = (int) (Math.random() * 10);
            ball[i].m=1 ;
            E+=ball[i].m*(ball[i].vX*ball[i].vX+ball[i].vY*ball[i].vY);
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
            dV[i]=new doblCoord();
        }
        f.add(new Canvas1());
        f.setVisible(true);
        doblCoord V1n= new doblCoord();
        doblCoord V2n= new doblCoord();
        doblCoord V1n2= new doblCoord();
        doblCoord V2n2= new doblCoord();
        doblCoord H = new doblCoord();
        double norm;
        doblCoord iHelp=new doblCoord();

        while (true) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {

            }
            f.repaint();
            e=0;
            for (int i = 0; i < n; ++i) {
                dV[i].x=0;
                dV[i].y=0;
                ball[i].x = ball[i].x + ball[i].vX;
                ball[i].y = ball[i].y + ball[i].vY;
                if ((ball[i].x > height-ball[i].r) || (ball[i].x < ball[i].r)) {
                    ball[i].vX = -ball[i].vX;
                    ball[i].x = ball[i].x + ball[i].vX;
                }
                if ((ball[i].y > width - ball[i].r) || (ball[i].y < ball[i].r)) {
                    ball[i].vY = -ball[i].vY;
                    ball[i].y = ball[i].y + ball[i].vY;
                }

                e+=ball[i].m*(ball[i].vX*ball[i].vX+ball[i].vY*ball[i].vY);

            }
            if(E>e*1.2){
                for(int i=0;i< n;++i){
                    ball[i].vX*=1.08;
                    ball[i].vY*=1.08;
                    //System.out.println(1+"");
                }
            }
            if(e>E*1.2){
                for(int i=0;i< n;++i){
                    ball[i].vX*=0.91;
                    ball[i].vY*=0.91;
                }
            }
            for(int i=0;i<n;++i){
                for (int j = i + 1; j < n; ++j) {
                    if (((ball[i].x - ball[j].x) * (ball[i].x - ball[j].x) + (ball[i].y - ball[j].y) * (ball[i].y - ball[j].y)) <= ((ball[i].r + ball[j].r) * (ball[i].r + ball[j].r))) {

                        H.x = ball[i].x - ball[j].x;
                        H.y = ball[i].y - ball[j].y;
                        norm = Math.sqrt(H.x * H.x + H.y * H.y);
                        norm = 1.0 / norm;
                        H.x = H.x * norm;
                        H.y = H.y * norm;

                        mul((ball[i].vX * H.x + ball[i].vY * H.y), H);
                        V1n.x = aHelp.x;
                        V1n.y = aHelp.y;
                        mul((ball[j].vX * H.x + ball[j].vY * H.y), H);
                        V2n.x = aHelp.x;
                        V2n.y = aHelp.y;
                        iHelp.x=V2n.x-V1n.x;
                        iHelp.y=V2n.y-V1n.y;
                        if((iHelp.x*H.x+iHelp.y*H.y)>0) {
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
            for(int i=0;i<n;++i){
                if((dV[i].x<1000)&&(dV[i].y<1000)) {
                    ball[i].vX += dV[i].x;
                    dV[i].x = 0;
                    ball[i].vY += dV[i].y;
                    dV[i].y = 0;
                }
            }
        }
    }

    private static void mul(double t, doblCoord v) {
        aHelp.x = v.x * t;
        aHelp.y = v.y * t;
    }

    private static doblCoord sum(doblCoord o1, doblCoord o2) {
        o1.x = o1.x + o2.x;
        o1.y = o1.y + o2.y;
        return o1;
    }
    static doblCoord a(int i, int j, doblCoord A){
        if (((ball[i].x - ball[j].x) * (ball[i].x - ball[j].x) + (ball[i].y - ball[j].y) * (ball[i].y - ball[j].y)) <= ((ball[i].r + ball[j].r) * (ball[i].r + ball[j].r))) {
            int a;

        }
        else{
            A.x=0;
            A.y=0;
        }

            return A;
    }

}

class doblCoord {
    double x;
    double y;
}
