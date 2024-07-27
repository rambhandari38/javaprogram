package com.project.studentmanagement.controllers.studentController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StudentFormController {

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField facultyField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private static final String STUDENT_FORM_CSV = "studentData/student_form_data.csv";

    @FXML
    public void handleSave() {
        List<String> data = Arrays.asList(
                studentIdField.getText(),
                usernameField.getText(),
                passwordField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                facultyField.getText(),
                emailField.getText(),
                genderComboBox.getValue(),
                phoneField.getText()
        );

        try {
            CSVUtils.writeDataToCSV(STUDENT_FORM_CSV, data);
            System.out.println("Data saved to CSV.");
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdate() {
        List<String> newData = Arrays.asList(
                studentIdField.getText(),
                usernameField.getText(),
                passwordField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                facultyField.getText(),
                emailField.getText(),
                genderComboBox.getValue(),
                phoneField.getText()
        );

        try {
            CSVUtils.updateCSV(STUDENT_FORM_CSV, studentIdField.getText(), newData);
            System.out.println("Data updated in CSV.");
        } catch (IOException e) {
            System.err.println("Error updating CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        try {
            CSVUtils.deleteFromCSV(STUDENT_FORM_CSV, studentIdField.getText());
            System.out.println("Data deleted from CSV.");
        } catch (IOException e) {
            System.err.println("Error deleting from CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
