import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        int totalWins = 0;
        boolean playAgain = true;

        System.out.println("=== NUMBER GUESSING GAME ===");
        while (playAgain) {
            int target = random.nextInt(100) + 1;
            int attemptsLeft = 7;
            boolean won = false;
            System.out.println("\nGuess a number between 1 and 100. You have 7 attempts.");

            while (attemptsLeft > 0) {
                System.out.print("Enter your guess: ");
                if (!sc.hasNextInt()) {
                    System.out.println("Please enter a whole number.");
                    sc.next();
                    continue;
                }
                int guess = sc.nextInt();
                if (guess < 1 || guess > 100) {
                    System.out.println("Your guess must be from 1 to 100.");
                    continue;
                }
                attemptsLeft--;
                if (guess == target) {
                    System.out.println("Correct! You guessed the number.");
                    won = true;
                    totalWins++;
                    break;
                } else if (guess < target) {
                    System.out.println("Too low!");
                } else {
                    System.out.println("Too high!");
                }
                System.out.println("Attempts remaining: " + attemptsLeft);
            }
            if (!won) System.out.println("Game over. The number was " + target + ".");
            System.out.println("Total rounds won: " + totalWins);
            System.out.print("Play another round? (yes/no): ");
            playAgain = sc.next().equalsIgnoreCase("yes");
        }
        System.out.println("Thanks for playing!");
        sc.close();
    }
}