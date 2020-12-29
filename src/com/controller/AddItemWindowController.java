package com.controller;

import com.model.DbConnect;
import com.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

    @FXML
    private Label codeCheckLabel;

    ArrayList<String> supplierNameList;

    String code, name, brand;
    int stock, retailPrice, wholesalePrice, supplierId;

    @FXML
    void cancelButtonAction(ActionEvent event) {
        System.out.println("Cancel Button Clicked");
        Stage stageToClose = (Stage) wholesaleTextField.getScene().getWindow();
        stageToClose.close();
    }

    @FXML
    void checkButtonAction(ActionEvent event) {
        System.out.println("Check Button Clicked");

        boolean isTaken = viewFactory.checkCode(codeTextField.getText());
        if (isTaken) {
            codeCheckLabel.setText("Not Available!");
        } else {
            codeCheckLabel.setText("Available");
        }
    }

    @FXML
    void addButtonAction(ActionEvent event) {
        System.out.println("Add Button Clicked");

        code = codeTextField.getText();
        name = nameTextField.getText();
        brand = brandTextField.getText();
        String supplier = supplierComboBox.getValue();

        AnchorPane.setLeftAnchor(messageLabel, 0.0);
        AnchorPane.setRightAnchor(messageLabel, 0.0);
        messageLabel.setAlignment(Pos.CENTER);

        try {
            stock = Integer.parseInt(stockTextField.getText());
            retailPrice = Integer.parseInt(retailTextField.getText());
            wholesalePrice = Integer.parseInt(wholesaleTextField.getText());


            DbConnect dbConnect = new DbConnect();
            int supplierId = dbConnect.getSupplierId(supplier);
            boolean isTaken = dbConnect.checkIfTaken("SELECT * FROM items WHERE code = '" + code + "';");
            if (isTaken) {
                messageLabel.setText("Id is already taken");
            } else {
                String query = "INSERT INTO items VALUES ('" + code + "', '" + name + "', '" + brand + "'," + stock + ", " + retailPrice + ", " + wholesalePrice + ", " + supplierId + ");";
                boolean isExecuted = dbConnect.executeQuery(query);
                if (isExecuted) {
                    messageLabel.setText("Success!");
                    resetTextField();
                } else {
                    messageLabel.setText("Failed adding the item");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR in converting text to string. " + e);
            messageLabel.setText("Invalid Input : " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DbConnect dbConnect = new DbConnect();
        supplierNameList = dbConnect.getSupplierList();
        supplierComboBox.getItems().addAll(supplierNameList);
    }

    void resetTextField() {
        codeTextField.setText("");
        nameTextField.setText("");
        brandTextField.setText("");
        stockTextField.setText("");
        retailTextField.setText("");
        wholesaleTextField.setText("");
        supplierComboBox.setPromptText("");
    }
}
