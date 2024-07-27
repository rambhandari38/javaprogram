package com.project.studentmanagement.controllers.studentController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FormController {

	@FXML
	private void showStudentForm() throws IOException {
		loadForm("/com/project/studentmanagement/fxml/studentfxml/StudentForm.fxml");
	}

	@FXML
	private void showStudentProblemForm() throws IOException {
		loadForm("/com/project/studentmanagement/fxml/studentfxml/StudentProblemForm.fxml");
	}

	private void loadForm(String fxmlFile) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		Parent root = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}
}
