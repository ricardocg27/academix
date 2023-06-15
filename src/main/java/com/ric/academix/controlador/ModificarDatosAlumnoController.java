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
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.CuadernoProfesor;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
import com.ric.academix.modelo.ProfesorAsignatura;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class ModificarDatosAlumnoController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrimerApellido;
    @FXML
    private TextField txtSegundoApellido;
    @FXML
    private TextField txtCorreo;
    @FXML
    private ComboBox<String> cmbGrupo;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    private SplitPane splitaPane;

    private Alumno alumno, alumnoActualizado;
    private AlumnoDao alumnoDao;
    private Grupo grupo;

    private ValidationSupport validador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validador = new ValidationSupport();
        validador.setErrorDecorationEnabled(true);

        GrupoDao grupoDao = new GrupoDaoImpl();
        List<Grupo> grupos = grupoDao.consultarTodos();
        List<String> cursosYLetras = gruposCursoYLetra(grupos);
        ObservableList<String> listadoGruposComboBox = FXCollections.observableArrayList(cursosYLetras);
        cmbGrupo.setItems(listadoGruposComboBox);
    }

    public void initAttributes(Alumno alumno) {
        this.alumno = alumno;
        this.txtNombre.setText(alumno.getNombre());
        this.txtPrimerApellido.setText(alumno.getPrimerApellido());
        this.txtSegundoApellido.setText(alumno.getSegundoApellido());
        this.txtCorreo.setText(alumno.getEmail());
        this.cmbGrupo.setValue(alumno.getGrupoId().getCurso() + " " + alumno.getGrupoId().getLetra());
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

    private List<String> gruposCursoYLetra(List<Grupo> grupos) {
        List<String> cursos = new ArrayList<>();
        for (Grupo grupo : grupos) {
            String curso = grupo.getCurso();
            String letra = grupo.getLetra();
            cursos.add(curso + " " + letra);
        }
        return cursos;
    }

    @FXML
    private void aceptarCambios(ActionEvent event) {
        alumnoDao = new AlumnoDaoImpl();

        if (camposRellenos()) {
            String nombre = txtNombre.getText();
            String primerApellido = txtPrimerApellido.getText();
            String segundoApellido = txtSegundoApellido.getText();
            String correo = txtCorreo.getText();
            String grupoSeleccionado = cmbGrupo.getValue();
            grupo = obtenerGrupo(grupoSeleccionado);

            alumnoActualizado = new Alumno(alumno.getId(), nombre, primerApellido, segundoApellido, correo, grupo);

            if (alumnoDao.actualizar(alumnoActualizado)) {
                this.alumno = alumnoActualizado;
                stage = (Stage) splitPane.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Se ha producido un error al actualizar el usuario, por favor, contacte con el administrador");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Rellene todos los campos para poder realizar la modificación del usuario");
            alert.showAndWait();
        }

    }

    @FXML
    private void cancelar(ActionEvent event) {
        stage = (Stage) splitPane.getScene().getWindow();
        stage.close();

    }

    private boolean camposRellenos() {
        boolean camposRellenos;

        validador.registerValidator(txtNombre, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtPrimerApellido, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtSegundoApellido, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(txtCorreo, Validator.createEmptyValidator("Campo requerido"));
        validador.registerValidator(cmbGrupo, Validator.createEmptyValidator("Seleccione un elemento"));

        if (validador.isInvalid()) {
            camposRellenos = false;
        } else {
            camposRellenos = true;
        }

        return camposRellenos;
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

    public Alumno getAlumno() {
        return alumno;
    }

   
}
