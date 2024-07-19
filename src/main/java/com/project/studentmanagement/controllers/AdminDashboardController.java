package com.project.studentmanagement.controllers;

import com.project.studentmanagement.Main;
import com.project.studentmanagement.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminDashboardController {
    @FXML
    private AnchorPane mainDashboard;
    @FXML
    private AnchorPane studentDashboard;
    @FXML
    private AnchorPane teacherDashboard;
    @FXML
    private AnchorPane courseDashboard;
    @FXML
    private AnchorPane settingsDashboard;

    private Main mainApp;

    //    ----------------------------------------
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void handleMinimizeButtonAction() {
        if (stage != null) {
            stage.setIconified(true);
        }
    }

    @FXML
    protected void handleCloseButtonAction() {
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    protected void handleTitleBarPressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    protected void handleTitleBarDragged(MouseEvent event) {
        if (stage != null) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        }
    }
//    -----------------------------------------------------------------

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private void showMainDashboard() {
        mainDashboard.setVisible(true);
        studentDashboard.setVisible(false);
        teacherDashboard.setVisible(false);
        courseDashboard.setVisible(false);
        settingsDashboard.setVisible(false);
    }

    @FXML
    private void showStudentDashboard() {
        mainDashboard.setVisible(false);
        studentDashboard.setVisible(true);
        teacherDashboard.setVisible(false);
        courseDashboard.setVisible(false);
        settingsDashboard.setVisible(false);
    }

    @FXML
    private void showTeacherDashboard() {
        mainDashboard.setVisible(false);
        studentDashboard.setVisible(false);
        teacherDashboard.setVisible(true);
        courseDashboard.setVisible(false);
        settingsDashboard.setVisible(false);
    }

    @FXML
    private void showCourseDashboard() {
        mainDashboard.setVisible(false);
        studentDashboard.setVisible(false);
        teacherDashboard.setVisible(false);
        courseDashboard.setVisible(true);
        settingsDashboard.setVisible(false);
    }

    @FXML
    private void showSettingsDashboard() {
        mainDashboard.setVisible(false);
        studentDashboard.setVisible(false);
        teacherDashboard.setVisible(false);
        courseDashboard.setVisible(false);
        settingsDashboard.setVisible(true);
    }

    @FXML
    private void handleLogout() {
        mainApp.showLoginScreen();
    }

    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
//        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
//        roleLabel.setText("Role: " + user.getRole());
    }

}
