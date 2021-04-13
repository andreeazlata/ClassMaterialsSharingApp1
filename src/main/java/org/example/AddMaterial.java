package org.example;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.domain.Material;
import org.example.service.ServiceMaterial;

public class AddMaterial {
    public TableView tbl;
    public TextField txtCakeId;
    public TextField txtCakeName;
    public TextField txtCakeIngredients;
    public TextField txtCakeCalories;
    public TextField txtCakePrice;
    public CheckBox chkSugarFree;

    private ServiceMaterial servicePrajitura;
    private ObservableList<Material> observableList;

    public void setCookieService(ServiceMaterial cookieService) {
        this.servicePrajitura = cookieService;
    }

    public void setObservableList(ObservableList<Material> cookies) {
        this.observableList = cookies;
        this.tblCakes.setItems(this.observableList);
    }

    public void btnAddCakeClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtCakeId.getText());
            String name = txtCakeName.getText();
            String ingredients = txtCakeIngredients.getText();
            int calories = Integer.parseInt(txtCakeCalories.getText());
            float price = Float.parseFloat(txtCakePrice.getText());
            boolean sugarFree = chkSugarFree.isSelected();

            servicePrajitura.addMaterial(idEntity,  name,  author,  description,  numberOfPages);

            this.observableList.clear();
            this.observableList.addAll(this.servicePrajitura.getAll());
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }
}
