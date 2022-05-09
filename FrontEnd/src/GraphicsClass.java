import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GraphicsClass extends JPanel {

    Random r = new Random();

    private int n = 0;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        n += 1;

        g.setColor(new Color(r.nextInt(0,100),0,0));
        g.fillOval(100,100,n % 100,n % 100);

        g.setColor(new Color(0,r.nextInt(0,100),0));
        g.fillOval(500,100,n % 100,n % 100);

        repaint();

    }

}
