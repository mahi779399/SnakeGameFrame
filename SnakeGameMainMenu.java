import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeGameMainMenu implements ActionListener{
    private JFrame mainFrame;  
    public static JLabel scoreLabel;
    static JButton[] buttons;
    JPanel buttonsPanel;
    MainMenuActions menuAction;
    static Color initialColor=MainMenuActions.initialColor;

    public SnakeGameMainMenu() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Snake Game - Main Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setResizable(true);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(600, 600);
        mainFrame.getContentPane().setBackground(Color.BLACK);
        mainFrame.setMinimumSize(new Dimension(400, 400));

        frameCenter(mainFrame);
        frameIcon(mainFrame);
        addComponents();

        mainFrame.setVisible(true);
    }

    public static void frameIcon(JFrame frame) {
        try {
            Image icon = Toolkit.getDefaultToolkit().getImage(SnakeGameMainMenu.class.getResource("/SnakeImages/SnakeIcon.png"));
            frame.setIconImage(icon);
        } catch (Exception e) {
            System.err.println("Icon image not found.");
        }
    }

    public static void frameCenter(JFrame frame) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        Dimension frameSize = frame.getSize();
        int x = centerPoint.x - frameSize.width / 2;
        int y = centerPoint.y - frameSize.height / 2;
        frame.setLocation(x, y);
    }
    public void addComponents() {
        scoreLabel = new JLabel("Top Score : 0");
        Font font = scoreLabel.getFont();
        Font boldFont = new Font(font.getName(), Font.BOLD, 16);
        scoreLabel.setFont(boldFont);
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        scoreLabel.setForeground(initialColor);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 1, 0, 5));
        buttonsPanel.setBackground(Color.BLACK);

        menuAction=new MainMenuActions();
        String[] buttonLabels = {"New Game", "Level", "Theme", "About"};
        buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = createButton(buttonLabels[i]);
            buttonsPanel.add(buttons[i]);
            buttons[i].addActionListener(this); 
        }

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(buttonsPanel, gbc);

        mainFrame.add(scoreLabel, BorderLayout.NORTH);
        mainFrame.add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setBackground(initialColor);
        button.setForeground(Color.BLACK);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        for (int i = 0; i < buttons.length; i++) {
            if (clickedButton == buttons[i]) {
                switch (i) {
                    case 0:
                        menuAction.newGameButtonAction();
                        break;
                    case 1:
                        menuAction.levelButtonAction();
                        break;
                    case 2:
                        menuAction.ThemeButtonAction();
                        break;
                    case 3:
                        menuAction.aboutButtonAction();
                }
                break;
            }
        }
    }

    public static void updateTopScoreLabel(int score) {
        scoreLabel.setText("Top Score : " + score);
    }
    public static void main(String[] args) {
        new SnakeGameMainMenu();
    }
}