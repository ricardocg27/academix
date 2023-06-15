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
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;

/**
 *
 * @author ricar
 */
@Entity
@Table(name = "grupo")
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
    private String letra;
    @JoinColumn(name = "tutor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profesor tutorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupoId")
    private Collection<Alumno> alumnoCollection;
    @OneToMany(mappedBy = "grupoId")
    private Collection<Examen> examenCollection;
    @OneToMany(mappedBy = "grupoId")
    private Collection<TareaEvaluable> tareaEvaluableCollection;


    public Grupo() {
    }

    public Grupo(Integer id) {
        this.id = id;
    }

    public Grupo(Integer id, String curso, String letra) {
        this.id = id;
        this.curso = curso;
        this.letra = letra;
    }

    public Grupo(String curso, String letra, Profesor tutorId) {
        this.curso = curso;
        this.letra = letra;
        this.tutorId = tutorId;
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

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
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

    public Collection<Examen> getExamenCollection() {
        return examenCollection;
    }

    public void setExamenCollection(Collection<Examen> examenCollection) {
        this.examenCollection = examenCollection;
    }

    public Collection<TareaEvaluable> getTareaEvaluableCollection() {
        return tareaEvaluableCollection;
    }

    public void setTareaEvaluableCollection(Collection<TareaEvaluable> tareaEvaluableCollection) {
        this.tareaEvaluableCollection = tareaEvaluableCollection;
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
        return "Grupo{" + "id=" + id + ", curso=" + curso + ", letra=" + letra + ", tutorId=" + tutorId + '}';
    }

    public String dameGrupo() {

        return this.curso + " " + this.letra;
    }

}
