/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.modelo;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author ricar
 */
@Entity
@Table(name = "cuaderno_profesor")
public class CuadernoProfesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha_insercion")
    private String fechaInsercion;
    @JoinColumn(name = "alumno_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Alumno alumnoId;
    @Column(name = "tareas_casa")
    private String tareasCasa;
    @Column(name = "participacion")
    private String participacion;
    @Column(name = "atencion")
    private String atencion;
    @Column(name = "tareas_clase")
    private String tareasClase;

    public CuadernoProfesor() {
    }

    public CuadernoProfesor(Integer id) {
        this.id = id;
    }

    public CuadernoProfesor(Integer id, String fechaInsercion) {
        this.id = id;
        this.fechaInsercion = fechaInsercion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(String fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getTareasCasa() {
        return tareasCasa;
    }

    public void setTareasCasa(String tareasCasa) {
        this.tareasCasa = tareasCasa;
    }

    public String getParticipacion() {
        return participacion;
    }

    public void setParticipacion(String participacion) {
        this.participacion = participacion;
    }

    public String getAtencion() {
        return atencion;
    }

    public void setAtencion(String atencion) {
        this.atencion = atencion;
    }

    public String getTareasClase() {
        return tareasClase;
    }

    public void setTareasClase(String tareasClase) {
        this.tareasClase = tareasClase;
    }

    public Alumno getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Alumno alumnoId) {
        this.alumnoId = alumnoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuadernoProfesor)) {
            return false;
        }
        CuadernoProfesor other = (CuadernoProfesor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CuadernoProfesor{" + "id=" + id
                + ", fechaInsercion=" + fechaInsercion
                + ", alumnoId=" + alumnoId.getNombreYApellidos()
                + ", tareasCasa=" + tareasCasa
                + ", participacion=" + participacion
                + ", atencion=" + atencion
                + ", tareasClase=" + tareasClase + '}';
    }
}
