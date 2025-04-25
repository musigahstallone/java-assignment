import java.util.Scanner;

public class ConsoleInterface {
    private final StudentManagementSystem sms;
    private final Scanner scanner;

    public ConsoleInterface(StudentManagementSystem sms) {
        this.sms = sms;
        this.scanner = new Scanner(System.in);
    }

    private void addStudent() {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter course: ");
            String course = scanner.nextLine();
            System.out.print("Enter grade: ");
            double grade = Double.parseDouble(scanner.nextLine());
            sms.addStudent(name, id, course, grade);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            System.out.print("Enter student ID to update: ");
            String id = scanner.nextLine();
            System.out.print("Enter new name (or press Enter to skip): ");
            String name = scanner.nextLine();
            System.out.print("Enter new course (or press Enter to skip): ");
            String course = scanner.nextLine();
            System.out.print("Enter new grade (or -1 to skip): ");
            double grade = Double.parseDouble(scanner.nextLine());
            sms.updateStudent(id, name.isEmpty() ? null : name, course.isEmpty() ? null : course, grade);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            System.out.print("Enter student ID to delete: ");
            String id = scanner.nextLine();
            sms.deleteStudent(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    sms.displayStudents();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
