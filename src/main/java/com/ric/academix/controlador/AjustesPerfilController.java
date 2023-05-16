/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Profesor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class AjustesPerfilController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtApellido2;
    @FXML
    private TextField txtFechaNacimiento;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtEmail;

    private Administrador admin;
    private Profesor prof;
    private Alumno alumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUsuario(Object object) {

        if (object instanceof Administrador) {
            this.admin = (Administrador) object;
            txtNombre.setText(admin.getNombre());
            txtApellido1.setText(admin.getPrimerApellido());
            txtApellido2.setText(admin.getSegundoApellido());
            txtEmail.setText(admin.getEmail());
        } else if (object instanceof Profesor) {
            this.prof = (Profesor) object;
            txtNombre.setText(prof.getNombre());
            txtApellido1.setText(prof.getPrimerApellido());
            txtApellido2.setText(prof.getSegundoApellido());
            txtEmail.setText(prof.getEmail());
        } else {
            this.alumn = (Alumno) object;
            txtNombre.setText(alumn.getNombre());
            txtApellido1.setText(alumn.getPrimerApellido());
            txtApellido2.setText(alumn.getSegundoApellido());
            txtEmail.setText(alumn.getEmail());
        }
    }

}
