package com.controller;

import com.model.DbConnect;
import com.model.Item;
import com.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
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

    @FXML
    private Label codeCheckLabel;

    ArrayList<String> supplierNameList;
    String code, name, brand;
    int stock, retailPrice, wholesalePrice, supplierId;

    public EditItemWindowController(ViewFactory viewFactory, String fxmlName, Item selectedItem) {
        super(viewFactory, fxmlName);
        this.selectedItem = selectedItem;
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        System.out.println("Cancel Button Clicked");

        Stage stageToClose = (Stage) nameTextField.getScene().getWindow();
        viewFactory.closeStage(stageToClose);
    }

    @FXML
    void editButtonAction() {
        System.out.println("Edit Button Clicked");


        code = codeTextField.getText();
        name = nameTextField.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codeTextField.setText(selectedItem.getCode());
        nameTextField.setText(selectedItem.getName());
        brandTextField.setText(selectedItem.getBrand());
        stockTextField.setText(String.valueOf(selectedItem.getStock()));
        retailTextField.setText(String.valueOf(selectedItem.getRetailPrice()));
        wholesaleTextField.setText(String.valueOf(selectedItem.getWholesalePrice()));


        DbConnect dbConnect = new DbConnect();
        supplierNameList = dbConnect.getSupplierList();
        supplierComboBox.getItems().addAll(supplierNameList);
        System.out.println("Display on combobox : "+selectedItem.getSupplierName());
        supplierComboBox.setPromptText(selectedItem.getSupplierName());
    }

    @FXML
    void checkButtonAction(ActionEvent event) {
        DbConnect dbConnect = new DbConnect();
        code = codeTextField.getText();
        String query = "SELECT * FROM items WHERE code = '" + code + "';";
        boolean isTaken = dbConnect.checkIfTaken(query);
        if (isTaken) {
            codeCheckLabel.setText("Not Available");
        } else {
            codeCheckLabel.setText("Available");
        }
    }
}
