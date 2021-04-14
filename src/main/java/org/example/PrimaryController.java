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
import org.example.domain.Transaction;
import org.example.service.ServiceMaterial;
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
    public TextField txtTransactionId;
    public TextField txtNumberOfItems;
    public TextField txtMaterialIdentification;
    public TextField txtStudentId;
    public TextField txtDateAndHour;
    public TextField txtSearchText;
    public Button btnFullTextSearch;
    public TextField txtDateHourStart;
    public TextField txtDateHourEnd;
    private ServiceMaterial serviceMaterial ;
    private ServiceTransaction transactionService;

    private ObservableList<Material> materials = FXCollections.observableArrayList();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private UndoRedoManager undoRedoManager;

    @FXML
    private void initialize() {


        Platform.runLater(() -> {
            materials.addAll(serviceMaterial.getAll());
            transactions.addAll(transactionService.getAll());
            tblMaterials.setItems(materials);
            tblTransactions.setItems(transactions);
        });
    }

    public void setServices(ServiceMaterial serviceMaterial, ServiceTransaction transactionService, UndoRedoManager undoRedoManager) {
        this.serviceMaterial = serviceMaterial;
        this.transactionService = transactionService;
        this.undoRedoManager = undoRedoManager;
    }

    public void btnAddMaterialClick(ActionEvent actionEvent) throws Exception {
        try {
            int id = Integer.parseInt(txtMaterialId.getText());
            String name = txtMaterialName.getText();
            String author = txtMaterialAuthor.getText();
            String description = txtMaterialDescription.getText();
            int numberOfPages = Integer.parseInt(txtMaterialNumberOfPages.getText());


            serviceMaterial.addMaterial(id, name, author, description,numberOfPages);

            refreshMaterialList();
        } catch (RuntimeException rex) {
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

            serviceMaterial.updateMaterial(id, name, author, description,numberOfPages);

            refreshMaterialList();
        } catch (RuntimeException rex) {
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
            int numberOfItems = Integer.parseInt(txtNumberOfItems.getText());
            int studentId = Integer.parseInt(txtStudentId.getText());
            transactionService.addTransaction(id, materialId, dateAndHour, numberOfItems, studentId);

            refreshTransactionList();
        } catch (Exception ex) {
            Common.showErrorMessage(ex.getMessage());
        }
    }

    public void btnUpdateTransactionClick(ActionEvent actionEvent) {
    }

    public void btnDeleteSelectedTransactionClick(ActionEvent actionEvent) {
    }

    public void btnFullTextSearchClick(ActionEvent actionEvent) {
        List<Material> materialResults = serviceMaterial.getMaterialsByText(txtSearchText.getText());
        List<Transaction> transactionResults = transactionService.getTransactionsByText(txtSearchText.getText());
        materials.clear();
        materials.addAll(materialResults);

        transactions.clear();
        transactions.addAll(transactionResults);
    }

    // 15.02.2021 20:00
    // 23.03.2021 16:00
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

//    public void btnShowCookiesWithNumberOfTransactionsClick(ActionEvent actionEvent) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("cookiesWithNumberOfTransactions.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//            Stage stage = new Stage();
//
//            CookiesWithNumberOfTransactions resultsController = fxmlLoader.getController();
//            resultsController.setPrajituriService(this.cookieService);
//
//            stage.setTitle("Prajiturile impreuna cu vanzarile");
//            stage.setScene(scene);
//            stage.showAndWait();
//        } catch (IOException iex) {
//            Common.showErrorMessage(iex.getMessage());
//        }
//    }

    public void btnUndoClick(ActionEvent actionEvent) {
        this.undoRedoManager.doUndo();

        this.refreshMaterialList();
        this.refreshTransactionList();
    }

    public void btnRedoClick(ActionEvent actionEvent) {
        this.undoRedoManager.doRedo();

        this.refreshMaterialList();
        this.refreshTransactionList();
    }

    public void btnOpenAddCookieWindowClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addCookie.fxml"));
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
}
