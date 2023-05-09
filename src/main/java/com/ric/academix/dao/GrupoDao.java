package com.ric.academix.dao;

import java.util.List;

import com.ric.academix.modelo.Grupo;



public interface GrupoDao {

	List<Grupo> consultarTodos();
	Grupo consultar(int id);
	Grupo consultarTutorGrupo(int tutorId);
	void insertar(Grupo grupo);
	void actualizar(Grupo grupo);
	boolean eliminar(int codigo);
	List<Grupo> consultarGrupoConAlumnos();
	
}
