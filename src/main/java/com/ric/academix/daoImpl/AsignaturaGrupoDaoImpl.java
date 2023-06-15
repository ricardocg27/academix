/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dao.AsignaturaGrupoDao;
import com.ric.academix.modelo.Alumno;
import com.ric.academix.modelo.AsignaturaGrupo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;


/**
 *
 * @author ricar
 */
public class AsignaturaGrupoDaoImpl implements AsignaturaGrupoDao {

    private static final String PERSISTENCE = "persistence";
    private static final AsignaturaGrupoDaoImpl instance;

    static {
        instance = new AsignaturaGrupoDaoImpl();
    }

    public static AsignaturaGrupoDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<AsignaturaGrupo> consultarAsignaturasYGrupo() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<AsignaturaGrupo> asignaturasGrupo;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT ag "
                    + " FROM AsignaturaGrupo ag";
            Query query = em.createQuery(hql);
            asignaturasGrupo = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return asignaturasGrupo;
    }

  

}
