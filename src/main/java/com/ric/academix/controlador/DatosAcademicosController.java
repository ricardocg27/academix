/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.dao.AsignaturaDao;
import com.ric.academix.dao.GrupoDao;
import com.ric.academix.dao.NotaExamenDao;
import com.ric.academix.dao.NotaTareaDao;
import com.ric.academix.daoImpl.AsignaturaDaoImpl;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.daoImpl.NotasAlumnosDaoImpl;
import com.ric.academix.dto.NotaAlumnoDTO;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.ric.academix.dao.NotasAlumnosDao;
import com.ric.academix.daoImpl.NotaExamenDaoImpl;
import com.ric.academix.daoImpl.NotaTareaDaoImpl;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Examen;
import com.ric.academix.modelo.NotaExamen;
import com.ric.academix.modelo.NotaTareaEvaluable;
import com.ric.academix.modelo.TareaEvaluable;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class DatosAcademicosController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private ComboBox<String> cmbGrupo;
    @FXML
    private Button btnBuscar;
    @FXML
    private ComboBox<String> cmbAsignatura;

    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblDescripcion1;
    @FXML
    private TableView<NotaTareaEvaluable> tblTareas;
    @FXML
    private TableView<NotaExamen> tblExamenes;
    @FXML
    private TableColumn<NotaTareaEvaluable, String> clmAlumnoTarea;
    @FXML
    private TableColumn<NotaTareaEvaluable, String> clmActividadTarea;
    @FXML
    private TableColumn<NotaTareaEvaluable, String> clmNotaTarea;
    @FXML
    private TableColumn<NotaExamen, String> clmAlumnoExamen;
    @FXML
    private TableColumn<NotaExamen, String> clmExamen;
    @FXML
    private TableColumn<NotaExamen, String> clmNotaExamen;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    private int ruta;

    private Alumno alumno;
    private AlumnoDao alumnoDao;
    private Asignatura asignatura;
    private Grupo grupo;
    private NotasAlumnosDao notasDao;
    private NotaExamen notaExamen;
    private NotaExamenDao notaExamenDao;
    private NotaTareaEvaluable notaTareaEvaluable;
    private NotaTareaDao notaTareaDao;

    private Profesor profesor;

    private List<Alumno> listaAlumnos;
    private List<NotaAlumnoDTO> listadoNotas;
    private List<NotaExamen> listadoNotasExamenes;
    private List<NotaTareaEvaluable> listadoNotasTareas;

    private ObservableList<NotaExamen> datosNotasExamen;
    private ObservableList<NotaTareaEvaluable> datosNotasTareas;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField lblPanelLateral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (tblTareas.getItems().isEmpty()) {
            Label lblMensaje = new Label("Selecciona un grupo y asignatura para mostrar los datos");
            lblMensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            tblTareas.setPlaceholder(lblMensaje);
        }
        if (tblExamenes.getItems().isEmpty()) {
            Label lblMensaje = new Label("Selecciona un grupo y asignatura para mostrar los datos");
            lblMensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            tblExamenes.setPlaceholder(lblMensaje);
        }
        iniciarCombos();
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
        lblTitulo.setText("PROFESOR: " + this.profesor.getNombre() + " " + this.profesor.getPrimerApellido() + " " + this.profesor.getSegundoApellido());
        lblPanelLateral.setText(this.profesor.getNombre() + " " + this.profesor.getPrimerApellido() + " " + this.profesor.getSegundoApellido());
    }

    @FXML
    private void moverVentana(MouseEvent event) {
        if (stage == null) {
            stage = (Stage) scrollPane.getScene().getWindow();
        }
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private void RegistrarPosicion(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void exit(MouseEvent event) {
        try {

            if (this.ruta == 1) {
                stage = (Stage) scrollPane.getScene().getWindow();
                stage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PanelGeneralProfesorVista.fxml"));
                Parent root = loader.load();

                PanelGeneralProfesorController controlador = loader.getController();
                controlador.setProfesor(profesor);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.DECORATED.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            } else {
                stage = (Stage) scrollPane.getScene().getWindow();
                stage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PanelAdministracionProfesorVista.fxml"));
                Parent root = loader.load();

                PanelAdministracionProfesorController controlador = loader.getController();
                controlador.setProfesor(profesor);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.DECORATED.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void buscar(ActionEvent event) {

        String grupoSeleccionado = cmbGrupo.getValue();
        String asignaturaSeleccionada = cmbAsignatura.getValue();

        if (grupoSeleccionado == null || asignaturaSeleccionada == null) {
            mensajeAlertaCamposIncompletos();
        } else {
            grupo = obtenerGrupo(cmbGrupo.getValue());
            AsignaturaDao asignaturaDao = new AsignaturaDaoImpl();
            asignatura = asignaturaDao.consultarPorNombre(cmbAsignatura.getValue());
            if (grupo != null && asignatura != null) {
                notasDao = new NotasAlumnosDaoImpl();
                //listadoNotas = notasDao.consultarNotasPorGrupoYAsignatura(grupo, asignatura);
                iniciarTablaTareas(grupo, asignatura);
                iniciarTablaExamenes(grupo, asignatura);

            }
        }

    }

    @FXML
    private void eliminar(ActionEvent event) {

        if (tblExamenes.getSelectionModel().isEmpty() && tblTareas.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setTitle("ítem no seleccionado");
            alert.setContentText("Por favor, seleccione un ítem para poder eliminarlo");
            alert.showAndWait();
        }

        if (!tblExamenes.getSelectionModel().isEmpty()) {
            notaExamen = tblExamenes.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("¿Desea eliminar la nota " + notaExamen.getPuntuacion() + " asignada a  " + notaExamen.getAlumnoId().getNombreYApellidos()
                    + " en el examen " + notaExamen.getExamenId().getNombre() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                eliminarNotaExamen(notaExamen);
                tblExamenes.refresh();
            }
        }

        if (!tblTareas.getSelectionModel().isEmpty()) {
            notaTareaEvaluable = tblTareas.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("¿Desea eliminar la nota " + notaTareaEvaluable.getPuntuacion() + " asignada a  " + notaTareaEvaluable.getAlumnoId().getNombreYApellidos()
                    + " en la tarea " + notaTareaEvaluable.getTareaEvaluableId().getNombre() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                eliminarNotaTarea(notaTareaEvaluable);
                tblTareas.refresh();
            }
        }
    }

    @FXML
    private void modificar(ActionEvent event) {

        try {
            if (tblExamenes.getSelectionModel().isEmpty() && tblTareas.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setTitle("ítem no seleccionado");
                alert.setContentText("Por favor, seleccione un ítem para poder modificarlo");
                alert.showAndWait();
            }
            if (!tblExamenes.getSelectionModel().isEmpty()) {
                notaExamen = tblExamenes.getSelectionModel().getSelectedItem();
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Introducir nota ");
                dialog.setHeaderText("Introduzca una nota para: " + notaExamen.getAlumnoId().getNombreYApellidos() + " - " + notaExamen.getExamenId().getNombre());
                if (notaExamen.getPuntuacion() == null) {
                    dialog.setContentText("Nota: ");
                } else {
                    dialog.setContentText("Nota: " + notaExamen.getPuntuacion());

                }

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String nota = result.get();
                    double notaNumerica = Double.parseDouble(nota);
                    notaExamen.setPuntuacion(notaNumerica);
                    notaExamenDao = new NotaExamenDaoImpl();
                    notaExamenDao.insertarNota(notaExamen);
                    tblExamenes.refresh();

                }
            }
            if (!tblTareas.getSelectionModel().isEmpty()) {
                notaTareaEvaluable = tblTareas.getSelectionModel().getSelectedItem();
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Introducir nota ");
                dialog.setHeaderText("Introduzca una nota para: " + notaTareaEvaluable.getAlumnoId().getNombreYApellidos() + " - " + notaTareaEvaluable.getTareaEvaluableId().getNombre());
                if (notaTareaEvaluable.getPuntuacion() == null) {
                    dialog.setContentText("Nota: ");
                } else {
                    dialog.setContentText("Nota: " + notaExamen.getPuntuacion());

                }

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String nota = result.get();
                    double notaNumerica = Double.parseDouble(nota);
                    notaTareaEvaluable.setPuntuacion(notaNumerica);
                    notaTareaDao = new NotaTareaDaoImpl();
                    notaTareaDao.insertarNota(notaTareaEvaluable);
                    tblTareas.refresh();

                }

            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setTitle("Formato no válido");
            alert.setContentText("Por favor, la nota debe ser un formato numérico");
            alert.showAndWait();
        }

    }

    public void initAttributes(Profesor profesor) {
        this.profesor = profesor;

    }

    private void iniciarCombos() {

        // cmbGrupo
        GrupoDao grupoDao = new GrupoDaoImpl();
        List<Grupo> grupos = grupoDao.consultarTodos();
        List<String> cursosYLetras = gruposCursoYLetra(grupos);
        ObservableList<String> listadoGruposComboBox = FXCollections.observableArrayList(cursosYLetras);
        cmbGrupo.setItems(listadoGruposComboBox);

        // cmbAsignatura
        AsignaturaDao asigDao = new AsignaturaDaoImpl();
        List<Asignatura> asignaturas = asigDao.consultarTodos();
        List<String> nombreAsignaturas = nombreAsignaturas(asignaturas);
        ObservableList<String> listadoAsignaturasComboBox = FXCollections.observableArrayList(nombreAsignaturas);
        cmbAsignatura.setItems(listadoAsignaturasComboBox);

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

    private List<String> nombreAsignaturas(List<Asignatura> asignaturas) {
        List<String> nombresAsignaturas = new ArrayList<>();
        String nombreAsignatura;
        for (Asignatura asignatura : asignaturas) {
            nombreAsignatura = asignatura.getNombre();
            nombresAsignaturas.add(nombreAsignatura);
        }

        return nombresAsignaturas;
    }

    private void mensajeAlertaCamposIncompletos() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setTitle("Campos no seleccionado");
        alert.setContentText("Por favor, seleccione un grupo y una asignatura para buscar las calificaciones asociadas");
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

    private void iniciarTablaTareas(Grupo grupo, Asignatura asignatura) {

        notaTareaDao = new NotaTareaDaoImpl();
        listadoNotasTareas = notaTareaDao.consultarPorGrupoYAsignatura(grupo, asignatura);
        datosNotasTareas = FXCollections.observableArrayList(listadoNotasTareas);
        tblTareas.setItems(datosNotasTareas);

        clmAlumnoTarea.setCellValueFactory((param) -> {

            NotaTareaEvaluable notaTareaEvaluable = (NotaTareaEvaluable) param.getValue();
            Alumno alumno = notaTareaEvaluable.getAlumnoId();
            if (alumno == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(alumno.getNombreYApellidos());
            }

        });

        clmActividadTarea.setCellValueFactory((param) -> {
            NotaTareaEvaluable notaTareaEvaluable = (NotaTareaEvaluable) param.getValue();
            TareaEvaluable tareaEvaluable = notaTareaEvaluable.getTareaEvaluableId();

            if (tareaEvaluable == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(tareaEvaluable.getNombre());
            }
        });

        clmNotaTarea.setCellValueFactory((param) -> {

            NotaTareaEvaluable notaTareaEvaluable = (NotaTareaEvaluable) param.getValue();
            if (notaTareaEvaluable.getPuntuacion() == null) {
                return new SimpleStringProperty("Añadir");
            } else {
                return new SimpleStringProperty(String.valueOf(notaTareaEvaluable.getPuntuacion()));
            }
        });
    }

    private void iniciarTablaExamenes(Grupo grupo, Asignatura asignatura) {

        notaExamenDao = new NotaExamenDaoImpl();
        listadoNotasExamenes = notaExamenDao.consultarPorGrupoYAsignatura(grupo, asignatura);
        datosNotasExamen = FXCollections.observableArrayList(listadoNotasExamenes);
        tblExamenes.setItems(datosNotasExamen);

        clmAlumnoExamen.setCellValueFactory((param) -> {
            NotaExamen notaExamen = (NotaExamen) param.getValue();
            Alumno alumno = notaExamen.getAlumnoId();
            if (alumno == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(alumno.getNombreYApellidos());
            }
        });

        clmExamen.setCellValueFactory((param) -> {
            NotaExamen notaExamen = (NotaExamen) param.getValue();
            Examen examen = notaExamen.getExamenId();

            if (examen == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(examen.getNombre());
            }
        });

        clmNotaExamen.setCellValueFactory((param) -> {
            NotaExamen notaExamen = (NotaExamen) param.getValue();

            if (notaExamen.getPuntuacion() == null) {
                return new SimpleStringProperty("Añadir");
            } else {
                return new SimpleStringProperty(String.valueOf(notaExamen.getPuntuacion()));
            }
        });
    }

    private void eliminarNotaExamen(NotaExamen notaExamen) {

        notaExamenDao = new NotaExamenDaoImpl();
        notaExamenDao.eliminarNota(notaExamen);

    }

    private void eliminarNotaTarea(NotaTareaEvaluable notaTareaEvaluable) {

        notaTareaDao = new NotaTareaDaoImpl();
        notaTareaDao.eliminarNota(notaTareaEvaluable);

    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

}
