/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.dto;

/**
 *
 * @author ricar
 */
public class NotaAlumnoDTO {

    private String nombreAlumno;
    private String primerApellido;
    private String segundoApellido;
    private String nombreExamen;
    private int porcentajeExamen;
    private Double puntuacionExamen;
    private String nombreTarea;
    private int porcentajeTarea;
    private Double puntuacionTarea;
    private Double notaFinal;
    private int grupoId;
    private int asignaturaId;

    public NotaAlumnoDTO() {
    }

    @Override
    public String toString() {
        return "NotaAlumnoDTO{" + "nombreAlumno=" + nombreAlumno + ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", nombreExamen=" + nombreExamen + ", porcentajeExamen=" + porcentajeExamen + ", puntuacionExamen=" + puntuacionExamen + ", nombreTarea=" + nombreTarea + ", porcentajeTarea=" + porcentajeTarea + ", puntuacionTarea=" + puntuacionTarea + ", notaFinal=" + notaFinal + ", grupoId=" + grupoId + ", asignaturaId=" + asignaturaId + '}';
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }

    public int getPorcentajeExamen() {
        return porcentajeExamen;
    }

    public void setPorcentajeExamen(int porcentajeExamen) {
        this.porcentajeExamen = porcentajeExamen;
    }

    public Double getPuntuacionExamen() {
        return puntuacionExamen;
    }

    public void setPuntuacionExamen(Double puntuacionExamen) {
        this.puntuacionExamen = puntuacionExamen;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public int getPorcentajeTarea() {
        return porcentajeTarea;
    }

    public void setPorcentajeTarea(int porcentajeTarea) {
        this.porcentajeTarea = porcentajeTarea;
    }

    public Double getPuntuacionTarea() {
        return puntuacionTarea;
    }

    public void setPuntuacionTarea(Double puntuacionTarea) {
        this.puntuacionTarea = puntuacionTarea;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }

    public int getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

}
