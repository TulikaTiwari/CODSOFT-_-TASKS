import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        System.out.print("Enter number of subjects: ");
        int subjects = readPositiveInt(sc);

        double total = 0;
        for (int i = 1; i <= subjects; i++) {
            double marks;
            while (true) {
                System.out.print("Enter marks for subject " + i + " (0-100): ");
                if (sc.hasNextDouble()) {
                    marks = sc.nextDouble();
                    if (marks >= 0 && marks <= 100) break;
                } else sc.next();
                System.out.println("Invalid marks. Enter a value from 0 to 100.");
            }
            total += marks;
        }

        double average = total / subjects;
        String grade;
        if (average >= 90) grade = "A+";
        else if (average >= 80) grade = "A";
        else if (average >= 70) grade = "B";
        else if (average >= 60) grade = "C";
        else if (average >= 50) grade = "D";
        else grade = "F";

        System.out.println("\n===== RESULT =====");
        System.out.println("Student: " + name);
        System.out.printf("Total marks: %.2f / %.2f%n", total, subjects * 100.0);
        System.out.printf("Average percentage: %.2f%%%n", average);
        System.out.println("Grade: " + grade);
        sc.close();
    }

    private static int readPositiveInt(Scanner sc) {
        while (true) {
            if (sc.hasNextInt()) {
                int n = sc.nextInt();
                if (n > 0) return n;
            } else sc.next();
            System.out.print("Enter a positive whole number: ");
        }
    }
}