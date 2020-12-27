package com.controller;

import com.model.DbConnect;
import com.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddItemWindowController extends BaseController implements Initializable {
    public AddItemWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField retailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField stockTextField;

    @FXML
    private TextField brandTextField;

    @FXML
    private TextField wholesaleTextField;

    @FXML
    private ComboBox<String> supplierComboBox;

    @FXML
    private Label messageLabel;

    ArrayList<String> supplierNameList;

    @FXML
    void cancelButtonAction(ActionEvent event) {
        System.out.println("Cancel Button Clicked");
        Stage stageToClose = (Stage) wholesaleTextField.getScene().getWindow();
        stageToClose.close();
    }

    @FXML
    void addButtonAction(ActionEvent event) {
        System.out.println("Add Button Clicked");

        String code = codeTextField.getText();
        String name = nameTextField.getText();
        String brand = brandTextField.getText();
        int stock, retailPrice, wholesalePrice;
        String supplier = supplierComboBox.getValue();

        try {
            stock = Integer.parseInt(stockTextField.getText());
            retailPrice = Integer.parseInt(retailTextField.getText());
            wholesalePrice = Integer.parseInt(wholesaleTextField.getText());


            DbConnect dbConnect = new DbConnect();
            int supplierId = dbConnect.getSupplierId(supplier);

            String query = "INSERT INTO items VALUES ('" + code + "', '" + name + "', '" + brand + "'," + stock + ", " + retailPrice + ", " + wholesalePrice + ", " + supplierId + ");" ;
            dbConnect.executeQuery(query);
            messageLabel.setText("Success!");

        } catch (Exception e) {
            System.out.println("ERROR in converting text to string." + e);
            AnchorPane.setLeftAnchor(messageLabel, 0.0);
            AnchorPane.setRightAnchor(messageLabel, 0.0);
            messageLabel.setAlignment(Pos.CENTER);
            messageLabel.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DbConnect dbConnect = new DbConnect();
        supplierNameList = dbConnect.getSupplierList();
        supplierComboBox.getItems().addAll(supplierNameList);
    }

    public boolean isClose() {
        return true;
    }
}
