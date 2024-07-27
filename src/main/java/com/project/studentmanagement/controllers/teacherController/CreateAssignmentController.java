package com.project.studentmanagement.controllers.teacherController;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CreateAssignmentController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField dueDateField;

    @FXML
    private Button submitButton;

    @FXML
    private Label statusLabel;

    @FXML
    public void handleSubmitButtonAction() {
        // Get the input values
        String title = titleField.getText();
        String description = descriptionArea.getText();
        String dueDate = dueDateField.getText();

        // Validate the input values
        if (title.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
            showErrorAlert("Error", "Please fill in all fields.");
            return;
        }

        // Create the assignment
        boolean success = createAssignment(title, description, dueDate);
        if (success) {
            statusLabel.setText("Assignment created successfully!");
        } else {
            showErrorAlert("Error", "Failed to create the assignment.");
        }
    }

    private boolean createAssignment(String title, String description, String dueDate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("assignments.csv", true))) {
            writer.write(String.format("%s,%s,%s%n", title, description, dueDate));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
