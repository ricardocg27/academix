/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.daoImpl;

import com.ric.academix.dto.NotaAlumnoDTO;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Grupo;
import java.util.List;
import com.ric.academix.dao.NotasAlumnosDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.ArrayList;

/**
 *
 * @author ricar
 */
public class NotasAlumnosDaoImpl implements NotasAlumnosDao {

    private static final String PERSISTENCE = "persistence";
    private static final NotasAlumnosDaoImpl instance;

    static {
        instance = new NotasAlumnosDaoImpl();
    }

    public static NotasAlumnosDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<NotaAlumnoDTO> consultarNotasPorGrupoYAsignatura(Grupo grupo, Asignatura asignatura) {

        EntityManagerFactory emf = null;
        EntityManager em = null;

        NotaAlumnoDTO notaAlumnoDTO;
        List<Object[]> result = null;
        List<NotaAlumnoDTO> listadoNotasAlumnos = new ArrayList<>();

        try {

            emf = Persistence.createEntityManagerFactory(PERSISTENCE);
            em = emf.createEntityManager();

            String hql = "SELECT alum.nombre AS nombreAlumno, "
                        + " alum.primerApellido AS primerApellido, "
                        + " alum.segundoApellido AS segundoApellido, "
                        + " exam.nombre AS nombreExamen,"
                        + " exam.porcentaje AS porcentajeExamen, "
                        + " ne.puntuacion AS puntuacionExamen, "
                        + " tae.nombre AS nombreTarea ,"
                        + " tae.porcentaje AS porcentajeTarea,"
                        + " nt.puntuacion AS puntuacionTarea "
                    + " FROM Alumno alum "
                    + " JOIN NotaExamen ne ON (ne.alumnoId.id = alum.id) "
                    + " JOIN NotaTareaEvaluable nt ON (nt.alumnoId.id = alum.id) "
                    + " JOIN Examen exam ON (ne.examenId.id = exam.id) "
                    + " JOIN TareaEvaluable tae ON (nt.tareaEvaluableId.id = tae.id) "
                    + " WHERE alum.grupoId.id = :v_grupoId "
                    + " and exam.asignaturaId.id = :v_asignaturaId "
                    + " AND tae.asignaturaId.id = :v_asignaturaId ";
            Query query = em.createQuery(hql);
            query.setParameter("v_grupoId", grupo.getId());
            query.setParameter("v_asignaturaId", asignatura.getId());
            result = query.getResultList();

            for (Object[] row : result) {
                notaAlumnoDTO = new NotaAlumnoDTO();
                notaAlumnoDTO.setNombreAlumno((String) row[0]);
                notaAlumnoDTO.setPrimerApellido((String) row[1]);
                notaAlumnoDTO.setSegundoApellido((String) row[2]);
                notaAlumnoDTO.setNombreExamen((String) row[3]);
                notaAlumnoDTO.setPorcentajeExamen((int) row[4]);
                notaAlumnoDTO.setPuntuacionExamen((Double) row[5]);
                notaAlumnoDTO.setNombreTarea((String) row[6]);
                notaAlumnoDTO.setPorcentajeTarea((int) row[7]);
                notaAlumnoDTO.setPuntuacionTarea((Double) row[8]);
                listadoNotasAlumnos.add(notaAlumnoDTO);
            }

        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return listadoNotasAlumnos;

    }

}
