package com.project.studentmanagement.controllers;

import com.project.studentmanagement.Main;
import com.project.studentmanagement.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class TeacherDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label roleLabel;

    private Main mainApp;
    private User loggedInUser;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        roleLabel.setText("Role: " + user.getRole());
    }

    @FXML
    private void handleLogout() {
        mainApp.showLoginScreen();
    }
}
