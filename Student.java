public class Student {
    private String name;
    private String id;
    private String course;
    private double grade;

    // Constructor
    public Student(String name, String id, String course, double grade) {
        this.name = name;
        this.id = id;
        this.course = course;
        this.grade = grade;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", course='" + course + '\'' +
                ", grade=" + grade +
                '}';
    }
}
