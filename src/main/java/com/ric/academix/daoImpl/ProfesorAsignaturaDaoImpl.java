package com.ric.academix.daoImpl;

import java.util.List;

import com.ric.academix.dao.ProfesorAsignaturaDao;
import com.ric.academix.modelo.ProfesorAsignatura;
import com.ric.academix.modelo.ProfesorAsignaturaPK;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class ProfesorAsignaturaDaoImpl implements ProfesorAsignaturaDao {

    private static final String PERSISTENCE = "persistence";
    private static ProfesorAsignaturaDaoImpl instance;

    static {
        instance = new ProfesorAsignaturaDaoImpl();
    }

    public static ProfesorAsignaturaDaoImpl getInstance() {
        return instance;
    }

    public ProfesorAsignaturaDaoImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<ProfesorAsignatura> consultarTodos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<ProfesorAsignatura> listaProfesorAsignatura;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            listaProfesorAsignatura = em.createQuery("SELECT pa FROM ProfesorAsignatura pa",
                    ProfesorAsignatura.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaProfesorAsignatura;
    }

    @Override
    public ProfesorAsignatura consultar(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insertar(ProfesorAsignatura profesorAsignatura) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.persist(profesorAsignatura);
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
    public boolean actualizar(ProfesorAsignatura profesorAsignaturaNuevo, ProfesorAsignatura profesorAsignaturaAntiguo) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        ProfesorAsignatura profesorAntiguo = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            ts = em.getTransaction();
            ts.begin();

            String hql = "UPDATE ProfesorAsignatura pa "
                    + " SET pa.asignatura = :v_asignatura, "
                    + " pa.profesor = :v_profesor, "
                    + " pa.agnoAcademico = :v_agnoAcademico"
                    + " WHERE pa.id = :v_id";
            Query query = em.createQuery(hql);
            query.setParameter("v_asignatura", profesorAsignaturaNuevo.getAsignatura());
            query.setParameter("v_profesor", profesorAsignaturaNuevo.getProfesor());
            query.setParameter("v_agnoAcademico", profesorAsignaturaNuevo.getAgnoAcademico());
            query.setParameter("v_id", profesorAsignaturaAntiguo.getId());
            query.executeUpdate();
            ts.commit();
            actualizado = true;

            System.err.println("Actualizado -->  " + actualizado);

        } catch (Exception e) {
            if (ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
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
    public boolean eliminar(int codigo
    ) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int eliminarProfesoryAsignaturasAsociadas(Integer idProfesor) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        int eliminado = 0;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();

            String hql = "DELETE FROM ProfesorAsignatura pa "
                    + " WHERE pa.profesor.id = :v_idProfesor";
            Query query = em.createQuery(hql);
            query.setParameter("v_idProfesor", idProfesor);
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
    public ProfesorAsignatura consultarPorProfesorYAsignatura(ProfesorAsignaturaPK profAsigPK) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        ProfesorAsignatura ProfesorAsignatura = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String hql = " FROM ProfesorAsignatura pa "
                    + " WHERE pa.asignatura.id = :v_idAsignatura "
                    + " AND pa.profesor.id = :v_idProfesor";
            Query query = em.createQuery(hql);
            query.setParameter("v_idAsignatura", profAsigPK.getIdAsignatura());
            query.setParameter("v_idProfesor", profAsigPK.getIdProfesor());
            ProfesorAsignatura = (ProfesorAsignatura) query.getSingleResult();

        } catch (NoResultException e) {
            System.err.println("null");
            ProfesorAsignatura = null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return ProfesorAsignatura;

    }


}
