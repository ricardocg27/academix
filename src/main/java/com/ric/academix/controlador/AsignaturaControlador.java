package com.ric.academix.controlador;

import com.ric.academix.modelo.Teclado;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ric.academix.dao.AsignaturaDao;
import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.AsignaturaDaoImpl;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Profesor;
import java.util.Scanner;




public class AsignaturaControlador {
	
	public static void menuOpciones() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("0) Salir del programa.");
		System.out.println("1) Insertar una asignatura en la base de datos.");
		System.out.println("2) Consultar todas las asignaturas de la base de datos.");
		System.out.println("3) Consultar una asignatura, por código, de la base de datos.");
		System.out.println("4) Actualizar una asignatura de la base de datos.");
		System.out.println("5) Eliminar una asignatura de la base de datos.");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}
	
	public static void visualizarAsignaturas(List<Asignatura> lista) {
		for (Asignatura x : lista) {
			System.out.println(x.toString());
		}
	}
	
	public static void main(String[] args) {
		
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		
		int opcion, codigo;
		String nombre;
		Asignatura asignatura;
		Profesor profesor;
		AsignaturaDao asignaturaDao = AsignaturaDaoImpl.getInstance();
		ProfesorDao profesorDao;
		List<Asignatura> listaAsignaturas, listaAsignaturasEnExamen, listaAsignaturasProfesor;
                Scanner sc = new Scanner(System.in);
		
		try {
			do {
				
				menuOpciones();
				System.out.println("Opción del menú");
				opcion = sc.nextInt();
                                

				switch(opcion) {
				// Salir del programa
				case 0:
					break;
					// Insertar una asignatura en la base de datos
				case 1:
					nombre = Teclado.leerCadena("¿Nombre de la asignatura?");
					asignatura = new Asignatura(nombre.trim());
					asignaturaDao.insertar(asignatura);
					break;

					// Consultar todas las asignaturas de la base de datos
				case 2:
					listaAsignaturas = asignaturaDao.consultarTodos();
					visualizarAsignaturas(listaAsignaturas);
					break; 
					// Consultar una asignatura, por id, de la base de datos
				case 3: 
					codigo = Teclado.leerEntero("¿Código de la asignatura?");
					asignatura = asignaturaDao.consultar(codigo);
					if (asignatura == null) {
						System.err.println("No existe ninguna asignatura con ese código.");
					}
					else {
						System.out.println(asignatura.toString());
					}
					break;
					// Actualizar una asignatura de la base de datos, por id identificativo
				case 4: 

					break;
					// Eliminar una asignatura, de la base de datos, por id identificativo
				case 5:
					codigo = Teclado.leerEntero("¿Código de la asignatura a eliminar?");
					asignatura = asignaturaDao.consultar(codigo);
					if (asignatura == null) {
						System.out.println("No existe ninguna asignatura con ese código.");
					}
					else {
						listaAsignaturasEnExamen = asignaturaDao.consultarAsignaturaEnExamen(codigo);
						if (listaAsignaturasEnExamen.size() > 0) {
							System.err.println("Se han encontrado los siguientes exámenes "
									+ "referenciados en la asignatura.");
							// Mostrar exámenes. Preguntar si se quieren borrar.
							// Dar posibilidad de hacer copia de esos datos 
						}
						else {
							listaAsignaturasProfesor = asignaturaDao.consultarAsignaturaProfesor(codigo);
							if (listaAsignaturasProfesor.size() > 0) {
								profesorDao = ProfesorDaoImpl.getInstance();
								profesor = profesorDao.consultarProfesorAsignatura(codigo);
								System.err.println("Error al eliminar.\n"
										+ "Se ha encontrado a " + profesor.getNombre() + 
									" " + profesor.getApellido() + " como profesor de la asignatura" );
								// Preguntar si borrar al profesor como profesor de la asignatura
							}
						}
					}
					break;
				default: 
					System.err.println("La opción debe estar comprendida entre 0-5");
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
