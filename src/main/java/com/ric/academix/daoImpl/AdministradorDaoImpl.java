/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.AdministradorDao;
import com.ric.academix.modelo.Administrador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author ricar
 */
public class AdministradorDaoImpl implements AdministradorDao {

    private static final String PERSISTENCE = "persistence";
    private static final AdministradorDaoImpl instance;

    static {
        instance = new AdministradorDaoImpl();
    }

    public static AdministradorDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Administrador> consultarTodos() {
        List<Administrador> administradores = null;

        return administradores;
    }

    @Override
    public Administrador consultar(int id) {
        Administrador admin = null;

        return admin;
    }

    @Override
    public void insertar(Administrador administrador) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            em.persist(administrador);
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
            if (emf != null){
                emf.close();
            }
        }
    }

    @Override
    public void actualizar(int id) {

    }

    @Override
    public boolean eliminar(int id) {
        boolean eliminado = false;

        return eliminado;
    }

}
