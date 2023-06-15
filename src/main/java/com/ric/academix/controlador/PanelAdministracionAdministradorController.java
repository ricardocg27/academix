/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.controlador;

import com.ric.academix.dao.AdministradorDao;
import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.dao.AsignaturaDao;
import com.ric.academix.dao.GrupoDao;
import com.ric.academix.dao.ProfesorAsignaturaDao;
import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.AdministradorDaoImpl;
import com.ric.academix.daoImpl.AlumnoDaoImpl;
import com.ric.academix.daoImpl.AsignaturaDaoImpl;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.daoImpl.ProfesorAsignaturaDaoImpl;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.CodigoUsuarios;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
import com.ric.academix.modelo.ProfesorAsignatura;
import com.ric.academix.modelo.ProfesorAsignaturaPK;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author ricar
 */
public class PanelAdministracionAdministradorController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private ComboBox<String> cmbTipoUsuario;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtApellidoUsuario;
    @FXML
    private PasswordField txtContrasenaUsuario;
    @FXML
    private TextField txtEmailUsuario;
    @FXML
    private Button btnAgregarUsuario;
    @FXML
    private ComboBox<String> cmbCurso;
    @FXML
    private ComboBox<String> cmbLetra;
    @FXML
    private ComboBox<String> cmbTutor;
    @FXML
    private ComboBox<String> cmbAsignatura;
    @FXML
    private Button btnAgregarGrupo;
    @FXML
    private Button btnAgregarAsignatura;
    @FXML
    private TextField txtNombreAsignatura;
    @FXML
    private ComboBox<String> cmbProfesor;
    @FXML
    private TableView<Grupo> tblGrupo;
    @FXML
    private TableColumn<Grupo, String> clmCurso;
    @FXML
    private TableColumn<Grupo, String> clmLetra;
    @FXML
    private TableColumn<Grupo, String> clmTutor;
    @FXML
    private TableView<ProfesorAsignatura> tblAsignaturas;
    @FXML
    private TableColumn<ProfesorAsignatura, String> clmAsignatura;
    @FXML
    private TableColumn<ProfesorAsignatura, String> clmProfesor;
    @FXML
    private TextField txtSegundoApellidoUsuario;
    private TableView<?> tblModificar;
    @FXML
    private Button btnModificar;
    @FXML
    private FontAwesomeIconView icnExit;
    @FXML
    private ComboBox<String> cmbOpcionModificar;
    @FXML
    private ComboBox<String> cmbOpcionModificar2;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    private ValidationSupport validadorUsuario, validadorGrupo, validadorAsignatura;
    private String nombre, apellido1, apellido2, contrasegna, email;
    private int tipoElemento;

    private Administrador administrador;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField lblPanelLateral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validadorUsuario = new ValidationSupport();
        validadorGrupo = new ValidationSupport();
        validadorAsignatura = new ValidationSupport();
        validadorUsuario.setErrorDecorationEnabled(true);
        validadorGrupo.setErrorDecorationEnabled(true);
        validadorAsignatura.setErrorDecorationEnabled(true);

        iniciarCombosBox();
        iniciarTablas();

    }

    private void iniciarCombosBox() {

        // Iniciar cbmTipoUsuarios
        ObservableList<String> tiposUsuario = FXCollections.observableArrayList("Administrador", "Profesor", "Alumno");
        cmbTipoUsuario.setItems(tiposUsuario);

        // Iniciar cmbCurso
        ObservableList<String> listadoCursosComboBox = FXCollections.observableArrayList("1º ESO", "2º ESO", "3º ESO", "4º ESO", "1º BACHILLERATO", "2º BACHILLERATO");
        cmbCurso.setItems(listadoCursosComboBox);

        // Iniciar cmbLetra
        ObservableList<String> listadoLetrasComboBox = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F", "G", "H", "I");
        cmbLetra.setItems(listadoLetrasComboBox);

        // Iniciar cmbTutor
        ProfesorDao profDao = new ProfesorDaoImpl();
        List<Profesor> profesores = profDao.consultarTodos();
        List<String> nombreProfesoresCompleto = nombreCompleto(profesores);
        ObservableList<String> listadoProfesoresComboBox = FXCollections.observableArrayList(nombreProfesoresCompleto);
        cmbTutor.setItems(listadoProfesoresComboBox);

        // Iniciar cmbAsignatura
        AsignaturaDao asigDao = new AsignaturaDaoImpl();
        List<Asignatura> asignaturas = asigDao.consultarTodos();
        List<String> nombreAsignaturas = nombreAsignaturas(asignaturas);
        ObservableList<String> listadoAsignaturasComboBox = FXCollections.observableArrayList(nombreAsignaturas);
        cmbAsignatura.setItems(listadoAsignaturasComboBox);

        // Iniciar cmbProfesor
        cmbProfesor.setItems(listadoProfesoresComboBox);

        // Iniciar cmbOpciónModificar
        ObservableList<String> opcionModificar = FXCollections.observableArrayList("Usuario", "Grupo", "Asignatura");
        cmbOpcionModificar.setItems(opcionModificar);
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

    private List<String> nombreAsignaturas(List<Asignatura> asignaturas) {
        List<String> nombresAsignaturas = new ArrayList<>();
        String nombreAsignatura;
        for (Asignatura asignatura : asignaturas) {
            nombreAsignatura = asignatura.getNombre();
            nombresAsignaturas.add(nombreAsignatura);
        }

        return nombresAsignaturas;
    }

    private void iniciarTablas() {
        // tabla grupos
        clmCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        clmLetra.setCellValueFactory(new PropertyValueFactory<>("letra"));

        clmTutor.setCellValueFactory(data -> {
            String dato = "";
            Profesor tutor = data.getValue().getTutorId();
            if (tutor != null) {
                dato = dato + tutor.getNombre() + " " + tutor.getPrimerApellido() + " " + tutor.getSegundoApellido();
            } else {
                dato = "-";
            }
            return new SimpleStringProperty(dato);
        });

        GrupoDao grupoDao = new GrupoDaoImpl();
        List<Grupo> grupos = grupoDao.consultarTodos();
        ObservableList<Grupo> datosTablaGrupos = FXCollections.observableArrayList(grupos);
        tblGrupo.setItems(datosTablaGrupos);

        // tabla asignaturas
        clmAsignatura.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAsignatura().getNombre()));
        clmProfesor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProfesor().getNombreCompleto()));

        ProfesorAsignaturaDao profAsignaturaDao = new ProfesorAsignaturaDaoImpl();
        List<ProfesorAsignatura> profAsignaturas = profAsignaturaDao.consultarTodos();
        ObservableList<ProfesorAsignatura> datosTablaAsignaturas = FXCollections.observableArrayList(profAsignaturas);

        tblAsignaturas.setItems(datosTablaAsignaturas);

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
    private void agregarUsuario(ActionEvent event) {

        if (camposUsuarioRellenos()) {
            insertar();
            iniciarTablas();
            iniciarCombosBox();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText("Usuario insertado.");
            alert.setContentText("Se ha insertado con éxito un nuevo usuario en la base de datos");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos sin rellenar");
            alert.setContentText("Por favor, rellene todos los datos necesarios para agregar un nuevo usuario");
            alert.showAndWait();
        }

    }

    private boolean camposUsuarioRellenos() {
        boolean camposRellenos;

        validadorUsuario.registerValidator(cmbTipoUsuario, Validator.createEmptyValidator("Elige una categoría"));
        validadorUsuario.registerValidator(txtNombreUsuario, Validator.createEmptyValidator("Campo requerido"));
        validadorUsuario.registerValidator(txtApellidoUsuario, Validator.createEmptyValidator("Campo requerido"));
        validadorUsuario.registerValidator(txtSegundoApellidoUsuario, Validator.createEmptyValidator("Campo requerido"));
        validadorUsuario.registerValidator(txtContrasenaUsuario, Validator.createEmptyValidator("Campo requerido"));
        validadorUsuario.registerValidator(txtEmailUsuario, Validator.createEmptyValidator("Campo requerido"));

        if (validadorUsuario.isInvalid()) {
            camposRellenos = false;
        } else {
            camposRellenos = true;
        }

        return camposRellenos;

    }

    private void insertar() {
        String categoriaUsuario = cmbTipoUsuario.getValue();

        switch (categoriaUsuario) {
            case "Administrador":
                Administrador admin = crearAdministrador();
                AdministradorDao adminDao = new AdministradorDaoImpl();
                adminDao.insertar(admin);
                break;
            case "Profesor":
                Profesor prof = crearProfesor();
                ProfesorDao profesorDao = new ProfesorDaoImpl();
                profesorDao.insertar(prof);
                break;
            case "Alumno":
                Alumno alumno = crearAlumno();
                AlumnoDao alumnoDao = new AlumnoDaoImpl();
                alumnoDao.insertar(alumno);
                break;
        }
    }

    private Administrador crearAdministrador() {
        CodigoUsuarios codigo = new CodigoUsuarios();
        Administrador admin = null;

        nombre = txtNombreUsuario.getText().trim();
        apellido1 = txtApellidoUsuario.getText().trim();
        apellido2 = txtSegundoApellidoUsuario.getText().trim();
        contrasegna = txtContrasenaUsuario.getText().trim();
        email = txtEmailUsuario.getText().trim();

        admin = new Administrador(nombre, apellido1, apellido2, contrasegna, email, codigo.getCodigoTipoAdmin());

        return admin;
    }

    private Profesor crearProfesor() {

        CodigoUsuarios codigo = new CodigoUsuarios();
        Profesor profesor = null;

        nombre = txtNombreUsuario.getText().trim();
        apellido1 = txtApellidoUsuario.getText().trim();
        apellido2 = txtSegundoApellidoUsuario.getText().trim();
        contrasegna = txtContrasenaUsuario.getText().trim();
        email = txtEmailUsuario.getText().trim();

        profesor = new Profesor(nombre, apellido1, apellido2, contrasegna, email, codigo.getCodigoTipoProfesor());

        return profesor;
    }

    private Alumno crearAlumno() {

        CodigoUsuarios codigo = new CodigoUsuarios();
        Alumno alumno = null;

        nombre = txtNombreUsuario.getText();
        apellido1 = txtApellidoUsuario.getText();
        apellido2 = txtSegundoApellidoUsuario.getText();
        contrasegna = txtContrasenaUsuario.getText();
        email = txtEmailUsuario.getText();
        email = txtEmailUsuario.getText();

        alumno = new Alumno(nombre, apellido1, apellido2, contrasegna, email, codigo.getCodigoTipoAlumno());

        return alumno;
    }

    @FXML
    private void agregarGrupo(ActionEvent event) {

        if (camposGrupoRellenos()) {
            insertarGrupo();
            tblGrupo.refresh();
            iniciarTablas();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos sin rellenar");
            alert.setContentText("Por favor, indique todos los datos necesarios para agregar un nuevo grupo");
            alert.showAndWait();
        }

    }

    private boolean camposGrupoRellenos() {
        boolean camposRellenos;

        validadorGrupo.registerValidator(cmbCurso, Validator.createEmptyValidator("Elija un curso"));
        validadorGrupo.registerValidator(cmbLetra, Validator.createEmptyValidator("Elija una letra"));
        validadorGrupo.registerValidator(cmbTutor, Validator.createEmptyValidator("Elija un curso"));

        if (validadorGrupo.isInvalid()) {
            camposRellenos = false;
        } else {
            camposRellenos = true;
        }

        return camposRellenos;

    }

    private void insertarGrupo() {
        ProfesorDao profesorDao = new ProfesorDaoImpl();
        GrupoDao grupoDao = new GrupoDaoImpl();

        String curso = cmbCurso.getSelectionModel().getSelectedItem();
        String letra = cmbLetra.getSelectionModel().getSelectedItem();
        String nombreTutor = cmbTutor.getSelectionModel().getSelectedItem();

        int idTutor = obtenerIdTutor(nombreTutor);
        Profesor profesor = profesorDao.consultar(idTutor);

        Profesor tutor = profesorDao.consultarTutor(profesor.getId());
        if (tutor != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Tutor existente");
            alert.setContentText("El profesor seleccionado ya es tutor de un grupo. Por favor, seleccione un profesor que no sea tutor de un grupo");
            alert.showAndWait();
        } else {
            Grupo grupoExistente = grupoDao.consultarGrupoPorCursoYLetra(curso, letra);
            if (grupoExistente != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Grupo existente");
                alert.setContentText("Ya existe este grupo. Indica otro curso u otra letra.");
                alert.showAndWait();
            } else {
                Grupo grupo = new Grupo(curso, letra, profesor);
                grupoDao.insertar(grupo);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText("Usuario insertado.");
                alert.setContentText("Se ha creado con éxito un nuevo grupo");
                alert.showAndWait();
            }

        }

    }

    private int obtenerIdTutor(String tutor) {
        ProfesorDao profesorDao = new ProfesorDaoImpl();
        String tutorNoEspacios = tutor.trim();
        List<String> nombres = obtenerNombreyApellidos(tutorNoEspacios);
        int idTutor = profesorDao.consultarIdPorNombreYApellidos(nombres);
        return idTutor;
    }

    private List<String> obtenerNombreyApellidos(String tutorNoEspacios) {
        List<String> nombres = new ArrayList<>();
        String[] partes = tutorNoEspacios.split(" ");
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

    @FXML
    private void agregarAsignaturaProfesor(ActionEvent event) {
        if (camposAsignaturaRellenos()) {
            insertarAsignaturaProfesor();
            iniciarTablas();
            tblAsignaturas.refresh();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos sin rellenar");
            alert.setContentText("Por favor, indique todos los datos necesarios para agregar una nueva asignatura");
            alert.showAndWait();
        }
    }

    private boolean camposAsignaturaRellenos() {
        boolean camposRellenos;

        //validador.registerValidator(txtNombreAsignatura, Validator.createEmptyValidator("Campo requerido"));
        validadorAsignatura.registerValidator(cmbProfesor, Validator.createEmptyValidator("Campo requerido"));

        if (validadorAsignatura.isInvalid()) {
            camposRellenos = false;
        } else {
            camposRellenos = true;
        }

        return camposRellenos;

    }

    private void insertarAsignaturaProfesor() {
        String nombreAsignatura = null;
        Asignatura asignatura = null;
        // inicio los daos
        ProfesorDao profesorDao = new ProfesorDaoImpl();
        AsignaturaDao asignaturaDao = new AsignaturaDaoImpl();
        ProfesorAsignaturaDao profAsigDao = new ProfesorAsignaturaDaoImpl();

        // obtengo el profesor
        String nombreTutor = cmbProfesor.getSelectionModel().getSelectedItem();
        int idProfesor = obtenerIdTutor(nombreTutor);
        Profesor profesor = profesorDao.consultar(idProfesor);

        // Si el campo de nueva asignatura no está vacío, 
        // creo una nueva asignatura y la inserto
        if (!txtNombreAsignatura.getText().isBlank()) {
            nombreAsignatura = txtNombreAsignatura.getText();

            asignatura = asignaturaDao.consultarPorNombre(nombreAsignatura);
            if (asignatura == null) {
                asignatura = new Asignatura(nombreAsignatura.trim());
                asignaturaDao.insertar(asignatura);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Asignatura existente");
                alert.setContentText("Por favor, seleccione la asignatura de la lista de opciones disponibles");
                alert.showAndWait();
            }
        } // Si está vacío, se coge el nombre del comboBox
        // consulto la asignatura de la bbdd y la obtengo
        else if (txtNombreAsignatura.getText().isBlank()) {
            nombreAsignatura = cmbAsignatura.getSelectionModel().getSelectedItem();
            asignatura = asignaturaDao.consultarPorNombre(nombreAsignatura.trim());
        }

        ProfesorAsignaturaPK profAsigPK = new ProfesorAsignaturaPK(profesor.getId(), asignatura.getId());
        ProfesorAsignatura profAsig = new ProfesorAsignatura(profAsigPK);

        int agno = LocalDate.now().getYear();
        String agnoString = String.valueOf(agno);
        profAsig.setAgnoAcademico(agnoString);

        profAsigDao.insertar(profAsig);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("");
        alert.setContentText("La asignatura : " + asignatura.getNombre() + " se ha asociado al profesor: " + profesor.getNombreCompleto());
        alert.showAndWait();

    }

    @FXML
    private void exit(MouseEvent event) {

        try {
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

        } catch (IOException ex) {
            Logger.getLogger(RegisterControlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void elegirOpcion(ActionEvent event) {
        if (cmbOpcionModificar.getSelectionModel().getSelectedItem().equals("Usuario")) {
            cmbOpcionModificar2.setVisible(true);
            btnModificar.setLayoutX(305);
            btnModificar.setLayoutY(466);
            ObservableList<String> opcionModificar2 = FXCollections.observableArrayList("Profesor", "Alumno");
            cmbOpcionModificar2.setItems(opcionModificar2);

        } else if (cmbOpcionModificar.getValue().equals("Grupo")) {
            cmbOpcionModificar2.setVisible(false);
            ajustarTamanoBoton(btnModificar);
            btnModificar.setText("Gestionar grupos");
            btnModificar.setLayoutX(167);
            btnModificar.setLayoutY(466);

        } else if (cmbOpcionModificar.getValue().equals("Asignatura")) {
            cmbOpcionModificar2.setVisible(false);
            ajustarTamanoBoton(btnModificar);
            btnModificar.setLayoutX(167);
            btnModificar.setLayoutY(466);
            btnModificar.setText("Gestionar asignaturas");
        }
    }

    @FXML
    private void elegirOpcionSegundoCombo(ActionEvent event) {
        if (cmbOpcionModificar2.getSelectionModel().getSelectedItem().equals("Profesor")) {
            ajustarTamanoBoton(btnModificar);
            btnModificar.setText("Gestionar profesores");
        } else {
            ajustarTamanoBoton(btnModificar);
            btnModificar.setText("Gestionar alumnos");
        }
    }

    private void ajustarTamanoBoton(Button btnModificar1) {
        btnModificar.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btnModificar.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

    @FXML
    private void modificar(ActionEvent event) {

        if (cmbOpcionModificar.getValue().equals("Usuario")) {
            if (cmbOpcionModificar2.getValue().equals("Profesor")) {
                irGestionElementos(1);
            } else if (cmbOpcionModificar2.getValue().equals("Alumno")) {
                irGestionElementos(2);
            }
        } else if (cmbOpcionModificar.getValue().equals("Grupo")) {
            irGestionElementos(3);
        } else {
            irGestionElementos(4); // asignaturas
        }

    }

    private void irGestionElementos(int tipoElemento) {

        String fxml = "";
        Object controller = null;
        System.err.println("Admin --> " + administrador.toString());
        Parent root = null;

        try {
            stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            switch (tipoElemento) {
                case 1: // profesor
                    fxml = "/fxml/GestionElementosVista.fxml";
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    root = loader.load();
                    GestionElementosController controlador = loader.getController();
                    controlador.setAdministrador(administrador);
                    controlador.setRuta(2);
                    break;
                case 2: // alumno
                    fxml = "/fxml/GestionAlumnosVista.fxml";
                    FXMLLoader loaderAlum = new FXMLLoader(getClass().getResource(fxml));
                    root = loaderAlum.load();
                    GestionAlumnosController controladorAlum = loaderAlum.getController();
                    controladorAlum.setAdministrador(administrador);
                    break;
                case 3: // grupo
                    fxml = "/fxml/GestionGruposVista.fxml";
                    FXMLLoader loaderGrupos = new FXMLLoader(getClass().getResource(fxml));
                    root = loaderGrupos.load();
                    GestionGruposController controladorGrupos = loaderGrupos.getController();
                    controladorGrupos.setAdministrador(administrador);
                    break;
                case 4: // asignatura
                    fxml = "/fxml/GestionAsignaturasVista.fxml";
                    FXMLLoader loaderAsig = new FXMLLoader(getClass().getResource(fxml));
                    root = loaderAsig.load();
                    GestionAsignaturasController controladorAsig = loaderAsig.getController();
                    controladorAsig.setAdministrador(administrador);
                    break;
                default:
                    break;
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED.UNDECORATED);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(RegisterControlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAdministrador(Administrador usuario) {
        this.administrador = usuario;
        lblTitulo.setText("ADMINISTRADOR: " + this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
        lblPanelLateral.setText(this.administrador.getNombre() + " " + this.administrador.getPrimerApellido() + " " + this.administrador.getSegundoApellido());
    }

    private void escribirEnNuevaAsignatura(KeyEvent event) {

    }

    @FXML
    private void escribirEnNuevaAsignatura(ActionEvent event) {
        cmbAsignatura.setPromptText("Seleccionar");
    }

}
