import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

// HandCricketGameGUI class extends JFrame to create the main game window.
public class HandCricketGameGUI extends JFrame {

    private GameLogic game; // Instance of the game logic.
    private CardLayout cardLayout; // Manages switching between different game screens.
    private JPanel mainPanel; // The main panel using CardLayout.

    // GUI Components for various stages.
    private JTextField playerNameInput;
    private JLabel welcomeMessageLabel;

    private ButtonGroup tossOddEvenGroup;
    private JPanel tossNumberButtonsPanel;
    private JLabel tossInstructionLabel;

    private JLabel tossResultLabel;
    private JButton batChoiceButton, bowlChoiceButton;

    private ButtonGroup difficultyGroup;

    private JLabel gameMessageLabel;
    private JLabel userScoreLabel, computerScoreLabel, targetLabel;
    private JPanel playNumberButtonsPanel;

    private JLabel finalResultLabel;

    // --- Constructor ---
    public HandCricketGameGUI() {
        setTitle("Hand Cricket Game");
        setSize(600, 700); // Increased window size for better layout.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window.
        
        // Removed custom font loading for easier portability.
        // The application will now use default Swing fonts, or those set by UIManager defaults.
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("RadioButton.font", new Font("Arial", Font.PLAIN, 14));


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        // Initialize and add all the different game stage panels.
        createWelcomePanel();
        createTossPanel();
        createTossResultPanel();
        createDifficultyPanel();
        createGameplayPanel();
        createResultPanel();

        // Start with the welcome panel.
        cardLayout.show(mainPanel, "Welcome");
    }

    // --- Panel Creation Methods ---

