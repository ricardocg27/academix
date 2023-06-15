/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dao;

import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.CuadernoProfesor;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface CuadernoProfesorDao {

    void insertar(CuadernoProfesor cuadernoProfesor);
    List<CuadernoProfesor> consultarTodos();
    List<CuadernoProfesor> consultarRegistrosPorFechas(String fechaInicio, String fechaFin);
    List<CuadernoProfesor> consultarRegistroPorAlumnoYFechas(Alumno alumno, String fechaInicioStr, String fechaFinStr);
    CuadernoProfesor consultarRegistroAlumnoExistente(Alumno alumno, String fechaFormateada);
    void eliminarRegistroPorAlumnoYFecha(CuadernoProfesor cuadernoProfesor);
    boolean actualizar(CuadernoProfesor cuadernoProfesor);
    
}
