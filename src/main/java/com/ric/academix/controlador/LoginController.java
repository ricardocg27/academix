/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.App;
import com.ric.academix.dao.AdministradorDao;
import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.AdministradorDaoImpl;
import com.ric.academix.daoImpl.AlumnoDaoImpl;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Profesor;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private FontAwesomeIconView icnUser;
    @FXML
    private FontAwesomeIconView icnKey;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane pneExit;
    @FXML
    private Hyperlink hypRegistrar;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    private ValidationSupport validador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validador = new ValidationSupport();
        validador.setErrorDecorationEnabled(true);
    }

    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void registrarPosicion(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
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
    private void irRegistrar(ActionEvent event) {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterVista.fxml"));
            Parent root = loader.load();

            RegisterControlador controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void login(ActionEvent event) {

        if (camposRellenos()) {
            Object object = validarUsuario();
            if (object != null) {
                iniciarSesion(object);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Usuario no válido");
                alert.setContentText("Por favor, introduzca un usuario existente");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos vacíos");
            alert.setContentText("Por favor, rellene todos los campos");
            alert.showAndWait();
        }

    } // fin botón login

    private boolean camposRellenos() {

        boolean camposRellenos;

        validador.registerValidator(txtUsuario, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtPassword, Validator.createEmptyValidator("Campo requerido"));

        if (validador.isInvalid()) {
            camposRellenos = false;
        } else {
            camposRellenos = true;
        }
        return camposRellenos;
    }

    private Object validarUsuario() {

        boolean encontrado;

        Administrador admin = validarAdministrador();

        if (admin == null) {
            Profesor profesor = validarProfesor();
            if (profesor == null) {
                Alumno alumno = validarAlumno();
                if (alumno == null) {
                    encontrado = false;
                } else {
                    encontrado = true; // alumno encontrado
                    return alumno;
                }

            } else {
                encontrado = true; // profesor encontrado
                return profesor;
            }

        } else {
            encontrado = true; //admin encontrado
            return admin;
        }

        return null;
    }

    private Administrador validarAdministrador() {
        AdministradorDao adminDao = new AdministradorDaoImpl();
        Administrador admin = adminDao.consultarPorEmailYContrasena(txtUsuario.getText(), txtPassword.getText());

        return admin;
    }

    private Profesor validarProfesor() {

        ProfesorDao profesorDao = new ProfesorDaoImpl();
        Profesor profesor = profesorDao.consultarPorEmailYContrasena(txtUsuario.getText(), txtPassword.getText());
        return profesor;
    }

    private Alumno validarAlumno() {

        AlumnoDao alumnoDao = new AlumnoDaoImpl();
        Alumno alumno = alumnoDao.consultarPorEmailYContrasena(txtUsuario.getText(), txtPassword.getText());
        return alumno;
    }

    private void iniciarSesion(Object object) {

        if (object instanceof Administrador) {
            irPanelGeneralAdministrador((Administrador) object);
        } else if (object instanceof Profesor) {
            irPanelGeneralProfesor((Profesor) object);
        } else {
            irPanelGeneralAlumno((Alumno) object);
        }
    }

    private void irPanelGeneralAdministrador(Administrador administrador) {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PanelGeneralAdministradorVista.fxml"));
            Parent root = loader.load();

            PanelGeneralAdministradorController controlador = loader.getController();  
            controlador.setUsuario(administrador);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void irPanelGeneralProfesor(Profesor profesor) {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PanelGeneralProfesorVista.fxml"));
            Parent root = loader.load();

            PanelGeneralProfesorController controlador = loader.getController();
            controlador.setUsuario(profesor);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void irPanelGeneralAlumno(Alumno alumno) {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PanelGeneralAlumnoVista.fxml"));
            Parent root = loader.load();

            PanelGeneralAlumnoController controlador = loader.getController();
            controlador.setUsuario(alumno);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void recuperarContraseña(ActionEvent event) {
        
    }

}
