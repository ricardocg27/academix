package com.ric.academix.dao;

import com.ric.academix.modelo.Asignatura;
import java.util.List;


public interface AsignaturaDao {

	List<Asignatura> consultarTodos();
	Asignatura consultar(int id);
	void insertar(Asignatura asignatura);
	void actualizar(int id);
	boolean eliminar(int id);
	List<Asignatura> consultarAsignaturaEnExamen(int codigo);
	List<Asignatura> consultarAsignaturaProfesor(int codigo);
        public Asignatura consultarPorNombre(String nombreAsignatura);

    
}
