/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.modelo.Alumno;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class PanelGeneralAlumnoController implements Initializable {

    private Alumno alumno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Beta Version");
        alert.setHeaderText("Versión en desarrollo");
        alert.setContentText("Por favor, selecciona un alumno para buscar información.");
        alert.showAndWait();

    }

    void setUsuario(Alumno alumno) {
        this.alumno = alumno;
    }

}
