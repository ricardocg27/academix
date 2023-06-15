package com.ric.academix.controlador;

import com.ric.academix.modelo.Teclado;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Profesor;



public class ProfesorControlador {

	public static void menuOpciones() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("0) Salir del programa.");
		System.out.println("1) Insertar un profesor en la base de datos.");
		System.out.println("2) Consultar todos los profesores de la base de datos.");
		System.out.println("3) Consultar un profesor, por código, de la base de datos.");
		System.out.println("4) Actualizar un profesor de la base de datos.");
		System.out.println("5) Eliminar un profesor de la base de datos.");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	public static void visualizarProfesores(List<Profesor> lista) {
		for (Profesor x : lista) {
			System.out.println(x.toString());
		}
	}

	private static Profesor setDatos() {

		String nombre = Teclado.leerCadena("¿Nombre?");
		String apellido = Teclado.leerCadena("¿Apellidos?");
		String contrasegna = Teclado.leerCadena("¿Contraseña?");
		String email = Teclado.leerCadena("¿Email?");

		Profesor profesor = new Profesor();

		profesor.setNombre(nombre.trim());
		//profesor.setApellido(apellido.trim());
		profesor.setContrasegna(contrasegna.trim());
		profesor.setEmail(email.trim());

		return profesor;
	}

	public static void main(String[] args) {
		// Utilizar tras desarrollo main inicio app
		// Quitar también showSQL del persistence.xml
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);

		int opcion, codigo;
		Profesor profesor;
		ProfesorDao profesorDao = ProfesorDaoImpl.getInstance();
		List<Profesor> listaProfesores;
		List<Profesor> listaProfesorEnGrupo = null;
		
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
					// Insertar un profesor en la base de datos
				case 1:
					profesor = setDatos();
					profesorDao.insertar(profesor);
					
					break;

					// Consultar todos los profesores de la base de datos
				case 2:
					listaProfesores = profesorDao.consultarTodos();
					visualizarProfesores(listaProfesores);
					break; 
					// Consultar un profesor, por id, de la base de datos
				case 3: 
					codigo = Teclado.leerEntero("¿Código identificativo del profesor?");
					profesor = profesorDao.consultar(codigo);
					if (profesor == null) {
						System.err.println("Profesor no encontrado en la base de datos.");
					}
					else {
						System.out.println(profesor.toString());
					}

					break;
					// Actualizar un profesor de la base de datos, por id identificativo
				case 4: 

					break;
					// Eliminar un profesor, de la base de datos, por id identificativo
				case 5:
					codigo = Teclado.leerEntero("¿Código identificativo del profesor?");
					profesor = profesorDao.consultar(codigo);
					if (profesor == null) {
						System.err.println("Profesor no encontrado en la base de datos.");
					}
					else {
						//listaProfesorEnGrupo = profesorDao.consultarProfesorTutor();
						if (listaProfesorEnGrupo.size() > 0) {
							System.err.println("No se puede eliminar. Existe "
									+ "al menos un grupo con ese profesor referenciado.");
						}
						else {
							if (profesorDao.eliminar(codigo)) {
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
			}while(opcion!=0);
			System.out.println("Programa finalizado.");
		}

		catch(Exception e) {
			e.printStackTrace();
		}

	}



}
