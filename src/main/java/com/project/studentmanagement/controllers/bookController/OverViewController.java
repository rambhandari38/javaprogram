package com.project.studentmanagement.controllers.bookController;

import com.project.studentmanagement.Main;
import com.opencsv.CSVReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class OverViewController extends Parent {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }



    @FXML
    private BorderPane contentPane;
//    ----------------------------------------------

    private Main mainApp;
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

//    ----------------------------------------------
    @FXML
    private TextField totalBooks, totalBorrowed, toBeReturned, totalReturned;

    private final String pathToBorrowCSV = "StudentManagementData/libraryData/borrowBook.csv";
    private final String pathToBookCSV = "StudentManagementData/libraryData/bookData.csv";

    public void initialize(){
        updateRows();
    }
    public void updateRows() {
        int totalBookCount = countNonEmptyRows(pathToBookCSV);
        totalBooks.setText(String.valueOf(totalBookCount));

        int borrowedBookCount = countNonEmptyRows(pathToBorrowCSV);
        totalBorrowed.setText(String.valueOf(borrowedBookCount));

        int returnedBookCount = countRowsWithNValue(pathToBorrowCSV, 5);
        totalReturned.setText(String.valueOf(returnedBookCount));

        int booksToReturn = borrowedBookCount -returnedBookCount;
        toBeReturned.setText(String.valueOf(booksToReturn));
    }


    public int countRowsWithNValue(String csvFilePath, int n) {
        int rowCount = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFilePath));
            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                int nonEmptyCount = 0;
                for (String field : row) {
                    if (field != null && !field.trim().isEmpty()) {
                        nonEmptyCount++;
                    }
                }
                if (nonEmptyCount == n) {
                    rowCount++;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Node node = loader.load();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int countNonEmptyRows(String csvFilePath) {
        int rowCount = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFilePath));
            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                boolean isEmpty = true;
                for (String field : row) {
                    if (field != null && !field.trim().isEmpty()) {
                        isEmpty = false;
                        break;
                    }
                }
                if (!isEmpty) {
                    rowCount++;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowCount;
    }


    public void bookMngmtAction(ActionEvent actionEvent) {
        loadFXML("fxml/bookFxml/BookManagement.fxml");
    }

    public void libraryReviewAction(ActionEvent actionEvent) {
        loadFXML("fxml/bookFxml/LibraryManagement.fxml");
    }

    public void overviewAction(ActionEvent actionEvent) {
        loadFXML("fxml/bookFxml/Overview.fxml");
    }
}