/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.modelo.Profesor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class PanelGeneralProfesorController implements Initializable {

    private Profesor profesor;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUsuario(Profesor object){
        this.profesor = object;
    }
    
}
