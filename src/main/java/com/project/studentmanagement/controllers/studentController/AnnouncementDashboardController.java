package com.project.studentmanagement.controllers.studentController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AnnouncementDashboardController {

    @FXML
    private Label importantNoticeTitle;

    @FXML
    private Label importantNoticeContent;

    @FXML
    private Label importantNoticeDate;

    @FXML
    private Label examScheduleTitle;

    @FXML
    private Label examScheduleContent;

    @FXML
    private Label examScheduleDate;

    @FXML
    private Label libraryClosureTitle;

    @FXML
    private Label libraryClosureContent;

    @FXML
    private Label libraryClosureDate;

    @FXML
    private void initialize() {
        // Initialize or update the labels with dynamic data
        importantNoticeTitle.setText("Important Notice");
        importantNoticeContent.setText("Details about the important notice...");
        importantNoticeDate.setText("Posted on: 2024-07-05");

        examScheduleTitle.setText("Exam Schedule Update");
        examScheduleContent.setText("Details about the exam schedule update...");
        examScheduleDate.setText("Posted on: 2024-07-05");

        libraryClosureTitle.setText("Library Closure");
        libraryClosureContent.setText("Details about the library closure...");
        libraryClosureDate.setText("Posted on: 2024-07-04");
    }

    @FXML
    private void handleBack() {
        // Handle the back button action
        System.out.println("Back button clicked");
        // Here you can implement the logic to navigate back to the previous screen
    }
}
