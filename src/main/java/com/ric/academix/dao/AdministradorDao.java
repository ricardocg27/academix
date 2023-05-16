/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dao;

import com.ric.academix.modelo.Administrador;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface AdministradorDao {
    
        List<Administrador> consultarTodos();
	Administrador consultar(int id);
        Administrador consultarPorEmailYContrasena(String email, String contrasena);
	void insertar(Administrador administrador);
	void actualizar(int id);
	boolean eliminar(int id);
	
    
}
