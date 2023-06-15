/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AsignaturaDao;
import com.ric.academix.dao.ExamenDao;
import com.ric.academix.dao.GrupoDao;
import com.ric.academix.daoImpl.AsignaturaDaoImpl;
import com.ric.academix.daoImpl.ExamenDaoImpl;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.daoImpl.TareaEvaluableDaoImpl;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Examen;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
import com.ric.academix.modelo.TareaEvaluable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class ModificarDatosExamenController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPorcentaje;
    @FXML
    private ComboBox<String> cmbAsignatura;
    @FXML
    private ComboBox<String> cmbGrupo;
    @FXML
    private FontAwesomeIconView icnExit;

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    private Asignatura asignatura;
    private AsignaturaDao asignaturaDao;
    private Grupo grupo;
    private Profesor profesor;
    private Examen examen, examenActualizado;
    private ExamenDao examenDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initAttributes(Profesor profesor, Examen examen) {
        this.profesor = profesor;
        this.examen = examen;

    }

    @FXML
    private void aceptar(ActionEvent event) {

        try {
            examenDao = new ExamenDaoImpl();

            if (txtNombre.getText().isBlank() || txtPorcentaje.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setTitle("Campos incompletos");
                alert.setContentText("Por favor, indique toda la información necesaria para modificar la actividad");
                alert.showAndWait();
            } else {
                String porcentajeString = txtPorcentaje.getText();
                int porcentaje = Integer.parseInt(porcentajeString);
                if (porcentaje < 0 || porcentaje > 100) {
                    mensajeAlertaFormato();
                } else {
                    String nombre = txtNombre.getText();
                    grupo = obtenerGrupo(cmbGrupo.getValue());
                    asignaturaDao = new AsignaturaDaoImpl();
                    asignatura = asignaturaDao.consultarPorNombre(cmbAsignatura.getValue());

                    examenActualizado = new Examen();
                    examenActualizado.setId(examen.getId());
                    examenActualizado.setNombre(nombre);
                    examenActualizado.setPorcentaje(porcentaje);
                    examenActualizado.setAsignaturaId(asignatura);
                    examenActualizado.setGrupoId(grupo);

                    if (examenDao.actualizar(examenActualizado)) {
                        this.examen = examenActualizado;
                        stage = (Stage) splitPane.getScene().getWindow();
                        stage.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("Se ha producido un error al actualizar la actividad, por favor, contacte con el administrador");
                        alert.showAndWait();
                    }

                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setTitle("Formato numérico no válido");
            alert.setContentText("Por favor, el porcentaje debe ser un número entero y comprendido entre 0 y 100");
            alert.showAndWait();
        }

    }

    @FXML
    private void cancelar(ActionEvent event) {
        stage = (Stage) splitPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void exit(MouseEvent event) {
        stage = (Stage) splitPane.getScene().getWindow();
        stage.close();
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

    private void mensajeAlertaFormato() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setTitle("Formato numérico no válido");
        alert.setContentText("Por favor, el porcentaje debe ser un número entero comprendido entre 0 y 100");
        alert.showAndWait();
    }

    private Grupo obtenerGrupo(String grupoSeleccionado) {
        GrupoDao grupoDao = new GrupoDaoImpl();
        if (grupoSeleccionado != null) {
            String partes[] = grupoSeleccionado.split(" ");
            if (partes.length == 3) {
                String curso = partes[0] + " " + partes[1];
                String letra = partes[2];
                grupo = grupoDao.consultarGrupoPorCursoYLetra(curso, letra);
            }
        }
        return grupo;
    }

}
