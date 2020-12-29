package com.controller;

import com.model.DbConnect;
import com.model.Item;
import com.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SellWindowController extends BaseController implements Initializable {
    public SellWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    private TableColumn codeColumn;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableView tableView;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField totalTextField;

    @FXML
    private Button addButton;

    @FXML
    private CheckBox retailCheckBox;

    private Item item;
    private int price, total;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeTextField.requestFocus();
        nameTextField.setPromptText("Item Name");
        quantityTextField.setPromptText("Quantity");
        priceTextField.setPromptText("Unit Price");
        totalTextField.setPromptText("Total Amount");

        nameTextField.setDisable(true);
        quantityTextField.setDisable(true);
        priceTextField.setDisable(true);
        totalTextField.setDisable(true);
        codeTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    System.out.println("Enter Key Pressed!!");
                    DbConnect dbConnect = new DbConnect();
                    item = dbConnect.getItem(codeTextField.getText());
                    nameTextField.setText(item.getName());
                    if (retailCheckBox.isSelected()) {
                        price = item.getRetailPrice();
                    } else {
                        price = item.getWholesalePrice();
                    }
                    priceTextField.setText(String.valueOf(price));
                    quantityTextField.setDisable(false);
                    quantityTextField.requestFocus();
                }
            }
        });


        quantityTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    int quantity = 0;
                    try {
                        quantity = Integer.parseInt(quantityTextField.getText());
                    } catch (Exception e) {
                        System.out.println("ERROR in changing text to int");
                        return;
                    }
                    total = quantity * price;
                    totalTextField.setText(String.valueOf(total));
                    addButton.requestFocus();
                }
            }
        });
    }

    @FXML
    void addButtonAction(ActionEvent event) {
        System.out.println("Add Button Clicked");
    }
}
