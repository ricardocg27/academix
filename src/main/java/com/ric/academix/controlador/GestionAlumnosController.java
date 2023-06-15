/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.dao.GrupoDao;
import com.ric.academix.dao.NotaDao;
import com.ric.academix.daoImpl.AlumnoDaoImpl;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.daoImpl.NotaDaoImpl;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
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
import javafx.scene.control.cell.PropertyValueFactory;
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
public class GestionAlumnosController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private TableView<Alumno> tblAlumnos;
    @FXML
    private TableColumn<Alumno, String> clmNombre;
    @FXML
    private TableColumn<Alumno, String> clmPrimerApellido;
    @FXML
    private TableColumn<Alumno, String> clmSegundoApellido;
    @FXML
    private TableColumn<Alumno, String> clmCorreo;
    @FXML
    private TableColumn<Alumno, String> clmTutor;
    @FXML
    private TableColumn<Alumno, String> clmGrupo;
    @FXML
    private ComboBox<String> cmbGrupo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnFiltrar;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    private int ruta;
    private Grupo grupo;
    private Profesor profesor;
    private Administrador administrador;
    private Alumno alumno;
    private AlumnoDao alumnoDao = null;
    private List<Alumno> listaAlumnos;

    private ObservableList<Alumno> datosTablaAlumnos, filtroAlumnosPorGrupo;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField lblPanelLateral;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alumnoDao = new AlumnoDaoImpl();
        iniciarTabla();
        iniciarComboBox();
        filtroAlumnosPorGrupo = FXCollections.observableArrayList();
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
                stage.show();
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
                stage.show();
            }

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
    private void RegistrarPosicion(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void iniciarTabla() {

        listaAlumnos = alumnoDao.consultarTodos();
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        clmSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        clmCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));

        clmGrupo.setCellValueFactory((data) -> {
            alumno = (Alumno) data.getValue();
            grupo = alumno.getGrupoId();

            if (grupo == null) {
                return new SimpleStringProperty("-");
            } else {
                return new SimpleStringProperty(grupo.getCurso() + " " + grupo.getLetra());
            }
        });

        clmTutor.setCellValueFactory((data) -> {
            alumno = (Alumno) data.getValue();
            grupo = alumno.getGrupoId();

            if (grupo == null) {
                return new SimpleStringProperty("-");
            } else {
                profesor = grupo.getTutorId();
                return new SimpleStringProperty(profesor.getNombreCompleto());
            }
        });

        datosTablaAlumnos = FXCollections.observableArrayList(listaAlumnos);
        tblAlumnos.setItems(datosTablaAlumnos);
    }

    private void iniciarComboBox() {

        GrupoDao grupoDao = new GrupoDaoImpl();
        List<Grupo> grupos = grupoDao.consultarTodos();
        List<String> cursosYLetras = gruposCursoYLetra(grupos);
        cursosYLetras.add(0, "Todos");
        ObservableList<String> listadoGruposComboBox = FXCollections.observableArrayList(cursosYLetras);
        cmbGrupo.setItems(listadoGruposComboBox);

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
    private void modificarAlumno(ActionEvent event) {

        try {
            alumno = this.tblAlumnos.getSelectionModel().getSelectedItem();
            if (alumno == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Selecciona un ítem para poder modificar los datos");
                //alert.setContentText(e.printStackTrace());
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarDatosAlumnoVista.fxml"));
                Parent root = loader.load();
                ModificarDatosAlumnoController controlador = loader.getController();
                controlador.initAttributes(alumno);

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
                this.tblAlumnos.refresh();
            }
        } catch (IOException ex) {
            Logger.getLogger(GestionAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void eliminarAlumno(ActionEvent event) {

        alumno = this.tblAlumnos.getSelectionModel().getSelectedItem();

        if (alumno == null) {
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
            alert.setContentText("¿Desea eliminar a " + alumno.getNombre() + " " + alumno.getPrimerApellido() + " " + alumno.getSegundoApellido()
                    + "?\nSerá eliminada toda la información asociada a él (datos y notas académicas)");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                datosTablaAlumnos.remove(alumno);
                eliminarAlumnoyDatos(alumno);
                tblAlumnos.refresh();
            }
        }
    }

    private void eliminarAlumnoyDatos(Alumno alumno) {

        // borrar notas del alumno
        NotaDao notaDao = new NotaDaoImpl();
        notaDao.eliminarPorAlumno(alumno.getId());
        // borrar alumno
        alumnoDao.eliminar(alumno);
    }

    @FXML
    private void filtrar(ActionEvent event) {

        String grupoSeleccionado = cmbGrupo.getValue();

        if (grupoSeleccionado == null || grupoSeleccionado.equals("Todos")) {
            tblAlumnos.setItems(datosTablaAlumnos);
        } else {
            grupo = obtenerGrupo(grupoSeleccionado);
            filtroAlumnosPorGrupo.clear();
            List<Alumno> alumnosPorGrupo = alumnoDao.consultarPorGrupo(grupo);
            for (Alumno alumno : alumnosPorGrupo) {
                this.filtroAlumnosPorGrupo.add(alumno);
            }
            this.tblAlumnos.setItems(filtroAlumnosPorGrupo);
        }

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

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
        lblTitulo.setText("ADMINISTRADOR: " + this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
        lblPanelLateral.setText(this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

}
