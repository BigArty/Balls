import javax.swing.*;
import java.awt.*;

public class Drowing extends Thread {
    Drowing(){
        start();
    }
    private static JFrame f = new JFrame("Balls");
    private static int width = Main.width;
    private static int height = Main.height;

    @Override
    public void run() {
        f.setLocation(0, 0);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(width / 4, height / 4));
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.add(new Canvas1());
        f.setVisible(true);
        Listner L = new Listner();
        f.addMouseListener(L);
        f.addMouseMotionListener(L);
        f.addMouseWheelListener(L);
        f.addKeyListener(L);
        System.out.println("window");
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
            f.repaint();
        }
    }
}
