import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighScoreManager {
    private static final String HIGHSCORE_FILE = "highscores.dat";
    private List<ScoreEntry> highScores;

    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadHighScores();
    }

    public void addScore(String playerName, int score) {
        ScoreEntry newScore = new ScoreEntry(playerName, score);
        highScores.add(newScore);

        // Sort scores in descending order
        Collections.sort(highScores, new Comparator<ScoreEntry>() {
            @Override
            public int compare(ScoreEntry s1, ScoreEntry s2) {
                return Integer.compare(s2.getScore(), s1.getScore());
            }
        });

        // Keep only top 10 scores
        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);
        }

        saveHighScores();
    }

    public List<ScoreEntry> getHighScores() {
        return new ArrayList<>(highScores);
    }

    private void loadHighScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE))) {
            highScores = (List<ScoreEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If file doesn't exist or there's an error, start with an empty list
            highScores = new ArrayList<>();
        }
    }

    private void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Nested class for score entries
    public static class ScoreEntry implements Serializable {
        private String playerName;
        private int score;

        public ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return playerName + ": " + score;
        }
    }
}