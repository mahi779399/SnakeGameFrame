import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class MainMenuActions {
    static int levelSelected;
    private JFrame levelFrame;
    private JSlider levelSlider;
    JLabel[] levelLabels;
    private Hashtable<Integer, JLabel> labelTable;
    static Color initialColor = Color.GREEN;
    static int lastSelectedLevel = 50;

    public void newGameButtonAction(){
        SwingUtilities.invokeLater(() -> new StartGame());
    }

    public void levelButtonAction() {
        if (levelFrame == null) {
            levelFrame = new JFrame("Level Selection");
            levelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            levelFrame.setSize(300, 100);
            levelFrame.getContentPane().setBackground(Color.BLACK);
            levelFrame.setLayout(new FlowLayout());
            SnakeGameMainMenu.frameCenter(levelFrame);
            SnakeGameMainMenu.frameIcon(levelFrame);

            levelSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, lastSelectedLevel);
            levelSlider.setMajorTickSpacing(50);
            levelSlider.setMinorTickSpacing(0);
            levelSlider.setPaintTicks(true);
            levelSlider.setPaintLabels(true);
            levelSlider.setSnapToTicks(true);
            levelSlider.setBackground(Color.BLACK);
            levelSlider.setForeground(initialColor);

            labelTable = new Hashtable<>();
            String[] labelStrings = {"Normal", "Medium", "Fast"};
            levelLabels = new JLabel[labelStrings.length];
            int j = 0;
            for (int i = 0; i < labelStrings.length; i++) {
                labelTable.put(j, new JLabel(labelStrings[i]) {{
                    setForeground(initialColor);
                }});
                j += 50;
            }
            levelSlider.setLabelTable(labelTable);

            levelSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider slider = (JSlider) e.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        levelSelected = slider.getValue();
                        lastSelectedLevel = levelSelected;
                    }
                }
            });

            levelFrame.add(levelSlider);
        } else {
            levelSlider.setValue(lastSelectedLevel);
        }
        levelFrame.setVisible(true);
    }

    public void ThemeButtonAction() {
        UIManager.put("ColorChooser.background", Color.BLACK);
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("Button.background", initialColor);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("TabbedPane.background", Color.BLACK);
        UIManager.put("TabbedPane.foreground", Color.WHITE);
        UIManager.put("TabbedPane.selected", initialColor);
        UIManager.put("TabbedPane.selectForeground", Color.WHITE);

        Color chosenColor = JColorChooser.showDialog(null, "Choose a color", initialColor);

        if (chosenColor.equals(Color.BLACK)) {
            JFrame colorPop = new JFrame();
            UIManager.put("OptionPane.messageForeground", initialColor);
            JOptionPane.showMessageDialog(colorPop, "Choose color except 'BLACK'");
        } else {
            if (chosenColor != null) {
                initialColor = chosenColor;
                SnakeGameMainMenu.scoreLabel.setForeground(chosenColor);
                for (JButton button : SnakeGameMainMenu.buttons) {
                    button.setBackground(chosenColor);
                }
                if (levelSlider != null) {
                    levelSlider.setForeground(chosenColor);
                    for (JLabel label : labelTable.values()) {
                        label.setForeground(chosenColor);
                    }
                }
                GamePanel2 gamePanel = new StartGame().gamePanel2;
                gamePanel.updateInitialColor(chosenColor);
            }
        }
    }

    public void aboutButtonAction() {
        JFrame aboutFrame = new JFrame("About");
        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aboutFrame.getContentPane().setBackground(Color.BLACK);
        aboutFrame.setLayout(new BorderLayout());
        SnakeGameMainMenu.frameIcon(aboutFrame);

        JLabel instructionsLabel = new JLabel("Instructions:");
        instructionsLabel.setForeground(initialColor);
        Font font = instructionsLabel.getFont();
        Font boldFont = new Font(font.getName(), Font.BOLD, 16);
        instructionsLabel.setFont(boldFont);

        JTextArea instructionTextArea = new JTextArea("\nWelcome to Snake Game!\n\n"
                + "The goal of the game is to eat as many food pellets as possible while avoiding collision with the wall or itself.\n"
                + "Use the arrow keys and 'WASD' keys to control the direction of the snake.\n"
                + "The game will end if the snake collides with the wall or itself.\n");
        instructionTextArea.setEditable(false);
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setWrapStyleWord(true);
        instructionTextArea.setBackground(Color.BLACK);
        instructionTextArea.setForeground(initialColor);
        instructionTextArea.setPreferredSize(new Dimension(400, 150));

        JTextArea buttonsTextArea = new JTextArea("1. New Game Button: The \"New Game\" button starts a new game of Snake. When clicked, it will launch a new game window where you can play the classic Snake game.\n"
                + "2. Level Button: The \"Level\" button allows you to select the game speed of the Snake game. You can choose from three levels: Normal, Medium, and Fast. The level you select will determine how quickly the snake moves in the game.\n"
                + "3. Theme Button: The \"Theme\" button lets you customize the game's color scheme. You can choose a new color for the buttons, labels, and other UI elements, but please note that selecting black is not allowed. This allows you to personalize the game to your liking.\n"
                + "4. About Button: The \"About\" button displays information about the game, including instructions on how to play. It also provides a brief overview of the game's features and functionality.\n\n"
                + "Good luck and have fun!");
        buttonsTextArea.setEditable(false);
        buttonsTextArea.setLineWrap(true);
        buttonsTextArea.setWrapStyleWord(true);
        buttonsTextArea.setBackground(Color.BLACK);
        buttonsTextArea.setForeground(initialColor);
        buttonsTextArea.setPreferredSize(new Dimension(400, 300));

        JPanel instructionPanel = new JPanel(new BorderLayout());
        instructionPanel.add(instructionsLabel, BorderLayout.NORTH);
        instructionPanel.add(instructionTextArea, BorderLayout.CENTER);
        instructionPanel.setBackground(Color.BLACK);
        instructionPanel.setForeground(initialColor);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(buttonsTextArea, BorderLayout.CENTER);
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.setForeground(initialColor);

        aboutFrame.add(instructionPanel, BorderLayout.NORTH);
        aboutFrame.add(buttonsPanel, BorderLayout.CENTER);
        aboutFrame.pack();
        SnakeGameMainMenu.frameCenter(aboutFrame);
        aboutFrame.setMinimumSize(aboutFrame.getSize());
        aboutFrame.setVisible(true);
    }
}
