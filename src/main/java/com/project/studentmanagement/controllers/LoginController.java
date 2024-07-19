package com.project.studentmanagement.controllers;

import com.project.studentmanagement.Main;
//import com.project.studentmanagement.User;
import com.project.studentmanagement.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

//    --------------------------------------------------------------------
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
//    --------------------------------------------------------------------
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
