module com.ric.academix {
    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;
    
    requires jakarta.persistence;
    requires transitive javafx.graphics;
    
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires charm.down.core;
    requires charm.down.plugin.display;
   

    opens com.ric.academix to javafx.fxml;
    opens com.ric.academix.controlador to javafx.fxml;
    opens com.ric.academix.modelo to javafx.base;
    
    exports com.ric.academix;
    exports com.ric.academix.controlador;
}
