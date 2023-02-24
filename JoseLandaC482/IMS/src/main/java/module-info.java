module ims.ims {
    requires javafx.controls;
    requires javafx.fxml;

    opens imsclasses to javafx.fxml;
    opens ims.ims to javafx.fxml;
    exports ims.ims;
    exports imsclasses;
}