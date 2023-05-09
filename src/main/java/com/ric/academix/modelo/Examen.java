package com.ric.academix.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the examen database table.
 * 
 */
@Entity
@NamedQuery(name="Examen.findAll", query="SELECT e FROM Examen e")
public class Examen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String fecha;

	private String nombre;

	//bi-directional many-to-one association to Asignatura
	@ManyToOne
	private Asignatura asignatura;

	//bi-directional many-to-one association to Nota
	@OneToMany(mappedBy="examen")
	private List<Nota> notas;

	public Examen() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Asignatura getAsignatura() {
		return this.asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public List<Nota> getNotas() {
		return this.notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public Nota addNota(Nota nota) {
		getNotas().add(nota);
		nota.setExamen(this);

		return nota;
	}

	public Nota removeNota(Nota nota) {
		getNotas().remove(nota);
		nota.setExamen(null);

		return nota;
	}

}