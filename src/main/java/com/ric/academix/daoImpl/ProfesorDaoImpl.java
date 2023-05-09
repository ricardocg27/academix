package com.ric.academix.daoImpl;

import java.util.List;

import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.modelo.Profesor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


public class ProfesorDaoImpl implements ProfesorDao{

	private static final String PERSISTENCE = "persistence";
	private static ProfesorDaoImpl instance;

	static {
		instance = new ProfesorDaoImpl();
	}
	public static ProfesorDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<Profesor> consultarTodos() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		List<Profesor> listaProfesores;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			listaProfesores = em.createQuery("SELECT p FROM Profesor p", Profesor.class).getResultList();
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return listaProfesores;
	}

	@Override
	public Profesor consultar(int id) {

		EntityManagerFactory emf = null;
		EntityManager em = null;
		Profesor profesor;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			profesor = em.find(Profesor.class, id);
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return profesor;

	}


	@Override
	public void insertar(Profesor profesor) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction ts = null;

		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			ts = em.getTransaction();
			ts.begin();

			em.persist(profesor);
			ts.commit();

		}
		catch (Exception e) {
			if (ts != null && ts.isActive()) {
				ts.rollback();
			}
			throw e;
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
	}


	@Override
	public void actualizar(Profesor profesor) {

	}

	@Override
	public boolean eliminar(int  codigo) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction ts = null;
		Profesor profesor = null;
		boolean eliminado = false;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			profesor = em.find(Profesor.class, codigo);
			if (profesor != null) {
				ts = em.getTransaction();
				ts.begin();
				em.remove(profesor);
				ts.commit();
				eliminado = true;
			}
		}
		catch (Exception e) {
			if (ts != null && ts.isActive()) {
				ts.rollback();
			}
			throw e;
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return eliminado;
	}

	@Override
	public List<Profesor> consultarProfesorTutor() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		List<Profesor> listaProfesores;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			String consulta = "SELECT p FROM Profesor p "
					+ "WHERE p.id IN (SELECT g.tutorId FROM Grupo g)";
			listaProfesores = em.createQuery(consulta, Profesor.class).getResultList();
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return listaProfesores;
	}

	@Override
	public Profesor consultarProfesorAsignatura(int codigo) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		Profesor profesor = null;
		List<Profesor> listaProfesores;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			String consulta = "SELECT p FROM Profesor p "
					+ "WHERE p.id IN (SELECT pa.id.idProfesor\r\n"
					+ "			   FROM ProfesorAsignatura pa\r\n"
					+ "			   WHERE pa.id.idAsignatura = " + codigo 
					+ "			   )\r\n"
					+ "ORDER BY p.id" ;
			System.out.println(consulta);
			listaProfesores = em.createQuery(consulta, Profesor.class).getResultList();
			if (listaProfesores.size() > 0) {
				profesor = listaProfesores.get(0);
			}
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return profesor;
	}

}









