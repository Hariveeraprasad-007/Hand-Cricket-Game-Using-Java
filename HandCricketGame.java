import java.util.*;

// This class represents the core logic of the Hand Cricket game.
class HandCricketGame {

    private final Scanner sc;
    private final Random random;
    private final List<Integer> playHistory;
    private final String userName;

    // Difficulty levels for the computer.
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    // Constructor to initialize game components and the player's name.
    public HandCricketGame(String userName) {
        this.userName = userName;
        this.sc = new Scanner(System.in);
        this.random = new Random();
        this.playHistory = new ArrayList<>();
    }

    // Main method to start and manage a match.
    public void startMatch() {
        playHistory.clear(); // Clear history for each new game to ensure fairness.

        System.out.println("\n--- Starting New Match ---");
        System.out.print("Choose 'odd' or 'even' for the toss: ");
        String userOddEvenChoice = sc.next();

        int userTossChoice;
        while (true) {
            System.out.print("Please enter your toss number (1-10): ");
            userTossChoice = sc.nextInt();
            if (userTossChoice >= 1 && userTossChoice <= 10) {
                break;
            } else {
                System.out.println("Invalid number. Please enter a number between 1 and 10.");
            }
        }

        int computerChoice = random.nextInt(10) + 1; // Computer's number for the toss
        int tossTotal = userTossChoice + computerChoice;

        System.out.printf("You chose %d, Computer chose %d. Total is %d.\n", userTossChoice, computerChoice, tossTotal);

        boolean userTossWin = (tossTotal % 2 == 0 && userOddEvenChoice.equalsIgnoreCase("even")) ||
                              (tossTotal % 2 != 0 && userOddEvenChoice.equalsIgnoreCase("odd"));

        boolean userBattingFirst = false;

        if (userTossWin) {
            System.out.print("You won the toss! What do you want to do first? (Bat/Bowl): ");
            userBattingFirst = sc.next().equalsIgnoreCase("Bat");
            System.out.printf("You won the toss and chose to %s first.\n", userBattingFirst ? "bat" : "bowl");
        } else {
            userBattingFirst = random.nextBoolean(); // Computer randomly chooses to bat or bowl
            System.out.printf("Computer won the toss and chose to %s first.\n", userBattingFirst ? "bat" : "bowl");
        }

        System.out.print("Choose the difficulty (easy/medium/hard): ");
        Difficulty difficulty = Difficulty.valueOf(sc.next().toUpperCase()); // Converts string to Difficulty enum

        int userScore;
        int computerScore;

        if (userBattingFirst) {
            System.out.println("\n--- " + userName + " is Batting First ---");
            userScore = playInnings("user", -1, difficulty); // -1 indicates no target yet
            System.out.println("\n--- Computer is Batting (Chasing " + (userScore + 1) + ") ---");
            computerScore = playInnings("computer", userScore + 1, difficulty);
        } else {
            System.out.println("\n--- Computer is Batting First ---");
            computerScore = playInnings("computer", -1, difficulty); // -1 indicates no target yet
            System.out.println("\n--- " + userName + " is Batting (Chasing " + (computerScore + 1) + ") ---");
            userScore = playInnings("user", computerScore + 1, difficulty);
        }

        System.out.println("\n--- Match Results ---");
        System.out.printf("Your Score: %d\nComputer Score: %d\n", userScore, computerScore);

        if (userScore > computerScore) {
            System.out.println("Congratulations, " + userName + "! You won the match!");
        } else if (computerScore > userScore) {
            System.out.println("The computer won the match. Better luck next time!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    /**
     * Handles a single innings for a player (user or computer).
     * @param batter The current batter ("user" or "computer").
     * @param target The score to chase. -1 if it's the first innings.
     * @param difficulty The AI difficulty for the computer.
     * @return The total score made in this innings.
     */
    private int playInnings(String batter, int target, Difficulty difficulty) {
        int currentScore = 0;
        
        if (target != -1) {
            System.out.printf("Target to chase: %d runs.\n", target);
        }

        // Loop continues until the batter is out or the target is chased (if applicable)
        while (target == -1 || currentScore <= target) {
            System.out.printf("Current score: %d\n", currentScore);
            int userChoice;
            int computerChoice;

            if (batter.equals("user")) {
                // User is batting, computer is bowling
                userChoice = getUserInput("Enter your batting number (1-10): ");
                computerChoice = getComputerResponse(playHistory, difficulty);
                System.out.printf("You chose: %d, Computer chose: %d\n", userChoice, computerChoice);
                playHistory.add(userChoice); // Add user's choice to history for computer's next prediction
                currentScore += userChoice;
            } else { // Computer is batting, user is bowling
                // User is bowling, computer is batting
                userChoice = getUserInput("Enter your bowling number (1-10): ");
                computerChoice = getComputerResponse(playHistory, difficulty);
                System.out.printf("You chose (bowling): %d, Computer chose (batting): %d\n", userChoice, computerChoice);
                playHistory.add(userChoice); // Add user's choice to history for computer's next prediction
                currentScore += computerChoice;
            }

            if (userChoice == computerChoice) {
                System.out.printf("%s is out! Final score: %d\n", (batter.equals("user") ? "You" : "Computer"), currentScore);
                return currentScore;
            }
            
            // If target is set and current score has passed it, the innings ends
            if (target != -1 && currentScore > target) {
                return currentScore;
            }
        }
        
        return currentScore; // Should only be reached if target is not -1 and currentScore == target
    }

    /**
     * Gets validated integer input from the user.
     * @param prompt The message to display to the user.
     * @return A valid integer between 1 and 10.
     */
    private int getUserInput(String prompt) {
        int choice;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice >= 1 && choice <= 10) {
                    break;
                } else {
                    System.out.println("Invalid number. Please enter a number between 1 and 10.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); // Consume the invalid input
            }
        }
        return choice;
    }

    /**
     * The logic for the computer's response based on difficulty.
     * @param history A list of previous user choices.
     * @param difficulty The chosen difficulty level.
     * @return The computer's chosen number (1-10).
     */
    private int getComputerResponse(List<Integer> history, Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                // Computer plays randomly
                return random.nextInt(10) + 1;
            case MEDIUM:
                // 30% chance to predict the last user choice, otherwise random
                if (!history.isEmpty() && random.nextDouble() < 0.3) {
                    return history.get(history.size() - 1);
                }
                return random.nextInt(10) + 1;
            case HARD:
                // Predicts the most frequent number from the user's history.
                // If history is empty, plays randomly.
                if (!history.isEmpty()) {
                    Map<Integer, Integer> freq = new HashMap<>();
                    for (int n : history) {
                        freq.put(n, freq.getOrDefault(n, 0) + 1);
                    }
                    int predicted = 0;
                    int maxVal = 0;
                    // Find the most frequent number
                    for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
                        if (entry.getValue() > maxVal) {
                            maxVal = entry.getValue();
                            predicted = entry.getKey();
                        }
                    }
                    return predicted;
                }
                return random.nextInt(10) + 1; // Default to random if history is empty
            default:
                return random.nextInt(10) + 1; // Fallback for any unexpected difficulty
        }
    }
}
