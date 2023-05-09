package com.ric.academix.dao;

import java.util.List;

import com.ric.academix.modelo.Profesor;



public interface ProfesorDao {

	List<Profesor> consultarTodos();
	Profesor consultar(int id);
	void insertar(Profesor profesor);
	void actualizar(Profesor profesor);
	boolean eliminar(int codigo);
	List<Profesor> consultarProfesorTutor();
	Profesor consultarProfesorAsignatura(int codigo);
	
}
