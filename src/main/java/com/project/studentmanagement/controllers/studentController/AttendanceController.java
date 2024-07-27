package com.project.studentmanagement.controllers.studentController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class AttendanceController {

    @FXML
    private ComboBox<String> classComboBox;

    @FXML
    private TableView<AttendanceRecord> attendanceTable;

    @FXML
    private TableColumn<AttendanceRecord, String> nameColumn;

    @FXML
    private TableColumn<AttendanceRecord, String> dateColumn;

    @FXML
    private TableColumn<AttendanceRecord, String> statusColumn;

    private final String CSV_FILE_PATH = "studentData/attendance_records.csv";
    private ObservableList<AttendanceRecord> classAData = FXCollections.observableArrayList();
    private ObservableList<AttendanceRecord> classBData = FXCollections.observableArrayList();
    private ObservableList<AttendanceRecord> classCData = FXCollections.observableArrayList();
    private ObservableList<AttendanceRecord> classDData = FXCollections.observableArrayList();
    private ObservableList<AttendanceRecord> classEData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize ComboBox with class names
        classComboBox.setItems(FXCollections.observableArrayList("Class A", "Class B", "Class C", "Class D", "Class E"));
        classComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTableData(newValue));

        // Set up TableColumn cell value factories
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load data from CSV
        loadCSVData();
    }

    private void loadCSVData() {
        try {
            List<List<String>> csvData = CSVUtils.readDataFromCSV(CSV_FILE_PATH);

            // Clear current data
            classAData.clear();
            classBData.clear();
            classCData.clear();
            classDData.clear();
            classEData.clear();

            // Populate data for each class from CSV
            for (List<String> record : csvData) {
                if (record.size() >= 4) {
                    String className = record.get(0);
                    String name = record.get(1);
                    String date = record.get(2);
                    String status = record.get(3);
                    AttendanceRecord attendanceRecord = new AttendanceRecord(name, date, status);

                    switch (className) {
                        case "Class A":
                            classAData.add(attendanceRecord);
                            break;
                        case "Class B":
                            classBData.add(attendanceRecord);
                            break;
                        case "Class C":
                            classCData.add(attendanceRecord);
                            break;
                        case "Class D":
                            classDData.add(attendanceRecord);
                            break;
                        case "Class E":
                            classEData.add(attendanceRecord);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTableData(String selectedClass) {
        switch (selectedClass) {
            case "Class A":
                attendanceTable.setItems(classAData);
                break;
            case "Class B":
                attendanceTable.setItems(classBData);
                break;
            case "Class C":
                attendanceTable.setItems(classCData);
                break;
            case "Class D":
                attendanceTable.setItems(classDData);
                break;
            case "Class E":
                attendanceTable.setItems(classEData);
                break;
            default:
                attendanceTable.setItems(FXCollections.emptyObservableList());
                break;
        }
    }
}
