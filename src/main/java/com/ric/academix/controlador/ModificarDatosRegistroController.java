/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.dao.CuadernoProfesorDao;
import com.ric.academix.daoImpl.AlumnoDaoImpl;
import com.ric.academix.daoImpl.CuadernoProfesorDaoImpl;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.CuadernoProfesor;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class ModificarDatosRegistroController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private FontAwesomeIconView exit;

    @FXML
    private DatePicker dtpFechaRegistro;

    @FXML
    private ComboBox<String> cmbTareasCasa;
    @FXML
    private ComboBox<String> cmbAtencion;
    @FXML
    private ComboBox<String> cmbParticipacion;
    @FXML
    private ComboBox<String> cmbTareasClase;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAceptar;
    @FXML
    private TextField txtAlumno;

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    private Alumno alumno;
    private AlumnoDao alumnoDao;
    private CuadernoProfesor cuadernoProfesor;
    private CuadernoProfesorDao cuadernoProfesorDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        iniciarCombos();

        Tooltip tooltipAlumno = new Tooltip("Campo no modificable.");
        tooltipAlumno.setShowDelay(Duration.ZERO);
        txtAlumno.setTooltip(tooltipAlumno);

        Tooltip toolDtpFechaRegistro = new Tooltip("Campo no modificable.");
        toolDtpFechaRegistro.setShowDelay(Duration.ZERO);
        dtpFechaRegistro.setTooltip(toolDtpFechaRegistro);

    }

    public void initAttributes(CuadernoProfesor cuadernoProfesor) {
        this.cuadernoProfesor = cuadernoProfesor;
        this.txtAlumno.setText(cuadernoProfesor.getAlumnoId().getNombreYApellidos());
        this.cmbTareasCasa.setValue(cuadernoProfesor.getTareasCasa());
        this.cmbParticipacion.setValue(cuadernoProfesor.getParticipacion());
        this.cmbAtencion.setValue(cuadernoProfesor.getAtencion());
        this.cmbTareasClase.setValue(cuadernoProfesor.getTareasClase());

        String fechaInsercionString = cuadernoProfesor.getFechaInsercion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaInsercionLocalDate = LocalDate.parse(fechaInsercionString, formatter);

        this.dtpFechaRegistro.setValue(fechaInsercionLocalDate);
    }

    @FXML
    private void moverVentana(MouseEvent event) {
        if (stage == null) {
            stage = (Stage) splitPane.getScene().getWindow();
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
        stage = (Stage) splitPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        stage = (Stage) splitPane.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void aceptar(ActionEvent event) {

        cuadernoProfesorDao = new CuadernoProfesorDaoImpl();

        String tareasCasa = cmbTareasCasa.getValue();
        String participacion = cmbParticipacion.getValue();
        String atencion = cmbAtencion.getValue();
        String tareasClase = cmbTareasClase.getValue();
        String alumnoString = txtAlumno.getText();

        alumno = obtenerAlumnoDelTxt(alumnoString);

        cuadernoProfesor.setAlumnoId(alumno);
        cuadernoProfesor.setTareasCasa(tareasCasa);
        cuadernoProfesor.setParticipacion(participacion);
        cuadernoProfesor.setAtencion(atencion);
        cuadernoProfesor.setTareasClase(tareasClase);

        if (cuadernoProfesorDao.actualizar(cuadernoProfesor)) {
            stage = (Stage) splitPane.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Se ha producido un error al actualizar el registro, por favor, contacte con el administrador");
            alert.showAndWait();
        }

    }

    private void iniciarCombos() {
        // cmbTareasCasa
        ObservableList<String> tareasCasa = FXCollections.observableArrayList("Realizada", "No Realizada");
        cmbTareasCasa.setItems(tareasCasa);

        ObservableList<String> opciones = FXCollections.observableArrayList("Sí", "No");
        // cmbParticipacion
        cmbParticipacion.setItems(opciones);
        // cmbAtención
        cmbAtencion.setItems(opciones);
        // cmbTareasClase
        cmbTareasClase.setItems(tareasCasa);

    }

    private Alumno obtenerAlumnoDelTxt(String alumnoString) {

        alumno = null;
        String nombre = null, primerApellido = null, segundoApellido = null;
        String partes[] = alumnoString.split(" ");
        List<String> nombres = new ArrayList<>();
        if (partes.length == 3) {
            nombre = partes[0];
            primerApellido = partes[1];
            segundoApellido = partes[2];
        } else if (partes.length == 4) {
            StringBuilder nombreCompuesto = new StringBuilder();
            for (int i = 0; i < partes.length - 2; i++) {
                nombreCompuesto.append(partes[i]).append(" ");
            }
            nombre = nombreCompuesto.toString().trim();
            primerApellido = partes[2];
            segundoApellido = partes[3];
        }
        nombres.add(nombre);
        nombres.add(primerApellido);
        nombres.add(segundoApellido);

        alumno = consultarPorNombreYApellidos(nombres);

        return alumno;

    }

    private Alumno consultarPorNombreYApellidos(List<String> nombres) {
        alumnoDao = new AlumnoDaoImpl();
        alumno = alumnoDao.consultarPorNombreYApellidos(nombres);
        return alumno;
    }

}
