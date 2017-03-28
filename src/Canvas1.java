import javax.swing.*;
import java.awt.*;



public class Canvas1 extends JComponent {
    static double dX=0;
    static double dY=0;
    static double h=1;
    public void paintComponent(Graphics a) {
        setOpaque(false);
        Graphics2D g = (Graphics2D) a;
        for(int i=0;i<Main.n;i++){
            g.setColor(Main.ball[i].c);
            g.fillOval((int)(((Main.ball[i].q.q.y)-Main.ball[i].r)*h-dX),(int)(((Main.ball[i].q.q.x)-Main.ball[i].r)*h-dY),(int)((Main.ball[i].r+2)*2*h),(int)((Main.ball[i].r+2)*2*h));
            g.setColor(Color.black);
            g.drawString(i+"",(int)((Main.ball[i].q.q.y)*h-dX), (int) (((Main.ball[i].q.q.x))*h-dY));
            g.fillOval((int)(((Energy.y)/Main.M-5)*h-dX),(int)(((Energy.x)/Main.M-5)*h-dY),10,10);
        }

    }
}
