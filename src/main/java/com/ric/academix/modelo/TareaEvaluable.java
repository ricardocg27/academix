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
@Table(name = "tarea_evaluable")
public class TareaEvaluable implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "porcentaje")
    private Integer porcentaje;
    @JoinColumn(name = "asignatura_id", referencedColumnName = "id")
    @ManyToOne
    private Asignatura asignaturaId;
    @JoinColumn(name = "grupo_id", referencedColumnName = "id")
    @ManyToOne
    private Grupo grupoId;
    @Column(name = "nombre")
    private String nombre;

    public TareaEvaluable() {
    }

    public TareaEvaluable(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Asignatura getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Asignatura asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public Grupo getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Grupo grupoId) {
        this.grupoId = grupoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if (!(object instanceof TareaEvaluable)) {
            return false;
        }
        TareaEvaluable other = (TareaEvaluable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ric.academix.modelo.TareaEvaluable[ id=" + id + " ]";
    }

}
