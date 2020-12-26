module Store.POS.System {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires javafx.graphics;

    opens com.controller;
    opens com.view;
    opens com;

    exports com.model;
    opens com.model to javafx.base;
}