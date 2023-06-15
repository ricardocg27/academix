/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dao;

import com.ric.academix.dto.NotaAlumnoDTO;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Grupo;
import java.util.List;

/**
 *
 * @author ricar
 */
public interface NotasAlumnosDao {

    List<NotaAlumnoDTO> consultarNotasPorGrupoYAsignatura(Grupo grupo, Asignatura asignatura);
    
}
