package com.ric.academix.daoImpl;

import java.util.List;

import com.ric.academix.dao.ProfesorAsignaturaDao;
import com.ric.academix.modelo.ProfesorAsignatura;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


public class ProfesorAsignaturaDaoImpl implements ProfesorAsignaturaDao {
	
	private static final String PERSISTENCE = "persistence";
	private static ProfesorAsignaturaDaoImpl instance;
	
	static {
		instance = new ProfesorAsignaturaDaoImpl();
	}
	
	public static ProfesorAsignaturaDaoImpl getInstance() {
		return instance;
	}
	

	public ProfesorAsignaturaDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ProfesorAsignatura> consultarTodos() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		List<ProfesorAsignatura> listaProfesorAsignatura;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			listaProfesorAsignatura = em.createQuery("SELECT pa FROM ProfesorAsignatura pa", 
					ProfesorAsignatura.class).getResultList();
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return listaProfesorAsignatura;
	}

	@Override
	public ProfesorAsignatura consultar(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertar(ProfesorAsignatura profesorAsignatura) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction ts = null;

		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			ts = em.getTransaction();
			ts.begin();
			em.persist(profesorAsignatura);
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
	public void actualizar(ProfesorAsignatura profesorAsignatura) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean eliminar(int codigo) {
		// TODO Auto-generated method stub
		return false;
	}

}
