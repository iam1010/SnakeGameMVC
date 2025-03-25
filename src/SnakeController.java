import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeController {
    private SnakeModel model;
    private SnakeView view;
    private Timer gameTimer;
    private MainMenu mainMenu;

    public SnakeController(SnakeModel model, SnakeView view, MainMenu mainMenu) {
        this.model = model;
        this.view = view;
        this.mainMenu = mainMenu;

        // Set up game loop
        gameTimer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getGameState() == SnakeModel.GameState.RUNNING) {
                    model.move();
                    view.updateView();
                }
            }
        });

        // Set up key listener
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        model.changeDirection(SnakeModel.Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        model.changeDirection(SnakeModel.Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        model.changeDirection(SnakeModel.Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        model.changeDirection(SnakeModel.Direction.RIGHT);
                        break;
                    case KeyEvent.VK_R:
                        if (model.getGameState() == SnakeModel.GameState.GAME_OVER) {
                            handleGameOver();
                        }
                        break;
                }
            }
        });
    }

    private void handleGameOver() {
        // Stop the game
        gameTimer.stop();

        // Prompt for player name and save high score
        String playerName = JOptionPane.showInputDialog(view,
                "Game Over! Enter your name:",
                "High Score",
                JOptionPane.PLAIN_MESSAGE);

        // Validate player name
        if (playerName != null && !playerName.trim().isEmpty()) {
            mainMenu.getHighScoreManager().addScore(playerName.trim(), model.getScore());
        }

        // Close the game window
        SwingUtilities.getWindowAncestor(view).dispose();

        // Show main menu again
        mainMenu.setVisible(true);
    }

    public void startGame() {
        gameTimer.start();
    }

    public void stopGame() {
        gameTimer.stop();
    }
}