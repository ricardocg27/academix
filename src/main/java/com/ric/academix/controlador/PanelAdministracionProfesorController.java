package com.ric.academix.controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.dao.AsignaturaDao;
import com.ric.academix.dao.AsignaturaGrupoDao;
import com.ric.academix.dao.ExamenDao;
import com.ric.academix.dao.GrupoDao;
import com.ric.academix.dao.NotaExamenDao;
import com.ric.academix.dao.NotaTareaDao;
import com.ric.academix.dao.TareaEvaluableDao;
import com.ric.academix.daoImpl.AlumnoDaoImpl;
import com.ric.academix.daoImpl.AsignaturaDaoImpl;
import com.ric.academix.daoImpl.ExamenDaoImpl;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.daoImpl.NotaExamenDaoImpl;
import com.ric.academix.daoImpl.NotaTareaDaoImpl;
import com.ric.academix.daoImpl.TareaEvaluableDaoImpl;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Examen;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.NotaTareaEvaluable;
import com.ric.academix.modelo.Profesor;
import com.ric.academix.modelo.TareaEvaluable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class PanelAdministracionProfesorController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private ComboBox<String> cmbCategoriaActividad;
    @FXML
    private TextField txtPorcentaje;
    @FXML
    private TextField txtNombreActividad;
    @FXML
    private ComboBox<String> cmbAsignaturaCrear;
    @FXML
    private TableView<TareaEvaluable> tblActividades;
    @FXML
    private TableColumn<?, ?> clmActividad;
    @FXML
    private TableColumn<TareaEvaluable, String> clmCategoria;
    @FXML
    private TableColumn<?, ?> clmPorcentaje;
    @FXML
    private TableColumn<TareaEvaluable, String> clmGrupo;
    @FXML
    private TableColumn<TareaEvaluable, String> clmAsignatura;
    @FXML
    private ComboBox<String> cmbGrupoBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private ComboBox<String> cmbAsignaturaBuscar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ComboBox<String> cmbGrupoCrear;
    @FXML
    private Button btnAceptar;
    @FXML
    private ComboBox<String> cmbTareaOExamen;
    @FXML
    private TableView<Examen> tblExamenes;
    @FXML
    private TableColumn<Examen, String> clmActividadExamen;
    @FXML
    private TableColumn<Examen, String> clmCategoriaExamen;
    @FXML
    private TableColumn<Examen, String> clmPorcentajeExamen;
    @FXML
    private TableColumn<Examen, String> clmGrupoExamen;
    @FXML
    private TableColumn<Examen, String> clmAsignaturaExamen;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    private Asignatura asignatura;
    private AsignaturaDao asignaturaDao;
    private AsignaturaGrupoDao asignaturaGrupoDao;
    private Examen examen, examenCreado;
    private ExamenDao examenDao;
    private Grupo grupo;
    private GrupoDao grupoDao;
    private Profesor profesor;
    private TareaEvaluable tareaEvaluable, tareaEvaluableCreada;
    private TareaEvaluableDao tareaEvaluableDao;

    private List<TareaEvaluable> listaTareasEvaluables;
    private List<Examen> listaExamenes;
    private ObservableList<String> listaTareasEvaluablesString = FXCollections.observableArrayList();
    private ObservableList<String> listaExamenesString = FXCollections.observableArrayList();
    private ObservableList<TareaEvaluable> datosTablaTareasEvaluables;
    private ObservableList<Examen> datosTablaExamenes;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Button btnGestionDatosAcademicos;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField lblPanelLateral;

    public PanelAdministracionProfesorController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip toolTipPorcentaje = new Tooltip("Porcentaje");
        toolTipPorcentaje.setShowDelay(Duration.ZERO);
        txtPorcentaje.setTooltip(toolTipPorcentaje);

        iniciarCombosYText();
        iniciarTablas();

    }

    @FXML
    private void exit(MouseEvent event) {
        try {
            stage = (Stage) borderPane.getScene().getWindow();
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

        } catch (IOException ex) {
            Logger.getLogger(RegisterControlador.class.getName()).log(Level.SEVERE, null, ex);
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
    private void crearActividad(ActionEvent event) {
        boolean existe = false;
        int tipo = 0;
        try {

            AsignaturaDao asignaturaDao = new AsignaturaDaoImpl();

            String actividad = txtNombreActividad.getText();
            String categoria = cmbCategoriaActividad.getValue();

            if (cmbAsignaturaCrear.getValue() != null) {
                asignatura = asignaturaDao.consultarPorNombre(cmbAsignaturaCrear.getValue());
            } else {
                mensajeAlertaCamposIncompletos();
            }

            if (cmbGrupoCrear.getValue() != null) {
                grupo = obtenerGrupo(cmbGrupoCrear.getValue());
            } else {
                mensajeAlertaCamposIncompletos();
            }

            String porcentajeString = txtPorcentaje.getText();

            if (actividad.isBlank() || porcentajeString.isBlank()) {
                mensajeAlertaCamposIncompletos();
            } else {
                int porcentaje = Integer.parseInt(porcentajeString);
                if (porcentaje < 0 || porcentaje > 100) {
                    mensajeAlertaFormato();
                } else {
                    System.err.println("Llego");
                    examenDao = new ExamenDaoImpl();
                    tareaEvaluableDao = new TareaEvaluableDaoImpl();
                    // comprobar si ha elegido examen o tarea_evaluable 
                    if (categoria.equalsIgnoreCase("Examen")) {
                        // comprobar si existe
                        System.err.println("Es examen");
                        examen = examenDao.consultarPorNombreAsignaturaYGrupo(actividad, asignatura, grupo);
                        if (examen != null) {
                            existe = true;
                        }
                        tipo = 1;
                        System.err.println("Es de tipo --> " + 1);

                    } else if (categoria.equalsIgnoreCase("Tarea evaluable")) {
                        System.err.println("Es tarea evaluable");
                        tareaEvaluable = tareaEvaluableDao.consultarPorNombreAsignaturaYGrupo(actividad, asignatura, grupo);
                        if (tareaEvaluable != null) {
                            existe = true;
                        }
                        tipo = 2;
                        System.err.println("Es de tipo --> " + tipo);
                    }
                    // insertamos
                    if (!existe) {
                        if (tipo == 1) {
                            // insertamos examen
                            System.err.println("Pues si es el tipo 1 ... insertamos");
                            examen = new Examen();
                            examen.setNombre(actividad);
                            examen.setAsignaturaId(asignatura);
                            examen.setGrupoId(grupo);
                            examen.setPorcentaje(porcentaje);
                            List<Examen> numeroExamenes = examenDao.consultarPorAsignaturaYGrupo(asignatura, grupo);
                            if (numeroExamenes.size() > 1) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Beta version");
                                alert.setTitle("Límite de éxamenes excedido");
                                alert.setContentText("La actual versión permite crear un máximo de 2 exámenes por asignatura y grupo");
                                alert.showAndWait();
                            } else {
                                examenDao.insertar(examen);
                                examenCreado = examenDao.consultarPorNombreAsignaturaYGrupo(actividad, asignatura, grupo);
                                AlumnoDao alumnoDao = new AlumnoDaoImpl();
                                List<Alumno> listaAlumnos = alumnoDao.consultarPorGrupo(grupo);
                                NotaExamenDao notaExamenDao = new NotaExamenDaoImpl();
                                notaExamenDao.crearCasillasNotasPorExamenYGrupo(examenCreado, listaAlumnos);

                                iniciarTablaExamenes();
                                tblActividades.refresh();
                            }

                        } else if (tipo == 2) {
                            System.err.println("Pues si el tipo es 2 ... insertamos");
                            // insertamos tareaEvaluable
                            tareaEvaluable = new TareaEvaluable();
                            tareaEvaluable.setNombre(actividad);
                            tareaEvaluable.setPorcentaje(porcentaje);
                            tareaEvaluable.setAsignaturaId(asignatura);
                            tareaEvaluable.setGrupoId(grupo);
                            List<TareaEvaluable> numeroTareas = tareaEvaluableDao.consultarPorAsignaturaYGrupo(asignatura, grupo);
                            if (numeroTareas.size() > 2) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Beta version");
                                alert.setTitle("Límite de tareas evaluables excedido");
                                alert.setContentText("La actual versión permite crear un máximo de 2 tareas evaluables por asignatura y grupo");
                                alert.showAndWait();
                            } else {
                                tareaEvaluableDao.insertar(tareaEvaluable);
                                tareaEvaluableCreada = tareaEvaluableDao.consultarPorNombreAsignaturaYGrupo(actividad, asignatura, grupo);
                                AlumnoDao alumnoDao = new AlumnoDaoImpl();
                                List<Alumno> listaAlumnos = alumnoDao.consultarPorGrupo(grupo);
                                NotaTareaDao notaTareaDao = new NotaTareaDaoImpl();
                                notaTareaDao.crearCasillasNotasPorTareasYGrupo(tareaEvaluableCreada, listaAlumnos);

                                iniciarTablaTareasEvaluables();
                                tblActividades.refresh();
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setTitle("Formato numérico no válido");
            alert.setContentText("Por favor, el porcentaje debe ser un número entero comprendido entre 0 y 100");
            alert.showAndWait();
        }
    }

    @FXML
    private void buscarActividad(ActionEvent event) {

        if (cmbGrupoBuscar.getValue().equalsIgnoreCase("Todos")
                && cmbAsignaturaBuscar.getValue().equalsIgnoreCase("Todas")) {
            if (cmbTareaOExamen.getValue().equalsIgnoreCase("Tareas evaluables")) {
                iniciarTablaTareasEvaluables();
            } else {
                iniciarTablaExamenes();
            }
        } else {
            if (cmbGrupoBuscar.getValue().equalsIgnoreCase("Todos")
                    || cmbAsignaturaBuscar.getValue().equalsIgnoreCase("Todas")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setTitle("Campos incompletos");
                alert.setContentText("Por favor, seleccione un grupo y una asignatura para realizar la búsqueda");
                alert.showAndWait();
            }
            if (!cmbGrupoBuscar.getValue().equalsIgnoreCase("Todos")
                    && !cmbAsignaturaBuscar.getValue().equalsIgnoreCase("Todas")) {

                AsignaturaDao asignaturaDao = new AsignaturaDaoImpl();
                grupo = obtenerGrupo(cmbGrupoBuscar.getValue());
                asignatura = asignaturaDao.consultarPorNombre(cmbAsignaturaBuscar.getValue());
                if (grupo != null && asignatura != null) {
                    if (cmbTareaOExamen.getValue().equalsIgnoreCase("Tareas evaluables")) {
                        tareaEvaluableDao = new TareaEvaluableDaoImpl();
                        listaTareasEvaluables = tareaEvaluableDao.consultarPorAsignaturaYGrupo(asignatura, grupo);
                        datosTablaTareasEvaluables = FXCollections.observableArrayList(listaTareasEvaluables);
                        tblActividades.setItems(datosTablaTareasEvaluables);
                        tblActividades.setVisible(true);
                        tblExamenes.setVisible(false);
                        lblDescripcion.setText("TAREAS");
                    } else {
                        examenDao = new ExamenDaoImpl();
                        listaExamenes = examenDao.consultarPorAsignaturaYGrupo(asignatura, grupo);
                        datosTablaExamenes = FXCollections.observableArrayList(listaExamenes);
                        tblExamenes.setItems(datosTablaExamenes);
                        tblActividades.setVisible(false);
                        tblExamenes.setVisible(true);
                        lblDescripcion.setText("EXÁMENES");
                    }
                }
            }
        }
    }

    @FXML
    private void eliminarActividad(ActionEvent event) {

        if (tblActividades.isVisible()) {
            tareaEvaluable = this.tblActividades.getSelectionModel().getSelectedItem();
            if (tareaEvaluable == null) {
                mensajeAlertaNoSeleccion();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Info");
                alert.setContentText("¿Desea eliminar la tarea:  " + tareaEvaluable.getNombre() +  "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    datosTablaTareasEvaluables.remove(tareaEvaluable);
                    eliminarTareaEvaluable(tareaEvaluable);
                    tblActividades.refresh();
                }
            }

        } else if (tblExamenes.isVisible()) {
            examen = this.tblExamenes.getSelectionModel().getSelectedItem();
            if (examen == null) {
                mensajeAlertaNoSeleccion();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Info");
                alert.setContentText("¿Desea eliminar el examen:  " + examen.getNombre() + "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    datosTablaExamenes.remove(examen);
                    eliminarExamen(examen);
                    tblExamenes.refresh();
                }
            }
        }

    }

    private void modificarActividad(ActionEvent event) {

        if (tblActividades.isVisible()) {
            tareaEvaluable = this.tblActividades.getSelectionModel().getSelectedItem();
            if (tareaEvaluable == null) {
                mensajeAlertaNoSeleccion();
            } else {
                irModificarTareaEvaluable(tareaEvaluable);
                iniciarTablaTareasEvaluables();
                this.tblActividades.refresh();
            }
        } else if (tblExamenes.isVisible()) {
            examen = this.tblExamenes.getSelectionModel().getSelectedItem();
            if (examen == null) {
                mensajeAlertaNoSeleccion();
            } else {
                irModificarExamen(examen);
                iniciarTablaExamenes();
                this.tblExamenes.refresh();
            }
        }

    }

    @FXML
    private void irDatosAcademicos(ActionEvent event) {

        try {

            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DatosAcademicosVista.fxml"));
            Parent root = loader.load();

            DatosAcademicosController controlador = loader.getController();
            controlador.initAttributes(profesor);
            controlador.setRuta(2);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.show();

            //Profesor prof = controlador.getProfesorActualizado();
            //this.datosTablaProfesores.remove(profesor);
            //this.datosTablaProfesores.add(prof);
            this.tblActividades.refresh();
        } catch (IOException ex) {
            Logger.getLogger(PanelAdministracionProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;

        lblTitulo.setText("PROFESOR: " + this.profesor.getNombre() + " " + this.profesor.getPrimerApellido() + " " + this.profesor.getSegundoApellido());
        lblPanelLateral.setText(this.profesor.getNombre() + " " + this.profesor.getPrimerApellido() + " " + this.profesor.getSegundoApellido());
    }

    private void iniciarTablas() {
        System.err.println("A iniciar tablas jejej");

        tareaEvaluableDao = new TareaEvaluableDaoImpl();
        examenDao = new ExamenDaoImpl();

        tblActividades.setVisible(true);
        tblExamenes.setVisible(false);
        lblDescripcion.setText("TAREAS");

        System.err.println("El valor es Tarea Evaluable");
        listaTareasEvaluables = tareaEvaluableDao.consultarTodos();
        datosTablaTareasEvaluables = FXCollections.observableArrayList(listaTareasEvaluables);
        tblActividades.setItems(datosTablaTareasEvaluables);

        clmActividad.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmCategoria.setCellValueFactory(data -> new SimpleStringProperty("Tarea evaluable"));
        clmPorcentaje.setCellValueFactory(new PropertyValueFactory<>("porcentaje"));
        clmGrupo.setCellValueFactory((param) -> {
            tareaEvaluable = (TareaEvaluable) param.getValue();
            Grupo grupo = tareaEvaluable.getGrupoId();
            if (grupo == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(grupo.dameGrupo());
            }
        });
        clmAsignatura.setCellValueFactory((param) -> {
            tareaEvaluable = (TareaEvaluable) param.getValue();
            Asignatura asignatura = tareaEvaluable.getAsignaturaId();
            if (asignatura == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(asignatura.getNombre());
            }
        });

        listaExamenes = examenDao.consultarTodos();
        datosTablaExamenes = FXCollections.observableArrayList(listaExamenes);
        tblExamenes.setItems(datosTablaExamenes);

        clmActividadExamen.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmCategoriaExamen.setCellValueFactory(data -> new SimpleStringProperty("Examen"));
        clmPorcentajeExamen.setCellValueFactory(new PropertyValueFactory<>("porcentaje"));
        clmGrupoExamen.setCellValueFactory((param) -> {
            examen = (Examen) param.getValue();
            Grupo grupo = examen.getGrupoId();
            if (grupo == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(grupo.dameGrupo());
            }
        });
        clmAsignaturaExamen.setCellValueFactory((param) -> {
            examen = (Examen) param.getValue();
            Asignatura asignatura = examen.getAsignaturaId();
            if (asignatura == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(asignatura.getNombre());
            }
        });

    }

    private void iniciarTablaTareasEvaluables() {
        tblActividades.setVisible(true);
        tblExamenes.setVisible(false);
        lblDescripcion.setText("TAREAS");

        System.err.println("El valor es Tarea Evaluable");
        listaTareasEvaluables = tareaEvaluableDao.consultarTodos();
        datosTablaTareasEvaluables = FXCollections.observableArrayList(listaTareasEvaluables);
        tblActividades.setItems(datosTablaTareasEvaluables);

        clmActividad.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmCategoria.setCellValueFactory(data -> new SimpleStringProperty("Tarea evaluable"));
        clmPorcentaje.setCellValueFactory(new PropertyValueFactory<>("porcentaje"));
        clmGrupo.setCellValueFactory((param) -> {
            tareaEvaluable = (TareaEvaluable) param.getValue();
            Grupo grupo = tareaEvaluable.getGrupoId();
            if (grupo == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(grupo.dameGrupo());
            }
        });
        clmAsignatura.setCellValueFactory((param) -> {
            tareaEvaluable = (TareaEvaluable) param.getValue();
            Asignatura asignatura = tareaEvaluable.getAsignaturaId();
            if (asignatura == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(asignatura.getNombre());
            }
        });
    }

    private void iniciarTablaExamenes() {
        tblActividades.setVisible(false);
        tblExamenes.setVisible(true);
        lblDescripcion.setText("EXÁMENES");

        listaExamenes = examenDao.consultarTodos();
        datosTablaExamenes = FXCollections.observableArrayList(listaExamenes);
        tblExamenes.setItems(datosTablaExamenes);

        clmActividadExamen.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmCategoriaExamen.setCellValueFactory(data -> new SimpleStringProperty("Examen"));
        clmPorcentajeExamen.setCellValueFactory(new PropertyValueFactory<>("porcentaje"));
        clmGrupoExamen.setCellValueFactory((param) -> {
            examen = (Examen) param.getValue();
            Grupo grupo = examen.getGrupoId();
            if (grupo == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(grupo.dameGrupo());
            }
        });
        clmAsignaturaExamen.setCellValueFactory((param) -> {
            examen = (Examen) param.getValue();
            Asignatura asignatura = examen.getAsignaturaId();
            if (asignatura == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(asignatura.getNombre());
            }
        });

    }

    private void iniciarCombosYText() {

        // txtNombre == example default
        txtNombreActividad.setText("ejemplo");
        // cmbCategoria
        ObservableList<String> categorias = FXCollections.observableArrayList("Examen", "Tarea evaluable");
        cmbCategoriaActividad.setItems(categorias);

        // cmbAsignatura
        AsignaturaDao asigDao = new AsignaturaDaoImpl();
        List<Asignatura> asignaturas = asigDao.consultarTodos();
        List<String> nombreAsignaturas = nombreAsignaturas(asignaturas);
        ObservableList<String> listadoAsignaturasComboBox = FXCollections.observableArrayList(nombreAsignaturas);
        ObservableList<String> listadoAsignaturasComboBoxBuscar = FXCollections.observableArrayList(nombreAsignaturas);
        listadoAsignaturasComboBoxBuscar.add(0, "Todas");
        cmbAsignaturaCrear.setItems(listadoAsignaturasComboBox);
        cmbAsignaturaBuscar.setItems(listadoAsignaturasComboBoxBuscar);
        cmbAsignaturaBuscar.setValue("Todas");

        // cmbGrupo
        GrupoDao grupoDao = new GrupoDaoImpl();
        List<Grupo> grupos = grupoDao.consultarTodos();
        List<String> cursosYLetras = gruposCursoYLetra(grupos);
        ObservableList<String> listadoGruposComboBox = FXCollections.observableArrayList(cursosYLetras);
        ObservableList<String> listadoGruposComboBoxBuscar = FXCollections.observableArrayList(cursosYLetras);
        listadoGruposComboBoxBuscar.add(0, "Todos");
        cmbGrupoCrear.setItems(listadoGruposComboBox);
        cmbGrupoBuscar.setItems(listadoGruposComboBoxBuscar);
        cmbGrupoBuscar.setValue("Todos");

        // porcentaje == 0 por default
        txtPorcentaje.setText("0");

        // cmbTareaOExamen
        ObservableList<String> opciones = FXCollections.observableArrayList("Examenes", "Tareas evaluables");
        cmbTareaOExamen.setItems(opciones);
        cmbTareaOExamen.setValue("Tareas evaluables");
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

    private List<String> gruposCursoYLetra(List<Grupo> grupos) {
        List<String> cursos = new ArrayList<>();
        for (Grupo grupo : grupos) {
            String curso = grupo.getCurso();
            String letra = grupo.getLetra();
            cursos.add(curso + " " + letra);
        }
        return cursos;
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

    private void mensajeAlertaCamposIncompletos() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setTitle("Campos incompletos");
        alert.setContentText("Por favor, indique toda la información necesaria para crear una nueva actividad");
        alert.showAndWait();
    }

    private void mensajeAlertaFormato() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setTitle("Formato numérico no válido");
        alert.setContentText("Por favor, el porcentaje debe ser un número entero comprendido entre 0 y 100");
        alert.showAndWait();
    }

    private void mensajeAlertaNoSeleccion() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setTitle("Ítem no seleccionado");
        alert.setContentText("Por favor, selecciona un ítem para poder realizar la acción indicada");
        alert.showAndWait();
    }

    private void eliminarTareaEvaluable(TareaEvaluable tareaEvaluable) {
        NotaTareaDao notaTareaDao = new NotaTareaDaoImpl();
        notaTareaDao.eliminarNotasPorTarea(tareaEvaluable);
        tareaEvaluableDao = new TareaEvaluableDaoImpl();
        tareaEvaluableDao.eliminar(tareaEvaluable);
    }

    private void eliminarExamen(Examen examen) {
        NotaExamenDao notaExamenDao = new NotaExamenDaoImpl();
        notaExamenDao.eliminarNotasPorExamen(examen);
        examenDao = new ExamenDaoImpl();
        examenDao.eliminar(examen);
    }

    private void irModificarTareaEvaluable(TareaEvaluable tareaEvaluable) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarDatosTareaEvaluableVista.fxml"));
            Parent root = loader.load();
            ModificarDatosTareaEvaluableController controlador = loader.getController();
            controlador.initAttributes(profesor, tareaEvaluable);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            iniciarTablaTareasEvaluables();
            //Profesor prof = controlador.getProfesorActualizado();
            //this.datosTablaProfesores.remove(profesor);
            //this.datosTablaProfesores.add(prof);
            this.tblActividades.refresh();
        } catch (IOException ex) {
            Logger.getLogger(PanelAdministracionProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void irModificarExamen(Examen examen) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarDatosExamenVista.fxml"));
            Parent root = loader.load();
            ModificarDatosExamenController controlador = loader.getController();
            controlador.initAttributes(profesor, examen);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            iniciarTablaExamenes();
            //Profesor prof = controlador.getProfesorActualizado();
            //this.datosTablaProfesores.remove(profesor);
            //this.datosTablaProfesores.add(prof);
            this.tblExamenes.refresh();
        } catch (IOException ex) {
            Logger.getLogger(PanelAdministracionProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
