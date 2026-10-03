import java.util.Scanner;
import java.util.concurrent.*;

public class QuizApplication {
    private static final int TIME_LIMIT_SECONDS = 15;

    private static class Question {
        String prompt;
        String[] options;
        int correct; // 1-based option number

        Question(String prompt, String[] options, int correct) {
            this.prompt = prompt;
            this.options = options;
            this.correct = correct;
        }
    }

    public static void main(String[] args) {
        Question[] questions = {
            new Question("Which keyword is used to inherit a class in Java?",
                new String[]{"implements", "extends", "inherits", "super"}, 2),
            new Question("Which method is the entry point of a Java application?",
                new String[]{"start()", "run()", "main()", "init()"}, 3),
            new Question("Which type stores true or false?",
                new String[]{"int", "char", "String", "boolean"}, 4),
            new Question("Which collection does not allow duplicate elements?",
                new String[]{"ArrayList", "HashSet", "LinkedList", "Vector"}, 2)
        };

        Scanner sc = new Scanner(System.in);
        int score = 0, correctCount = 0;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println("=== JAVA QUIZ ===");
        System.out.println("You have " + TIME_LIMIT_SECONDS + " seconds per question.");

        for (int i = 0; i < questions.length; i++) {
            Question q = questions[i];
            System.out.println("\nQuestion " + (i + 1) + ": " + q.prompt);
            for (int j = 0; j < q.options.length; j++)
                System.out.println((j + 1) + ". " + q.options[j]);

            Future<String> answerFuture = executor.submit(() -> {
                System.out.print("Your answer (1-4): ");
                return sc.nextLine();
            });

            try {
                String answer = answerFuture.get(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
                int selected;
                try { selected = Integer.parseInt(answer.trim()); }
                catch (NumberFormatException e) { selected = -1; }

                if (selected == q.correct) {
                    System.out.println("Correct!");
                    score++;
                    correctCount++;
                } else {
                    System.out.println("Incorrect. Correct answer: " + q.correct + ". " + q.options[q.correct - 1]);
                }
            } catch (TimeoutException e) {
                answerFuture.cancel(true);
                System.out.println("\nTime is up! Correct answer: " + q.correct + ". " + q.options[q.correct - 1]);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Quiz interrupted.");
                break;
            } catch (ExecutionException e) {
                System.out.println("Could not read answer.");
            }
        }
        executor.shutdownNow();
        System.out.println("\n===== QUIZ RESULT =====");
        System.out.println("Questions: " + questions.length);
        System.out.println("Correct answers: " + correctCount);
        System.out.println("Incorrect / unanswered: " + (questions.length - correctCount));
        System.out.println("Final score: " + score + " / " + questions.length);
        sc.close();
    }
}