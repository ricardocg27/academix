/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ricar
 */
@Entity
@Table(name = "asignatura_grupo")
public class AsignaturaGrupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AsignaturaGrupoPK id;

    @ManyToOne
    @JoinColumn(name = "asignatura_id", updatable = false, insertable = false)
    private Asignatura asignatura;

    @ManyToOne
    @JoinColumn(name = "grupo_id", updatable = false, insertable = false)
    private Grupo grupo;

    public AsignaturaGrupo() {
    }

    public AsignaturaGrupoPK getId() {
        return id;
    }

    public void setId(AsignaturaGrupoPK id) {
        this.id = id;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.asignatura);
        hash = 29 * hash + Objects.hashCode(this.grupo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AsignaturaGrupo other = (AsignaturaGrupo) obj;
        return true;
    }

    @Override
    public String toString() {
        return "AsignaturaGrupo{" + "id=" + id + ", asignatura=" + asignatura.toString() + ", grupo=" + grupo.toString() + '}';
    }

}
