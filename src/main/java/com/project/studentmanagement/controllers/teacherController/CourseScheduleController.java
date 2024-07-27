package com.project.studentmanagement.controllers.teacherController;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class CourseScheduleController {

    public static class Course {

        private StringProperty time;
        private StringProperty monday;
        private StringProperty tuesday;
        private StringProperty wednesday;
        private StringProperty thursday;
        private StringProperty friday;

        public Course(String time, String monday, String tuesday, String wednesday, String thursday, String friday) {
            this.time = new SimpleStringProperty(time);
            this.monday = new SimpleStringProperty(monday);
            this.tuesday = new SimpleStringProperty(tuesday);
            this.wednesday = new SimpleStringProperty(wednesday);
            this.thursday = new SimpleStringProperty(thursday);
            this.friday = new SimpleStringProperty(friday);
        }

        public StringProperty timeProperty() {
            return time;
        }

        public StringProperty mondayProperty() {
            return monday;
        }

        public StringProperty tuesdayProperty() {
            return tuesday;
        }

        public StringProperty wednesdayProperty() {
            return wednesday;
        }

        public StringProperty thursdayProperty() {
            return thursday;
        }

        public StringProperty fridayProperty() {
            return friday;
        }
    }

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TableColumn<Course, String> timeColumn;

    @FXML
    private TableColumn<Course, String> mondayColumn;

    @FXML
    private TableColumn<Course, String> tuesdayColumn;

    @FXML
    private TableColumn<Course, String> wednesdayColumn;

    @FXML
    private TableColumn<Course, String> thursdayColumn;

    @FXML
    private TableColumn<Course, String> fridayColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    private ObservableList<Course> courses;

    @FXML
    public void initialize() {
        // Initialize course data
        courses = FXCollections.observableArrayList();
        courses.add(new Course("8:00 - 9:00 AM", "Mathematics", "Physics", "Chemistry", "Chemistry", "Computer Science"));
        courses.add(new Course("9:00 - 10:00 AM", "English", "Physics Lab", " ", "Chemistry Lab", "Computer Science"));
        courses.add(new Course("10:00 - 11:00 AM", "Break", "Break", " ", "Break", "Break"));

        // Set table data
        courseTable.setItems(courses);

        wednesdayColumn.setCellValueFactory(cellData -> cellData.getValue().wednesdayProperty());
        thursdayColumn.setCellValueFactory(cellData -> cellData.getValue().thursdayProperty());
        fridayColumn.setCellValueFactory(cellData -> cellData.getValue().fridayProperty());
    }

    @FXML
    public void handleAddButtonAction() {
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Add Course");
        dialog.setHeaderText("Enter course details:");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField time = new TextField();
        TextField monday = new TextField();
        TextField tuesday = new TextField();
        TextField wednesday = new TextField();
        TextField thursday = new TextField();
        TextField friday = new TextField();

        grid.add(new Label("Time:"), 0, 0);
        grid.add(time, 1, 0);
        grid.add(new Label("Monday:"), 0, 1);
        grid.add(monday, 1, 1);
        grid.add(new Label("Tuesday:"), 0, 2);
        grid.add(tuesday, 1, 2);
        grid.add(new Label("Wednesday:"), 0, 3);
        grid.add(wednesday, 1, 3);
        grid.add(new Label("Thursday:"), 0, 4);
        grid.add(thursday, 1, 4);
        grid.add(new Label("Friday:"), 0, 5);
        grid.add(friday, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default
        Platform.runLater(() -> time.requestFocus());

        // Convert the result to a Course object when the add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return new Course(time.getText(), monday.getText(), tuesday.getText(), wednesday.getText(), thursday.getText(), friday.getText());
            }
            return null;
        });

        // Show the dialog and get the result
        Optional<Course> result = dialog.showAndWait();

        result.ifPresent(course -> courses.add(course));
    }

    @FXML
    public void handleDeleteButtonAction() {
        // Delete selected course from table
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            courses.remove(selectedCourse);
        }
    }

    @FXML
    public void handleSaveButtonAction() {
        saveCoursesToCSV("courses.csv");
    }

    private void saveCoursesToCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Time,Monday,Tuesday,Wednesday,Thursday,Friday\n");
            for (Course course : courses) {
                writer.write(course.timeProperty().get() + ","
                        + course.mondayProperty().get() + ","
                        + course.tuesdayProperty().get() + ","
                        + course.wednesdayProperty().get() + ","
                        + course.thursdayProperty().get() + ","
                        + course.fridayProperty().get() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}