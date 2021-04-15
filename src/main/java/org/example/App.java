package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.repository.IRepository;
import org.example.service.ServiceMaterial;
import org.example.service.ServiceStudent;
import org.example.service.ServiceTransaction;
import org.example.service.UndoRedoManager;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Parent parentFxml = fxmlLoader.load();
        scene = new Scene(parentFxml, 640, 480);

        RepositoryFactory repositoryFactory = new RepositoryFactory(RepositoryFactory.JSON_REPOSITORY);
        IRepository<Material> materialIRepository = repositoryFactory.getMaterialRepository();
        IRepository<Transaction> transactionRepository = repositoryFactory.getTransactionRepository();
        IRepository<Student> studentIRepository = repositoryFactory.getStudentRepository();

        TransactionValidator transactionValidator = new TransactionValidator();
        MaterialValidator materialValidator = new MaterialValidator();
        StudentValidator studentValidator = new StudentValidator();
        UndoRedoManager undoRedoManager = new UndoRedoManager();
        ServiceMaterial serviceMaterial = new ServiceMaterial(materialIRepository, transactionRepository,studentIRepository, materialValidator, undoRedoManager);
        ServiceTransaction serviceTransaction = new ServiceTransaction(transactionRepository, studentIRepository, transactionValidator, undoRedoManager);
        ServiceStudent serviceStudent = new ServiceStudent(studentIRepository, transactionRepository, studentValidator, undoRedoManager);


        PrimaryController primaryController = fxmlLoader.getController();
        primaryController.setServices(serviceMaterial, serviceTransaction, serviceStudent, undoRedoManager);

        stage.setTitle("Materials manager");
        stage.setScene(scene);
        stage.show();

    }
}