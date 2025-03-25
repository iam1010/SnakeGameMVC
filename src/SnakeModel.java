import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class SnakeModel {
    // Game board dimensions
    public static final int BOARD_WIDTH = 30;
    public static final int BOARD_HEIGHT = 20;
    public static final int TILE_SIZE = 20;

    // Game states
    public enum GameState {
        RUNNING, GAME_OVER
    }

    // Possible directions
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Snake components
    private LinkedList<Point> snakeBody;
    private Direction currentDirection;
    private Point food;
    private int score;
    private GameState gameState;
    private Random random;

    public SnakeModel() {
        initGame();
    }

    public void initGame() {
        // Initialize snake at the center of the board
        snakeBody = new LinkedList<>();
        snakeBody.add(new Point(BOARD_WIDTH / 2, BOARD_HEIGHT / 2));

        currentDirection = Direction.RIGHT;
        score = 0;
        gameState = GameState.RUNNING;
        random = new Random();

        // Place initial food
        placeFood();
    }

    public void placeFood() {
        int x, y;
        do {
            x = random.nextInt(BOARD_WIDTH);
            y = random.nextInt(BOARD_HEIGHT);
        } while (snakeBody.contains(new Point(x, y)));

        food = new Point(x, y);
    }

    public void move() {
        if (gameState != GameState.RUNNING) return;

        Point head = snakeBody.getFirst();
        Point newHead = (Point) head.clone();

        // Update head position based on current direction
        switch (currentDirection) {
            case UP:
                newHead.y = (newHead.y - 1 + BOARD_HEIGHT) % BOARD_HEIGHT;
                break;
            case DOWN:
                newHead.y = (newHead.y + 1) % BOARD_HEIGHT;
                break;
            case LEFT:
                newHead.x = (newHead.x - 1 + BOARD_WIDTH) % BOARD_WIDTH;
                break;
            case RIGHT:
                newHead.x = (newHead.x + 1) % BOARD_WIDTH;
                break;
        }

        // Check for self-collision
        if (snakeBody.contains(newHead)) {
            gameState = GameState.GAME_OVER;
            return;
        }

        // Add new head
        snakeBody.addFirst(newHead);

        // Check if food is eaten
        if (newHead.equals(food)) {
            score++;
            placeFood();
        } else {
            // Remove tail if food not eaten
            snakeBody.removeLast();
        }
    }

    public void changeDirection(Direction newDirection) {
        // Prevent 180-degree turns
        if ((currentDirection == Direction.UP && newDirection == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && newDirection == Direction.UP) ||
                (currentDirection == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && newDirection == Direction.LEFT)) {
            return;
        }
        currentDirection = newDirection;
    }

    // Getters
    public LinkedList<Point> getSnakeBody() {
        return snakeBody;
    }

    public Point getFood() {
        return food;
    }

    public int getScore() {
        return score;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void restart() {
        initGame();
    }
}