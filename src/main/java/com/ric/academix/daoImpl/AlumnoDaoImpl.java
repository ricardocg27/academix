/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.modelo.Alumno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

/**
 *
 * @author ricar
 */
public class AlumnoDaoImpl implements AlumnoDao {

    private static final String PERSISTENCE = "persistence";
    private static final AlumnoDaoImpl instance;

    static {
        instance = new AlumnoDaoImpl();
    }

    public static AlumnoDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void insertar(Alumno alumno) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();
            em.persist(alumno);
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
    public Alumno consultarPorEmailYContrasena(String email, String contrasegna) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        Alumno alumno = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT alum "
                    + " FROM Alumno alum "
                    + " WHERE alum.email = :v_email"
                    + " AND alum.contrasegna = :v_contrasegna";
            Query query = em.createQuery(hql);
            query.setParameter("v_email", email);
            query.setParameter("v_contrasegna", contrasegna);

            alumno = (Alumno) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return alumno;
    }

}
