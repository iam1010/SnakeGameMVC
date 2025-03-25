import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.Point;

public class SnakeView extends JPanel {
    private SnakeModel model;
    private static final Color SNAKE_COLOR = new Color(0, 180, 0);
    private static final Color FOOD_COLOR = Color.RED;
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    private JLabel scoreLabel;
    private JLabel gameStateLabel;

    public SnakeView(SnakeModel model) {
        this.model = model;

        // Set up the panel
        setPreferredSize(new Dimension(
                SnakeModel.BOARD_WIDTH * SnakeModel.TILE_SIZE,
                SnakeModel.BOARD_HEIGHT * SnakeModel.TILE_SIZE
        ));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        // Create info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(BACKGROUND_COLOR);
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        gameStateLabel = new JLabel("");
        gameStateLabel.setForeground(Color.WHITE);

        infoPanel.add(scoreLabel);
        infoPanel.add(gameStateLabel);

        add(infoPanel, BorderLayout.NORTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw snake
        g.setColor(SNAKE_COLOR);
        for (Point segment : model.getSnakeBody()) {
            g.fillRect(
                    segment.x * SnakeModel.TILE_SIZE,
                    segment.y * SnakeModel.TILE_SIZE,
                    SnakeModel.TILE_SIZE,
                    SnakeModel.TILE_SIZE
            );
        }

        // Draw food
        g.setColor(FOOD_COLOR);
        Point food = model.getFood();
        g.fillRect(
                food.x * SnakeModel.TILE_SIZE,
                food.y * SnakeModel.TILE_SIZE,
                SnakeModel.TILE_SIZE,
                SnakeModel.TILE_SIZE
        );

        // Update score and game state
        scoreLabel.setText("Score: " + model.getScore());

        if (model.getGameState() == SnakeModel.GameState.GAME_OVER) {
            gameStateLabel.setText("Game Over! Press R to Restart");
        } else {
            gameStateLabel.setText("");
        }
    }

    public void addKeyListener(KeyListener listener) {
        // Ensure the panel can receive key events
        setFocusable(true);
        super.addKeyListener(listener);
    }

    public void updateView() {
        repaint();
    }
}