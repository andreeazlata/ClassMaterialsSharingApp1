package org.example;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.domain.StudentWithNumberOfDownloads;
import org.example.service.ServiceStudent;

public class PersonWithNumberOfDownloads {

    public TableView tblStudentWithDownloads;
    private ServiceStudent serviceStudent;

    public void setStudentService(ServiceStudent serviceStudent) {
        this.serviceStudent = serviceStudent;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {

            ObservableList<StudentWithNumberOfDownloads> studentWithNumberOfDownloads = FXCollections.observableArrayList();
            studentWithNumberOfDownloads.addAll(serviceStudent.getStudentWithNumberOfDownloads());
            tblStudentWithDownloads.setItems(studentWithNumberOfDownloads);
        });
    }
}
