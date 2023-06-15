package com.ric.academix.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import java.util.Collection;
import java.util.List;

/**
 * The persistent class for the asignatura database table.
 *
 */
@Entity
@NamedQuery(name = "Asignatura.findAll", query = "SELECT a FROM Asignatura a")
public class Asignatura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturaId")
    private Collection<Examen> examenCollection;
    @OneToMany(mappedBy = "asignaturaId")
    private Collection<TareaEvaluable> tareaEvaluableCollection;

    //bi-directional many-to-one association to ProfesorAsignatura
    @OneToMany(mappedBy = "asignatura")
    private List<ProfesorAsignatura> profesorAsignaturas;

    public Asignatura() {
    }

    public Asignatura(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<ProfesorAsignatura> getProfesorAsignaturas() {
        return this.profesorAsignaturas;
    }

    public void setProfesorAsignaturas(List<ProfesorAsignatura> profesorAsignaturas) {
        this.profesorAsignaturas = profesorAsignaturas;
    }

    public ProfesorAsignatura addProfesorAsignatura(ProfesorAsignatura profesorAsignatura) {
        getProfesorAsignaturas().add(profesorAsignatura);
        profesorAsignatura.setAsignatura(this);

        return profesorAsignatura;
    }

    public ProfesorAsignatura removeProfesorAsignatura(ProfesorAsignatura profesorAsignatura) {
        getProfesorAsignaturas().remove(profesorAsignatura);
        profesorAsignatura.setAsignatura(null);

        return profesorAsignatura;
    }

}
