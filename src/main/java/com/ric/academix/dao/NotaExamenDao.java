/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dao;

import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Examen;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.NotaExamen;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface NotaExamenDao {

    void crearCasillasNotasPorExamenYGrupo(Examen examenCreado, List<Alumno> listaAlumnos);
    List<NotaExamen> consultarPorGrupoYAsignatura(Grupo grupo, Asignatura asignatura);
    void eliminarNota(NotaExamen notaExamen);
    void insertarNota(NotaExamen notaExamen);

    void eliminarNotasPorExamen(Examen examen);

   
}
