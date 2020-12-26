package com.controller;

import com.model.AlertBox;
import com.model.ConfirmBox;
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
        //Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to exit?", ButtonType.YES, ButtonType.NO);
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirm Dialog");
//        alert.setContentText("Do you want to exit");
//        alert.showAndWait();

//        viewFactory.showAlertBox("Hello", "Do you want to quit?");
//        viewFactory.showAlertBox("AlertBox.fxml", "Hello", "Hello");
//        if (alert.getResult() == ButtonType.YES) {
//            Stage stageToClose = (Stage) messageLabel.getScene().getWindow();
//            stageToClose.close();
//        }

        boolean isTrue = ConfirmBox.show("Confirm Box", "Are you sure to quit? Do you know that you are about to close the window?");
        System.out.println(isTrue);
        if (isTrue) {
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
        user.login();
        boolean isLogin = user.getIsLogin();
        if (isLogin) {
            messageLabel.getStyleClass().add("success");
            messageLabel.setText("Login Successful");
            boolean isAdmin = user.getIsAdmin();
            if (isAdmin) {
                viewFactory.showAdminMainWindow();
            } else {
                viewFactory.showUserMainWindow();
            }
            Stage loginWindow = (Stage) messageLabel.getScene().getWindow();
            loginWindow.close();
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
