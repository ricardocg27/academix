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

    private final String codigoAdmin = "Ad5555*";
    private final String codigoProfesor = "Prof555*";
    private final String codigoAlumno = "Alum5555*";
    
    private static final int codigoTipoAdmin = 1, codigoTipoProfesor = 2, codigoTipoAlumno = 3;
    

    public String getCodigoAdmin() {
        return codigoAdmin;
    }

    public String getCodigoProfesor() {
        return codigoProfesor;
    }

    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    public int getCodigoTipoAdmin() {
        return codigoTipoAdmin;
    }

    public int getCodigoTipoProfesor() {
        return codigoTipoProfesor;
    }

    public int getCodigoTipoAlumno() {
        return codigoTipoAlumno;
    }
    
    
    
    
}
