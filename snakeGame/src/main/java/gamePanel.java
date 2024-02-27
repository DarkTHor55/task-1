import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener {
    // Constants and member variables...


    final int SCREEN_WIDTH = 600;
    final int SCREEN_HEIGTH = 600;
    final int UNIT_SIZE = 20;
    final int GAME_UNIT = (SCREEN_HEIGTH * SCREEN_WIDTH) / UNIT_SIZE;
    final int DELAY = 205;
    final int x[] = new int[GAME_UNIT];
    final int y[] = new int[GAME_UNIT];
    int bodyParts = 6;
    int forgEaten;
    int frogX;
    int frogY;
    char dir = 'R';// in starting snake moving towords right side
    boolean running = false;
    Timer timer;
    Random random;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (running) {
            move();
            checkFrog();
            checkCllision();


        }
        repaint();
    }
    // Constructor code..
    gamePanel() {

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGTH));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdpter());
        startGame();
        this.requestFocusInWindow();
    }


    public void startGame() {

        newFrog();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();


    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {

// here we can user matrix for easy understanding
//            for (int i = 0; i < SCREEN_HEIGTH / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGTH);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//
//            }
            g.setColor(Color.red);
            g.fillOval(frogX, frogY, UNIT_SIZE, UNIT_SIZE);
            for (int b = 0; b < bodyParts; b++) {
                if (b == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.cyan);
                    g.fillRect(x[b], y[b], UNIT_SIZE, UNIT_SIZE);

                }

                g.setColor(Color.red);
                g.setColor(new Color(random.nextInt(255)));
                g.setFont(new Font("Ink Free", Font.BOLD, 40));

            }
        }else {
            gameOver(g);
        }
    }

    public void newFrog() {
        frogX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        frogY = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (dir) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;

            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;


        }

    }

    public void checkFrog() {
        if ((x[0] == frogX) && (y[0] == frogY)) {
            bodyParts++;
            forgEaten++;
            newFrog();
        }
    }

    public void checkCllision() {
        //head touch body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //head touch left border
        if (x[0] < 0) {
            running = false;
        }
        //head touch Right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //head touch top border
        if (y[0] < 0) {
            running = false;
        }
        //head touch bottom border
        if (y[0] > SCREEN_HEIGTH) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        //gameIver
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,50));
        FontMetrics metrics=g.getFontMetrics(g.getFont());
        g.drawString("Score : "+forgEaten,(SCREEN_WIDTH-metrics.stringWidth("Game Over "))/2,SCREEN_HEIGTH/2);
    }
    /**
     * Handles key events for controlling the snake's movement.
     */
    public class myKeyAdpter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (dir != 'R') dir = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir != 'L') dir = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (dir != 'D') dir = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (dir != 'U') dir = 'D';
                    break;
            }
        }

    }

}
