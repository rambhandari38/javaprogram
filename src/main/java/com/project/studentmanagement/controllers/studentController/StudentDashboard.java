package com.project.studentmanagement.controllers.studentController;

import com.project.studentmanagement.Main;
import com.project.studentmanagement.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDashboard {

    private static final Logger LOGGER = Logger.getLogger(StudentDashboard.class.getName());

    private Main mainApp;

    private User loggedInUser;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }

    @FXML
    private BorderPane mainPane;

    @FXML
    private StackPane contentPane;

    @FXML
    private ImageView profileImage;

    @FXML
    public void initialize() {
        // Additional initialization code if needed
        if (mainPane == null) {
            LOGGER.severe("mainPane is null in initialize");
        } else {
            LOGGER.info("mainPane is successfully initialized");
        }

        if (contentPane == null) {
            LOGGER.severe("contentPane is null in initialize");
        } else {
            LOGGER.info("contentPane is successfully initialized");
        }
    }

    @FXML
    private void loadOverview() {
        loadUI("/com/project/studentmanagement/fxml/studentfxml/Overview.fxml");
    }

    @FXML
    private void loadMyAssignment() {
        loadUI("/com/project/studentmanagement/fxml/studentfxml/MyAssignment.fxml");
    }

    @FXML
    private void loadGrade() {
        loadUI("/com/project/studentmanagement/fxml/studentfxml/Grade.fxml");
    }

    @FXML
    private void loadAttendance() {
        loadUI("/com/project/studentmanagement/fxml/studentfxml/Attendance.fxml");
    }

    @FXML
    private void loadCourseSchedule() {
        loadUI("/com/project/studentmanagement/fxml/studentfxml/CourseSchedule.fxml");
    }

    @FXML
    private void loadPersonalInformation() {
        loadUI("/com/project/studentmanagement/fxml/studentfxml/PersonalInformation.fxml");
    }

    @FXML
    private void loadForm() {
        loadUI("/com/project/studentmanagement/fxml/studentfxml/Form.fxml");
    }

    @FXML
    private void loadAnnouncementDashboard() {
        loadUI("/com/project/studentmanagement/fxml/studentfxml/AnnouncementDashboard.fxml");
    }

    @FXML
    private void handleLogout() {
        LOGGER.info("handleLogout called");

        try {
            if (mainPane == null) {
                LOGGER.severe("mainPane is null in handleLogout");
                return;
            }

            Stage currentStage = (Stage) mainPane.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            } else {
                LOGGER.severe("Current stage is null");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/studentmanagement/fxml/studentfxml/Logout.fxml"));
            Parent root = loader.load();
            Stage logoutStage = new Stage();
            logoutStage.setScene(new Scene(root));
            logoutStage.setTitle("Logout");
            logoutStage.show();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error handling logout", e);
        }
    }

    @FXML
    public void loadUI(String ui) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(ui));
            Pane pane = loader.load();
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading UI: " + ui, e);
        }
    }
}
