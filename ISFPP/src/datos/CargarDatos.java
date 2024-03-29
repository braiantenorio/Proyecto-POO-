package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;

import modelo.Relacion;
import modelo.Usuario;
import java.util.List;
import java.util.ArrayList;
import net.datastructures.TreeMap;

public class CargarDatos {
	private static TreeMap<String, Usuario> usuarios;

	/**
	 * Carga las usuarios desde un archivo de texto y las guarda en un mapa donde la clave es la ID 
	 * y el valor es el usuario
	 * 
	 * @param fileName Nombre del archivo fuente de usuarios
	 * @return TreeMap<String, Usuario> grafo no dirigido con
	 * @throws FileNotFoundException si no se encuentra el archivo
	 */
	public static TreeMap<String, Usuario> cargarUsuarios(String fileName) throws FileNotFoundException {
		Scanner read = null;

		usuarios = new TreeMap<String, Usuario>();
		try {
			read = new Scanner(new File(fileName));

		} catch (FileNotFoundException e) {
			System.out.printf("Error archivo no encontrado");

		}
		read.useDelimiter("\\s*;\\s*");
		String codigo, nombre, genero, ciudadAct;
		int edad;
		try {
			while (read.hasNext()) {
				codigo = read.next();
				nombre = read.next();
				edad = read.nextInt();
				genero = read.next();
				ciudadAct = read.next();

				usuarios.put(codigo, new Usuario(codigo, nombre, edad, genero, ciudadAct));
			}
		} catch (InputMismatchException e) {
			System.out.printf("Error archivo %s con formato inv�lido.", fileName);
		} finally {
			read.close();
		}
		return usuarios;

	}

	/**
	 * Crea las relaciones desde un archivo y las guarda en una lista
	 * 
	 * @param fileName fileName Nombre del archivo con las relaciones entre usuarios
	 * @return List<Relacion> Lista con todas las relaciones
	 * @throws FileNotFoundException si no se encuentra el archivo
	 */
	public static List<Relacion> crearRelaciones(String fileName)
			throws FileNotFoundException {
		Scanner read = null;
		List<Relacion> relaciones = new ArrayList<Relacion>();
		try {
			read = new Scanner(new File(fileName));

		} catch (FileNotFoundException e) {
			System.out.printf("Error archivo %s no encontrado", fileName);
		}
		read.useDelimiter("\\s*;\\s*");
		Usuario usr1, usr2;
		int tSiendoAmigos, likes, tInterDiaria;
		try {
			while (read.hasNext()) {
				usr1 = usuarios.get(read.next());
				usr2 = usuarios.get(read.next());
				tInterDiaria = read.nextInt();
				likes = read.nextInt();
				tSiendoAmigos = read.nextInt();
				relaciones.add(0, new Relacion(usr1, usr2, tInterDiaria, likes, tSiendoAmigos));
			}
		} catch (InputMismatchException e) {
			System.out.printf("Error archivo %s con formato inv�lido.", fileName);
		} finally {
			read.close();
		}
		return relaciones;
	}

}
