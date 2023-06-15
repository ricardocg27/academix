/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.ExamenDao;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Examen;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.TareaEvaluable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author ricar
 */
public class ExamenDaoImpl implements ExamenDao {

    private static final String PERSISTENCE = "persistence";
    private static final ExamenDaoImpl instance;

    static {
        instance = new ExamenDaoImpl();
    }

    public static ExamenDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Examen consultarPorNombreAsignaturaYGrupo(String nombre, Asignatura asignatura, Grupo grupo) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        Examen examen = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT exam "
                    + " FROM Examen exam "
                    + " WHERE exam.nombre = :v_nombre "
                    + " AND exam.asignaturaId.id = :v_asignaturaId"
                    + " AND exam.grupoId.id = :v_grupoId";
            Query query = em.createQuery(hql);
            query.setParameter("v_nombre", nombre.trim());
            query.setParameter("v_asignaturaId", asignatura.getId());
            query.setParameter("v_grupoId", grupo.getId());
            examen = (Examen) query.getSingleResult();

        } catch (NoResultException e) {
            examen = null;
        }

        return examen;

    }

    @Override
    public List<Examen> consultarPorAsignaturaYGrupo(Asignatura asignatura, Grupo grupo) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Examen> listaExamenes;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT exam "
                    + " FROM Examen exam "
                    + " WHERE exam.asignaturaId.id = :v_asignaturaId "
                    + " AND exam.grupoId.id = :v_grupoId";
            Query query = em.createQuery(hql);
            query.setParameter("v_asignaturaId", asignatura.getId());
            query.setParameter("v_grupoId", grupo.getId());
            listaExamenes = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaExamenes;

    }

    @Override
    public void insertar(Examen examen) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();
            em.persist(examen);
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
    public List<Examen> consultarTodos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Examen> listaExamenes;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT exam "
                    + " FROM Examen exam ";
            Query query = em.createQuery(hql);
            listaExamenes = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaExamenes;

    }

    @Override
    public boolean eliminar(Examen examen) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        boolean eliminado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            examen = em.find(Examen.class, examen.getId());
            if (examen != null) {
                ts = em.getTransaction();
                ts.begin();
                em.remove(examen);
                ts.commit();
                eliminado = true;
            }
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
        return eliminado;

    }

    @Override
    public boolean actualizar(Examen examenActualizado) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        Examen examenAntiguo = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            examenAntiguo = em.find(Examen.class, examenActualizado.getId());

            if (examenAntiguo != null) {
                ts = em.getTransaction();
                ts.begin();
                examenAntiguo.setNombre(examenActualizado.getNombre());
                examenAntiguo.setAsignaturaId(examenActualizado.getAsignaturaId());
                examenAntiguo.setGrupoId(examenActualizado.getGrupoId());
                examenAntiguo.setPorcentaje(examenActualizado.getPorcentaje());
                ts.commit();
                actualizado = true;
            }
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
        return actualizado;

    }

}
