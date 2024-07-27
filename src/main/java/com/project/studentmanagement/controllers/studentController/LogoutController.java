package com.project.studentmanagement.controllers.studentController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LogoutController {

    @FXML
    private Label logoutMessageLabel;

    @FXML
    public void initialize() {
        if (logoutMessageLabel != null) {
            // This line is optional since the text is already set in the FXML file
            logoutMessageLabel.setText("You have been logged out.");
        }
    }
}
