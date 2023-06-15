/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.NotaTareaDao;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Examen;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.NotaExamen;
import com.ric.academix.modelo.NotaTareaEvaluable;
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
public class NotaTareaDaoImpl implements NotaTareaDao {

    private static final String PERSISTENCE = "persistence";
    private static final NotaTareaDaoImpl instance;

    static {
        instance = new NotaTareaDaoImpl();
    }

    public static NotaTareaDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<NotaTareaEvaluable> consultarPorGrupoYAsignatura(Grupo grupo, Asignatura asignatura) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<NotaTareaEvaluable> notaTareas = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT nte "
                    + " FROM NotaTareaEvaluable nte "
                    + " INNER JOIN TareaEvaluable te  ON(nte.tareaEvaluableId.id = te.id) "
                    + " WHERE te.grupoId.id = :v_grupoId "
                    + " AND te.asignaturaId.id = :v_asignaturaId";
            Query query = em.createQuery(hql);
            query.setParameter("v_grupoId", grupo.getId());
            query.setParameter("v_asignaturaId", asignatura.getId());

            notaTareas = query.getResultList();

        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }

        return notaTareas;

    }

    @Override
    public void crearCasillasNotasPorTareasYGrupo(TareaEvaluable tareaEvaluableCreada, List<Alumno> listaAlumnos) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();

            for (Alumno alumno : listaAlumnos) {
                NotaTareaEvaluable notaTareaEvaluable = new NotaTareaEvaluable();
                notaTareaEvaluable.setTareaEvaluableId(tareaEvaluableCreada);
                notaTareaEvaluable.setAlumnoId(alumno);
                notaTareaEvaluable.setPuntuacion(null);

                em.persist(notaTareaEvaluable);
            }

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
    public void eliminarNota(NotaTareaEvaluable notaTareaEvaluable) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();
            notaTareaEvaluable.setPuntuacion(null);
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
    public void insertarNota(NotaTareaEvaluable notaTareaEvaluable) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        NotaTareaEvaluable notaTareaOld;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            notaTareaOld = em.find(NotaTareaEvaluable.class, notaTareaEvaluable.getId());

            if (notaTareaOld != null) {
                ts.begin();

                notaTareaOld.setPuntuacion(notaTareaEvaluable.getPuntuacion());

                ts.commit();
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

    }

    @Override
    public void eliminarNotasPorTarea(TareaEvaluable tareaEvaluable) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();
            String hql = "DELETE FROM NotaTareaEvaluable nte "
                    + " WHERE nte.tareaEvaluableId.id = :v_id ";
            Query query = em.createQuery(hql);
            query.setParameter("v_id", tareaEvaluable.getId());

            query.executeUpdate();
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


}
