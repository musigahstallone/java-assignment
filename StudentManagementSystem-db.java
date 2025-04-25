import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystem {
    private static final String DB_URL = "jdbc:sqlite:students.db";

    public StudentManagementSystem() {
        // Initialize database and create table if not exists
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                    "id TEXT PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "course TEXT NOT NULL," +
                    "grade REAL NOT NULL CHECK(grade >= 0 AND grade <= 100)" +
                    ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    // Add a student with validation
    public synchronized void addStudent(String name, String id, String course, double grade) throws IllegalArgumentException {
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

        String sql = "INSERT INTO students(id, name, course, grade) VALUES (?, ?, ?, ?);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, course);
            pstmt.setDouble(4, grade);
            pstmt.executeUpdate();
            System.out.println("Student added: " + new Student(name, id, course, grade));
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    // Get student by ID
    public Student getStudentById(String id) {
        String sql = "SELECT * FROM students WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String course = rs.getString("course");
                double grade = rs.getDouble("grade");
                return new Student(name, id, course, grade);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
        }
        return null;
    }

    // Update student
    public synchronized void updateStudent(String id, String name, String course, double grade) throws IllegalArgumentException {
        Student existing = getStudentById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Student not found");
        }
        if (name == null || name.trim().isEmpty()) {
            name = existing.getName();
        }
        if (course == null || course.trim().isEmpty()) {
            course = existing.getCourse();
        }
        if (grade < 0 || grade > 100) {
            grade = existing.getGrade();
        }

        String sql = "UPDATE students SET name = ?, course = ?, grade = ? WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, course);
            pstmt.setDouble(3, grade);
            pstmt.setString(4, id);
            pstmt.executeUpdate();
            System.out.println("Student updated: " + new Student(name, id, course, grade));
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }

    // Delete student
    public synchronized void deleteStudent(String id) throws IllegalArgumentException {
        Student existing = getStudentById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Student not found");
        }
        String sql = "DELETE FROM students WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            System.out.println("Student deleted: " + existing);
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }

    // Display all students
    public void displayStudents() {
        String sql = "SELECT * FROM students;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            boolean empty = true;
            while (rs.next()) {
                empty = false;
                String id = rs.getString("id");
                String name = rs.getString("name");
                String course = rs.getString("course");
                double grade = rs.getDouble("grade");
                System.out.println(new Student(name, id, course, grade));
            }
            if (empty) {
                System.out.println("No students registered.");
            }
        } catch (SQLException e) {
            System.err.println("Error displaying students: " + e.getMessage());
        }
    }

    // Get all students as a list (for GUI)
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String course = rs.getString("course");
                double grade = rs.getDouble("grade");
                students.add(new Student(name, id, course, grade));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
        return students;
    }
}
