package com.project.studentmanagement.controllers;

import com.project.studentmanagement.Main;
//import com.project.studentmanagement.User;
import com.project.studentmanagement.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = mainApp.validateLogin(username, password);
        if (user != null) {
            mainApp.showDashboard(user);
        } else {
            mainApp.showAlert("Login Failed", "Invalid username or password", Alert.AlertType.ERROR);
        }
    }
}
