package com.ric.academix.daoImpl;

import java.util.List;

import com.ric.academix.dao.AsignaturaDao;
import com.ric.academix.modelo.Asignatura;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;


public class AsignaturaDaoImpl implements AsignaturaDao {

    private static final String PERSISTENCE = "persistence";
    private static final AsignaturaDaoImpl instance;

    static {
        instance = new AsignaturaDaoImpl();
    }

    public static AsignaturaDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Asignatura> consultarTodos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Asignatura> listaAsignaturas;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            listaAsignaturas = em.createQuery("SELECT a FROM Asignatura a",
                    Asignatura.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaAsignaturas;
    }

    @Override
    public Asignatura consultar(int id) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Asignatura asignatura;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            asignatura = em.find(Asignatura.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return asignatura;
    }

    @Override
    public void insertar(Asignatura asignatura) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.persist(asignatura);
            ts.commit();
        } catch (Exception e) {
            if (ts != null && ts.isActive()) {
                ts.rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    @Override
    public void actualizar(int id) {

    }

    @Override
    public boolean eliminar(int codigo) {

        return false;
    }

    @Override
    public List<Asignatura> consultarAsignaturaEnExamen(int codigo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Asignatura> listaAsignaturas;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String consulta = "SELECT a FROM Asignatura a\r\n"
                    + "WHERE a.id = " + codigo
                    + " AND a.id IN (SELECT e.asignatura.id FROM Examen e)";
            listaAsignaturas = em.createQuery(consulta, Asignatura.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaAsignaturas;
    }

    @Override
    public List<Asignatura> consultarAsignaturaProfesor(int codigo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Asignatura> listaAsignaturas;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String consulta = "SELECT a FROM Asignatura a "
                    + "WHERE a.id = " + codigo
                    + " AND a.id IN (SELECT pa.id.idAsignatura "
                    + "			   FROM ProfesorAsignatura pa)";
            listaAsignaturas = em.createQuery(consulta, Asignatura.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaAsignaturas;
    }

    @Override
    public Asignatura consultarPorNombre(String nombreAsignatura) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Asignatura asignatura = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String hql = "SELECT asig "
                    + " FROM Asignatura asig "
                    + " WHERE asig.nombre = :v_nombreAsignatura";
            Query query = em.createQuery(hql);
            query.setParameter("v_nombreAsignatura", nombreAsignatura);
            asignatura = (Asignatura) query.getSingleResult();

        } catch (NoResultException e) {
            asignatura = null;
        }
        finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return asignatura;
    }

}
