/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import com.ric.academix.dao.NotaDao;

/**
 *
 * @author ricar
 */
public class NotaDaoImpl implements NotaDao {

    private static final String PERSISTENCE = "persistence";
    private static NotaDaoImpl instance;

    static {
        instance = new NotaDaoImpl();
    }

    public static NotaDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void eliminar(int idNota) {
    }

    @Override
    public void eliminarPorAlumno(int idAlumno) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            
            String hql = "DELETE FROM Alumno alum"
                    + " WHERE alum.id = :v_idAlumno";
            Query query = em.createQuery(hql);
            query.setParameter("v_idAlumno", idAlumno);
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
