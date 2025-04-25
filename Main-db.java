public class Main {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        ConsoleInterface console = new ConsoleInterface(sms);
        console.start();
    }
}
