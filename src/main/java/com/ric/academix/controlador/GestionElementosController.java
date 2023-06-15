/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.GrupoDao;
import com.ric.academix.dao.ProfesorAsignaturaDao;
import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.daoImpl.ProfesorAsignaturaDaoImpl;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
import com.ric.academix.modelo.ProfesorAsignatura;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import jakarta.persistence.NoResultException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class GestionElementosController implements Initializable {

    @FXML
    private TableView<Profesor> tblProfesores;
    @FXML
    private TextField txtNombre;
    @FXML
    private RadioButton rdbTutor;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSegundoApellido;
    @FXML
    private TextField txtPrimerApellido;
    @FXML
    private TableColumn<Profesor, String> clmNombreProfesor;
    @FXML
    private TableColumn<Profesor, String> clmApellido1Profesor;
    @FXML
    private TableColumn<Profesor, String> clmApellido2Profesor;
    @FXML
    private TableColumn<Profesor, String> clmCorreoProfesor;
    @FXML
    private TableColumn<Profesor, String> clmProfesorTutor;
    @FXML
    private TableColumn<Profesor, String> clmGrupoProfesor;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private FontAwesomeIconView icnExit;

    private int tipoElemento, ruta;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    private ObservableList<Profesor> datosTablaProfesores;
    private ObservableList<Profesor> filtroProfesores;

    private Administrador administrador;
    private List<Profesor> profesores;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField lblPanelLateral;

    public GestionElementosController(int tipoElemento) {
        this.tipoElemento = tipoElemento;
    }

    public GestionElementosController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            iniciarTablas();
            filtroProfesores = FXCollections.observableArrayList();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
    }

    public void iniciarTablas() throws NoResultException {

        // tabla grupos
        ProfesorDao profDao = new ProfesorDaoImpl();
        profesores = profDao.consultarTodos();

        clmNombreProfesor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmApellido1Profesor.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        clmApellido2Profesor.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        clmCorreoProfesor.setCellValueFactory(new PropertyValueFactory<>("email"));
        // es tutor
        clmProfesorTutor.setCellValueFactory(data -> {
            Profesor profesor = (Profesor) data.getValue();
            int idProfesor = profesor.getId();

            Profesor tutor = profDao.consultarTutor(idProfesor);

            if (tutor == null) {
                return new SimpleStringProperty("No");
            } else {
                return new SimpleStringProperty("Sí");
            }
        });
        // grupo en el que es tutor
        clmGrupoProfesor.setCellValueFactory(data -> {

            Profesor profesor = (Profesor) data.getValue();
            int idProfesor = profesor.getId();
            Profesor tutor = profDao.consultarTutor(idProfesor);

            if (tutor == null) {
                return new SimpleStringProperty("-");
            } else {
                GrupoDao grupoDao = new GrupoDaoImpl();
                Grupo grupo = grupoDao.consultarTutorGrupo(idProfesor);
                return new SimpleStringProperty(grupo.getCurso() + " " + grupo.getLetra());
            }

        });

        datosTablaProfesores = FXCollections.observableArrayList(profesores);
        tblProfesores.setItems(datosTablaProfesores);
        tblProfesores.setVisible(true);
    }

    public void setElemento(int tipoElemento) {
        this.tipoElemento = tipoElemento;
    }

    @FXML
    private void filtrarPorNombre(KeyEvent event) {
        String filtroNombre = txtNombre.getText();
        if (filtroNombre.isBlank()) {
            tblProfesores.setItems(datosTablaProfesores);
        } else {
            this.filtroProfesores.clear();
            for (Profesor p : profesores) {
                if (p.getNombre().toLowerCase().contains(filtroNombre)) {
                    this.filtroProfesores.add(p);
                }
            }
            this.tblProfesores.setItems(filtroProfesores);
        }
    }

    @FXML
    private void filtrarPorPrimerApellido(KeyEvent event) {
        String filtroApellido = txtPrimerApellido.getText();
        if (filtroApellido.isBlank()) {
            tblProfesores.setItems(datosTablaProfesores);
        } else {
            this.filtroProfesores.clear();
            for (Profesor p : profesores) {
                if (p.getPrimerApellido().toLowerCase().contains(filtroApellido)) {
                    this.filtroProfesores.add(p);
                }
            }
            this.tblProfesores.setItems(filtroProfesores);
        }
    }

    @FXML
    private void filtrarPorSegundoApellido(KeyEvent event) {
        String filtroSegundoApellido = txtSegundoApellido.getText();
        if (filtroSegundoApellido.isBlank()) {
            tblProfesores.setItems(datosTablaProfesores);
        } else {
            this.filtroProfesores.clear();
            for (Profesor p : profesores) {
                if (p.getSegundoApellido().toLowerCase().contains(filtroSegundoApellido)) {
                    this.filtroProfesores.add(p);
                }
            }
            this.tblProfesores.setItems(filtroProfesores);
        }
    }

    @FXML
    private void filtrarPorCorreo(KeyEvent event) {

        String filtroCorreo = txtEmail.getText();
        if (filtroCorreo.isBlank()) {
            tblProfesores.setItems(datosTablaProfesores);
        } else {
            this.filtroProfesores.clear();
            for (Profesor p : profesores) {
                if (p.getEmail().toLowerCase().contains(filtroCorreo)) {
                    this.filtroProfesores.add(p);
                }
            }
            this.tblProfesores.setItems(filtroProfesores);
        }
    }

    @FXML
    private void filtrarPorTutor(ActionEvent event) {
        if (!rdbTutor.isSelected()) {
            tblProfesores.setItems(datosTablaProfesores);
        } else {
            ProfesorDao profDao = new ProfesorDaoImpl();
            this.filtroProfesores.clear();
            List<Profesor> tutores = profDao.consultarProfesoresSonTutores();
            System.err.println("tutores --> " + tutores.toString());
            for (Profesor tutor : tutores) {
                this.filtroProfesores.add(tutor);
            }
            this.tblProfesores.setItems(filtroProfesores);
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
    private void seleccionar(MouseEvent event) {

        Profesor profesor = this.tblProfesores.getSelectionModel().getSelectedItem();

        if (profesor != null) {

        }

    }

    @FXML
    private void modificar(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Atención");
        alert.setHeaderText("Beta versión: modificar no implementado");
        alert.setContentText("Esta función será añadida en posteriores versiones");

        //alert.setContentText(e.printStackTrace());
        alert.showAndWait();
//        try {
//
//            // controlar que tipo de objeto recibe
//            // selecciono el profesor
//            Profesor profesor = this.tblProfesores.getSelectionModel().getSelectedItem();
//            // si es null
//            if (profesor == null) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setHeaderText(null);
//                alert.setTitle("Error");
//                alert.setContentText("Selecciona un ítem para poder modificar los datos");
//                //alert.setContentText(e.printStackTrace());
//                alert.showAndWait();
//            } else {
//                // si no es null
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarDatosProfesorVista.fxml"));
//                Parent root = loader.load();
//                ModificarDatosProfesorController controlador = loader.getController();
//                controlador.initAttributes(profesor);
//                controlador.setAdministrador(administrador);
//
//                Scene scene = new Scene(root);
//                Stage stage = new Stage();
//                stage.initStyle(StageStyle.DECORATED.UNDECORATED);
//                stage.initModality(Modality.APPLICATION_MODAL);
//                stage.setScene(scene);
//                stage.showAndWait();
//                iniciarTablas();
//                //Profesor prof = controlador.getProfesorActualizado();
//                //this.datosTablaProfesores.remove(profesor);
//                //this.datosTablaProfesores.add(prof);
//                this.tblProfesores.refresh();
//
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(GestionElementosController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void eliminar(ActionEvent event) {

        Profesor profesor = this.tblProfesores.getSelectionModel().getSelectedItem();

        if (profesor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Selecciona un ítem para poder eliminarlo");
            //alert.setContentText(e.printStackTrace());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("¿Desea eliminar a " + profesor.getNombre() + " " + profesor.getPrimerApellido() + " " + profesor.getSegundoApellido()
                    + "\nSerá eliminada toda la información asociada a él (tutorías y asignaturas que imparte)");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                datosTablaProfesores.remove(profesor);
                eliminarProfesoryDatos(profesor);
                tblProfesores.refresh();
            }
        }
    } // fin eliminar

    private void eliminarProfesoryDatos(Profesor profesor) {

        GrupoDao grupoDao = new GrupoDaoImpl();
        int eliminado = grupoDao.eliminarTutor(profesor.getId());

        ProfesorAsignaturaDao profAsigDao = new ProfesorAsignaturaDaoImpl();
        profAsigDao.eliminarProfesoryAsignaturasAsociadas(profesor.getId());
        ProfesorDao profDao = new ProfesorDaoImpl();
        profDao.eliminar(profesor.getId());

    }

    @FXML
    private void exit(MouseEvent event) {
        try {

            if (this.ruta == 1) {
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

            } else {
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAdministrador(Administrador usuario) {
        this.administrador = usuario;
        lblTitulo.setText("ADMINISTRADOR: " + this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
        lblPanelLateral.setText(this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

}
