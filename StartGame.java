import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

class StartGame extends JFrame {
    GamePanel2 gamePanel2;
    static JFrame gameFrame;

    public StartGame() {
        setTitle("Snake Game");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gamePanel2 = new GamePanel2();
        add(gamePanel2);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        SnakeGameMainMenu.frameIcon(this);
        setVisible(true);
        gameFrame=this;
    }
}

class GamePanel2 extends JPanel implements ActionListener {
    private static final int BOARD_WIDTH = 400;
    private static final int BOARD_HEIGHT = 400;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (BOARD_WIDTH * BOARD_HEIGHT) / UNIT_SIZE;
    private static int DELAY = 50;
    private final int[] snakeX = new int[GAME_UNITS];
    private final int[] snakeY = new int[GAME_UNITS];
    private int snakeLength;
    private int foodX;
    private int foodY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private final Random random;
    static int highestScore = 0;
    Color initialColor = Color.RED; 
    int levelSelected = 0; 

    public GamePanel2() {
        random = new Random();
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.setBackground(Color.BLACK); 
        this.setFocusable(true);
        this.addKeyListener(new GameKeyAdapter());
        setSpeed(levelSelected);
        startGame();
    }
    public void updateInitialColor(Color newColor) {
        initialColor = newColor;
        repaint(); 
    }
    public void startGame() {
        snakeLength = 3;
        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = 0;
            snakeY[i] = 0;
        }
        placeFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void setSpeed(int levelSelected) {
        switch (levelSelected) {
            case 0:
                DELAY = 100; 
                break;
            case 50:
                DELAY = 50; 
                break;
            case 100:
                DELAY = 20; 
                break;
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.WHITE); 
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < snakeLength; i++) {
                if (i == 0) {
                    g.setColor(initialColor); 
                } else {
                    g.setColor(initialColor);
                }
                g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(initialColor); 
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + (snakeLength - 1), (BOARD_WIDTH - metrics.stringWidth("Score: " + (snakeLength - 1))) / 2, g.getFont().getSize());
        } else {
            gameOver(this);
        }
    }

    public void placeFood() {
        foodX = random.nextInt((int) (BOARD_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((int) (BOARD_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {
            case 'U':
                snakeY[0] = snakeY[0] - UNIT_SIZE;
                break;
            case 'D':
                snakeY[0] = snakeY[0] + UNIT_SIZE;
                break;
            case 'L':
                snakeX[0] = snakeX[0] - UNIT_SIZE;
                break;
            case 'R':
                snakeX[0] = snakeX[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkFood() {
        if ((snakeX[0] == foodX) && (snakeY[0] == foodY)) {
            snakeLength++;
            placeFood();
        }
    }

    public void updateHighestScore() {
        if (snakeLength - 1 > highestScore) {
            highestScore = snakeLength - 1;
        }
    }

    public void checkCollisions() {
        for (int i = snakeLength; i > 0; i--) {
            if ((snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
                running = false;
            }
        }

        if (snakeX[0] < 0 || snakeX[0] >= BOARD_WIDTH || snakeY[0] < 0 || snakeY[0] >= BOARD_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
            updateHighestScore();
            gameOver(this);
        }
    }

    public void gameOver(GamePanel2 gamePanel2) {
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameOverFrame.setResizable(false);
        gameOverFrame.setLayout(new BorderLayout());
        gameOverFrame.setSize(400, 400);
        gameOverFrame.getContentPane().setBackground(Color.BLACK); 

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.setBackground(Color.BLACK); 

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gameOverLabel.setForeground(initialColor); 
        centerPanel.add(gameOverLabel);

        JLabel scoreLabel = new JLabel("Your score: " + (snakeLength - 1));
        scoreLabel.setForeground(initialColor); 
        centerPanel.add(scoreLabel);

        JLabel highestScoreLabel = new JLabel("Highest score: " + highestScore);
        highestScoreLabel.setForeground(initialColor); 
        centerPanel.add(highestScoreLabel);

        gameOverFrame.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBackground(Color.BLACK); 

        JButton quitGameButton = new JButton("Quit Game");
        quitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameOverFrame.dispose();
                System.exit(0);
            }
        });
        quitGameButton.setForeground(Color.BLACK); 
        quitGameButton.setBackground(initialColor); 
        buttonPanel.add(quitGameButton);

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                gameOverFrame.dispose();
                StartGame.gameFrame.dispose();
            }
        });
        mainMenuButton.setForeground(Color.BLACK); 
        mainMenuButton.setBackground(initialColor); 
        buttonPanel.add(mainMenuButton);

        gameOverFrame.add(buttonPanel, BorderLayout.SOUTH);

        gameOverFrame.pack();
        gameOverFrame.setLocationRelativeTo(null);
        gameOverFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    private class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (direction!= 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if (direction!= 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    if (direction!= 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if (direction!= 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}