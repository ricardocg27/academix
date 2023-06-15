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
import com.ric.academix.modelo.NotaTareaEvaluable;
import com.ric.academix.modelo.TareaEvaluable;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface NotaTareaDao {

    List<NotaTareaEvaluable> consultarPorGrupoYAsignatura(Grupo grupo, Asignatura asignatura);
    void crearCasillasNotasPorTareasYGrupo(TareaEvaluable tareaEvaluableCreada, List<Alumno> listaAlumnos);
    void eliminarNota(NotaTareaEvaluable notaTareaEvaluable);
    void insertarNota(NotaTareaEvaluable notaTareaEvaluable);

    void eliminarNotasPorTarea(TareaEvaluable tareaEvaluable);

}
