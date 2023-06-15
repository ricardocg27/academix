/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AsignaturaDao;
import com.ric.academix.dao.ProfesorAsignaturaDao;
import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.AsignaturaDaoImpl;
import com.ric.academix.daoImpl.ProfesorAsignaturaDaoImpl;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Profesor;
import com.ric.academix.modelo.ProfesorAsignatura;
import com.ric.academix.modelo.ProfesorAsignaturaPK;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class ModificarDatosAsignaturaController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private ComboBox<String> cmbAsignatura;
    @FXML
    private ComboBox<String> cmbProfesor;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    private SplitPane splitaPane;

    private Profesor profesor;
    private Asignatura asignatura;
    private ProfesorAsignatura profesorAsignaturaAntiguo, profesorAsignaturaNuevo;
    private ProfesorAsignaturaDao profesorAsignaturaDao;
    @FXML
    private FontAwesomeIconView btnExit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Tooltip tooltip = new Tooltip("Sólo se debe modificar el profesor de la asignatura");
        tooltip.setShowDelay(Duration.millis(50));
        Tooltip.install(cmbAsignatura, tooltip);

    }

    public void initAttributes(ProfesorAsignatura profesorAsignatura) {

        this.profesorAsignaturaAntiguo = profesorAsignatura;
        //Iniciar cmbAsignatura
        cmbAsignatura.setValue(profesorAsignatura.getAsignatura().getNombre());

        // Iniciar cmbProfesor
        ProfesorDao profDao = new ProfesorDaoImpl();
        List<Profesor> profesores = profDao.consultarTodos();
        List<String> nombreProfesoresCompleto = nombreCompleto(profesores);
        ObservableList<String> listadoProfesoresComboBox = FXCollections.observableArrayList(nombreProfesoresCompleto);
        cmbProfesor.setItems(listadoProfesoresComboBox);
        cmbProfesor.setValue(profesorAsignatura.getProfesor().getNombreCompleto());

        // Iniciar cmb Año académico
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
    private void aceptarCambios(ActionEvent event) {

        // Obtengo el profesor
        String nombreProfesor = cmbProfesor.getSelectionModel().getSelectedItem();
        profesor = obtenerProfesor(nombreProfesor);

        // obtener asignatura       
        String nombreAsignatura = cmbAsignatura.getSelectionModel().getSelectedItem();
        AsignaturaDao asignaturaDao = new AsignaturaDaoImpl();
        asignatura = asignaturaDao.consultarPorNombre(nombreAsignatura);

        // obtener año
        int agno = LocalDate.now().getYear();
        String agnoString = String.valueOf(agno);
       
        // Almaceno los nuevos datos a modificar
        profesorAsignaturaNuevo = new ProfesorAsignatura();
        profesorAsignaturaNuevo.setAsignatura(asignatura);
        profesorAsignaturaNuevo.setProfesor(profesor);
        profesorAsignaturaNuevo.setAgnoAcademico(agnoString);
        
        profesorAsignaturaDao = new ProfesorAsignaturaDaoImpl();
        
        // los actualizo
        if (profesorAsignaturaDao.actualizar(profesorAsignaturaNuevo, profesorAsignaturaAntiguo)) {
            stage = (Stage) splitPane.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Se ha producido un error de actualización, por favor, contacte con el administrador");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        stage = (Stage) splitPane.getScene().getWindow();
        stage.close();
    }

    private List<String> nombreAsignaturas(List<Asignatura> asignaturas) {
        List<String> nombresAsignaturas = new ArrayList<>();
        String nombreAsignatura;
        for (Asignatura asignatura : asignaturas) {
            nombreAsignatura = asignatura.getNombre();
            nombresAsignaturas.add(nombreAsignatura);
        }

        return nombresAsignaturas;
    }

    private List<String> nombreCompleto(List<Profesor> profesores) {
        List<String> nombres = new ArrayList<>();
        String nombreCompleto;
        for (Profesor profesor : profesores) {
            nombreCompleto = profesor.getNombre() + " " + profesor.getPrimerApellido() + " " + profesor.getSegundoApellido();
            nombres.add(nombreCompleto);
        }
        return nombres;
    }

    private Profesor obtenerProfesor(String nombreProfesor) {

        ProfesorDao profesorDao = new ProfesorDaoImpl();
        List<String> nombres = obtenerNombreyApellidos(nombreProfesor);
        int id = profesorDao.consultarIdPorNombreYApellidos(nombres);
        profesor = profesorDao.consultar(id);
        return profesor;
    }

    private List<String> obtenerNombreyApellidos(String nombreProfesor) {
        List<String> nombres = new ArrayList<>();
        String[] partes = nombreProfesor.split(" ");
        String nombre = null, primerApellido = null, segundoApellido = null;

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
        return nombres;
    }

}
