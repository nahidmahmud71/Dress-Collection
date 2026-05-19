module com.shop.dresscollection {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.shop.dresscollection to javafx.fxml;
    exports com.shop.dresscollection;
}