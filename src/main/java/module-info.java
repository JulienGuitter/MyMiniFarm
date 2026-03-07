module fr.eseo.e3a.myminifarm {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires javafx.media;


    opens fr.eseo.e3a.myminifarm to javafx.fxml;
//    opens fr.eseo.e3a.myminifarm.controller to org.junit.jupiter.api;
    exports fr.eseo.e3a.myminifarm;
}