import javax.swing.*;
import java.awt.*;
import java.awt.image.RescaleOp;

public class Canvas3 extends JComponent {
    double[] v=new double[Main.n];
    double vMax;
    double step;
    int j=0;
    double cMax;
    double k=0;
    public void paintComponent(Graphics a) {
        cMax=0;
        vMax=0;
        setOpaque(false);
        Graphics2D g = (Graphics2D) a;
        for(int i=0;i<Main.n;++i){
            v[i]=Math.sqrt(Main.ball[i].q.dqdt.x*Main.ball[i].q.dqdt.x+Main.ball[i].q.dqdt.y*Main.ball[i].q.dqdt.y);
            if(v[i]>vMax){
                vMax=v[i];
            }
        }
        step=vMax/(Velosity.steps-1);
        for(int i=0;i<Main.n;++i){
            j=(int)(v[i]/step);
            Velosity.c[j]++;
        }
        for(int i=0;i<Velosity.steps;++i){
            if(Velosity.c[i]>cMax){
                cMax=Velosity.c[i];
            }
        }
        k=500.0/cMax;
        for(int i=0;i<Velosity.steps;++i){
            g.drawRect(i*10,0,10,(int)(Velosity.c[i]*k));
        }
    }
}