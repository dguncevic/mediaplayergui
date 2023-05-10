module com.filip.mediaplayergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.media;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.coreui;
    requires org.kordamp.ikonli.devicons;
    requires org.kordamp.ikonli.dashicons;
    requires org.kordamp.ikonli.fontawesome5;
    requires jaudiotagger;
  

    opens com.filip.mediaplayergui to javafx.fxml;
    exports com.filip.mediaplayergui;
}
