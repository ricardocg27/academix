/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dao;

import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Examen;
import com.ric.academix.modelo.Grupo;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface ExamenDao {

    Examen consultarPorNombreAsignaturaYGrupo(String nombre, Asignatura asignatura, Grupo grupo);
    List<Examen> consultarPorAsignaturaYGrupo(Asignatura asignatura, Grupo grupo);
    void insertar(Examen examen);
    List<Examen> consultarTodos();
    boolean eliminar(Examen examen);
    boolean actualizar(Examen examenActualizado);
    
}
