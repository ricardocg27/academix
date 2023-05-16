/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dao;

import com.ric.academix.modelo.Alumno;

/**
 *
 * @author ricar
 */
public interface AlumnoDao {
    
    
    Alumno consultarPorEmailYContrasena(String email, String contrasegna);
    void insertar(Alumno alumno);
    
}
