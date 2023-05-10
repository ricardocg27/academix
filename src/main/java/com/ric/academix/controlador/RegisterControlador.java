/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AdministradorDao;
import com.ric.academix.daoImpl.AdministradorDaoImpl;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.CodigoUsuarios;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class RegisterControlador implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField txtNombre;
    @FXML
    private PasswordField txtContra;
    @FXML
    private Button btnRegister;
    @FXML
    private FontAwesomeIconView icnUser;
    @FXML
    private FontAwesomeIconView icnKey;
    @FXML
    private Pane pneExit;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private TextField txtCodigo;
    
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    private ValidationSupport validador;
    
    private String nombre, apellido, contrasegna, email;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validador = new ValidationSupport();
        validador.setErrorDecorationEnabled(true);

        cmbCategoria.setItems(FXCollections.observableArrayList("Administrador", "Profesor", "Alumno"));
    }

    @FXML
    private void exit(MouseEvent event) {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginVista.fxml"));
            Parent root = loader.load();
            LoginController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(RegisterControlador.class.getName()).log(Level.SEVERE, null, ex);
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
    private void registrar(ActionEvent event) {
        System.out.println("Entro a registrar");
        if (camposRellenos()) {
            System.out.println("Entro al if");
            if (comprobarFormatos()) {
                if (codigoValido()) {
                    registrarUsuario();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Código no válido");
                    alert.setContentText("Por favor, introduzca un código de registro de usuario válido. "
                            + "Para más información, contacte con el administrador de su organización");
                    alert.showAndWait();
                }

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos vacíos");
            alert.setContentText("Por favor, rellene todos los campos");
            alert.showAndWait();
        }
    }

    private boolean camposRellenos() {
        System.out.println("Entro a camposRellenos");
        boolean camposRellenos;

        validador.registerValidator(txtNombre, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtApellido, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtContra, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtEmail, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtCodigo, Validator.createEmptyValidator("Campo requerido"));

        //cmbCategoria.getItems().addAll("Administrador", "Profesor", "Alumno");
        cmbCategoria.getItems();
        validador.registerValidator(cmbCategoria, Validator.createEmptyValidator("Elige una categoría"));

        if (validador.isInvalid()) {
            System.out.println("Entro al validador");
            camposRellenos = false;
        } else {
            System.out.println("Están todos rellenos");
            camposRellenos = true;
        }

        return camposRellenos;
    }

    private boolean comprobarFormatos() {

        boolean formatoCorrecto;

        validador.registerValidator(txtEmail, Validator.createRegexValidator("Introduce un email válido", "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", Severity.ERROR));

        validador.registerValidator(txtContra, Validator.createRegexValidator("La contraseña debe tener al menos:\n\t8 caracteres "
                + "\n\tun símbolo especial\n\tuna mayúscula", "^(?=.*[A-Z])(?=.*[@*#$%^&+=])(?=\\S+$).{8,}$", Severity.ERROR));

        if (validador.isInvalid()) {
            return formatoCorrecto = false;
        } else {
            return formatoCorrecto = true;
        }

    }

    private boolean codigoValido() {

        boolean valido = false;
        CodigoUsuarios codigo = new CodigoUsuarios();
        String categoriaUsuario = cmbCategoria.getValue();

        switch (categoriaUsuario) {
            case "Administrador":
                if (txtCodigo.getText() == codigo.getCodigoAdmin()) {
                    valido = true;
                }
                break;
            case "Profesor":
                if (txtCodigo.getText() == codigo.getCodigoProfesor()) {
                    valido = true;
                }
                break;
            case "Alumno":
                if (txtCodigo.getText() == codigo.getCodigoAlumno()) {
                    valido = true;
                }
                break;
        }
        return valido;

    }

    private void registrarUsuario() {

        String categoriaUsuario = cmbCategoria.getValue();

        switch (categoriaUsuario) {
            case "Administrador":
                Administrador admin = crearAdministrador();
                AdministradorDao dao = new AdministradorDaoImpl();
                dao.insertar(admin);
                break;
            case "Profesor":

                break;
            case "Alumno":

                break;
        }
    }

    private Administrador crearAdministrador() {
        CodigoUsuarios codigo = new CodigoUsuarios();
        Administrador admin = null;

        nombre = txtNombre.getText();
        apellido = txtApellido.getText();
        contrasegna = txtContra.getText();
        email = txtEmail.getText();
        
        admin = new Administrador(nombre, apellido, contrasegna, email, codigo.getCodigoTipoAdmin());
        
        return admin;
    }

}
