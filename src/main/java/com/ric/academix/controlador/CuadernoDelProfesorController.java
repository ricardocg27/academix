/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.dao.CuadernoProfesorDao;
import com.ric.academix.dao.GrupoDao;
import com.ric.academix.daoImpl.AlumnoDaoImpl;
import com.ric.academix.daoImpl.CuadernoProfesorDaoImpl;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.CuadernoProfesor;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.FileOutputStream;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class CuadernoDelProfesorController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private ComboBox<String> cmbAlumno;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<CuadernoProfesor> tblCuadernoProfesor;
    @FXML
    private TableColumn<CuadernoProfesor, String> clmAlumno;
    @FXML
    private TableColumn<?, ?> clmFecha;
    @FXML
    private TableColumn<?, ?> clmTareasCasa;
    @FXML
    private TableColumn<?, ?> clmParticipa;
    @FXML
    private TableColumn<?, ?> clmAtiende;
    @FXML
    private TableColumn<?, ?> clmTareasClase;
    @FXML
    private DatePicker dtpFechaInicio;
    @FXML
    private DatePicker dtpFechaFin;
    @FXML
    private ComboBox<String> cmbTareasCasa;
    @FXML
    private ComboBox<String> cmbParticipacion;
    @FXML
    private ComboBox<String> cmbAtencion;
    @FXML
    private ComboBox<String> cmbTareasClase;
    @FXML
    private DatePicker dtpFechaRegistro;
    @FXML
    private ComboBox<String> cmbGrupo;
    @FXML
    private ComboBox<String> cmbGrupoRegistro;
    @FXML
    private ComboBox<String> cmbAlumnoRegistro;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnExportar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    private CuadernoProfesor cuadernoProfesor, cuadernoProfesorExistente;
    private CuadernoProfesorDao cuadernoProfesorDao;
    private Grupo grupo;
    private GrupoDao grupoDao;
    private Profesor profesor;
    private Alumno alumno;
    private AlumnoDao alumnoDao;
    private List<Alumno> listaAlumnos;
    private List<CuadernoProfesor> listaCuadernos, listaCuadernosFiltrados;
    private ObservableList<String> listaNombresAlumnos = FXCollections.observableArrayList();
    private ObservableList<String> listaNombresAlumnosNuevoRegistro = FXCollections.observableArrayList();
    private ObservableList<CuadernoProfesor> datosTablaCuadernoProfesor;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField lblPanelLateral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarCombos();
        iniciarTabla();

        Tooltip tooltipTareasCasa = new Tooltip("Tareas en casa");
        tooltipTareasCasa.setShowDelay(Duration.ZERO);
        cmbTareasCasa.setTooltip(tooltipTareasCasa);

        Tooltip tooltipParticipacion = new Tooltip("Participación");
        tooltipParticipacion.setShowDelay(Duration.ZERO);
        cmbParticipacion.setTooltip(tooltipParticipacion);

        Tooltip tooltipAtencion = new Tooltip("Atención");
        tooltipAtencion.setShowDelay(Duration.ZERO);
        cmbAtencion.setTooltip(tooltipAtencion);

        Tooltip tooltipTareasClase = new Tooltip("Tareas en clase");
        tooltipTareasClase.setShowDelay(Duration.ZERO);
        cmbTareasClase.setTooltip(tooltipTareasClase);

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

        } catch (IOException e) {
            e.printStackTrace();
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
        // dtpFechaRegistro
        dtpFechaRegistro.setValue(LocalDate.now());

        // cmBGrupo
        GrupoDao grupoDao = new GrupoDaoImpl();
        List<Grupo> grupos = grupoDao.consultarTodos();
        List<String> cursosYLetras = gruposCursoYLetra(grupos);
        ObservableList<String> listadoGruposComboBox = FXCollections.observableArrayList(cursosYLetras);
        cmbGrupo.setItems(listadoGruposComboBox);
        cmbGrupoRegistro.setItems(listadoGruposComboBox);

        // FechaFin
        dtpFechaFin.setValue(LocalDate.now());

        // FechaInicio
        LocalDate fechaFin = dtpFechaFin.getValue();
        int agno = fechaFin.getYear();
        int agnoAnterior = agno - 1;
        LocalDate fechaInicio = LocalDate.of(agnoAnterior, Month.SEPTEMBER, 1);
        dtpFechaInicio.setValue(fechaInicio);

    }

    @FXML
    private void seleccionarCurso(ActionEvent event) {
        iniciarCmbAlumno();
    }

    @FXML
    private void seleccionarCursoNuevoRegistro(ActionEvent event) {
        iniciarCmbAlumnoNuevoRegistro();
    }

    @FXML
    private void buscarAlumno(ActionEvent event) {

        if (cmbAlumno.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Faltan campos por completar");
            alert.setContentText("Por favor, selecciona un alumno para buscar información.");
            alert.showAndWait();
        } else {
            String alumnoString = cmbAlumno.getValue();
            alumno = obtenerAlumnoDelComboBox(alumnoString);

            if (alumno == null && !alumnoString.equalsIgnoreCase("Todos")) {
                System.err.println("Entra aquí");
                System.err.println("valor cmb --> " + alumnoString.toString());
                System.err.println(alumno);
                System.err.println(alumnoString);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campos incompletos");
                alert.setHeaderText("Faltan campos por completar");
                alert.setContentText("Por favor, selecciona un alumno para buscar información.");
                alert.showAndWait();

            } else {
                LocalDate fechaInicio = dtpFechaInicio.getValue();
                LocalDate fechaFin = dtpFechaFin.getValue();
                String fechaInicioStr = fechaInicio.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String fechaFinStr = fechaFin.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                cuadernoProfesorDao = new CuadernoProfesorDaoImpl();

                if (alumnoString.equalsIgnoreCase("Todos")) {
                    listaCuadernosFiltrados = cuadernoProfesorDao.consultarRegistrosPorFechas(fechaInicioStr, fechaFinStr);

                } else {
                    listaCuadernosFiltrados = cuadernoProfesorDao.consultarRegistroPorAlumnoYFechas(alumno, fechaInicioStr, fechaFinStr);
                }

                datosTablaCuadernoProfesor = tblCuadernoProfesor.getItems();
                datosTablaCuadernoProfesor.clear();
                datosTablaCuadernoProfesor.addAll(listaCuadernosFiltrados);
                tblCuadernoProfesor.refresh();
            }
        }
    }

    @FXML
    private void registrar(ActionEvent event) {

        String tareasCasa = cmbTareasCasa.getValue();
        String participacion = cmbParticipacion.getValue();
        String atencion = cmbAtencion.getValue();
        String tareasClase = cmbTareasClase.getValue();
        String alumnoString = cmbAlumnoRegistro.getValue();

        LocalDate fechaRegisto = dtpFechaRegistro.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaFormateada = fechaRegisto.format(formatter);
        alumno = obtenerAlumnoDelComboBox(alumnoString);

        if (tareasCasa != null && participacion != null && atencion != null && tareasClase != null && alumnoString != null) {

            // consultar que ya haya un registro para ese alumno en ese fecha
            cuadernoProfesorDao = new CuadernoProfesorDaoImpl();
            cuadernoProfesorExistente = cuadernoProfesorDao.consultarRegistroAlumnoExistente(alumno, fechaFormateada);
            if (cuadernoProfesorExistente == null) {
                cuadernoProfesor = new CuadernoProfesor();
                cuadernoProfesor.setAlumnoId(alumno);
                cuadernoProfesor.setFechaInsercion(fechaFormateada);
                cuadernoProfesor.setTareasCasa(tareasCasa);
                cuadernoProfesor.setParticipacion(participacion);
                cuadernoProfesor.setAtencion(atencion);
                cuadernoProfesor.setTareasClase(tareasClase);

                cuadernoProfesorDao.insertar(cuadernoProfesor);

                // Actualización de la tabla tras la inserción
//                datosTablaCuadernoProfesor = tblCuadernoProfesor.getItems();
//                datosTablaCuadernoProfesor.add(cuadernoProfesor);
                iniciarTabla();
                tblCuadernoProfesor.refresh();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registro existente");
                alert.setHeaderText("Fecha errónea");
                alert.setContentText("Ya existe un registro para el alumno " + alumno.getNombreYApellidos() + " en la fecha " + fechaFormateada.toString());
                alert.showAndWait();
            }

        } else {
            // Alguno de los campos está vacío, mostrar una alerta al usuario
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Faltan campos por completar");
            alert.setContentText("Por favor, complete todos los campos para crear un nuevo registro.");
            alert.showAndWait();
        }

    }

    @FXML
    private void Exportar(ActionEvent event) {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Datos de la tabla");

            // Encabezados de columna
            String[] headers = {"Alumno", "Fecha de registro", "Tareas de casa", "Participa", "Atiende", "Tareas de clase"};

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Crear la fila para los encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);

            }

            datosTablaCuadernoProfesor = tblCuadernoProfesor.getItems();
            // Llenar los datos de la tabla
            for (int rowIndex = 0; rowIndex < datosTablaCuadernoProfesor.size(); rowIndex++) {
                Row row = sheet.createRow(rowIndex + 1);
                CuadernoProfesor cuaderno = datosTablaCuadernoProfesor.get(rowIndex);

                row.createCell(0).setCellValue(cuaderno.getAlumnoId().getNombreYApellidos());
                row.createCell(1).setCellValue(cuaderno.getFechaInsercion());
                row.createCell(2).setCellValue(cuaderno.getTareasCasa());
                row.createCell(3).setCellValue(cuaderno.getParticipacion());
                row.createCell(4).setCellValue(cuaderno.getAtencion());
                row.createCell(5).setCellValue(cuaderno.getTareasClase());
            }

            String userHome = System.getProperty("user.home");
            String downloadsPath = Paths.get(userHome, "Downloads").toString();
            // Ruta completa del archivo Excel en la carpeta "Descargas"
            String filePath = Paths.get(downloadsPath, "datos_tabla.xlsx").toString();

            // Guardar el archivo Excel
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText("Archivo excel generado con éxito");
                alert.setContentText("Archivo: datos_tabla.xlsx guardado correctamente en Descargas.");
                alert.showAndWait();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {

        cuadernoProfesor = this.tblCuadernoProfesor.getSelectionModel().getSelectedItem();

        if (cuadernoProfesor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Selecciona un registro para poder eliminarlo");
            //alert.setContentText(e.printStackTrace());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("¿Desea eliminar registro de " + cuadernoProfesor.getAlumnoId().getNombreYApellidos() + " con fecha "
                    + cuadernoProfesor.getFechaInsercion() + "?"
                    + "\nEsta decisión conlleva la eliminación del registro forma irreversible");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                datosTablaCuadernoProfesor.remove(cuadernoProfesor);
                eliminarRegistro(cuadernoProfesor);
                tblCuadernoProfesor.refresh();
            }
        }

    }

    @FXML
    private void modificar(ActionEvent event) {

        try {
            cuadernoProfesor = this.tblCuadernoProfesor.getSelectionModel().getSelectedItem();
            if (alumno == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Selecciona un ítem para poder modificar los datos");
                //alert.setContentText(e.printStackTrace());
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarDatosRegistroVista.fxml"));
                Parent root = loader.load();
                ModificarDatosRegistroController controlador = loader.getController();
                controlador.initAttributes(cuadernoProfesor);

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
                this.tblCuadernoProfesor.refresh();
            }
        } catch (IOException ex) {
            Logger.getLogger(GestionAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    private void iniciarCmbAlumno() {

        String grupoString = cmbGrupo.getSelectionModel().getSelectedItem();
        grupo = obtenerGrupo(grupoString);

        if (grupo != null) {
            alumnoDao = new AlumnoDaoImpl();
            listaAlumnos = alumnoDao.consultarPorGrupo(grupo);
            listaNombresAlumnos.clear();
            listaNombresAlumnos.add("Todos");
            for (Alumno x : listaAlumnos) {
                listaNombresAlumnos.add(x.getNombreYApellidos());
            }
            cmbAlumno.setItems(listaNombresAlumnos);
            cmbAlumno.getSelectionModel().selectFirst();
            cmbAlumnoRegistro.setItems(listaNombresAlumnos);
        }
    }

    private void iniciarCmbAlumnoNuevoRegistro() {

        String grupoString = cmbGrupoRegistro.getSelectionModel().getSelectedItem();
        grupo = obtenerGrupo(grupoString);

        if (grupo != null) {
            alumnoDao = new AlumnoDaoImpl();
            listaAlumnos = alumnoDao.consultarPorGrupo(grupo);
            listaNombresAlumnosNuevoRegistro.clear();
            for (Alumno x : listaAlumnos) {
                listaNombresAlumnosNuevoRegistro.add(x.getNombreYApellidos());
            }
            cmbAlumnoRegistro.setItems(listaNombresAlumnosNuevoRegistro);
        }
    }

    private Grupo obtenerGrupo(String grupoString) {

        grupo = null;
        grupoDao = new GrupoDaoImpl();

        if (grupoString != null) {
            String partes[] = grupoString.split(" ");
            if (partes.length == 3) {
                String curso = partes[0] + " " + partes[1];
                String letra = partes[2];
                grupo = grupoDao.consultarGrupoPorCursoYLetra(curso, letra);
            }
        }
        return grupo;
    }

    public void setUsuario(Profesor profesor) {
        this.profesor = profesor;
        lblTitulo.setText("PROFESOR: " + this.profesor.getNombre() + " " + this.profesor.getPrimerApellido() + " " + this.profesor.getSegundoApellido());
        lblPanelLateral.setText(this.profesor.getNombre() + " " + this.profesor.getPrimerApellido() + " " + this.profesor.getSegundoApellido());

    }

    private Alumno obtenerAlumnoDelComboBox(String alumnoString) {

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

    private void iniciarTabla() {
        cuadernoProfesorDao = new CuadernoProfesorDaoImpl();
        listaCuadernos = cuadernoProfesorDao.consultarTodos();

        clmAlumno.setCellValueFactory((data) -> {
            cuadernoProfesor = (CuadernoProfesor) data.getValue();
            alumno = cuadernoProfesor.getAlumnoId();
            if (alumno == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(alumno.getNombreYApellidos());
            }
        });

        clmFecha.setCellValueFactory(new PropertyValueFactory<>("fechaInsercion"));
        clmTareasCasa.setCellValueFactory(new PropertyValueFactory<>("tareasCasa"));
        clmParticipa.setCellValueFactory(new PropertyValueFactory<>("participacion"));
        clmAtiende.setCellValueFactory(new PropertyValueFactory<>("atencion"));
        clmTareasClase.setCellValueFactory(new PropertyValueFactory<>("tareasClase"));

        datosTablaCuadernoProfesor = FXCollections.observableArrayList(listaCuadernos);
        tblCuadernoProfesor.setItems(datosTablaCuadernoProfesor);
    }

    private void eliminarRegistro(CuadernoProfesor cuadernoProfesor) {

        cuadernoProfesorDao = new CuadernoProfesorDaoImpl();
        cuadernoProfesorDao.eliminarRegistroPorAlumnoYFecha(cuadernoProfesor);

    }

}
