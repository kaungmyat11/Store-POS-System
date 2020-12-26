package com.controller;

import com.model.Item;
import com.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditItemWindowController extends BaseController implements Initializable {
    Item selectedItem;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField brandTextField;

    @FXML
    private TextField stockTextField;

    @FXML
    private TextField retailTextField;

    @FXML
    private TextField wholesaleTextField;

    @FXML
    private ComboBox<String> supplierComboBox;

    public EditItemWindowController(ViewFactory viewFactory, String fxmlName, Item selectedItem) {
        super(viewFactory, fxmlName);
        this.selectedItem = selectedItem;
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        System.out.println("Cancel Button Clicked");

        Stage stageToClose = (Stage) nameTextField.getScene().getWindow();
        stageToClose.close();
    }

    @FXML
    void editButtonAction() {
        System.out.println("Edit Button Clicked");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codeTextField.setText(selectedItem.getCode());
        nameTextField.setText(selectedItem.getName());
        brandTextField.setText(selectedItem.getBrand());
        stockTextField.setText(String.valueOf(selectedItem.getStock()));
        retailTextField.setText(String.valueOf(selectedItem.getRetailPrice()));
        wholesaleTextField.setText(String.valueOf(selectedItem.getWholesalePrice()));
    }
}
