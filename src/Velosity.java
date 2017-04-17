import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Velosity extends Thread {
    Velosity(){
        start();
    }
    private static JFrame Vel = new JFrame("Vel");
    static BufferedImage img;
    static Graphics2D g;
    static int steps=50;
    static double[] c=new double[steps];

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
        Vel.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Vel.setLocation(0, 0);
        Vel.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height / 4));
        Vel.add(new Canvas3());
        Vel.setVisible(true);
        img = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();
        g.setColor(Color.black);
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            Vel.repaint();
        }
    }
}
