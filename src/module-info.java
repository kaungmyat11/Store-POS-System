module Store.POS.System {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;

    opens com.controller;
    opens com.view;
    opens com;
}