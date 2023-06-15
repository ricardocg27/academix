/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.AlumnoDao;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;

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
    public List<Alumno> consultarTodos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Alumno> listaAlumnos;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT alumn "
                    + " FROM Alumno alumn";
            Query query = em.createQuery(hql);
            listaAlumnos = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaAlumnos;
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
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
            return alumno;
        }
    }

    @Override
    public boolean eliminar(Alumno alumno) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        boolean eliminado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            alumno = em.find(Alumno.class, alumno.getId());
            if (alumno != null) {
                ts = em.getTransaction();
                ts.begin();
                em.remove(alumno);
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
    public List<Alumno> consultarPorGrupo(Grupo grupo
    ) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Alumno> listaAlumnos;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT alum "
                    + " FROM Alumno alum "
                    + " WHERE alum.grupoId.id = :v_grupoId";
            Query query = em.createQuery(hql);
            query.setParameter("v_grupoId", grupo.getId());
            listaAlumnos = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaAlumnos;

    }

    @Override
    public boolean actualizar(Alumno alumnoNuevo) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        Alumno alumnoAntiguo = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            alumnoAntiguo = em.find(Alumno.class, alumnoNuevo.getId());

            if (alumnoAntiguo != null) {
                ts = em.getTransaction();
                ts.begin();

                alumnoAntiguo.setNombre(alumnoNuevo.getNombre());
                alumnoAntiguo.setPrimerApellido(alumnoNuevo.getPrimerApellido());
                alumnoAntiguo.setSegundoApellido(alumnoNuevo.getSegundoApellido());
                alumnoAntiguo.setEmail(alumnoNuevo.getEmail());
                alumnoAntiguo.setGrupoId(alumnoNuevo.getGrupoId());

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
    public int eliminarGrupo(Grupo grupo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        int eliminado = 0;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();

            String hql = "UPDATE Alumno alum "
                    + " SET alum.grupoId = NULL "
                    + " WHERE alum.grupoId.id = :v_grupoId ";
            Query query = em.createQuery(hql);
            query.setParameter("v_grupoId", grupo.getId());

            eliminado = query.executeUpdate();

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
        return eliminado;

    }

    @Override
    public Alumno consultarPorNombreYApellidos(List<String> nombres
    ) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        Alumno alumno = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT alum "
                    + " FROM Alumno alum "
                    + " WHERE alum.nombre like :v_nombre"
                    + " AND alum.primerApellido LIKE :v_primerApellido "
                    + " AND alum.segundoApellido LIKE :v_segundoApellido ";
            Query query = em.createQuery(hql);
            query.setParameter("v_nombre", nombres.get(0));
            query.setParameter("v_primerApellido", "%" + nombres.get(1));
            query.setParameter("v_segundoApellido", "%" + nombres.get(2));
            alumno = (Alumno) query.getSingleResult();

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

        return alumno;

    }

}
