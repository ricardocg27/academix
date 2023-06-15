/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.modelo.Administrador;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class PanelGeneralAdministradorController implements Initializable {

    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane pnePerfilUsuario;

    @FXML
    private Pane pnePanelAdministracion;
    @FXML
    private Pane pnePanelGestionProfesores;
    @FXML
    private Pane pneCalendario;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    private Administrador usuario;
    @FXML
    private Pane pneGestionAlumnos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip tooltip = new Tooltip("Beta version: not implemented yet");
        tooltip.setShowDelay(Duration.millis(50));
        Tooltip.install(pneCalendario, tooltip);

    }

    public void setUsuario(Administrador administrador) {

        this.usuario = administrador;

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
    private void irPerfilUsuario(MouseEvent event) {

        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjustesPerfilVista.fxml"));
            Parent root = loader.load();

            AjustesPerfilController controlador = loader.getController();
            controlador.setUsuario(usuario);

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
    private void irPoliticaPrivacidad(MouseEvent event) {

        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PoliticaPrivacidadVista.fxml"));
            Parent root = loader.load();

            PoliticaPrivacidadController controlador = loader.getController();
            controlador.setAdministrador(usuario);

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
    private void irPanelAdministracion(MouseEvent event) {

        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PanelAdministracionAdministradorVista.fxml"));
            Parent root = loader.load();

            PanelAdministracionAdministradorController controlador = loader.getController();
            controlador.setAdministrador(usuario);

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
    private void irGestionProfesores(MouseEvent event) {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GestionElementosVista.fxml"));
            Parent root = loader.load();

            GestionElementosController controlador = loader.getController();
            controlador.setAdministrador(usuario);
            controlador.setRuta(1);

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
    private void irGestionAlumnos(MouseEvent event) {

        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GestionAlumnosVista.fxml"));
            Parent root = loader.load();

            GestionAlumnosController controlador = loader.getController();
            controlador.setAdministrador(usuario);
            controlador.setRuta(1);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
