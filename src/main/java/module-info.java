module com.example.trainging5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.studentmanagement to javafx.fxml;
    exports com.project.studentmanagement;
    exports com.project.studentmanagement.controllers;
    opens com.project.studentmanagement.controllers to javafx.fxml;
    exports com.project.studentmanagement.model;
    opens com.project.studentmanagement.model to javafx.fxml;
}