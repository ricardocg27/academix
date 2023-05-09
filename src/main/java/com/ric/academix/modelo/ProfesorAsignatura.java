package com.ric.academix.modelo;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the profesor_asignatura database table.
 * 
 */
@Entity
@Table(name="profesor_asignatura")
public class ProfesorAsignatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProfesorAsignaturaPK id;

	@Column(name="agno_academico")
	private String agnoAcademico;

	//bi-directional many-to-one association to Asignatura
	@ManyToOne
	@JoinColumn(name="id_asignatura" , insertable=false, updatable=false)
	private Asignatura asignatura;

	//bi-directional many-to-one association to Profesor
	@ManyToOne
	@JoinColumn(name="id_profesor" , insertable=false, updatable=false)
	private Profesor profesor;

	public ProfesorAsignatura() {
	}
	
	public ProfesorAsignatura(ProfesorAsignaturaPK id) {
		this.id = id;
	}


	public ProfesorAsignaturaPK getId() {
		return this.id;
	}

	public void setId(ProfesorAsignaturaPK id) {
		this.id = id;
	}

	public String getAgnoAcademico() {
		return this.agnoAcademico;
	}

	public void setAgnoAcademico(String agnoAcademico) {
		this.agnoAcademico = agnoAcademico;
	}

	public Asignatura getAsignatura() {
		return this.asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public Profesor getProfesor() {
		return this.profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

}