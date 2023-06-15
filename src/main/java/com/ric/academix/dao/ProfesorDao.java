package com.ric.academix.dao;

import com.ric.academix.modelo.Profesor;
import java.util.List;
import javafx.scene.control.PasswordField;




public interface ProfesorDao {

	List<Profesor> consultarTodos();
	Profesor consultar(int id);
        Profesor consultarPorEmailYContrasena(String email, String contrasegna);
	void insertar(Profesor profesor);
	boolean actualizar(Profesor profesor);
        boolean actualizarDatosUsuario(Profesor profesor);
	boolean eliminar(int codigo);
	List<Profesor> consultarProfesoresSonTutores();
	Profesor consultarProfesorAsignatura(int codigo);
	int consultarIdPorNombreYApellidos(List<String> nombres);
        Profesor consultarTutor(Integer id);
        Profesor consultarPorContrasena(Profesor prof, PasswordField txtOldPass);
        boolean actualizarContrasena(Profesor prof, PasswordField txtNewPass);
        
       
}
