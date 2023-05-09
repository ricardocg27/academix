package com.ric.academix.controlador;

import com.ric.academix.modelo.Teclado;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ric.academix.dao.AsignaturaDao;
import com.ric.academix.dao.ProfesorAsignaturaDao;
import com.ric.academix.dao.ProfesorDao;
import com.ric.academix.daoImpl.AsignaturaDaoImpl;
import com.ric.academix.daoImpl.ProfesorAsignaturaDaoImpl;
import com.ric.academix.daoImpl.ProfesorDaoImpl;
import com.ric.academix.modelo.Asignatura;
import com.ric.academix.modelo.Profesor;
import com.ric.academix.modelo.ProfesorAsignatura;
import com.ric.academix.modelo.ProfesorAsignaturaPK;



public class ProfesorAsignaturaControlador {

	public static void menuOpciones() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("0) Salir del programa.");
		System.out.println("1) Insertar una asignatura dada por un profesor.");
		System.out.println("2) Consultar todas las asignaturas dadas por todos los profesores.");
		System.out.println("3) Consultar qué profesores dan una asignatura en concreto.");
		System.out.println("4) Consultar qué asignaturas da un profesor.");
		System.out.println("5) Actualizar una asignatura dada por un profesor.");
		System.out.println("6) Eliminar una asignatura dada por un profesor.");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	private static void visualizarLista(List<ProfesorAsignatura> listaProfesorAsignatura) {

		for (ProfesorAsignatura pa : listaProfesorAsignatura) {
			System.out.println(pa.toString());
		}
	}

	public static void main(String[] args) {

		Logger.getLogger("org.hibernate").setLevel(Level.OFF);

		int opcion, codigo;

		Asignatura asignatura;
		AsignaturaDao asignaturaDao;
		Profesor profesor;
		ProfesorDao profesorDao;
		ProfesorAsignatura profAsig;
		ProfesorAsignaturaPK profAsigPK;
		ProfesorAsignaturaDao profAsigDao = ProfesorAsignaturaDaoImpl.getInstance();
		List<ProfesorAsignatura> listaProfesorAsignatura;
		HashSet<ProfesorAsignatura> conjuntoSinDuplicados;

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
					//  Insertar una asignatura dada por un profesor.
				case 1:
					codigo = Teclado.leerEntero("¿Código de la asignatura?");
					asignaturaDao = AsignaturaDaoImpl.getInstance();
					asignatura = asignaturaDao.consultar(codigo);
					if (asignatura == null) {
						System.err.println("No existe ninguna asignatura con ese código");
					}
					else {
						codigo = Teclado.leerEntero("¿Código del profesor?");
						profesorDao = ProfesorDaoImpl.getInstance();
						profesor = profesorDao.consultar(codigo);
						if (profesor == null) {
							System.err.println("No existe ningún profesor con ese código");
						}
						else {
							listaProfesorAsignatura = profAsigDao.consultarTodos();
							conjuntoSinDuplicados = new HashSet<ProfesorAsignatura>(listaProfesorAsignatura);
							profAsigPK = new ProfesorAsignaturaPK(profesor.getId(), asignatura.getId());
							profAsig = new ProfesorAsignatura(profAsigPK);
							profAsig.setAgnoAcademico("2023");

							if (conjuntoSinDuplicados.contains(profAsig)) {
								System.out.println("Ya existe este profesor impartiendo esta asignatura.");
							}
							else {
								profAsigDao.insertar(profAsig);
							}
						}
					}
					break;

				
				case 2:
					listaProfesorAsignatura = profAsigDao.consultarTodos();
					visualizarLista(listaProfesorAsignatura);
					break; 
					
				case 3: 


					break;
				
				case 4: 

					break;
					
				case 5:

					break;
				case 6:

					break;
				default: 
					System.err.println("La opción debe de estar comprendida entre 0-6");
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
