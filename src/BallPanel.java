import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BallPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private JLabel ball;
    private ImageIcon image = new ImageIcon(".idea/images/golfBall.png");
    private double xPos;
    private double yPos;
    private double velocityX;
    private double velocityY;
    private boolean dragging = false;
    private Point star = new Point();
    private Point fin = new Point();
    private Timer timer;
    private Board board = new Board();
    private boolean shot = false;

    public BallPanel() {
        this.ball = new JLabel(image);
        this.setLayout(null);
        this.add(ball);
        ball.setBounds((int) xPos, (int) yPos, image.getIconWidth(), image.getIconHeight());
        addMouseListener(this);
        addMouseMotionListener(this);

        timer = new Timer(20, this);
        timer.start();
        resetBall();
    }

    private void resetBall() {
        xPos = board.getStartX();
        yPos = board.getStartY();
        velocityX = 0;
        velocityY = 0;
        shot = false;
        ball.setBounds((int)xPos, (int)yPos, image.getIconWidth(), image.getIconHeight());
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (ball.getBounds().contains(e.getPoint()) && (velocityX == 0 && velocityY == 0)) {
            dragging = true;
            star.setLocation(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragging) {
            fin.setLocation(e.getX(), e.getY());
            double x = star.getX() - fin.getX();
            double y = star.getY() - fin.getY();
            velocityX = x / 8.0;
            velocityY = y / 8.0;
            dragging = false;
            shot = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!dragging) {
            double nextX = xPos + velocityX;
            double nextY = yPos + velocityY;

            int ballWidth = image.getIconWidth();
            int ballHeight = image.getIconHeight();

            Rectangle nbX = new Rectangle((int) nextX, (int) yPos, ballWidth, ballHeight);
            Rectangle nbY = new Rectangle((int) xPos, (int) nextY, ballWidth, ballHeight);

            if (collideWall(nbX)) {
                velocityX *= -1;
            } else {
                xPos = nextX;
            }

            if (collideWall(nbY)) {
                velocityY *= -1;
            } else {
                yPos = nextY;
            }

            int tile = board.getTileAt((int)(xPos + ballWidth / 2), (int)(yPos + ballHeight / 2));
            if (tile == 6) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "You lost, you hit the lava!");
                System.exit(0);
            }
            double friction = 0.98;
            if (tile == 4) {
                friction = 1.0;
            } else if (tile == 5) {
                friction = 0.90;
            }

            velocityX *= friction;
            velocityY *= friction;

            if (Math.abs(velocityX) < 0.2 || Math.abs(velocityY) < 0.2) {
                velocityX = 0;
                velocityY = 0;
            }

            if (tile == 2 && Math.abs(velocityX) < 10.0 && Math.abs(velocityY) < 10.0) {
                board = new Board();
                resetBall();
            }

            if (velocityX == 0 && velocityY == 0 && shot) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "You lost, no hole in one!");
                System.exit(0);
            }

            ball.setBounds((int) xPos, (int) yPos, ballWidth, ballHeight);
            repaint();
        }
    }

    private boolean collideWall(Rectangle b) {
        for (int x = b.x; x < b.x + b.width; x++) {
            for (int y = b.y; y < b.y + b.height; y++) {
                if (board.isWallAt(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void mouseMoved(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
}
