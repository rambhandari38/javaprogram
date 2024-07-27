package com.project.studentmanagement.controllers.teacherController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grade {

    public TableView<Grade> gradeTableView;
    public TableColumn<Grade, String> subjectColumn;
    public TableColumn<Grade, String> gradeColumn;
    public TextField subjectField;
    public TextField gradeField;

    private String subject;
    private String grade;

    public Grade() {
        // no-argument constructor
    }

    public Grade(String subject, String grade) {
        this.subject = subject;
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void initialize() {
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // Sample data
        gradeTableView.getItems().addAll(
                new Grade("Math", "A"),
                new Grade("Science", "B+"),
                new Grade("History", "A-")
        );
    }

    public void handleBackButtonAction(ActionEvent actionEvent) {
        try {
            // Load the main FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();

            // Display the main page in a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Main");
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) gradeTableView.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSaveButtonAction(ActionEvent actionEvent) {
        List<Grade> grades = new ArrayList<>(gradeTableView.getItems());
        String filePath = "grades.csv";
        saveGradesToCSV(grades, filePath);
    }

    private void saveGradesToCSV(List<Grade> grades, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Subject,Grade\n");
            for (Grade grade : grades) {
                writer.write(grade.getSubject() + "," + grade.getGrade() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddButtonAction(ActionEvent actionEvent) {
            String subject = subjectField.getText();
            String grade = gradeField.getText();

            if (!subject.isEmpty() &&!grade.isEmpty()) {
                Grade newGrade = new Grade(subject, grade);
                gradeTableView.getItems().add(newGrade);
                subjectField.clear();
                gradeField.clear();
            }
        }
    }
