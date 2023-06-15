/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.TareaEvaluableDao;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Asignatura;
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
public class TareaEvaluableDaoImpl implements TareaEvaluableDao {

    private static final String PERSISTENCE = "persistence";
    private static final TareaEvaluableDaoImpl instance;

    static {
        instance = new TareaEvaluableDaoImpl();
    }

    public static TareaEvaluableDaoImpl getInstance() {
        return instance;
    }

    @Override
    public TareaEvaluable consultarPorNombreAsignaturaYGrupo(String nombre, Asignatura asignatura, Grupo grupo) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        TareaEvaluable tareaEvaluable = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT tae "
                    + " FROM TareaEvaluable tae "
                    + " WHERE tae.nombre = :v_nombre "
                    + " AND tae.asignaturaId.id = :v_asignaturaId"
                    + " AND tae.grupoId.id = :v_grupoId";
            Query query = em.createQuery(hql);
            query.setParameter("v_nombre", nombre.trim());
            query.setParameter("v_asignaturaId", asignatura.getId());
            query.setParameter("v_grupoId", grupo.getId());
            tareaEvaluable = (TareaEvaluable) query.getSingleResult();

        } catch (NoResultException e) {
            tareaEvaluable = null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }

        return tareaEvaluable;

    }

    @Override
    public List<TareaEvaluable> consultarPorAsignaturaYGrupo(Asignatura asignatura, Grupo grupo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<TareaEvaluable> listaTareasEvaluables;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT te "
                    + " FROM TareaEvaluable te "
                    + " WHERE te.asignaturaId.id = :v_asignaturaId "
                    + " AND te.grupoId.id = :v_grupoId";
            Query query = em.createQuery(hql);
            query.setParameter("v_asignaturaId", asignatura.getId());
            query.setParameter("v_grupoId", grupo.getId());
            listaTareasEvaluables = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaTareasEvaluables;

    }

    @Override
    public void insertar(TareaEvaluable tareaEvaluable) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();
            em.persist(tareaEvaluable);
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
    public List<TareaEvaluable> consultarTodos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<TareaEvaluable> listaTareasEvaluables;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT te "
                    + " FROM TareaEvaluable te ";
            Query query = em.createQuery(hql);
            listaTareasEvaluables = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaTareasEvaluables;
    }

    @Override
    public boolean eliminar(TareaEvaluable tareaEvaluable) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        boolean eliminado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            tareaEvaluable = em.find(TareaEvaluable.class, tareaEvaluable.getId());
            if (tareaEvaluable != null) {
                ts = em.getTransaction();
                ts.begin();
                em.remove(tareaEvaluable);
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
    public boolean actualizar(TareaEvaluable tareaEvaluable1Actualizada) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        TareaEvaluable tareaEvaluableAntigua = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            tareaEvaluableAntigua = em.find(TareaEvaluable.class, tareaEvaluable1Actualizada.getId());

            if (tareaEvaluableAntigua != null) {
                ts = em.getTransaction();
                ts.begin();
                tareaEvaluableAntigua.setNombre(tareaEvaluable1Actualizada.getNombre());
                tareaEvaluableAntigua.setAsignaturaId(tareaEvaluable1Actualizada.getAsignaturaId());
                tareaEvaluableAntigua.setGrupoId(tareaEvaluable1Actualizada.getGrupoId());
                tareaEvaluableAntigua.setPorcentaje(tareaEvaluable1Actualizada.getPorcentaje());
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
