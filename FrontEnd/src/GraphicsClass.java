import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GraphicsClass extends JPanel {

    Random r = new Random();

    private int n = 0;

    private int max_n = 100;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        n += 1;

        g.setColor(new Color(r.nextInt(50,100),0,0));
        g.fillOval(n % max_n,0, 100,100);

        g.setColor(new Color(0,r.nextInt(50,100),0));
        g.fillOval(max_n - n % max_n,200 ,100, 100);

        repaint();

    }

    public void set_n(int value){
        this.max_n = value;
    }

}
