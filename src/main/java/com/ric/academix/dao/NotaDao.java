/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dao;

/**
 *
 * @author ricar
 */
public interface NotaDao {
    
    void eliminar(int idNota);
    void eliminarPorAlumno(int idAlumno);
    
}
