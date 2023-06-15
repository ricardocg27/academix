package com.ric.academix.daoImpl;

import java.util.List;

import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.modelo.Profesor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import javafx.scene.control.PasswordField;

public class ProfesorDaoImpl implements ProfesorDao {

    private static final String PERSISTENCE = "persistence";
    private static ProfesorDaoImpl instance;

    static {
        instance = new ProfesorDaoImpl();
    }

    public static ProfesorDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Profesor> consultarTodos() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Profesor> listaProfesores;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            listaProfesores = em.createQuery("SELECT p FROM Profesor p", Profesor.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaProfesores;
    }

    @Override
    public Profesor consultar(int id) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        Profesor profesor;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            profesor = em.find(Profesor.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return profesor;

    }

    @Override
    public void insertar(Profesor profesor) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();

            em.persist(profesor);
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
    public boolean actualizar(Profesor profesorNuevo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        Profesor profesorAntiguo = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            profesorAntiguo = em.find(Profesor.class, profesorNuevo.getId());

            if (profesorAntiguo != null) {
                ts = em.getTransaction();
                ts.begin();

                profesorAntiguo.setNombre(profesorNuevo.getNombre());
                profesorAntiguo.setPrimerApellido(profesorNuevo.getPrimerApellido());
                profesorAntiguo.setSegundoApellido(profesorNuevo.getSegundoApellido());
                profesorAntiguo.setEmail(profesorNuevo.getEmail());

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
    public boolean eliminar(int codigo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;
        Profesor profesor = null;
        boolean eliminado = false;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            profesor = em.find(Profesor.class, codigo);
            if (profesor != null) {
                ts = em.getTransaction();
                ts.begin();
                em.remove(profesor);
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
    public List<Profesor> consultarProfesoresSonTutores() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Profesor> listaProfesores;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String consulta = "SELECT p "
                    + "FROM Profesor p "
                    + "WHERE p.id IN (SELECT g.tutorId FROM Grupo g)";
            listaProfesores = em.createQuery(consulta, Profesor.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listaProfesores;
    }

    @Override
    public Profesor consultarProfesorAsignatura(int codigo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Profesor profesor = null;
        List<Profesor> listaProfesores;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            String consulta = "SELECT p FROM Profesor p "
                    + "WHERE p.id IN (SELECT pa.id.idProfesor\r\n"
                    + "			   FROM ProfesorAsignatura pa\r\n"
                    + "			   WHERE pa.id.idAsignatura = " + codigo
                    + "			   )\r\n"
                    + "ORDER BY p.id";
            System.out.println(consulta);
            listaProfesores = em.createQuery(consulta, Profesor.class).getResultList();
            if (listaProfesores.size() > 0) {
                profesor = listaProfesores.get(0);
            }
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return profesor;
    }

    @Override
    public Profesor consultarPorEmailYContrasena(String email, String contrasegna) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        Profesor profesor = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT prof "
                    + " FROM Profesor prof "
                    + " WHERE prof.email = :v_email"
                    + " AND prof.contrasegna = :v_contrasegna";
            Query query = em.createQuery(hql);
            query.setParameter("v_email", email);
            query.setParameter("v_contrasegna", contrasegna);

            profesor = (Profesor) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return profesor;
    }

    @Override
    public int consultarIdPorNombreYApellidos(List<String> nombres) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        Profesor profesor = null;
        int idProfesor = -1;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT prof.id "
                    + " FROM Profesor prof "
                    + " WHERE prof.nombre LIKE :v_nombre "
                    + " AND prof.primerApellido LIKE :v_primer_apellido "
                    + " AND prof.segundoApellido LIKE :v_segundo_apellido";
            Query query = em.createQuery(hql);
            query.setParameter("v_nombre", "%" + nombres.get(0));
            query.setParameter("v_primer_apellido", "%" + nombres.get(1));
            query.setParameter("v_segundo_apellido", "%" + nombres.get(2));
            idProfesor = (int) query.getSingleResult();

        } catch (NoResultException e) {
            e.printStackTrace();
            idProfesor = -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return idProfesor;

    }

    @Override
    public Profesor consultarTutor(Integer id) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Profesor profesor = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT profesor "
                    + "FROM Profesor profesor "
                    + "WHERE profesor.id = :v_idProfesor "
                    + "AND profesor.id IN (SELECT grupo.tutorId.id "
                    + "                    FROM Grupo grupo)";
            Query query = em.createQuery(hql);
            query.setParameter("v_idProfesor", id);
            profesor = (Profesor) query.getSingleResult();

        } catch (NoResultException e) {
            profesor = null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return profesor;
    }

    @Override
    public boolean actualizarDatosUsuario(Profesor profesorNuevo) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        Profesor profesorAntiguo = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            profesorAntiguo = em.find(Profesor.class, profesorNuevo.getId());

            if (profesorAntiguo != null) {
                ts = em.getTransaction();
                ts.begin();

                profesorAntiguo.setNombre(profesorNuevo.getNombre());
                profesorAntiguo.setPrimerApellido(profesorNuevo.getPrimerApellido());
                profesorAntiguo.setSegundoApellido(profesorNuevo.getSegundoApellido());
                profesorAntiguo.setFechaNacimiento(profesorNuevo.getFechaNacimiento());
                profesorAntiguo.setTelefono(profesorNuevo.getTelefono());
                profesorAntiguo.setEmail(profesorNuevo.getEmail());
                profesorAntiguo.setDireccion(profesorNuevo.getDireccion());

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
    public Profesor consultarPorContrasena(Profesor prof, PasswordField txtOldPass) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Profesor profesor = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT prof "
                    + " FROM Profesor prof "
                    + " WHERE prof.id = :v_id"
                    + " AND prof.contrasegna = :v_contrasegna";
            Query query = em.createQuery(hql);
            query.setParameter("v_id", prof.getId());
            query.setParameter("v_contrasegna", txtOldPass.getText());

            profesor = (Profesor) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return profesor;
    }

    @Override
    public boolean actualizarContrasena(Profesor prof, PasswordField txtNewPass) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction ts = null;

        Profesor profesorAntiguo = null;
        boolean actualizado = false;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();
            profesorAntiguo = em.find(Profesor.class, prof.getId());

            if (profesorAntiguo != null) {
                ts = em.getTransaction();
                ts.begin();

                profesorAntiguo.setContrasegna(txtNewPass.getText());

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
