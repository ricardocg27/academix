/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ricar
 */
@Embeddable
public class AsignaturaGrupoPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "asignatura_id")
    private Long asignaturaId;

    @Column(name = "grupo_id")
    private Long grupoId;

    public AsignaturaGrupoPK() {
    }

    public Long getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Long asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.asignaturaId);
        hash = 89 * hash + Objects.hashCode(this.grupoId);
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
        final AsignaturaGrupoPK other = (AsignaturaGrupoPK) obj;
        if (!Objects.equals(this.asignaturaId, other.asignaturaId)) {
            return false;
        }
        if (!Objects.equals(this.grupoId, other.grupoId)) {
            return false;
        }
        return true;
    }
    
    
}
