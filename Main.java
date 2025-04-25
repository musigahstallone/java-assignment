public class Main {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();

        // Console-based interface
        ConsoleInterface console = new ConsoleInterface(sms);
        console.start();

        // Multithreading example: Simulate multiple users
        Thread user1 = new Thread(() -> {
            try {
                sms.addStudent("Alice", "S001", "Math", 85.5);
            } catch (IllegalArgumentException e) {
                System.out.println("User1 Error: " + e.getMessage());
            }
        });

        Thread user2 = new Thread(() -> {
            try {
                sms.addStudent("Bob", "S002", "Physics", 90.0);
            } catch (IllegalArgumentException e) {
                System.out.println("User2 Error: " + e.getMessage());
            }
        });

        user1.start();
        user2.start();

        try {
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Launch GUI (uncomment to run)
        // StudentManagementGUI.main(args);
    }
}
