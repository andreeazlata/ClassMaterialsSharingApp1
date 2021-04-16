package org.example;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.domain.Material;
import org.example.domain.Student;
import org.example.domain.Transaction;
import org.example.service.ServiceMaterial;
import org.example.service.ServiceStudent;
import org.example.service.ServiceTransaction;
import org.example.service.UndoRedoManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrimaryController {

    public TextField txtMaterialId;
    public TextField txtMaterialName;
    public TextField txtMaterialAuthor;
    public TextField txtMaterialDescription;
    public TextField txtMaterialNumberOfPages;
    public TableView tblMaterials;
    public TableView tblTransactions;
    public TableView tblStudents;
    public TextField txtTransactionId;
    public TextField txtNumberOfItems;
    public TextField txtMaterialIdentification;
    public TextField txtStudentId;
    public TextField txtUploaderId;
    public TextField txtNumberOfUploads;
    public TextField txtStudentIdentification;
    public TextField txtDateAndHour;
    public TextField txtSearchText;
    public Button btnFullTextSearch;
    public TextField txtDateHourStart;
    public TextField txtDateHourEnd;
    public TextField txtStudentName;
    public TextField txtDepartment;
    private ServiceMaterial serviceMaterial;
    private ServiceTransaction transactionService;
    private ServiceStudent serviceStudent;

    private final ObservableList<Material> materials = FXCollections.observableArrayList();
    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private final ObservableList<Student> students = FXCollections.observableArrayList();
    private UndoRedoManager undoRedoManager;

    @FXML
    private void initialize() {


        Platform.runLater(() -> {
            materials.addAll(serviceMaterial.getAll());
            transactions.addAll(transactionService.getAll());
            students.addAll(serviceStudent.getAll());
            tblMaterials.setItems(materials);
            tblTransactions.setItems(transactions);
            tblStudents.setItems(students);
        });
    }

    public void setServices(ServiceMaterial serviceMaterial, ServiceTransaction transactionService, ServiceStudent serviceStudent, UndoRedoManager undoRedoManager) {
        this.serviceMaterial = serviceMaterial;
        this.transactionService = transactionService;
        this.serviceStudent = serviceStudent;
        this.undoRedoManager = undoRedoManager;
    }

    /**
     * adds a student when the button is clicked.
     *
     * @param actionEvent
     * @throws Exception
     */
    public void btnAddStudentClick(ActionEvent actionEvent) throws Exception {
        try {
            int id = Integer.parseInt(txtStudentIdentification.getText());
            String name = txtStudentName.getText();
            String department = txtDepartment.getText();


            serviceStudent.addStudent(id, name, department);

            refreshStudentList();
        } catch (Exception rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    /**
     * updates a already existing student when the button is clicked.
     *
     * @param actionEvent
     * @throws Exception
     */
    public void btnUpdateStudentClick(ActionEvent actionEvent) throws Exception {
        try {
            int id = Integer.parseInt(txtStudentIdentification.getText());
            String name = txtStudentName.getText();
            String department = txtDepartment.getText();


            serviceStudent.updateStudent(id, name, department);

            refreshStudentList();
        } catch (Exception rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    /**
     * deletes a selected student when the button is clicked.
     *
     * @param actionEvent
     */
    public void btnDeleteSelectedStudentClick(ActionEvent actionEvent) {
        Student selectedStudent = (Student) tblStudents.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            serviceStudent.deleteStudent(selectedStudent.getIdEntity());
            refreshStudentList();
        }
    }

    private void refreshStudentList() {
        students.clear();
        students.addAll(serviceStudent.getAll());
    }

    public void btnAddMaterialClick(ActionEvent actionEvent) throws Exception {
        try {
            int id = Integer.parseInt(txtMaterialId.getText());
            String name = txtMaterialName.getText();
            String author = txtMaterialAuthor.getText();
            String description = txtMaterialDescription.getText();
            int numberOfPages = Integer.parseInt(txtMaterialNumberOfPages.getText());
            int uploaderId = Integer.parseInt(txtUploaderId.getText());


            serviceMaterial.addMaterial(id, name, author, description, numberOfPages, uploaderId);

            refreshMaterialList();
            refreshStudentList();
        } catch (Exception rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnUpdateMaterialClick(ActionEvent actionEvent) throws Exception {
        try {
            int id = Integer.parseInt(txtMaterialId.getText());
            String name = txtMaterialName.getText();
            String author = txtMaterialAuthor.getText();
            String description = txtMaterialDescription.getText();
            int numberOfPages = Integer.parseInt(txtMaterialNumberOfPages.getText());
            int uploaderId = Integer.parseInt(txtUploaderId.getText());

            serviceMaterial.updateMaterial(id, name, author, description, numberOfPages, uploaderId);

            refreshMaterialList();
            refreshStudentList();
        } catch (Exception rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnDeleteSelectedMaterialClick(ActionEvent actionEvent) {
        Material selectedMaterial = (Material) tblMaterials.getSelectionModel().getSelectedItem();
        if (selectedMaterial != null) {
            serviceMaterial.delete(selectedMaterial.getIdEntity());
            refreshMaterialList();
        }
    }

    private void refreshMaterialList() {
        materials.clear();
        materials.addAll(serviceMaterial.getAll());
    }

    private void refreshTransactionList() {
        transactions.clear();
        transactions.addAll(transactionService.getAll());
    }

    public void btnAddTransactionClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtTransactionId.getText());
            int materialId = Integer.parseInt(txtMaterialIdentification.getText());
            String dateAndHour = txtDateAndHour.getText();
            int studentId = Integer.parseInt(txtStudentId.getText());
            transactionService.addTransaction(id, materialId, dateAndHour, studentId);

            refreshTransactionList();
        } catch (Exception ex) {
            Common.showErrorMessage(ex.getMessage());
        }
    }

    public void btnUpdateTransactionClick(ActionEvent actionEvent) throws Exception {
        try {
            int id = Integer.parseInt(txtTransactionId.getText());
            int materialId = Integer.parseInt(txtMaterialIdentification.getText());
            String dateAndHour = txtDateAndHour.getText();
            int studentId = Integer.parseInt(txtStudentId.getText());
            transactionService.updateTransaction(id, materialId, dateAndHour, studentId);

            refreshTransactionList();
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnDeleteSelectedTransactionClick(ActionEvent actionEvent) {
        Transaction selectedTransaction = (Transaction) tblTransactions.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            transactionService.deleteTransaction(selectedTransaction.getIdEntity());
            refreshTransactionList();
        }
    }

    public void btnFullTextSearchClick(ActionEvent actionEvent) {
        List<Material> materialResults = serviceMaterial.getMaterialsByText(txtSearchText.getText());
        List<Transaction> transactionResults = transactionService.getTransactionsByText(txtSearchText.getText());
        List<Student> studentResults = serviceStudent.getStudentsByText(txtSearchText.getText());
        materials.clear();
        students.clear();
        transactions.clear();
        materials.addAll(materialResults);
        transactions.addAll(transactionResults);
        students.addAll(studentResults);
    }

    // 15.02.2021 20:00
    // 18.03.2021 10:00
    public void btnTransactionDateHourSearchClick(ActionEvent actionEvent) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime start = LocalDateTime.parse(txtDateHourStart.getText(), formatter);
            LocalDateTime end = LocalDateTime.parse(txtDateHourEnd.getText(), formatter);
            List<Transaction> searchResults = transactionService.getBetweenTwoDateAndTimes(start, end);

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("transactionDateSearchResults.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            TransactionDateSearchResultsController resultsController = fxmlLoader.getController();
            resultsController.setResultsList(searchResults);


            stage.setTitle("Transaction date search results");
            stage.setScene(scene);
            stage.showAndWait();

        } catch (RuntimeException | IOException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }


    public void btnUndoClick(ActionEvent actionEvent) {
        this.undoRedoManager.doUndo();

        this.refreshStudentList();
        this.refreshMaterialList();
        this.refreshTransactionList();
    }

    public void btnRedoClick(ActionEvent actionEvent) {
        this.undoRedoManager.doRedo();

        this.refreshStudentList();
        this.refreshMaterialList();
        this.refreshTransactionList();
    }

    public void btnOpenAddMaterialWindowClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addMaterial.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            AddMaterial addMaterialController = fxmlLoader.getController();
            addMaterialController.setMaterialService(this.serviceMaterial);
            addMaterialController.setObservableList(this.materials);

            stage.setTitle("Adaugare material");
            stage.setScene(scene);
            stage.show();
        } catch (IOException iex) {
            Common.showErrorMessage(iex.getMessage());
        }
    }

    public void btnShowStudentsWithNumberOfDownloadsClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("studentWithNumberOfDownloads.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            PersonWithNumberOfDownloads resultsController = fxmlLoader.getController();
            resultsController.setStudentService(this.serviceStudent);

            stage.setTitle("Students with downloads");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException iex) {
            Common.showErrorMessage(iex.getMessage());
        }
    }

    public void btnShowStudentsWithNumberOfUploadsClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("studentWithNumberOfUploads.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            PersonWithNumberOfUploads resultsController = fxmlLoader.getController();
            resultsController.setStudentService(this.serviceStudent);

            stage.setTitle("Students with uploads");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException iex) {
            Common.showErrorMessage(iex.getMessage());
        }
    }
}
