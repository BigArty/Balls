import javax.swing.*;
import java.awt.*;



public class Canvas1 extends JComponent {

    public void paintComponent(Graphics a) {
        int h=Main.f.getContentPane().getSize().height;
        int w=Main.f.getContentPane().getSize().width;
        setOpaque(false);
        Graphics2D g = (Graphics2D) a;
        for(int i=0;i<Main.n;i++){
            g.setColor(Main.ball[i].c);
            g.fillOval((int)((Main.ball[i].y)-Main.ball[i].r)*w/Main.width,(int)((Main.ball[i].x)-Main.ball[i].r)*h/Main.height,(int)Main.ball[i].r*2*w/Main.width,(int)Main.ball[i].r*2*h/Main.height);
            g.setColor(Color.black);
            //g.drawString(i+"",(int)((Main.ball[i].y)*w/Main.width),(int)((Main.ball[i].x))*h/Main.height);
        }

    }
}
