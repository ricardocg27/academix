package com.ric.academix.dao;

import java.util.List;
import com.ric.academix.modelo.ProfesorAsignatura;


public interface ProfesorAsignaturaDao {

	List<ProfesorAsignatura> consultarTodos();
	ProfesorAsignatura consultar(int id);
	void insertar(ProfesorAsignatura profesorAsignatura);
	void actualizar(ProfesorAsignatura profesorAsignatura);
	boolean eliminar(int codigo);
	
}
