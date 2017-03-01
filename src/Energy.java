import javax.swing.*;
import java.awt.*;

public class Energy extends Thread {
    Energy() {
        start();
    }
    private static JFrame En=new JFrame("Energy");
    @Override
    public void run() {
        En.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        En.setLocation(0, 0);
        En.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height / 4));
        En.setVisible(true);


    }
}
