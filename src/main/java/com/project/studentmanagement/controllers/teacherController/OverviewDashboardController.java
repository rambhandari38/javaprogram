package com.project.studentmanagement.controllers.teacherController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OverviewDashboardController {

    @FXML
    private Label totalUsersLabel;

    @FXML
    private Label activeCoursesLabel;

    @FXML
    private Label attendanceRateLabel;

    @FXML
    void initialize() {
        // Initialize the labels with data from the database or other sources
        totalUsersLabel.setText("1500");
        activeCoursesLabel.setText("30");
        attendanceRateLabel.setText("85%");
    }
}