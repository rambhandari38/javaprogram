package com.project.studentmanagement.controllers.studentController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GradeController implements Initializable {

    @FXML
    private TableView<GradeRecord> gradeTable;

    @FXML
    private TableColumn<GradeRecord, String> subjectColumn;

    @FXML
    private TableColumn<GradeRecord, String> gradeColumn;

    @FXML
    private TableColumn<GradeRecord, String> remarksColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up TableColumn cell value factories
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        remarksColumn.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        // Load data from CSV
        ObservableList<GradeRecord> data = loadGradesFromCSV("studentData/grades.csv");
        gradeTable.setItems(data);
    }

    private ObservableList<GradeRecord> loadGradesFromCSV(String filePath) {
        ObservableList<GradeRecord> data = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    data.add(new GradeRecord(fields[0], fields[1], fields[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }

    public static class GradeRecord {
        private final String subject;
        private final String grade;
        private final String remarks;

        public GradeRecord(String subject, String grade, String remarks) {
            this.subject = subject;
            this.grade = grade;
            this.remarks = remarks;
        }

        public String getSubject() {
            return subject;
        }

        public String getGrade() {
            return grade;
        }

        public String getRemarks() {
            return remarks;
        }
    }
}
