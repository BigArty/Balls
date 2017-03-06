import javax.swing.*;
import java.awt.*;
import java.awt.image.RescaleOp;

public class Canvas2 extends JComponent {
    private float[] scales = { 1f, 1f, 1f, 1f };
    private float[] offsets = new float[4];
    private RescaleOp rop = new RescaleOp(scales, offsets, null);
    public void paintComponent(Graphics a) {
        setOpaque(false);
        Graphics2D g = (Graphics2D) a;
        g.drawImage(Energy.img,rop,0,0);
        g.drawLine(30,200,30+(int)(100000*Energy.x/Main.M),200+(int)(100000*Energy.y/Main.M));
    }
}
