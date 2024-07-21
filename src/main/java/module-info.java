module com.example.trainging5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;


    opens com.project.studentmanagement to javafx.fxml;
    exports com.project.studentmanagement;

    exports com.project.studentmanagement.controllers;
    opens com.project.studentmanagement.controllers to javafx.fxml;

    exports com.project.studentmanagement.model;
    opens com.project.studentmanagement.model to javafx.fxml;

//    exports delete.delete;
//    opens delete.delete to javafx.fxml;
    exports com.project.studentmanagement.controllers.bookController;
    opens com.project.studentmanagement.controllers.bookController to javafx.fxml;
    exports delete.delete;
    opens delete.delete to javafx.fxml;
}

