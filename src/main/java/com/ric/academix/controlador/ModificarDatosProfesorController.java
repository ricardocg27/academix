/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.GrupoDao;
import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class ModificarDatosProfesorController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrimerApellido;
    @FXML
    private TextField txtSegundoApellido;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> cmbGrupos;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    private SplitPane splitaPane;

    private Object object;
    private Profesor profesor, profesorActualizado;
    private Administrador administrador;
    private Alumno alumno;
    private Grupo grupo, esTutor;
    private ValidationSupport validador;
    private String curso, letra;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validador = new ValidationSupport();
        validador.setErrorDecorationEnabled(true);

        GrupoDao grupoDao = new GrupoDaoImpl();
        List<Grupo> grupos = grupoDao.consultarTodos();
        List<String> cursosYLetras = gruposCursoYLetra(grupos);
        ObservableList<String> listadoGruposComboBox = FXCollections.observableArrayList(cursosYLetras);
        cmbGrupos.setItems(listadoGruposComboBox);
    }

    public void initAttributes(Profesor profesor) {
        this.profesor = profesor;
        this.txtNombre.setText(profesor.getNombre());
        this.txtPrimerApellido.setText(profesor.getPrimerApellido());
        this.txtSegundoApellido.setText(profesor.getSegundoApellido());
        this.txtEmail.setText(profesor.getEmail());
    }

    @FXML
    private void exit(MouseEvent event) {
        stage = (Stage) splitPane.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void aceptar(ActionEvent event) {

        if (camposRellenos()) {
            // obtener valores de los campos
            String nombre = txtNombre.getText();
            String primerApellido = txtPrimerApellido.getText();
            String segundoApellido = txtSegundoApellido.getText();
            String email = txtEmail.getText();

            profesorActualizado = new Profesor(profesor.getId(), nombre, primerApellido, segundoApellido, email);
            ProfesorDao profDao = new ProfesorDaoImpl();

            // actualizo los datos del profesor
            if (profDao.actualizar(profesorActualizado)) {

                GrupoDao grupoDao = new GrupoDaoImpl();
                String grupoSeleccionado = cmbGrupos.getValue();

                // actualizo el grupo
                if (grupoSeleccionado != null) {
                    String partes[] = grupoSeleccionado.split(" ");
                    if (partes.length == 3) {
                        curso = partes[0] + " " + partes[1];
                        letra = partes[2];
                        grupo = grupoDao.consultarGrupoPorCursoYLetra(curso, letra);
                    }
                    // Se elimina como tutor
//                    if (grupoSeleccionado.equals("Eliminar tutor grupo")) {
//                        grupoDao.eliminarTutor(profesor.getId());
//                    } else {
//                    }
                    asignarTutorAGrupo(grupo, profesor, grupoDao);

                } // fin grupoSeleccionado != null
            } // FIN ACTUALIZAR GRUPO
        }

        this.profesor = profesorActualizado;
        stage = (Stage) splitPane.getScene().getWindow();
        stage.close();

    } // fin aceptar

    private void asignarTutorAGrupo(Grupo grupo, Profesor profesor, GrupoDao grupoDao) {
        // si se le asigna el mismo grupo de tutoría, no se hace nada
        System.err.println("Grupo --> " + grupo.toString());
        if (grupo.getTutorId() != null) {
            // Si se le asigna otro grupo
            // Primero se consulta si el grupo del que se quiere hacer tutor ya tiene tutor  
            // Sí tiene tutor
            // Se avisa de que este grupo ya tiene tutor
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText(grupo.getTutorId().getNombreCompleto()
                    + " es tutor de " + grupo.getCurso() + " " + grupo.getLetra()
                    + "\n¿Desea eliminar este usuario como tutor de " + grupo.getCurso() + " " + grupo.getLetra() + "? ");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // se elimina del grupo anterior
                // se asigna al nuevo grupo
                grupoDao.eliminarTutor(grupo.getTutorId().getId());
                grupoDao.eliminarTutor(profesor.getId());
                grupoDao.asignarTutor(grupo.getId(), profesor.getId());
            }
        } else {
            // No tiene tutor
            // primero se consulta si el profesor ya es tutor de un grupo
            esTutor = grupoDao.consultarTutorGrupo(profesor.getId());
            if (esTutor == null) {
                //No es tutor
                // se le asigna al grupo ese profesor como tutor
                grupoDao.asignarTutor(grupo.getId(), profesor.getId());
            } else {
                // SÍ es tutor
                // Se avisa que este profesor ya es tutor del grupo 
                // se pregunta si quiere eliminarlo como tutor del grupo anterior
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setHeaderText(null);
                alert2.setTitle("Información");
                alert2.setContentText(profesor.getNombre() + " " + profesor.getPrimerApellido() + " " + profesor.getSegundoApellido()
                        + " es tutor de " + esTutor.getCurso() + " " + esTutor.getLetra()
                        + "\n¿Desea asignarlo como tutor de " + grupo.getCurso() + " " + grupo.getLetra() + "? ");
                Optional<ButtonType> result2 = alert2.showAndWait();

                if (result2.isPresent() && result2.get() == ButtonType.OK) {
                    // se elimina del grupo anterior
                    // se asigna al nuevo grupo
                    grupoDao.eliminarTutor(esTutor.getTutorId().getId());
                    grupoDao.asignarTutor(grupo.getId(), profesor.getId());

                }
            } // fin grupo no tiene tutor
        }// fin cambiar grupo de tutoría

    }

    @FXML
    private void cancelar(ActionEvent event) {
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

    private boolean camposRellenos() {
        boolean camposRellenos = true;
//
//        validador.registerValidator(txtNombre, Validator.createRegexValidator("Campo requerido", ".+", Severity.ERROR));
//        validador.registerValidator(txtPrimerApellido, Validator.createRegexValidator("Campo requerido", ".+", Severity.ERROR));
//        validador.registerValidator(txtSegundoApellido, Validator.createRegexValidator("Campo requerido", ".+", Severity.ERROR));
//        validador.registerValidator(txtEmail, Validator.createRegexValidator("Campo requerido", ".+", Severity.ERROR));
//        validador.registerValidator(cmbGrupos, Validator.createEmptyValidator("Seleccione un elemento"));
//
//        if (validador.isInvalid()) {
//            camposRellenos = false;
//        } else {
//            camposRellenos = true;
//        }

        return camposRellenos;
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

    public Profesor getProfesorActualizado() {
        return profesorActualizado;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

}
