/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.modelo;

/**
 *
 * @author ricar
 */
public class CodigoUsuarios {

    private final String codigoValidacionAdmin = "Ad5555*";
    private final String codigoValidacionProfesor = "Prof555*";
    private final String codigoValidacionAlumno = "Alum5555*";
    
    private static final int CODIGO_TIPO_ADMIN = 1, CODIGO_TIPO_PROFESOR = 2, CODIGO_TIPO_ALUMNO = 3;
    

    public String getCodigoValidacionAdmin() {
        return codigoValidacionAdmin;
    }

    public String getCodigoValidacionProfesor() {
        return codigoValidacionProfesor;
    }

    public String getCodigoValidacionAlumno() {
        return codigoValidacionAlumno;
    }

    public int getCodigoTipoAdmin() {
        return CODIGO_TIPO_ADMIN;
    }

    public int getCodigoTipoProfesor() {
        return CODIGO_TIPO_PROFESOR;
    }

    public int getCodigoTipoAlumno() {
        return CODIGO_TIPO_ALUMNO;
    }
    
    
    
    
}
