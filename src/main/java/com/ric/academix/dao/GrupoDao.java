package com.ric.academix.dao;

import java.util.List;

import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;



public interface GrupoDao {

	List<Grupo> consultarTodos();
	Grupo consultar(int id);
	Grupo consultarTutorGrupo(int tutorId);
	void insertar(Grupo grupo);
	void actualizar(Grupo grupo);
	boolean eliminar(int codigo);
	List<Grupo> consultarGrupoConAlumnos();
        List<String> consultarCursos(); 
        List<String> consultarLetrasGrupos();
        int eliminarTutor(Integer id);
        Grupo consultarGrupoPorCursoYLetra(String curso, String letra);
        void asignarTutor(int grupoId, int profId);
        Long consultarAlumnosPorGrupo(Grupo grupo);
       
        
	
}
