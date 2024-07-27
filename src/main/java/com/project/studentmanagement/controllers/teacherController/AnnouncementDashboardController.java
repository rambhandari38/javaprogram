package com.project.studentmanagement.controllers.teacherController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AnnouncementDashboardController {

    @FXML
    private Button overviewButton;
    @FXML
    private Button courseScheduleButton;
    @FXML
    private Button gradeAssignmentsButton;
    @FXML
    private Button createAssignmentButton;
    @FXML
    private Button attendanceManagementButton;
    @FXML
    private Button announcementButton;
    @FXML
    private Button logoutButton;

    @FXML
    private Label importantNoticeLabel;
    @FXML
    private Label libraryClosureLabel;
    @FXML
    private Label examScheduleUpdateLabel;

    @FXML
    public void handleOverviewButtonAction(ActionEvent event) {
        // code to handle Overview button click
    }

    @FXML
    public void handleCourseScheduleButtonAction(ActionEvent event) {
        // code to handle Course Schedule button click
    }

    @FXML
    public void handleGradeAssignmentsButtonAction(ActionEvent event) {
        // code to handle Grade Assignments button click
    }


    @FXML
    public void handleAttendanceManagementButtonAction(ActionEvent event) {
        // code to handle Attendance Management button click
    }

    @FXML
    public void handleAnnouncementButtonAction(ActionEvent event) {
        // code to handle Announcement button click
    }

    public void handleLogoutButtonAction(ActionEvent actionEvent) {
        
    }

    @FXML
    public void handleCreateAssignmentButtonAction(ActionEvent event) {
        try {
            // Load the create assignment FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAssignment.fxml"));
            Parent root = loader.load();

            // Display the create assignment page in a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create Assignment");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}