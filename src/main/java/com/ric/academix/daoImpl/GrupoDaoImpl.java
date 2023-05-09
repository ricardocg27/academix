package com.ric.academix.daoImpl;

import java.util.List;

import com.ric.academix.dao.GrupoDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import com.ric.academix.modelo.Grupo;


public class GrupoDaoImpl implements GrupoDao {
	
	private static final String PERSISTENCE = "persistence";
	private static GrupoDaoImpl instance;
	
	static {
		instance = new GrupoDaoImpl();
	}
	
	public static GrupoDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<Grupo> consultarTodos() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		List<Grupo> listaGrupos;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			listaGrupos = em.createQuery("SELECT g FROM Grupo g", Grupo.class).getResultList();
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return listaGrupos;
	}

	@Override
	public Grupo consultar(int id) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		Grupo grupo;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			grupo = em.find(Grupo.class, id);
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return grupo;
	}

	@Override
	public void insertar(Grupo grupo) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction ts = null;
		
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			ts = em.getTransaction();
			ts.begin();
	
			em.persist(grupo);
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
	public void actualizar(Grupo grupo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean eliminar(int codigo) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction ts = null;
		Grupo grupo = null;
		boolean eliminado = false;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			grupo = em.find(Grupo.class, codigo);
			if (grupo != null) {
				ts = em.getTransaction();
				ts.begin();
				em.remove(grupo);
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
	public Grupo consultarTutorGrupo(int tutorId) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		Grupo grupo = null;
		List<Grupo> listaGrupos;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			String consulta = "SELECT g FROM Grupo g "
					+ "WHERE g.tutorId = " + tutorId;
			listaGrupos = em.createQuery(consulta, Grupo.class).getResultList();
			if (listaGrupos.size() > 0) {
				grupo = listaGrupos.get(0);
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
		return grupo;
	}

	@Override
	public List<Grupo> consultarGrupoConAlumnos() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		List<Grupo> listaGrupos;
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE);
			em = emf.createEntityManager(); 
			String consulta = "SELECT g FROM Grupo g "
							+ "WHERE g.id IN (SELECT a.grupoId FROM Alumno a)";
			listaGrupos = em.createQuery(consulta, Grupo.class).getResultList();
		}
		finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return listaGrupos;
	}

	
}










