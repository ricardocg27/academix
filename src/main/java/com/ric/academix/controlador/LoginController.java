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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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

}
