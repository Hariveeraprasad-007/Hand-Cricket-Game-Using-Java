import java.util.*;

// GameLogic class handles all the core mechanics of the Hand Cricket game.
class GameLogic {
    // Enum to define difficulty levels for the computer AI.
    public enum Difficulty { EASY, MEDIUM, HARD }
    // Enum to identify players in the game.
    public enum Player { USER, COMPUTER }

    private String playerName; // The name of the user playing the game.
    private Difficulty difficulty; // The selected difficulty level.
    private Random random; // Random number generator for computer choices.
    private List<Integer> playHistory; // Stores user's number choices to help AI.

    private int userScore; // Current score of the user.
    private int computerScore; // Current score of the computer.
    private int target; // The score to chase in the second innings (-1 if no target yet).
    private Player currentBatter; // Indicates who is currently batting (USER or COMPUTER).
    private Player tossWinner; // Stores who won the toss.
    private boolean userBattingFirstInMatch; // True if user bats first, false if computer bats first.
    private int inningsCount; // Tracks current innings (1 for first, 2 for second, 3 for game over).

    // Constructor to initialize the game logic with the player's name.
    public GameLogic(String playerName) {
        this.playerName = playerName;
        this.random = new Random();
        this.playHistory = new ArrayList<>();
        resetGame(); // Initialize all game variables to default state.
    }

    // Sets the difficulty level for the computer AI.
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // Resets all game state variables to start a new match.
    public void resetGame() {
        userScore = 0;
        computerScore = 0;
        target = -1;
        currentBatter = null;
        tossWinner = null;
        userBattingFirstInMatch = false;
        inningsCount = 0; // 0 indicates game not started/reset
        playHistory.clear();
    }

    /**
     * Performs the toss phase of the game.
     * @param userOddEvenChoice User's choice for odd or even.
     * @param userTossNumber User's chosen number for the toss.
     * @return A TossResult object containing details of the toss.
     */
    public TossResult performToss(String userOddEvenChoice, int userTossNumber) {
        playHistory.clear(); // Clear history, as toss is a new phase.
        int computerTossNumber = random.nextInt(10) + 1; // Computer's random number for toss.
        int tossTotal = userTossNumber + computerTossNumber;

        // Determine if the user won the toss.
        boolean userWonToss = (tossTotal % 2 == 0 && userOddEvenChoice.equalsIgnoreCase("even")) ||
                              (tossTotal % 2 != 0 && userOddEvenChoice.equalsIgnoreCase("odd"));

        tossWinner = userWonToss ? Player.USER : Player.COMPUTER;

        return new TossResult(userTossNumber, computerTossNumber, tossTotal, userWonToss);
    }

    /**
     * Sets who will bat first based on toss winner's choice or computer's decision.
     * @param userChoosesToBat True if the user, if they won the toss, chose to bat.
     * @return The player who will bat first in the match.
     */
    public Player setBattingChoice(boolean userChoosesToBat) {
        if (tossWinner == Player.USER) {
            userBattingFirstInMatch = userChoosesToBat;
        } else { // Computer won the toss, it makes a random decision.
            userBattingFirstInMatch = random.nextBoolean();
        }
        currentBatter = userBattingFirstInMatch ? Player.USER : Player.COMPUTER;
        inningsCount = 1; // Mark the beginning of the first innings.
        playHistory.clear(); // Clear history for the new innings.
        return currentBatter;
    }

    /**
     * Plays a single "ball" or turn in the game.
     * @param userPlayNumber The number chosen by the user (batting or bowling).
     * @return A BallResult object detailing the outcome of the turn.
     */
    public BallResult playBall(int userPlayNumber) {
        if (currentBatter == null) {
            throw new IllegalStateException("Innings not started. Set batting choice first.");
        }

        int computerPlayNumber = getComputerResponse(); // Computer's number (batting if batting, bowling if bowling).
        playHistory.add(userPlayNumber); // Always record the user's input for AI history.

        int scoreToAdd;
        boolean isOut = false;
        String message = "";

        // Check if the user's number matches the computer's number (out condition).
        if (userPlayNumber == computerPlayNumber) {
            isOut = true;
            message = (currentBatter == Player.USER ? playerName : "Computer") + " is OUT!";
            if (inningsCount == 1) { // If it's the first innings and an out, set the target.
                target = (currentBatter == Player.USER ? userScore : computerScore);
                message += " Target to chase: " + (target + 1);
            }
        } else {
            // If not out, add score.
            scoreToAdd = (currentBatter == Player.USER ? userPlayNumber : computerPlayNumber);
            if (currentBatter == Player.USER) {
                userScore += scoreToAdd;
                message = String.format("%s batted %d, Computer bowled %d. Current score: %d", playerName, userPlayNumber, computerPlayNumber, userScore);
            } else {
                computerScore += scoreToAdd;
                message = String.format("Computer batted %d, %s bowled %d. Current score: %d", computerPlayNumber, playerName, userPlayNumber, computerScore);
            }

            // In the second innings, check if the target has been chased.
            if (inningsCount == 2 && target != -1) {
                if (currentBatter == Player.USER && userScore > target) {
                    isOut = true; // User successfully chased target.
                    message = playerName + " chased the target!";
                } else if (currentBatter == Player.COMPUTER && computerScore > target) {
                    isOut = true; // Computer successfully chased target.
                    message = "Computer chased the target!";
                }
            }
        }
        return new BallResult(userPlayNumber, computerPlayNumber, userScore, computerScore, isOut, message);
    }

