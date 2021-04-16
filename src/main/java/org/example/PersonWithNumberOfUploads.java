package org.example;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.domain.StudentWithNumberOfUploads;
import org.example.service.ServiceStudent;

public class PersonWithNumberOfUploads {

    public TableView tblStudentWithUploads;
    private ServiceStudent serviceStudent;

    public void setStudentService(ServiceStudent serviceStudent) {
        this.serviceStudent = serviceStudent;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {

            ObservableList<StudentWithNumberOfUploads> studentWithNumberOfUploads = FXCollections.observableArrayList();
            studentWithNumberOfUploads.addAll(serviceStudent.getStudentWithNumberOfUploads());
            tblStudentWithUploads.setItems(studentWithNumberOfUploads);
        });
    }
}
