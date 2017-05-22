import java.awt.*;

public class Temperature extends Thread  {
    Temperature(){
        start();
    }
    double[] v=new double[Main.n];
    double vMax;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        int i;

        int red;
        int blue;
        int t=0;
        while(true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
            vMax = 0;
            for (i = 0; i < Main.n; ++i) {
                v[i] = Math.sqrt(Main.ball[i].q.dqdt.x * Main.ball[i].q.dqdt.x + Main.ball[i].q.dqdt.y * Main.ball[i].q.dqdt.y);
                if (v[i] > vMax) {
                    vMax = v[i];
                }
            }


            for(i=0;i<Main.n;++i){
                v[i]=v[i]/vMax;
                v[i]=Math.sqrt(v[i]);
            }

            for(i=0;i<Main.n;++i){
                red= (int) (255*(v[i]));
                blue=255-red;
                Main.ball[i].c=new Color(red, 0, blue);
            }
            if (t<20){
                t++;
            }
            else{
                System.gc();
                t=0;
            }

        }
    }
}
