package com.project.studentmanagement.controllers.studentController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OverviewController {

    @FXML
    private TableView<OverviewRecord> overviewTable;

    @FXML
    private TableColumn<OverviewRecord, String> activityColumn;

    @FXML
    private TableColumn<OverviewRecord, String> dateColumn;

    @FXML
    private TableColumn<OverviewRecord, String> detailsColumn;

    @FXML
    public void initialize() {
        ObservableList<OverviewRecord> data = FXCollections.observableArrayList(
                new OverviewRecord("Logged In", "2024-07-01", "User logged in from IP 192.168.0.1"),
                new OverviewRecord("Submitted Assignment", "2024-07-03", "Submitted assignment for Math 101"),
                new OverviewRecord("Viewed Grades", "2024-07-05", "Viewed grades for Science 101"),
                new OverviewRecord("Updated Profile", "2024-07-07", "Updated contact information and profile picture"),
                new OverviewRecord("Attended Lecture", "2024-07-10", "Attended lecture on Data Structures"),
                new OverviewRecord("Downloaded Material", "2024-07-12", "Downloaded study material for History 201"),
                new OverviewRecord("Participated in Forum", "2024-07-15", "Participated in discussion about project deadlines"),
                new OverviewRecord("Checked Email", "2024-07-18", "Checked and responded to university email"),
                new OverviewRecord("Registered for Course", "2024-07-20", "Registered for Advanced Programming course")
        );

        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));

        overviewTable.setItems(data);
    }

    public static class OverviewRecord {
        private final String activity;
        private final String date;
        private final String details;

        public OverviewRecord(String activity, String date, String details) {
            this.activity = activity;
            this.date = date;
            this.details = details;
        }

        public String getActivity() {
            return activity;
        }

        public String getDate() {
            return date;
        }

        public String getDetails() {
            return details;
        }
    }
}
