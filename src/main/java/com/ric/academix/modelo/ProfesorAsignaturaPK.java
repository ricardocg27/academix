package com.ric.academix.modelo;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the profesor_asignatura database table.
 * 
 */
@Embeddable
public class ProfesorAsignaturaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_profesor", insertable=false, updatable=false)
	private int idProfesor;

	@Column(name="id_asignatura", insertable=false, updatable=false)
	private int idAsignatura;

	public ProfesorAsignaturaPK() {
	}
	
	public ProfesorAsignaturaPK(int idProfesor, int idAsignatura) {
		this.idProfesor = idProfesor;
		this.idAsignatura = idAsignatura;
	}




	public int getIdProfesor() {
		return this.idProfesor;
	}

	public void setIdProfesor(int idProfesor) {
		this.idProfesor = idProfesor;
	}
	public int getIdAsignatura() {
		return this.idAsignatura;
	}
	public void setIdAsignatura(int idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProfesorAsignaturaPK)) {
			return false;
		}
		ProfesorAsignaturaPK castOther = (ProfesorAsignaturaPK)other;
		return 
			(this.idProfesor == castOther.idProfesor)
			&& (this.idAsignatura == castOther.idAsignatura);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idProfesor;
		hash = hash * prime + this.idAsignatura;
		
		return hash;
	}
}