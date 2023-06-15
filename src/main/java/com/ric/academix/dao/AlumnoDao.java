/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dao;

import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Grupo;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface AlumnoDao {
    
    List<Alumno> consultarTodos();
    Alumno consultarPorEmailYContrasena(String email, String contrasegna);
    void insertar(Alumno alumno);
    boolean actualizar(Alumno alumno);
    boolean eliminar(Alumno alumno);
    List<Alumno> consultarPorGrupo(Grupo grupo);
    int eliminarGrupo(Grupo grupo);
    Alumno consultarPorNombreYApellidos(List<String> nombres);

   
    
}
