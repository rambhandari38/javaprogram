package com.project.studentmanagement.controllers.teacherController;

import com.project.studentmanagement.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherDashboardController {

    @FXML
    private Label announcementDashboardLabel;

    @FXML
    private Label importantNoticeLabel;

    @FXML
    private Label libraryClosureLabel;

    @FXML
    private Label examScheduleUpdateLabel;

    @FXML
    private Button overviewButton;

    @FXML
    private Button courseScheduleButton;

    @FXML
    private Button gradeAssignmentsButton;

    @FXML
    private Button createAssignmentButton;

    @FXML
    private Button announcementButton;

    @FXML
    private Button logoutButton;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void handleCloseButtonAction() {
        if (stage != null) {
            stage.close();
        }
    }

    // Methods to handle button clicks (you'll need to implement these)

    @FXML
    void handleOverviewButtonAction() {
        // Load the overview dashboard FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/studentmanagement/fxml/teacherfxml/OverviewDashboardController.fxml"));
        try {
            // Display the overview dashboard page in a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Overview");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCourseScheduleButtonAction() {
        // Load the course schedule FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/studentmanagement/fxml/teacherfxml/CourseSchedule.fxml"));
        try {
            // Display the course schedule page in a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Course Schedule");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleGradesAssignmentsButtonAction() {
        // Load the grades assignments FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/studentmanagement/fxml/teacherfxml/Grade.fxml"));
        try {
            // Display the grades assignments page in a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Grades Assignments");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCreateAssignmentButtonAction() {
        // Load the create assignment FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/studentmanagement/fxml/teacherfxml/CreateAssignment.fxml"));
        try {
            // Display the create assignment page in a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Create Assignment");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAnnouncementButtonAction() {
        // Load the announcement FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/studentmanagement/fxml/teacherfxml/Announcement.fxml"));
        try {
            // Display the announcement page in a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Announcement");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogoutButtonAction() {
        // Handle logout action here
        // You might close the current stage and show a login screen, etc.
    }
}