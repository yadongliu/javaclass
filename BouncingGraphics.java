import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;

class GameCircle {
    public int dx;
    public int dy;
    int centerX, centerY;
    int radius;
    Color color;

    GameCircle(int dx, int dy, int cx, int cy, int r, Color c) {
        this.dx = dx;
        this.dy = dy;
        this.centerX = cx;
        this.centerY = cy;
        this.radius = r;
        this.color = c;
    }

    public void updateCenter() {
        this.centerX += dx;
        this.centerY += dy;
    }

    // Accessor for center
    public int getCenterX() {
        return this.centerX;
    }

    public int getCenterY() {
        return this.centerY;
    }

    public void setDX(int dx) {
        this.dx = dx;
    }

    public void setDY(int dy) {
        this.dy = dy;
    }
}

class GamePanel extends JPanel {
    java.util.List<GameCircle> circles;

    GamePanel() {
        super();
        setupGame();
    }

    public void setupGame() {
        circles = new ArrayList<GameCircle>();

        GameCircle gc = new GameCircle(3, 4, 50, 50, 10, Color.yellow);
        circles.add(gc);
        gc = new GameCircle(6, 5, 100, 125, 10, Color.red);
        circles.add(gc);

        gc = new GameCircle(10, 9, 290, 290, 10, Color.magenta);
        circles.add(gc);

    }

    public void runGame() {
        // System.out.println("(x, y)" + center.x + ","+center.y);
        this.gameUpdate();
        this.doDrawing(getGraphics());
    }

    private void gameUpdate() {
        for(int i=0; i<circles.size(); i++) {
            GameCircle gc = circles.get(i);
            gc.updateCenter();

            if(gc.getCenterX()>getWidth()-gc.radius) {
                gc.dx = -gc.dx;
                gc.centerX = getWidth()-gc.radius;
            } else if(gc.getCenterX()<gc.radius) {
                gc.dx = -gc.dx;
                gc.centerX = gc.radius;
            }

            if(gc.getCenterY()>getHeight()-gc.radius) {
                gc.dy = -gc.dy;
                gc.centerY = getHeight()-gc.radius;
            } else if(gc.getCenterY()<gc.radius) {
                gc.dy = -gc.dy;
                gc.centerY = gc.radius;
            }
        }
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.clearRect(0, 0, getWidth(), getHeight());

        for(int i=0; i<circles.size(); i++) {
            GameCircle gc = circles.get(i);

            g2d.setPaint(gc.color);
            this.drawCircle(g2d, gc.getCenterX(), gc.getCenterY(), gc.radius);
        }
    }

    // Private helper method to draw a circle
    private void drawCircle(Graphics2D g2d, int x, int y, int radius) {
        g2d.fillOval(x-radius, y-radius, 2*radius, 2*radius);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}

public class BouncingGraphics extends JFrame {
    static final int GAME_INTERVAL = 30;
    GamePanel gamePanel;
    public BouncingGraphics() {
        /*
        ActionListener animate = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                gamePanel.runGame();
            }
        };
        Timer timer = new Timer(GAME_INTERVAL,animate);
        timer.start();
        */
        initUI();
    }

    private void initUI() {
        gamePanel = new GamePanel();
        add(gamePanel);

        setTitle("Graphics Examples");
        setSize(480, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame() {
        while(true) {
            gamePanel.runGame();
            try {
                Thread.sleep(GAME_INTERVAL);
            } catch(InterruptedException e) {

            }
        }
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                BouncingGraphics lg = new BouncingGraphics();
                lg.setVisible(true);

                lg.startGame();
            }
        });
    }
}

