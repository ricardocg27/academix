package com.ric.academix.modelo;

import java.io.Serializable;


import jakarta.persistence.*;
import jakarta.persistence.NamedQuery;


/**
 * The persistent class for the nota database table.
 * 
 */
@Entity
@NamedQuery(name="Nota.findAll", query="SELECT n FROM Nota n")
public class Nota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String valor;

	//bi-directional many-to-one association to Alumno
	@ManyToOne
	private Alumno alumno;

	//bi-directional many-to-one association to Examen
	@ManyToOne
	private Examen examen;

	public Nota() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Alumno getAlumno() {
		return this.alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Examen getExamen() {
		return this.examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

}