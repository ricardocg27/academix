package com.ric.academix.controlador;

import com.ric.academix.modelo.Teclado;
import java.util.List;

import com.ric.academix.dao.GrupoDao;
import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.GrupoDaoImpl;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.Profesor;




public class GrupoControlador {
	
	public static void menuOpciones() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("0) Salir del programa.");
		System.out.println("1) Insertar un grupo en la base de datos.");
		System.out.println("2) Consultar todos los grupos de la base de datos.");
		System.out.println("3) Consultar un grupo, por código, de la base de datos.");
		System.out.println("4) Actualizar un grupo de la base de datos.");
		System.out.println("5) Eliminar un grupo de la base de datos.");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}
	
	public static void visualizarGrupos(List<Grupo> lista) {
		for (Grupo x : lista) {
			System.out.println(x.toString());
		}

	}
	
	private static Grupo setDatos() {
		
		ProfesorDao profesorDao = ProfesorDaoImpl.getInstance();
		
		String etapa = Teclado.leerCadena("¿Etapa? (ESO o Bachillerato)");
		while (!etapa.equalsIgnoreCase("Bachillerato") && !etapa.equalsIgnoreCase("ESO")) {
			System.out.println("La etapa tiene que corresponderse con: ESO o Bachillerato.");
			etapa = Teclado.leerCadena("¿Etapa? (ESO o Bachillerato)");
		}
		
		int agno_curso = Teclado.leerEntero("Introduce el año del curso");
		
		String curso = agno_curso + "º " + etapa;
		char letra = Teclado.leerCaracter("¿Letra?");

		int tutorId = pedirTutor();
		Profesor profesor = profesorDao.consultar(tutorId);
		while(profesor == null) {
			System.out.println("Introduce un Id existente");
			tutorId = Teclado.leerEntero("¿Id del tutor?");
			profesor = profesorDao.consultar(tutorId);
		}

		Grupo grupo = new Grupo();

		grupo.setCurso(curso.trim().toLowerCase());
		grupo.setLetra(letra);
		//grupo.setProfesor(profesor);

		return grupo;
	}
	// Modificar letra a varchar en la bbdd y el script
	// controlar que solo pueda incluir una letra

	private static int pedirTutor() {
		int tutorId = Teclado.leerEntero("¿Id del tutor?");
		GrupoDao grupoDao = GrupoDaoImpl.getInstance();
		Grupo grupo = grupoDao.consultarTutorGrupo(tutorId);
		
		while(grupo != null) {
			System.out.println("El tutor ya tiene un grupo asignado.\nPor favor, borre el tutor "
					+ "del grupo o asigne un profesor disponible.");
			tutorId = Teclado.leerEntero("¿Id del tutor?");
			grupo = grupoDao.consultarTutorGrupo(tutorId);
		}
		return tutorId;
	}

	public static void main(String[] args) {
		int opcion, codigo;

		Grupo grupo;
		GrupoDao grupoDao = GrupoDaoImpl.getInstance();
		List<Grupo> listaGrupos, listaGruposConAlumnos;
		try {
			do {
				System.out.println();
				menuOpciones();
				System.out.println();
				opcion = Teclado.leerEntero("¿Opción del menú?");

				switch(opcion) {
				// Salir del programa
				case 0:
					break;
					// Insertar un grupo en la base de datos
				case 1:
					grupo = setDatos();
					grupoDao.insertar(grupo);
					System.out.println("Grupo insertado en la base de datos.");
					break;

					// Consultar todos los grupos de la base de datos
				case 2:
					listaGrupos = grupoDao.consultarTodos();
					visualizarGrupos(listaGrupos);
					break; 
					// Consultar un grupo, por id, de la base de datos
				case 3: 
					codigo = Teclado.leerEntero("¿Código del grupo a consultar?");
					grupo = grupoDao.consultar(codigo);
					break;
					
					// Actualizar un grupo de la base de datos, por id identificativo
				case 4: 

					break;
					// Eliminar un grupo, de la base de datos, por id identificativo
				case 5:
					codigo = Teclado.leerEntero("¿Código del grupo a eliminar?");
					grupo = grupoDao.consultar(codigo);
					if (grupo == null) {
						System.err.println("Grupo no encontrado en la base de datos.");
					}
					else {
						listaGruposConAlumnos = grupoDao.consultarGrupoConAlumnos();
						if (listaGruposConAlumnos.size() > 0) {
							System.err.println("No se puede eliminar. Existe al menos un alumno "
									+ "existente en ese grupo.\nBorre primero el alumno.");
						}
						else {
							if (grupoDao.eliminar(codigo)) {
								System.out.println("Eliminado de la base de datos.");
							}
							else {
								System.err.println("Error al eliminar.");
							}
						}
					}
					break;
				default: 
					System.err.println("La opción debe de estar comprendida entre 0-5");
					break;

				}
			} while(opcion!=0);
			System.out.println("Programa finalizado.");
		}

		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
