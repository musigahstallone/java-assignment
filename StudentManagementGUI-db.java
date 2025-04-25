import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentManagementGUI extends Application {
    private StudentManagementSystem sms = new StudentManagementSystem();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management System");

        // Create GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Input fields
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        Label courseLabel = new Label("Course:");
        TextField courseField = new TextField();
        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField();

        // Buttons
        Button addButton = new Button("Add Student");
        Button displayButton = new Button("Display Students");
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        // Add components to grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(idLabel, 0, 1);
        grid.add(idField, 1, 1);
        grid.add(courseLabel, 0, 2);
        grid.add(courseField, 1, 2);
        grid.add(gradeLabel, 0, 3);
        grid.add(gradeField, 1, 3);
        grid.add(addButton, 0, 4);
        grid.add(displayButton, 1, 4);
        grid.add(outputArea, 0, 5, 2, 1);

        // Button actions
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String id = idField.getText();
                String course = courseField.getText();
                double grade = Double.parseDouble(gradeField.getText());
                sms.addStudent(name, id, course, grade);
                outputArea.setText("Student added successfully!");
                clearFields(nameField, idField, courseField, gradeField);
            } catch (NumberFormatException ex) {
                outputArea.setText("Error: Invalid grade format");
            } catch (IllegalArgumentException ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        });

        displayButton.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sms.getStudents().forEach(s -> sb.append(s.toString()).append("\n"));
            outputArea.setText(sb.length() == 0 ? "No students registered." : sb.toString());
        });

        // Set scene and show
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