    /**
     * Ends the current innings and prepares for the next, or marks game over.
     */
    public void endCurrentInnings() {
        if (inningsCount == 1) {
            inningsCount = 2; // Move to second innings.
            // Switch the batter for the second innings.
            currentBatter = (userBattingFirstInMatch ? Player.COMPUTER : Player.USER);
            playHistory.clear(); // Reset history for the new innings.
        } else if (inningsCount == 2) {
            inningsCount = 3; // Game is over after second innings.
        }
    }

    /**
     * Determines the computer's number choice based on difficulty and play history.
     * @return The computer's chosen number (1-10).
     */
    private int getComputerResponse() {
        int computerChoice = random.nextInt(10) + 1; // Default random choice.

        switch (difficulty) {
            case EASY:
                // Computer always makes a random choice.
                break;
            case MEDIUM:
                // 30% chance for the computer to try and match the user's last move.
                if (!playHistory.isEmpty() && random.nextDouble() < 0.3) {
                    computerChoice = playHistory.get(playHistory.size() - 1);
                }
                break;
            case HARD:
                // Computer tries to predict the user's most frequent choice from history.
                if (!playHistory.isEmpty()) {
                    Map<Integer, Integer> freq = new HashMap<>();
                    for (int n : playHistory) {
                        freq.put(n, freq.getOrDefault(n, 0) + 1);
                    }
                    int mostFrequent = -1;
                    int maxVal = 0;
                    for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
                        if (entry.getValue() > maxVal) {
                            maxVal = entry.getValue();
                            mostFrequent = entry.getKey();
                        }
                    }
                    if (mostFrequent != -1) {
                        computerChoice = mostFrequent;
                    }
                }
                break;
        }
        return computerChoice;
    }

    // --- Getters for Game State ---
    public String getPlayerName() { return playerName; }
    public int getUserScore() { return userScore; }
    public int getComputerScore() { return computerScore; }
    public int getTarget() { return target; }
    public Player getCurrentBatter() { return currentBatter; }
    public Player getTossWinner() { return tossWinner; }
    public boolean isUserBattingFirstInMatch() { return userBattingFirstInMatch; }
    public int getInningsCount() { return inningsCount; }
    public boolean isGameOver() { return inningsCount == 3; } // Game is over when inningsCount reaches 3.

    /**
     * Determines the final result message of the match.
     * @return A string describing who won or if it was a tie.
     */
    public String getFinalResult() {
        if (userScore > computerScore) {
            return "Congratulations, " + playerName + "! You won by " + (userScore - computerScore) + " runs!";
        } else if (computerScore > userScore) {
            return "Computer won by " + (computerScore - userScore) + " runs. Better luck next time!";
        } else {
            return "It's a tie! Well played!";
        }
    }

    // --- Helper classes for returning results ---

    // Class to encapsulate the results of the toss.
    public static class TossResult {
        public final int userTossNumber;
        public final int computerTossNumber;
        public final int tossTotal;
        public final boolean userWonToss;

        public TossResult(int userTossNumber, int computerTossNumber, int tossTotal, boolean userWonToss) {
            this.userTossNumber = userTossNumber;
            this.computerTossNumber = computerTossNumber;
            this.tossTotal = tossTotal;
            this.userWonToss = userWonToss;
        }
    }

    // Class to encapsulate the results of a single ball/turn.
    public static class BallResult {
        public final int userPlayNumber;
        public final int computerPlayNumber;
        public final int currentUserScore;
        public final int currentComputerScore;
        public final boolean isOut;
        public final String message;

        public BallResult(int userPlayNumber, int computerPlayNumber, int currentUserScore, int currentComputerScore, boolean isOut, String message) {
            this.userPlayNumber = userPlayNumber;
            this.computerPlayNumber = computerPlayNumber;
            this.currentUserScore = currentUserScore;
            this.currentComputerScore = currentComputerScore;
            this.isOut = isOut;
            this.message = message;
        }
    }
}
