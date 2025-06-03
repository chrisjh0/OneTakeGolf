import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        // frame initialization
        JFrame frame = new JFrame("One Take Golf");
        frame.setSize(654, 677);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // ball initialization
        BallPanel ball = new BallPanel();

        // frame finalization
        frame.add(ball);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}