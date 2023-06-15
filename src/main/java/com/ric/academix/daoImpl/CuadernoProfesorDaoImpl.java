/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.CuadernoProfesorDao;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.CuadernoProfesor;
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
public class CuadernoProfesorDaoImpl implements CuadernoProfesorDao {

    private static final String PERSISTENCE = "persistence";
    private static CuadernoProfesorDaoImpl instance;

    static {
        instance = new CuadernoProfesorDaoImpl();
    }

    public static CuadernoProfesorDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void insertar(CuadernoProfesor cuadernoProfesor) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.persist(cuadernoProfesor);
            ts.commit();

        } catch (Exception e) {
            if (ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
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
    public List<CuadernoProfesor> consultarTodos() {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<CuadernoProfesor> listaCuadernosProfesor;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT cp "
                    + " FROM CuadernoProfesor cp ";
            Query query = em.createQuery(hql);
            listaCuadernosProfesor = query.getResultList();

        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaCuadernosProfesor;

    }

    @Override
    public List<CuadernoProfesor> consultarRegistrosPorFechas(String fechaInicio, String fechaFin) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<CuadernoProfesor> listaCuadernosProfesor;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT cp "
                    + " FROM CuadernoProfesor cp "
                    + " WHERE STR_TO_DATE(cp.fechaInsercion, '%d-%m-%Y')"
                    + " BETWEEN STR_TO_DATE(:v_fechaInicio, '%d-%m-%Y') "
                    + "AND STR_TO_DATE(:v_fechaFin, '%d-%m-%Y')";
            Query query = em.createQuery(hql);
            query.setParameter("v_fechaInicio", fechaInicio);
            query.setParameter("v_fechaFin", fechaFin);
            listaCuadernosProfesor = query.getResultList();

        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaCuadernosProfesor;

    }

    @Override
    public List<CuadernoProfesor> consultarRegistroPorAlumnoYFechas(Alumno alumno, String fechaInicio, String fechaFin) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<CuadernoProfesor> listaCuadernosProfesor;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT cp "
                    + " FROM CuadernoProfesor cp "
                    + " WHERE STR_TO_DATE(cp.fechaInsercion, '%d-%m-%Y')"
                    + " BETWEEN STR_TO_DATE(:v_fechaInicio, '%d-%m-%Y') "
                    + " AND STR_TO_DATE(:v_fechaFin, '%d-%m-%Y') "
                    + " AND alumnoId.id = :v_alumnoId";
            Query query = em.createQuery(hql);
            query.setParameter("v_fechaInicio", fechaInicio);
            query.setParameter("v_fechaFin", fechaFin);
            query.setParameter("v_alumnoId", alumno.getId());
            listaCuadernosProfesor = query.getResultList();

        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaCuadernosProfesor;

    }

    @Override
    public CuadernoProfesor consultarRegistroAlumnoExistente(Alumno alumno, String fechaFormateada) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        CuadernoProfesor cuadernoProfesor = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT cp "
                    + " FROM CuadernoProfesor cp "
                    + " WHERE cp.alumnoId.id = :v_alumnoId "
                    + " AND cp.fechaInsercion = :v_fechaInsercion";
            Query query = em.createQuery(hql);
            query.setParameter("v_alumnoId", alumno.getId());
            query.setParameter("v_fechaInsercion", fechaFormateada);
            cuadernoProfesor = (CuadernoProfesor) query.getSingleResult();

        } catch (NoResultException e) {
            cuadernoProfesor = null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return cuadernoProfesor;
    }

    @Override
    public void eliminarRegistroPorAlumnoYFecha(CuadernoProfesor cuadernoProfesor) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();

            String hql = "DELETE FROM CuadernoProfesor cp "
                    + " WHERE cp.alumnoId.id = :v_alumnoId "
                    + " AND cp.fechaInsercion = :v_fechaInsercion";
            Query query = em.createQuery(hql);
            query.setParameter("v_alumnoId", cuadernoProfesor.getAlumnoId().getId());
            query.setParameter("v_fechaInsercion", cuadernoProfesor.getFechaInsercion());
            query.executeUpdate();
            ts.commit();

        } catch (Exception e) {
            if (ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
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
    public boolean actualizar(CuadernoProfesor cuadernoProfesorNuevo) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        CuadernoProfesor cuadernoProfesorAntiguo = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            cuadernoProfesorAntiguo = em.find(CuadernoProfesor.class, cuadernoProfesorNuevo.getId());

            if (cuadernoProfesorAntiguo != null) {
                ts = em.getTransaction();
                ts.begin();

                cuadernoProfesorAntiguo.setTareasCasa(cuadernoProfesorNuevo.getTareasCasa());
                cuadernoProfesorAntiguo.setParticipacion(cuadernoProfesorNuevo.getParticipacion());
                cuadernoProfesorAntiguo.setAtencion(cuadernoProfesorNuevo.getAtencion());
                cuadernoProfesorAntiguo.setTareasClase(cuadernoProfesorNuevo.getTareasClase());

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
