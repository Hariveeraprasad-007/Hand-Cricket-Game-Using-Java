import java.util.Scanner;

// Main class to run the Hand Cricket application.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Welcome to Hand Cricket! Please enter your name: ");
        String name = sc.next();

        boolean playAgain;
        do {
            HandCricketGame game = new HandCricketGame(name); // Create a new game instance for each match
            game.startMatch(); // Start the game
            System.out.print("Do you want to play again? (y/n): ");
            playAgain = sc.next().equalsIgnoreCase("y");
        } while (playAgain);

        System.out.println("Thanks for playing, " + name + "!");
        sc.close(); // Close the scanner to prevent resource leaks
    }
}
