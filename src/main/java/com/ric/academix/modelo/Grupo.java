/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.modelo;

import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Profesor;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ricar
 */
@Entity
@Table(name = "grupo")
@NamedQueries({
    @NamedQuery(name = "Grupo_1.findAll", query = "SELECT g FROM Grupo_1 g"),
    @NamedQuery(name = "Grupo_1.findById", query = "SELECT g FROM Grupo_1 g WHERE g.id = :id"),
    @NamedQuery(name = "Grupo_1.findByCurso", query = "SELECT g FROM Grupo_1 g WHERE g.curso = :curso"),
    @NamedQuery(name = "Grupo_1.findByLetra", query = "SELECT g FROM Grupo_1 g WHERE g.letra = :letra")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "curso")
    private String curso;
    @Basic(optional = false)
    @Column(name = "letra")
    private int letra;
    @JoinColumn(name = "tutor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profesor tutorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupoId")
    private Collection<Alumno> alumnoCollection;

    public Grupo() {
    }

    public Grupo(Integer id) {
        this.id = id;
    }

    public Grupo(Integer id, String curso, int letra) {
        this.id = id;
        this.curso = curso;
        this.letra = letra;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getLetra() {
        return letra;
    }

    public void setLetra(int letra) {
        this.letra = letra;
    }

    public Profesor getTutorId() {
        return tutorId;
    }

    public void setTutorId(Profesor tutorId) {
        this.tutorId = tutorId;
    }

    public Collection<Alumno> getAlumnoCollection() {
        return alumnoCollection;
    }

    public void setAlumnoCollection(Collection<Alumno> alumnoCollection) {
        this.alumnoCollection = alumnoCollection;
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
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ric.academix.modelo.Grupo_1[ id=" + id + " ]";
    }
    
}