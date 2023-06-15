/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.ProfesorAsignaturaDao;
import com.ric.academix.daoImpl.ProfesorAsignaturaDaoImpl;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Profesor;
import com.ric.academix.modelo.ProfesorAsignatura;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class GestionAsignaturasController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private FontAwesomeIconView btnExit;
    @FXML
    private TableView<ProfesorAsignatura> tblAsignaturas;
    @FXML
    private TableColumn<ProfesorAsignatura, String> clmAsignatura;
    @FXML
    private TableColumn<ProfesorAsignatura, String> clmProfesor;
    @FXML
    private TableColumn<ProfesorAsignatura, String> clmAgno;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Pane modificarAsignatura;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    private Profesor profesor;
    private Administrador administrador;
    private Asignatura asignatura;
    private ProfesorAsignatura profesorAsignatura;
    private ProfesorAsignaturaDao profesorAsignaturaDao;

    private List<ProfesorAsignatura> profesoresAsignaturas;
    private ObservableList<ProfesorAsignatura> datosTabla;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField lblPanelLateral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarTabla();
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
    private void exit(MouseEvent event) {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PanelAdministracionAdministradorVista.fxml"));
            Parent root = loader.load();

            PanelAdministracionAdministradorController controlador = loader.getController();
            controlador.setAdministrador(administrador);

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
    private void eliminarAsignatura(ActionEvent event) {

        profesorAsignaturaDao = new ProfesorAsignaturaDaoImpl();

        profesorAsignatura = this.tblAsignaturas.getSelectionModel().getSelectedItem();

        if (profesorAsignatura == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Selecciona un ítem para poder eliminarlo");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("¿Desea eliminar a " + profesorAsignatura.getProfesor().getNombreCompleto() + " como profesor de la asignatura "
                    + profesorAsignatura.getAsignatura().getNombre() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                datosTabla.remove(profesorAsignatura);
                profesorAsignaturaDao.eliminarProfesoryAsignaturasAsociadas(profesorAsignatura.getProfesor().getId());
                tblAsignaturas.refresh();
            }
        }

    }

    @FXML
    private void modificarAsignatura(ActionEvent event) {

        try {
            profesorAsignatura = this.tblAsignaturas.getSelectionModel().getSelectedItem();

            if (profesorAsignatura == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Selecciona un ítem para poder modificar los datos");
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarDatosAsignaturaVista.fxml"));
                Parent root = loader.load();
                ModificarDatosAsignaturaController controlador = loader.getController();
                controlador.initAttributes(profesorAsignatura);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.DECORATED.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                iniciarTabla();
                //Profesor prof = controlador.getProfesorActualizado();
                //this.datosTablaProfesores.remove(profesor);
                //this.datosTablaProfesores.add(prof);
                this.tblAsignaturas.refresh();
            }

        } catch (IOException ex) {
            Logger.getLogger(GestionAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void iniciarTabla() {
        profesorAsignaturaDao = new ProfesorAsignaturaDaoImpl();

        profesoresAsignaturas = profesorAsignaturaDao.consultarTodos();

        clmAsignatura.setCellValueFactory((data) -> {

            profesorAsignatura = data.getValue();
            asignatura = profesorAsignatura.getAsignatura();

            if (asignatura == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(asignatura.getNombre());
            }
        });

        clmProfesor.setCellValueFactory((data) -> {

            profesorAsignatura = data.getValue();
            profesor = profesorAsignatura.getProfesor();

            if (profesor == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(profesor.getNombreCompleto());
            }
        });
        clmAgno.setCellValueFactory((data) -> {
            profesorAsignatura = data.getValue();
            String agno = profesorAsignatura.getAgnoAcademico();

            if (agno == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(agno.toString());
            }
        });

        datosTabla = FXCollections.observableArrayList(profesoresAsignaturas);
        tblAsignaturas.setItems(datosTabla);
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
        lblTitulo.setText("ADMINISTRADOR: " + this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
        lblPanelLateral.setText(this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
    }

}