    // Creates the initial welcome screen for player name input.
    private void createWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255)); // AliceBlue
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titleLabel = new JLabel("<html><h1 style='color:#4682B4;'>Welcome to Hand Cricket!</h1></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        welcomeMessageLabel = new JLabel("Enter your name to start:");
        welcomeMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeMessageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(welcomeMessageLabel);
        panel.add(Box.createVerticalStrut(10));

        playerNameInput = new JTextField(20);
        playerNameInput.setMaximumSize(new Dimension(300, 30));
        playerNameInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(playerNameInput);
        panel.add(Box.createVerticalStrut(20));

        JButton startButton = createStyledButton("Start Game", new Color(60, 179, 113)); // MediumSeaGreen
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            String name = playerNameInput.getText().trim();
            if (!name.isEmpty()) {
                game = new GameLogic(name); // Initialize game logic with player name.
                welcomeMessageLabel.setText("Hello " + game.getPlayerName() + "! Let's play Hand Cricket!");
                cardLayout.show(mainPanel, "Toss");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your name!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(startButton);

        mainPanel.add(panel, "Welcome");
    }

    // Creates the panel for the toss phase (Odd/Even choice and number selection).
    private void createTossPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(255, 250, 205)); // LemonChiffon
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Top section for instructions
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        tossInstructionLabel = new JLabel("Choose Odd or Even for the toss:");
        tossInstructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tossInstructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(tossInstructionLabel);
        topPanel.add(Box.createVerticalStrut(15));

        // Odd/Even radio buttons
        JPanel oddEvenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        oddEvenPanel.setOpaque(false);
        tossOddEvenGroup = new ButtonGroup();
        JRadioButton oddBtn = new JRadioButton("Odd");
        JRadioButton evenBtn = new JRadioButton("Even");
        oddBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        evenBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        oddBtn.setOpaque(false);
        evenBtn.setOpaque(false);

        tossOddEvenGroup.add(oddBtn);
        tossOddEvenGroup.add(evenBtn);
        oddEvenPanel.add(oddBtn);
        oddEvenPanel.add(evenBtn);
        topPanel.add(oddEvenPanel);
        panel.add(topPanel, BorderLayout.NORTH);

        // Center section for number buttons
        tossNumberButtonsPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        tossNumberButtonsPanel.setOpaque(false);
        addNumberButtons(tossNumberButtonsPanel, e -> handleTossNumberChoice(Integer.parseInt(((JButton)e.getSource()).getText())));
        // Initially disable number buttons until odd/even is chosen
        setNumberButtonsEnabled(tossNumberButtonsPanel, false);

        panel.add(tossNumberButtonsPanel, BorderLayout.CENTER);

        // Add action listeners for Odd/Even buttons to enable number buttons
        oddBtn.addActionListener(e -> {
            tossInstructionLabel.setText("You chose ODD. Now enter your toss number (1-10):");
            setNumberButtonsEnabled(tossNumberButtonsPanel, true);
        });
        evenBtn.addActionListener(e -> {
            tossInstructionLabel.setText("You chose EVEN. Now enter your toss number (1-10):");
            setNumberButtonsEnabled(tossNumberButtonsPanel, true);
        });


        mainPanel.add(panel, "Toss");
    }

    // Creates the panel showing toss results and choice to bat/bowl.
    private void createTossResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(224, 255, 255)); // LightCyan
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        tossResultLabel = new JLabel();
        tossResultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tossResultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(tossResultLabel);
        panel.add(Box.createVerticalStrut(30));

        JPanel choicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        choicePanel.setOpaque(false);
        batChoiceButton = createStyledButton("Bat", new Color(70, 130, 180)); // SteelBlue
        bowlChoiceButton = createStyledButton("Bowl", new Color(100, 149, 237)); // CornflowerBlue

        batChoiceButton.addActionListener(e -> handleBatBowlChoice(true));
        bowlChoiceButton.addActionListener(e -> handleBatBowlChoice(false));

        choicePanel.add(batChoiceButton);
        choicePanel.add(bowlChoiceButton);
        panel.add(choicePanel);

        mainPanel.add(panel, "TossResult");
    }

    // Creates the panel for selecting difficulty.
    private void createDifficultyPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 220)); // Beige
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel difficultyInstructionLabel = new JLabel("Choose Difficulty for the Computer:");
        difficultyInstructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyInstructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(difficultyInstructionLabel);
        panel.add(Box.createVerticalStrut(20));

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        radioPanel.setOpaque(false);
        difficultyGroup = new ButtonGroup();
        JRadioButton easyBtn = new JRadioButton("Easy");
        JRadioButton mediumBtn = new JRadioButton("Medium");
        JRadioButton hardBtn = new JRadioButton("Hard");

        easyBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        mediumBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        hardBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        easyBtn.setOpaque(false);
        mediumBtn.setOpaque(false);
        hardBtn.setOpaque(false);

        easyBtn.addActionListener(e -> setDifficultyAndStart(GameLogic.Difficulty.EASY));
        mediumBtn.addActionListener(e -> setDifficultyAndStart(GameLogic.Difficulty.MEDIUM));
        hardBtn.addActionListener(e -> setDifficultyAndStart(GameLogic.Difficulty.HARD));

        difficultyGroup.add(easyBtn);
        difficultyGroup.add(mediumBtn);
        difficultyGroup.add(hardBtn);
        radioPanel.add(easyBtn);
        radioPanel.add(mediumBtn);
        radioPanel.add(hardBtn);
        panel.add(radioPanel);

        mainPanel.add(panel, "Difficulty");
    }

    // Creates the main gameplay panel where numbers are chosen and scores updated.
    private void createGameplayPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(230, 230, 250)); // Lavender
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Top section for game messages and scores
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        gameMessageLabel = new JLabel("Game in progress...");
        gameMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameMessageLabel.setFont(new Font("Arial", Font.BOLD, 22));
        topPanel.add(gameMessageLabel);
        topPanel.add(Box.createVerticalStrut(15));

        userScoreLabel = new JLabel("Your Score: 0");
        userScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userScoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        userScoreLabel.setForeground(new Color(34, 139, 34)); // ForestGreen
        topPanel.add(userScoreLabel);

        computerScoreLabel = new JLabel("Computer Score: 0");
        computerScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        computerScoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        computerScoreLabel.setForeground(new Color(220, 20, 60)); // Crimson
        topPanel.add(computerScoreLabel);

        targetLabel = new JLabel("Target: --");
        targetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        targetLabel.setFont(new Font("Arial", Font.BOLD, 18));
        targetLabel.setForeground(new Color(128, 0, 128)); // Purple
        targetLabel.setVisible(false); // Hidden initially
        topPanel.add(targetLabel);

        panel.add(topPanel, BorderLayout.NORTH);

        // Center section for number buttons (1-10)
        playNumberButtonsPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        playNumberButtonsPanel.setOpaque(false);
        addNumberButtons(playNumberButtonsPanel, e -> handlePlayNumberChoice(Integer.parseInt(((JButton)e.getSource()).getText())));
        panel.add(playNumberButtonsPanel, BorderLayout.CENTER);

        mainPanel.add(panel, "Gameplay");
    }

    // Creates the final result panel.
    private void createResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 248, 220)); // Cornsilk
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        finalResultLabel = new JLabel();
        finalResultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        finalResultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(finalResultLabel);
        panel.add(Box.createVerticalStrut(30));

        JButton playAgainButton = createStyledButton("Play Again", new Color(60, 179, 113)); // MediumSeaGreen
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.addActionListener(e -> resetAndRestartGame());
        panel.add(playAgainButton);

        mainPanel.add(panel, "Result");
    }

    // --- Helper Methods for GUI Elements ---

    // Adds number buttons (1-10) to a given panel with a shared action listener.
    private void addNumberButtons(JPanel panel, ActionListener listener) {
        for (int i = 1; i <= 10; i++) {
            JButton button = createStyledButton(String.valueOf(i), new Color(100, 149, 237)); // CornflowerBlue
            button.setPreferredSize(new Dimension(80, 80)); // Make buttons square
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.addActionListener(listener);
            panel.add(button);
        }
    }

    // Enables or disables a set of number buttons.
    private void setNumberButtonsEnabled(JPanel panel, boolean enabled) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(enabled);
            }
        }
    }

    // Creates a JButton with common styling.
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 2));
        button.setPreferredSize(new Dimension(150, 45)); // Consistent button size.
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover.
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }


    // --- Game Flow Handlers ---

    // Handles the user's choice for their toss number.
    private void handleTossNumberChoice(int userNumber) {
        String selectedOddEven = getSelectedRadioButtonText(tossOddEvenGroup);
        if (selectedOddEven == null) {
            JOptionPane.showMessageDialog(this, "Please choose Odd or Even first!", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GameLogic.TossResult tossResult = game.performToss(selectedOddEven, userNumber);
        String resultText = String.format("You chose %d, Computer chose %d. Total: %d.<br>",
                                           tossResult.userTossNumber, tossResult.computerTossNumber, tossResult.tossTotal);

        if (tossResult.userWonToss) {
            tossResultLabel.setText("<html>" + resultText + "You won the toss! What do you want to do first?</html>");
            batChoiceButton.setVisible(true);
            bowlChoiceButton.setVisible(true);
        } else {
            tossResultLabel.setText("<html>" + resultText + "Computer won the toss and chose to " + (game.setBattingChoice(false) == GameLogic.Player.USER ? "bat" : "bowl") + " first.</html>");
            // If computer won, we automatically proceed to difficulty after a short delay
            batChoiceButton.setVisible(false); // Hide buttons as user doesn't choose
            bowlChoiceButton.setVisible(false);
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(mainPanel, "Difficulty");
                    ((Timer)e.getSource()).stop(); // Stop the timer
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
        cardLayout.show(mainPanel, "TossResult");
    }

    // Handles the user's choice to bat or bowl after winning the toss.
    private void handleBatBowlChoice(boolean userChoseBat) {
        game.setBattingChoice(userChoseBat);
        cardLayout.show(mainPanel, "Difficulty");
    }

    // Sets the chosen difficulty and starts the gameplay.
    private void setDifficultyAndStart(GameLogic.Difficulty selectedDifficulty) {
        game.setDifficulty(selectedDifficulty);
        startGameplayInnings(); // Prepare UI and start innings
        cardLayout.show(mainPanel, "Gameplay");
    }

    // Initializes the gameplay UI for the current innings.
    private void startGameplayInnings() {
        if (game.getInningsCount() == 1) {
            gameMessageLabel.setText((game.getCurrentBatter() == GameLogic.Player.USER ? game.getPlayerName() : "Computer") + " is batting first!");
        } else {
            gameMessageLabel.setText((game.getCurrentBatter() == GameLogic.Player.USER ? game.getPlayerName() : "Computer") + " is batting second!");
            targetLabel.setText("Target: " + (game.getTarget() + 1));
            targetLabel.setVisible(true);
        }
        updateScoreDisplays();
    }

    // Handles the user's number choice during gameplay (batting or bowling).
    private void handlePlayNumberChoice(int userNumber) {
        GameLogic.BallResult result = game.playBall(userNumber);
        gameMessageLabel.setText(result.message);
        updateScoreDisplays();

        if (result.isOut) {
            if (game.getInningsCount() == 1) {
                // End of first innings
                game.endCurrentInnings();
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startGameplayInnings(); // Start second innings
                        ((Timer)e.getSource()).stop();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else { // End of second innings, game over
                game.endCurrentInnings(); // Sets inningsCount to 3
                endGame();
            }
        } else if (game.isGameOver()) {
            // This case handles when a team chases the target mid-innings (second innings).
            endGame();
        }
    }

    // Updates the score displays.
    private void updateScoreDisplays() {
        userScoreLabel.setText("Your Score: " + game.getUserScore());
        computerScoreLabel.setText("Computer Score: " + game.getComputerScore());
    }

    // Handles the end of the game, displaying the final result.
    private void endGame() {
        finalResultLabel.setText("<html><div style='text-align: center;'>" + game.getFinalResult() + "</div></html>");
        cardLayout.show(mainPanel, "Result");
    }

    // Resets the game state and restarts from the welcome screen or toss screen.
    private void resetAndRestartGame() {
        game.resetGame(); // Reset game logic (scores, target, etc.)

        tossOddEvenGroup.clearSelection(); // Clear radio button selections for next game
        difficultyGroup.clearSelection();

        // Re-enable odd/even buttons and disable number buttons for the next toss
        for (Enumeration<AbstractButton> buttons = tossOddEvenGroup.getElements(); buttons.hasMoreElements();) {
            buttons.nextElement().setEnabled(true);
        }
        setNumberButtonsEnabled(tossNumberButtonsPanel, false);

        targetLabel.setVisible(false); // Hide target label

        // If a player name is already set, go directly to the toss screen, otherwise back to welcome
        if (game != null && game.getPlayerName() != null && !game.getPlayerName().isEmpty()) {
            tossInstructionLabel.setText("Choose Odd or Even for the toss:");
            cardLayout.show(mainPanel, "Toss");
        } else {
            playerNameInput.setText(""); // Only clear name if going back to welcome
            cardLayout.show(mainPanel, "Welcome");
        }
    }

    // --- Utility Method ---
    // Gets the text of the selected radio button from a ButtonGroup.
    private String getSelectedRadioButtonText(ButtonGroup group) {
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    // --- Main Method ---
    public static void main(String[] args) {
        // Ensure GUI updates are performed on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            HandCricketGameGUI gui = new HandCricketGameGUI();
            gui.setVisible(true);
        });
    }
}
