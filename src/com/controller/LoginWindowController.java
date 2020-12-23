package com.controller;

import com.model.User;
import com.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController{
    public LoginWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label messageLabel;

    @FXML
    void quitButtonAction(ActionEvent event) {
        System.out.println("Quit Button Clicked");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Stage stageToClose = (Stage) messageLabel.getScene().getWindow();
            stageToClose.close();
        }
    }

    @FXML
    void loginButtonAction(ActionEvent event) {
        System.out.println("Login Button Clicked");
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        AnchorPane.setLeftAnchor(messageLabel, 0.0);
        AnchorPane.setRightAnchor(messageLabel, 0.0);
        messageLabel.setAlignment(Pos.CENTER);

        User user = new User(username, password);
        boolean isLogin = user.login();
        if (isLogin) {
            messageLabel.getStyleClass().add("success");
            messageLabel.setText("Login Successful");
        } else {
            messageLabel.getStyleClass().add("error");
            messageLabel.setText("Invalid Username or Password!");
        }
    }

    @FXML
    void resetButtonAction(ActionEvent event) {
        System.out.println("Reset Button Clicked");
        usernameTextField.setText("");
        passwordField.setText("");
    }
}
