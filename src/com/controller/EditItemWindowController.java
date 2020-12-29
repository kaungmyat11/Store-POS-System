package com.controller;

import com.model.AlertBox;
import com.model.DbConnect;
import com.model.Item;
import com.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
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

    ArrayList<String> supplierNameList;
    String name, brand;
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

        name = nameTextField.getText();
        brand = brandTextField.getText();
        String supplier = supplierComboBox.getPromptText();
        Stage stageToClose = (Stage) nameTextField.getScene().getWindow();

        try {
            stock = Integer.parseInt(stockTextField.getText());
            retailPrice = Integer.parseInt(retailTextField.getText());
            wholesalePrice = Integer.parseInt(wholesaleTextField.getText());

            DbConnect dbConnect = new DbConnect();
            supplierId = dbConnect.getSupplierId(supplier);
            System.out.println(supplier + " id is " + supplierId);

            String query = "Update items SET name = '" + name + "', brand = '" + brand + "', stock = " + stock + ", retail_price = " + retailPrice + ", wholesale_price = " + wholesalePrice + ", supplier_id = " + supplierId + " WHERE code = '" + selectedItem.getCode() + "';";
            boolean isExecuted = dbConnect.executeQuery(query);
            if (isExecuted) {
                AlertBox.show("Success", "Edit Item Success!");
                viewFactory.closeStage(stageToClose);
            } else {
                AlertBox.show("Failed", "Failed editing the item!");
            }

        } catch (Exception e) {
            System.out.println("ERROR in converting text to string. " + e);
            AlertBox.show("Error", "Error in editing item. Please check " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codeTextField.setText(selectedItem.getCode());
        codeTextField.setDisable(true);
        nameTextField.setText(selectedItem.getName());
        brandTextField.setText(selectedItem.getBrand());
        stockTextField.setText(String.valueOf(selectedItem.getStock()));
        retailTextField.setText(String.valueOf(selectedItem.getRetailPrice()));
        wholesaleTextField.setText(String.valueOf(selectedItem.getWholesalePrice()));
        supplierId = selectedItem.getSupplierId();
        System.out.println(supplierId);


        DbConnect dbConnect = new DbConnect();
        supplierNameList = dbConnect.getSupplierList();
        supplierComboBox.getItems().addAll(supplierNameList);
        System.out.println("Display on combobox : "+selectedItem.getSupplierName());
        supplierComboBox.setPromptText(selectedItem.getSupplierName());
    }
}
