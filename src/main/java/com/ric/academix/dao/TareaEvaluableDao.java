/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dao;

import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.TareaEvaluable;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface TareaEvaluableDao {

    TareaEvaluable consultarPorNombreAsignaturaYGrupo(String actividad, Asignatura asignatura, Grupo grupo);
    List<TareaEvaluable> consultarPorAsignaturaYGrupo(Asignatura asignatura, Grupo grupo);
    void insertar(TareaEvaluable tareaEvaluable);
    List<TareaEvaluable> consultarTodos();
    boolean eliminar(TareaEvaluable tareaEvaluable);

    public boolean actualizar(TareaEvaluable tareaEvaluable1Actualizada);

 
    
}
