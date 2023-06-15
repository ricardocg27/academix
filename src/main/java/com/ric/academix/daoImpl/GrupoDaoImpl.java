package com.ric.academix.daoImpl;

import java.util.List;

import com.ric.academix.dao.GrupoDao;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class GrupoDaoImpl implements GrupoDao {

    private static final String PERSISTENCE = "persistence";
    private static GrupoDaoImpl instance;

    static {
        instance = new GrupoDaoImpl();
    }

    public static GrupoDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Grupo> consultarTodos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Grupo> listaGrupos;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            listaGrupos = em.createQuery("SELECT g FROM Grupo g", Grupo.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaGrupos;
    }

    @Override
    public Grupo consultar(int id) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Grupo grupo;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            grupo = em.find(Grupo.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return grupo;
    }

    @Override
    public void insertar(Grupo grupo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();

            em.persist(grupo);
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
    public void actualizar(Grupo grupo) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean eliminar(int codigo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        Grupo grupo = null;
        boolean eliminado = false;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            grupo = em.find(Grupo.class, codigo);
            if (grupo != null) {
                ts = em.getTransaction();
                ts.begin();
                em.remove(grupo);
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
    public Grupo consultarTutorGrupo(int tutorId) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Grupo grupo = null;
        List<Grupo> listaGrupos;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String consulta = "SELECT g "
                    + "FROM Grupo g "
                    + "WHERE g.tutorId = " + tutorId;
            listaGrupos = em.createQuery(consulta, Grupo.class).getResultList();
            if (listaGrupos.size() > 0) {
                grupo = listaGrupos.get(0);
            }
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return grupo;
    }

    @Override
    public List<Grupo> consultarGrupoConAlumnos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Grupo> listaGrupos;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String consulta = "SELECT g "
                    + "FROM Grupo g "
                    + "WHERE g.id IN "
                    + "             (SELECT a.grupoId "
                    + "              FROM Alumno a)";
            listaGrupos = em.createQuery(consulta, Grupo.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaGrupos;
    }

    @Override
    public List<String> consultarCursos() {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<String> listaCursos;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String hql = "SELECT grupo.curso "
                    + "FROM Grupo grupo ";
            Query query = em.createQuery(hql);
            listaCursos = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaCursos;
    }

    @Override
    public List<String> consultarLetrasGrupos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<String> listaLetras;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String hql = "SELECT grupo.letra "
                    + "FROM Grupo grupo ";
            Query query = em.createQuery(hql);
            listaLetras = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaLetras;
    }

    @Override
    public int eliminarTutor(Integer id) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        int eliminado = 0;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();

            String hql = "UPDATE Grupo grupo"
                    + " SET grupo.tutorId = NULL "
                    + " WHERE grupo.tutorId.id = :v_id";
            Query query = em.createQuery(hql);
            query.setParameter("v_id", id);
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
    public Grupo consultarGrupoPorCursoYLetra(String curso, String letra) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Grupo grupo = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String hql = "SELECT grupo"
                    + " FROM Grupo grupo"
                    + " WHERE grupo.curso = :v_curso "
                    + " AND grupo.letra = :v_letra";
            Query query = em.createQuery(hql);
            query.setParameter("v_curso", curso);
            query.setParameter("v_letra", letra);
            grupo = (Grupo) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();;
            }
        }
        return grupo;
    }

    @Override
    public void asignarTutor(int grupoId, int profId) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        Profesor profesorOld = null;
        Grupo grupoOld = null;

        try {
            System.err.println("Llego a asignar tutor**********************");
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            grupoOld = consultar(grupoId);
            System.err.println("a ver si así ..." + grupoOld);
            
            grupoOld = em.find(Grupo.class,(short) grupoId);
            profesorOld = em.find(Profesor.class, (long) profId);

            System.err.println(grupoOld);
            System.err.println(profesorOld);
            
            if (grupoOld != null && profesorOld != null) {
                System.err.println("No son null ninguno");
                ts = em.getTransaction();
                ts.begin();
                grupoOld.setTutorId(profesorOld);
                System.err.println("grupo setteado " + grupoOld.toString());
                ts.commit();
            }

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
    public Long consultarAlumnosPorGrupo(Grupo grupo) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        Long numeroAlumnos;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT count(*) AS numeroAlumnos"
                    + " FROM Grupo gr JOIN Alumno al ON (gr.id = al.grupoId.id) "
                    + " WHERE gr.id = :v_grupoId";
            Query query = em.createQuery(hql);
            query.setParameter("v_grupoId", grupo.getId());
            numeroAlumnos = (Long) query.getSingleResult();

        } catch (NoResultException e) {
            numeroAlumnos = 0L;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return numeroAlumnos;

    }

}
