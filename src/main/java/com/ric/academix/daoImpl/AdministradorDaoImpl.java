/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.AdministradorDao;
import com.ric.academix.modelo.Administrador;
import com.ric.academix.modelo.Alumno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import javafx.scene.control.PasswordField;

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
            ts.begin();
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
            if (emf != null) {
                emf.close();
            }
        }
    }

    @Override
    public boolean actualizar(Administrador administradorNuevo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        Administrador administradorAntiguo = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            administradorAntiguo = em.find(Administrador.class, administradorNuevo.getId());

            if (administradorAntiguo != null) {
                ts = em.getTransaction();
                ts.begin();

                administradorAntiguo.setNombre(administradorNuevo.getNombre());
                administradorAntiguo.setPrimerApellido(administradorNuevo.getPrimerApellido());
                administradorAntiguo.setSegundoApellido(administradorNuevo.getSegundoApellido());
                administradorAntiguo.setFechaNacimiento(administradorNuevo.getFechaNacimiento());
                administradorAntiguo.setTelefono(administradorNuevo.getTelefono());
                administradorAntiguo.setEmail(administradorNuevo.getEmail());
                administradorAntiguo.setDireccion(administradorNuevo.getDireccion());

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

    @Override
    public boolean eliminar(int id) {
        boolean eliminado = false;

        return eliminado;
    }

    @Override
    public Administrador consultarPorEmailYContrasena(String email, String contrasena) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        Administrador admin = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT admin "
                    + " FROM Administrador admin "
                    + " WHERE admin.email = :v_email"
                    + " AND admin.contrasegna = :v_contrasegna";
            Query query = em.createQuery(hql);
            query.setParameter("v_email", email);
            query.setParameter("v_contrasegna", contrasena);
            admin = (Administrador) query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
        return admin;
    }

    @Override
    public Administrador consultarContrasena(Administrador adminContra, PasswordField txtOldPass) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Administrador admin = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT admin "
                    + " FROM Administrador admin "
                    + " WHERE admin.id = :v_id"
                    + " AND admin.contrasegna = :v_contrasegna";
            Query query = em.createQuery(hql);
            query.setParameter("v_id", adminContra.getId());
            query.setParameter("v_contrasegna", txtOldPass.getText());
            admin = (Administrador) query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
        return admin;
    }

    @Override
    public boolean actualizarContrasena(Administrador admin, PasswordField txtNewPass) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        Administrador administradorAntiguo = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            administradorAntiguo = em.find(Administrador.class, admin.getId());

            if (administradorAntiguo != null) {
                ts = em.getTransaction();
                ts.begin();

                administradorAntiguo.setContrasegna(txtNewPass.getText());
                

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
