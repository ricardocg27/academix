package com.ric.academix.dao;

import java.util.List;
import com.ric.academix.modelo.ProfesorAsignatura;
import com.ric.academix.modelo.ProfesorAsignaturaPK;


public interface ProfesorAsignaturaDao {

	List<ProfesorAsignatura> consultarTodos();
	ProfesorAsignatura consultar(int id);
	void insertar(ProfesorAsignatura profesorAsignatura);
	boolean actualizar(ProfesorAsignatura profesorAsignaturaNuevo, ProfesorAsignatura profesorAsignaturaAntiguo);
	boolean eliminar(int codigo);
        public int  eliminarProfesoryAsignaturasAsociadas(Integer id);
        ProfesorAsignatura consultarPorProfesorYAsignatura(ProfesorAsignaturaPK nuevo);
	
}
