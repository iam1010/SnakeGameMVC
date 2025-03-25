import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SnakeGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create MVC components
                SnakeModel model = new SnakeModel();
                SnakeView view = new SnakeView(model);
                SnakeController controller = new SnakeController(model, view);

                // Set up the game window
                JFrame frame = new JFrame("Snake Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(view);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // Start the game
                controller.startGame();
            }
        });
    }
}