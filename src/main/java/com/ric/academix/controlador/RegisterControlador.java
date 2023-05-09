/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.App;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private PasswordField txtEmail;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

}
