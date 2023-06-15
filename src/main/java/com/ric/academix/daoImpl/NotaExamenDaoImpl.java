/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.NotaExamenDao;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Examen;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.NotaExamen;
import com.ric.academix.modelo.NotaTareaEvaluable;
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
public class NotaExamenDaoImpl implements NotaExamenDao {

    private static final String PERSISTENCE = "persistence";
    private static final NotaExamenDaoImpl instance;

    static {
        instance = new NotaExamenDaoImpl();
    }

    public static NotaExamenDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void crearCasillasNotasPorExamenYGrupo(Examen examenCreado, List<Alumno> listaAlumnos) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();

            for (Alumno alumno : listaAlumnos) {
                NotaExamen notaExamen = new NotaExamen();
                notaExamen.setExamenId(examenCreado);
                notaExamen.setAlumnoId(alumno);
                notaExamen.setPuntuacion(null);

                em.persist(notaExamen);
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

    } // final crearCasillas

    @Override
    public List<NotaExamen> consultarPorGrupoYAsignatura(Grupo grupo, Asignatura asignatura) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<NotaExamen> notaExamenes = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT ne "
                    + " FROM NotaExamen ne "
                    + " INNER JOIN Examen exam  ON (ne.examenId.id = exam.id) "
                    + " WHERE exam.grupoId.id = :v_grupoId "
                    + " AND exam.asignaturaId.id = :v_asignaturaId";
            Query query = em.createQuery(hql);
            query.setParameter("v_grupoId", grupo.getId());
            query.setParameter("v_asignaturaId", asignatura.getId());

            notaExamenes = query.getResultList();

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

        return notaExamenes;
    }

    @Override
    public void eliminarNota(NotaExamen notaExamen) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();
            notaExamen.setPuntuacion(null);
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
    public void insertarNota(NotaExamen notaExamen) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        NotaExamen notaExamenOld;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            notaExamenOld = em.find(NotaExamen.class, notaExamen.getId());

            if (notaExamenOld != null) {
                ts.begin();

                notaExamenOld.setPuntuacion(notaExamen.getPuntuacion());

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
    public void eliminarNotasPorExamen(Examen examen) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();
            String hql = "DELETE FROM NotaExamen ne "
                    + " WHERE ne.examenId.id = :v_id ";
                   
            Query query = em.createQuery(hql);
            query.setParameter("v_id", examen.getId());

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
