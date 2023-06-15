/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AdministradorDao;
import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.AdministradorDaoImpl;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Profesor;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

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
    private TextField txtCiudad;
    @FXML
    private TextField txtEmail;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btnGuardar;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    private Administrador admin, adminContrasena;
    private Profesor prof, profesorContrasena;
    private Alumno alumn;

    private AdministradorDao administradorDao;
    private AlumnoDao alumnoDao;
    private ProfesorDao profesorDao;

    private ValidationSupport validador;
    @FXML
    private Label lblPerfil;
    @FXML
    private Label lblContra;
    @FXML
    private Pane pneContra;
    private PasswordField txtPass;
    private PasswordField txtConfirmPass;
    @FXML
    private Button btnGuardarContra;
    @FXML
    private Pane pneAjustesPerfil;
    @FXML
    private PasswordField txtOldPass;
    @FXML
    private PasswordField txtNewPass;
    @FXML
    private Label lblInicio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pneAjustesPerfil.setDisable(false);
        pneAjustesPerfil.setVisible(true);
        pneContra.setDisable(true);
        pneContra.setVisible(false);

        btnGuardar.setDisable(false);
        btnGuardar.setVisible(true);

        btnGuardarContra.setDisable(true);
        btnGuardarContra.setVisible(false);
        validador = new ValidationSupport();
        validador.setErrorDecorationEnabled(true);

    }

    public void setUsuario(Object object) {
        if (object instanceof Administrador) {
            this.admin = (Administrador) object;
            txtNombre.setText(admin.getNombre());
            txtApellido1.setText(admin.getPrimerApellido());
            txtApellido2.setText(admin.getSegundoApellido());
            txtEmail.setText(admin.getEmail());
            if (admin.getFechaNacimiento() != null) {
                txtFechaNacimiento.setText(admin.getFechaNacimiento());
            }
            if (admin.getTelefono() != null) {
                txtNumero.setText(admin.getTelefono());
            }
            if (admin.getDireccion() != null) {
                txtCiudad.setText(admin.getDireccion());
            }

        } else if (object instanceof Profesor) {
            this.prof = (Profesor) object;
            txtNombre.setText(prof.getNombre());
            txtApellido1.setText(prof.getPrimerApellido());
            txtApellido2.setText(prof.getSegundoApellido());
            txtEmail.setText(prof.getEmail());
            if (prof.getFechaNacimiento() != null) {
                txtFechaNacimiento.setText(prof.getFechaNacimiento());
            }
            if (prof.getTelefono() != null) {
                txtNumero.setText(prof.getTelefono());
            }
            if (prof.getDireccion() != null) {
                txtCiudad.setText(prof.getDireccion());
            }

        } else {
            this.alumn = (Alumno) object;
            txtNombre.setText(alumn.getNombre());
            txtApellido1.setText(alumn.getPrimerApellido());
            txtApellido2.setText(alumn.getSegundoApellido());
            txtEmail.setText(alumn.getEmail());
        }
    }

    @FXML
    private void exit(MouseEvent event) {
        if (this.admin != null) {
            irPanelGeneralAdmin();
        } else if (this.prof != null) {
            irPanelGeneralProfesor();
        } else {

        }

    }

    private void irPanelGeneralAdmin() {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PanelGeneralAdministradorVista.fxml"));
            Parent root = loader.load();

            PanelGeneralAdministradorController controlador = loader.getController();
            controlador.setUsuario(admin);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void irPanelGeneralProfesor() {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PanelGeneralProfesorVista.fxml"));
            Parent root = loader.load();

            PanelGeneralProfesorController controlador = loader.getController();
            controlador.setUsuario(prof);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void moverVentana(MouseEvent event) {
        if (stage == null) {
            stage = (Stage) borderPane.getScene().getWindow();
        }
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private void registrarPosicion(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void guardarCambios(ActionEvent event) {

        if (camposRellenos()) {
            if (this.admin != null) {
                System.err.println("Admin --> " + admin.toString());
                this.admin.setNombre(txtNombre.getText());
                this.admin.setPrimerApellido(txtApellido1.getText());
                this.admin.setSegundoApellido(txtApellido2.getText());
                this.admin.setFechaNacimiento(txtFechaNacimiento.getText());
                this.admin.setTelefono(txtNumero.getText());
                this.admin.setEmail(txtEmail.getText());
                this.admin.setDireccion(txtCiudad.getText());
                administradorDao = new AdministradorDaoImpl();
                if (administradorDao.actualizar(admin)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Éxito");
                    alert.setContentText("Usuario actualizado con éxito");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Error de actualización de usuario");
                    alert.showAndWait();
                }

            } else if (this.prof != null) {
                System.err.println("Profesor --> " + prof.toString());
                this.prof.setNombre(txtNombre.getText());
                this.prof.setPrimerApellido(txtApellido1.getText());
                this.prof.setSegundoApellido(txtApellido2.getText());
                this.prof.setFechaNacimiento(txtFechaNacimiento.getText());
                this.prof.setTelefono(txtNumero.getText());
                this.prof.setEmail(txtEmail.getText());
                this.prof.setDireccion(txtCiudad.getText());
                profesorDao = new ProfesorDaoImpl();
                if (profesorDao.actualizarDatosUsuario(prof)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Éxito");
                    alert.setContentText("Usuario actualizado con éxito");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Error de actualización de usuario");
                    alert.showAndWait();
                }

            } else if (this.alumn != null) {

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Por favor, rellene todos los datos para actualizar la información del usuario");
            alert.showAndWait();
        }

    } // fin guardarCambios

    private boolean camposRellenos() {

        boolean camposRellenos;

        validador.registerValidator(txtNombre, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtApellido1, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtApellido2, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtFechaNacimiento, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtNumero, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtEmail, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtCiudad, Validator.createEmptyValidator("Campo requerido"));

        validador.registerValidator(txtEmail, Validator.createRegexValidator("Introduce un email válido", "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", Severity.ERROR));

        validador.registerValidator(txtFechaNacimiento, Validator.createRegexValidator("Introduce una fecha con el formato dd-mm-yyyy",
                "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-((19|20)\\d\\d)$", Severity.ERROR));

        if (validador.isInvalid()) {
            camposRellenos = false;
        } else {
            camposRellenos = true;
        }
        return camposRellenos;
    }

    @FXML
    private void vistaPerfil(MouseEvent event) {

        pneAjustesPerfil.setDisable(false);
        pneAjustesPerfil.setVisible(true);
        pneContra.setDisable(true);
        pneContra.setVisible(false);

        btnGuardar.setDisable(false);
        btnGuardar.setVisible(true);

        btnGuardarContra.setDisable(true);
        btnGuardarContra.setVisible(false);
    }

    @FXML
    private void vistaContra(MouseEvent event) {
        pneAjustesPerfil.setDisable(true);
        pneAjustesPerfil.setVisible(false);
        pneContra.setDisable(false);
        pneContra.setVisible(true);

        btnGuardar.setDisable(true);
        btnGuardar.setVisible(false);

        btnGuardarContra.setDisable(false);
        btnGuardarContra.setVisible(true);

    }

    @FXML
    private void guardarCambiosContra(ActionEvent event) {

        if (comprobarFormato()) {
            if (contrasenaAntiguaValida()) {
                if (this.admin != null) {
                    administradorDao = new AdministradorDaoImpl();
                    if (administradorDao.actualizarContrasena(admin, txtNewPass)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Contraseña actualizada");
                        alert.setContentText("Se ha modificado la contraseña del usuario");
                        alert.showAndWait();
                    }
                }
                if (this.prof != null) {
                    profesorDao = new ProfesorDaoImpl();
                    if (profesorDao.actualizarContrasena(prof, txtNewPass)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Contraseña actualizada");
                        alert.setContentText("Se ha modificado la contraseña del usuario");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Contraseña no idéntica");
                alert.setContentText("La contraseña antigua introducida no coincide con la contraseña del usuario");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Contraseña no válida");
            alert.setContentText("Por favor, introduzca una contraseña con un formato válido");
            alert.showAndWait();
        }
    }

    private boolean comprobarFormato() {

        boolean formatoCorrecto;

        validador.registerValidator(txtNewPass, Validator.createRegexValidator("La contraseña debe tener al menos:\n\t8 caracteres "
                + "\n\tun símbolo especial\n\tuna mayúscula", "^(?=.*[A-Z])(?=.*[@*#$%^&+=])(?=\\S+$).{8,}$", Severity.ERROR));

        if (validador.isInvalid()) {
            return formatoCorrecto = false;
        } else {
            return formatoCorrecto = true;
        }

    }

    private boolean contrasenaAntiguaValida() {
        boolean valida = false;

        if (this.admin != null) {
            administradorDao = new AdministradorDaoImpl();
            adminContrasena = administradorDao.consultarContrasena(admin, txtOldPass);
            if (adminContrasena != null) {
                valida = true;
            }
        }
        if (this.prof != null) {
            profesorDao = new ProfesorDaoImpl();
            profesorContrasena = profesorDao.consultarPorContrasena(prof, txtOldPass);
            if (profesorContrasena != null) {
                valida = true;
            }
        }
        return valida;
    }

    @FXML
    private void irInicio(MouseEvent event) {
        if (this.admin != null) {
            irPanelGeneralAdmin();
        } else if (this.prof != null) {
            irPanelGeneralProfesor();
        } else {

        }
    }

}
