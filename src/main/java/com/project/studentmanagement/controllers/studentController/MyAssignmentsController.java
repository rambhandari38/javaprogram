package com.project.studentmanagement.controllers.studentController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyAssignmentsController {

    @FXML
    private TableView<AssignmentRecord> assignmentsTable;

    @FXML
    private TableColumn<AssignmentRecord, String> assignmentNameColumn;

    @FXML
    private TableColumn<AssignmentRecord, String> dueDateColumn;

    @FXML
    private TableColumn<AssignmentRecord, String> statusColumn;

    @FXML
    public void initialize() {
        assignmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentName"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load data from CSV
        ObservableList<AssignmentRecord> data = loadAssignmentsFromCSV("studentData/assignments.csv");
        assignmentsTable.setItems(data);
    }

    private ObservableList<AssignmentRecord> loadAssignmentsFromCSV(String filePath) {
        ObservableList<AssignmentRecord> data = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    data.add(new AssignmentRecord(fields[0], fields[1], fields[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }

    public static class AssignmentRecord {
        private final String assignmentName;
        private final String dueDate;
        private final String status;

        public AssignmentRecord(String assignmentName, String dueDate, String status) {
            this.assignmentName = assignmentName;
            this.dueDate = dueDate;
            this.status = status;
        }

        public String getAssignmentName() {
            return assignmentName;
        }

        public String getDueDate() {
            return dueDate;
        }

        public String getStatus() {
            return status;
        }
    }
}
