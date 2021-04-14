package org.example;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.domain.Material;
import org.example.service.ServiceMaterial;

public class AddMaterial {
    public TableView tblMaterials;
    public TextField txtMaterialId;
    public TextField txtMaterialName;
    public TextField txtMaterialAuthor;
    public TextField txtMaterialDescription;
    public TextField txtMaterialNumberOfPages;


    private ServiceMaterial serviceMaterial;
    private ObservableList<Material> observableList;

    public void setMaterialService(ServiceMaterial serviceMaterial) {
        this.serviceMaterial = serviceMaterial;
    }

    public void setObservableList(ObservableList<Material> materials) {
        this.observableList = materials;
        this.tblMaterials.setItems(this.observableList);
    }

    public void btnAddMaterialClick(ActionEvent actionEvent) throws Exception {
        try {
            int id = Integer.parseInt(txtMaterialId.getText());
            String name = txtMaterialName.getText();
            String author = txtMaterialAuthor.getText();
            String description = txtMaterialDescription.getText());
            int numberOfPages = Integer.parseInt(txtMaterialNumberOfPages.getText());


            serviceMaterial.addMaterial(id, name, author, description, numberOfPages);

            this.observableList.clear();
            this.observableList.addAll(this.serviceMaterial.getAll());
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }
}
