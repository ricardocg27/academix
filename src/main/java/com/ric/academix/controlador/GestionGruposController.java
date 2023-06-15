/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.dao.GrupoDao;
import com.ric.academix.daoImpl.AlumnoDaoImpl;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class GestionGruposController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private TableView<Grupo> tblGrupo;
    @FXML
    private TableColumn<Grupo, String> clmCurso;
    @FXML
    private TableColumn<Grupo, String> clmLetra;
    @FXML
    private TableColumn<Grupo, String> clmTutor;
    @FXML
    private TableColumn<Grupo, Long> clmNumeroAlumnos;
    @FXML
    private Button btnEliminar;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    private ObservableList<Grupo> datosTablaGrupos;

    private Administrador administrador;
    private Profesor profesor;
    private Grupo grupo;
    private GrupoDao grupoDao;
    private List<Grupo> grupos;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField lblPanelLateral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarTabla();

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
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modificarGrupo(ActionEvent event) {

        try {
            grupo = this.tblGrupo.getSelectionModel().getSelectedItem();

            if (grupo == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Selecciona un grupo para poder modificar los datos");
                //alert.setContentText(e.printStackTrace());
                alert.showAndWait();
            } else {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarDatosGrupoVista.fxml"));
                Parent root = loader.load();
                ModificarDatosGrupoController controlador = loader.getController();
                controlador.initAttributes(grupo);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.DECORATED.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                iniciarTabla();
                this.tblGrupo.refresh();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarGrupo(ActionEvent event) {

        grupo = this.tblGrupo.getSelectionModel().getSelectedItem();

        if (grupo == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Selecciona un grupo para poder eliminarlo");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("¿Desea eliminar el grupo " + grupo.dameGrupo()
                    + "\nAquellos alumnos asociados a este grupo quedarán sin un grupo asignado");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                datosTablaGrupos.remove(grupo);
                eliminarGrupoYDatos(grupo);
                tblGrupo.refresh();
            }
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

    private void iniciarTabla() {

        grupoDao = new GrupoDaoImpl();
        grupos = grupoDao.consultarTodos();

        datosTablaGrupos = FXCollections.observableArrayList(grupos);
        tblGrupo.setItems(datosTablaGrupos);

        clmCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        clmLetra.setCellValueFactory(new PropertyValueFactory<>("letra"));
        clmTutor.setCellValueFactory(data -> {
            grupo = (Grupo) data.getValue();
            profesor = grupo.getTutorId();

            if (profesor == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(profesor.getNombreCompleto());
            }
        });

        clmNumeroAlumnos.setCellValueFactory(data -> {

            grupo = (Grupo) data.getValue();
            Long numeroAlumnos = obtenerAlumnosPorGrupo(grupo);
            if (numeroAlumnos < 0L) {
                numeroAlumnos = 0L;
            }

            return new SimpleLongProperty(numeroAlumnos).asObject();

        });

    }

    private void eliminarGrupoYDatos(Grupo grupo) {

        AlumnoDao alumnoDao = new AlumnoDaoImpl();

        if (alumnoDao.eliminarGrupo(grupo) > 0) {
            grupoDao = new GrupoDaoImpl();
            grupoDao.eliminar(grupo.getId());
        }

    }

    private Long obtenerAlumnosPorGrupo(Grupo grupo) {

        Long numeroAlumnos = grupoDao.consultarAlumnosPorGrupo(grupo);

        return numeroAlumnos;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
        lblTitulo.setText("ADMINISTRADOR: " + this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
        lblPanelLateral.setText(this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
    }

}
