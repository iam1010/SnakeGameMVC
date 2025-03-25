import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenu extends JFrame {
    private HighScoreManager highScoreManager;

    public MainMenu() {
        // Initialize high score manager
        highScoreManager = new HighScoreManager();

        // Set up the frame
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 500));
        setLayout(new BorderLayout());

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Title
        JLabel titleLabel = new JLabel("SNAKE GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // New Game Button
        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(200, 50));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        mainPanel.add(newGameButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // High Scores Button
        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoresButton.setMaximumSize(new Dimension(200, 50));
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHighScores();
            }
        });
        mainPanel.add(highScoresButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 50));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mainPanel.add(exitButton);

        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);

        // Pack and center the frame
        pack();
        setLocationRelativeTo(null);
    }

    private void startNewGame() {
        // Create MVC components
        SnakeModel model = new SnakeModel();
        SnakeView view = new SnakeView(model);
        SnakeController controller = new SnakeController(model, view, this);

        // Create game window
        JFrame gameFrame = new JFrame("Snake Game");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.add(view);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        // Start the game
        controller.startGame();

        // Hide main menu
        this.setVisible(false);
    }

    private void showHighScores() {
        // Create high scores dialog
        JDialog highScoresDialog = new JDialog(this, "High Scores", true);
        highScoresDialog.setPreferredSize(new Dimension(300, 400));

        // Create high scores panel
        JPanel highScoresPanel = new JPanel();
        highScoresPanel.setLayout(new BoxLayout(highScoresPanel, BoxLayout.Y_AXIS));
        highScoresPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoresPanel.add(titleLabel);
        highScoresPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Get high scores
        List<HighScoreManager.ScoreEntry> scores = highScoreManager.getHighScores();

        // Display high scores
        if (scores.isEmpty()) {
            JLabel noScoresLabel = new JLabel("No high scores yet!");
            noScoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            highScoresPanel.add(noScoresLabel);
        } else {
            for (int i = 0; i < scores.size(); i++) {
                HighScoreManager.ScoreEntry entry = scores.get(i);
                JLabel scoreLabel = new JLabel((i + 1) + ". " + entry.getPlayerName() + ": " + entry.getScore());
                scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                highScoresPanel.add(scoreLabel);
            }
        }

        // Close button
        highScoresPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setMaximumSize(new Dimension(200, 50));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highScoresDialog.dispose();
            }
        });
        highScoresPanel.add(closeButton);

        // Add panel to dialog
        highScoresDialog.add(highScoresPanel);
        highScoresDialog.pack();
        highScoresDialog.setLocationRelativeTo(this);
        highScoresDialog.setVisible(true);
    }

    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
}