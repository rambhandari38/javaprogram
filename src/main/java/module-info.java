module com.example.studentmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.logging;


    opens com.project.studentmanagement to javafx.fxml;
    exports com.project.studentmanagement;

    exports com.project.studentmanagement.controllers;
    opens com.project.studentmanagement.controllers to javafx.fxml;

    exports com.project.studentmanagement.model;
    opens com.project.studentmanagement.model to javafx.fxml;

    exports com.project.studentmanagement.controllers.bookController;
    opens com.project.studentmanagement.controllers.bookController to javafx.fxml;

    exports com.project.studentmanagement.controllers.studentController;
    opens com.project.studentmanagement.controllers.studentController to javafx.fxml;

    exports com.project.studentmanagement.controllers.teacherController;
    opens com.project.studentmanagement.controllers.teacherController to javafx.fxml;
}

