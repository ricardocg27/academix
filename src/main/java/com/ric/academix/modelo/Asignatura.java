package com.ric.academix.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the asignatura database table.
 * 
 */
@Entity
@NamedQuery(name="Asignatura.findAll", query="SELECT a FROM Asignatura a")
public class Asignatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nombre;

	//bi-directional many-to-one association to Examen
	@OneToMany(mappedBy="asignatura")
	private List<Examen> examens;

	//bi-directional many-to-one association to ProfesorAsignatura
	@OneToMany(mappedBy="asignatura")
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

	public List<Examen> getExamens() {
		return this.examens;
	}

	public void setExamens(List<Examen> examens) {
		this.examens = examens;
	}

	public Examen addExamen(Examen examen) {
		getExamens().add(examen);
		examen.setAsignatura(this);

		return examen;
	}

	public Examen removeExamen(Examen examen) {
		getExamens().remove(examen);
		examen.setAsignatura(null);

		return examen;
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