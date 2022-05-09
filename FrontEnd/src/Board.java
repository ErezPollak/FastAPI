import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JFrame {

    private GraphicsClass gc;

    public Board() {

        setBounds(5, 5, 1015, 565);

        gc = new GraphicsClass();

        //adding the graphics class as a component to the frame.
        add(gc);

        //adding the listeners of the keyboard to the frame as a component too.
        addKeyListener(new TAdapter());


    }

    class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT -> {

                }
                case KeyEvent.VK_RIGHT -> {

                }
                case KeyEvent.VK_UP -> {

                }

                case KeyEvent.VK_DOWN -> {

                }

            }
        }
    }

    public static void main(String[] args) {
        Board b = new Board();
        b.setVisible(true);
    }

}


