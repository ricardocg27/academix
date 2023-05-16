package com.ric.academix.dao;

import java.util.List;

import com.ric.academix.modelo.Profesor;



public interface ProfesorDao {

	List<Profesor> consultarTodos();
	Profesor consultar(int id);
        Profesor consultarPorEmailYContrasena(String email, String contrasegna);
	void insertar(Profesor profesor);
	void actualizar(Profesor profesor);
	boolean eliminar(int codigo);
	List<Profesor> consultarProfesorTutor();
	Profesor consultarProfesorAsignatura(int codigo);
	
}
