package com.project.studentmanagement.controllers.studentController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.List;

public class CourseScheduleController {

    @FXML
    private TableView<Course> courseScheduleTable;

    @FXML
    private TableColumn<Course, String> courseColumn;

    @FXML
    private TableColumn<Course, String> timeColumn;

    @FXML
    private TableColumn<Course, String> locationColumn;

    @FXML
    private TextField courseField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField locationField;

    @FXML
    private Button addButton;

    private final ObservableList<Course> courseList = FXCollections.observableArrayList();
    private static final String COURSE_SCHEDULE_CSV = "studentData/course_schedule.csv";

    @FXML
    private void initialize() {
        // Initialize the columns with properties
        courseColumn.setCellValueFactory(cellData -> cellData.getValue().courseProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());

        // Set the items in the table
        courseScheduleTable.setItems(courseList);

        // Load existing data from CSV
        loadCoursesFromCSV();
    }

    @FXML
    private void addCourse() {
        String courseName = courseField.getText();
        String time = timeField.getText();
        String location = locationField.getText();

        // Validate input
        if (courseName.isEmpty() || time.isEmpty() || location.isEmpty()) {
            showError();
            return;
        }

        // Create a new Course object
        Course newCourse = new Course(courseName, time, location);
        courseList.add(newCourse);

        // Save the new course to CSV
        try {
            CSVUtils.writeDataToCSV(COURSE_SCHEDULE_CSV, List.of(courseName, time, location));
            System.out.println("Course added to CSV.");
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
            e.printStackTrace();
        }

        // Clear the input fields
        courseField.clear();
        timeField.clear();
        locationField.clear();
    }

    private void loadCoursesFromCSV() {
        try {
            List<List<String>> data = CSVUtils.readDataFromCSV(COURSE_SCHEDULE_CSV);
            for (List<String> row : data) {
                if (row.size() == 3) { // Ensure the row has the expected number of fields
                    Course course = new Course(row.get(0), row.get(1), row.get(2));
                    courseList.add(course);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all fields");
        alert.showAndWait();
    }
}
