package com.project.studentmanagement.controllers.studentController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;

public class StudentProblemFormController {

    @FXML
    private TextField problemIdField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField scIdField;

    @FXML
    private TextField creatorNameField;

    @FXML
    private Button submitButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button deleteButton;

    private static final String CSV_FILE_PATH = "studentData/student_problems.csv";

    @FXML
    public void handleSubmit() {
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bw)) {

            String problemData = String.join(",",
                    problemIdField.getText(),
                    titleField.getText(),
                    scIdField.getText(),
                    creatorNameField.getText()
            );

            out.println(problemData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleView() {
        // Implement view logic for CSV
    }

    @FXML
    public void handleDelete() {
        // Implement delete logic for CSV
    }
}
