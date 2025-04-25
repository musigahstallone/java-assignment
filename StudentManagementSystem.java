import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystem {
    private List<Student> students;
    private final String fileName = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudentsFromFile();
    }

    // Add a student with validation
    public synchronized void addStudent(String name, String id, String course, double grade) throws IllegalArgumentException {
        // Input validation
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (id == null || id.trim().isEmpty() || getStudentById(id) != null) {
            throw new IllegalArgumentException("Invalid or duplicate ID");
        }
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course cannot be empty");
        }
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }

        Student student = new Student(name, id, course, grade);
        students.add(student);
        saveStudentsToFile();
        System.out.println("Student added: " + student);
    }

    // Get student by ID
    public Student getStudentById(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update student
    public synchronized void updateStudent(String id, String name, String course, double grade) {
        Student student = getStudentById(id);
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }
        if (name != null && !name.trim().isEmpty()) student.setName(name);
        if (course != null && !course.trim().isEmpty()) student.setCourse(course);
        if (grade >= 0 && grade <= 100) student.setGrade(grade);
        saveStudentsToFile();
        System.out.println("Student updated: " + student);
    }

    // Delete student
    public synchronized void deleteStudent(String id) {
        Student student = getStudentById(id);
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }
        students.remove(student);
        saveStudentsToFile();
        System.out.println("Student deleted: " + student);
    }

    // Display all students
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
        } else {
            students.forEach(System.out::println);
        }
    }

    // Save students to file
    private synchronized void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student s : students) {
                writer.write(s.getName() + "," + s.getId() + "," + s.getCourse() + "," + s.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    // Load students from file
    private synchronized void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String id = parts[1];
                    String course = parts[2];
                    double grade = Double.parseDouble(parts[3]);
                    students.add(new Student(name, id, course, grade));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing student data found. Starting fresh.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading from file: " + e.getMessage());
        }
    }

    // Getter for students list (used in GUI)
    public List<Student> getStudents() {
        return students;
    }
}
