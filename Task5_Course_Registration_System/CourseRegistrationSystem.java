import java.util.*;

public class CourseRegistrationSystem {
    private static class Course {
        String code, title, description, schedule;
        int capacity;
        Set<String> students = new LinkedHashSet<>();

        Course(String code, String title, String description, int capacity, String schedule) {
            this.code = code; this.title = title; this.description = description;
            this.capacity = capacity; this.schedule = schedule;
        }

        int availableSlots() { return capacity - students.size(); }
    }

    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final Map<String, String> studentNames = new LinkedHashMap<>();

    private CourseRegistrationSystem() {
        addCourse("JAVA101", "Java Programming", "Core Java and OOP", 3, "Mon/Wed 10:00");
        addCourse("DBMS201", "Database Systems", "SQL and database concepts", 2, "Tue/Thu 11:00");
        addCourse("WEB110", "Web Development", "HTML, CSS and JavaScript", 3, "Fri 09:00");
    }

    private void addCourse(String code, String title, String description, int capacity, String schedule) {
        courses.put(code, new Course(code, title, description, capacity, schedule));
    }

    private void listCourses() {
        System.out.println("\n===== AVAILABLE COURSES =====");
        for (Course c : courses.values()) {
            System.out.printf("%s | %s | %s | Schedule: %s | Slots: %d%n",
                c.code, c.title, c.description, c.schedule, c.availableSlots());
        }
    }

    private void register(String studentId, String name, String code) {
        Course c = courses.get(code.toUpperCase());
        if (c == null) { System.out.println("Course not found."); return; }
        studentNames.putIfAbsent(studentId, name);
        if (c.students.contains(studentId)) { System.out.println("Already registered in this course."); return; }
        if (c.availableSlots() <= 0) { System.out.println("No seats available."); return; }
        c.students.add(studentId);
        System.out.println("Registration successful for " + c.title + ".");
    }

    private void drop(String studentId, String code) {
        Course c = courses.get(code.toUpperCase());
        if (c == null) { System.out.println("Course not found."); return; }
        if (c.students.remove(studentId)) System.out.println("Course dropped successfully.");
        else System.out.println("You are not registered in this course.");
    }

    private void showStudentCourses(String studentId) {
        System.out.println("\nCourses registered by " + studentNames.getOrDefault(studentId, studentId) + ":");
        boolean found = false;
        for (Course c : courses.values()) {
            if (c.students.contains(studentId)) {
                System.out.println(c.code + " - " + c.title);
                found = true;
            }
        }
        if (!found) System.out.println("No courses registered.");
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter student ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        boolean running = true;

        while (running) {
            System.out.println("\n===== COURSE REGISTRATION =====");
            System.out.println("1. List courses\n2. Register for a course\n3. Drop a course\n4. View my courses\n5. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": system.listCourses(); break;
                case "2":
                    system.listCourses();
                    System.out.print("Enter course code: ");
                    system.register(id, name, sc.nextLine().trim());
                    break;
                case "3":
                    system.showStudentCourses(id);
                    System.out.print("Enter course code to drop: ");
                    system.drop(id, sc.nextLine().trim());
                    break;
                case "4": system.showStudentCourses(id); break;
                case "5": running = false; System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid option.");
            }
        }
        sc.close();
    }
}