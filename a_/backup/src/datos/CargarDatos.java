package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import modelo.Genero;
import modelo.NivelAcademico;
import modelo.Relacion;
import modelo.Usuario;
import negocio.UsuarioNoValidoException;
import net.datastructures.TreeMap;

public class CargarDatos {
	private static TreeMap<String, Usuario> usuarios;

	/**
	 * Carga las usuarios desde un archivo de texto y las guarda en un mapa donde la
	 * clave es la ID y el valor es el usuario
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
		String codigo, nombre, ciudadAct;
		String genero;
		String nivelAcademico;
		int fechaAnho, fechaMes, fechaDia;
		Usuario usuario;
		try {
			while (read.hasNext()) {
				codigo = read.next();
				nombre = read.next();
				fechaAnho = read.nextInt();
				fechaMes = read.nextInt();
				fechaDia = read.nextInt();
				genero = read.next();
				ciudadAct = read.next();
				nivelAcademico = read.next();
				usuario = new Usuario(codigo, nombre, LocalDate.of(fechaAnho, fechaMes, fechaDia),
						Genero.valueOf(genero.toUpperCase()), ciudadAct,
						NivelAcademico.valueOf(nivelAcademico.toUpperCase()));
				if (usuarios.get(codigo) != null)
					throw new UsuarioRepetidoException("usuario repetido:" + codigo);
				usuarios.put(codigo, usuario);
			} // toUpperCase reduce error con el formato
		} catch (InputMismatchException e) {
			System.out.printf("Error archivo %s con formato invalido.", fileName);
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
	public static List<Relacion> crearRelaciones(String fileName) throws FileNotFoundException {
		Scanner read = null;
		List<Relacion> relaciones = new ArrayList<Relacion>();
		try {
			read = new Scanner(new File(fileName));

		} catch (FileNotFoundException e) {
			System.out.printf("Error archivo %s no encontrado", fileName);
		}
		read.useDelimiter("\\s*;\\s*");
		Usuario usr1, usr2;
		int likes, tInterDiaria;
		int anio, mes, dia;
		Relacion relacion;
		try {
			while (read.hasNext()) {
				usr1 = usuarios.get(read.next());
				usr2 = usuarios.get(read.next());
				tInterDiaria = read.nextInt();
				likes = read.nextInt();
				anio = read.nextInt();
				mes = read.nextInt();
				dia = read.nextInt();
				if (usr1 == null || usr2 == null)
					throw new UsuarioNoValidoException();
				relacion = new Relacion(usr1, usr2, tInterDiaria, likes, LocalDate.of(anio, mes, dia));
				if (relaciones.contains(relacion))
					throw new RelacionRepetidaException("relacion:" + usr1.getCodigo() + "-" + usr2.getCodigo());
				relaciones.add(relacion);

			}
		} catch (InputMismatchException e) {
			System.out.printf("Error archivo %s con formato invalido.", fileName);
		} finally {
			read.close();
		}
		return relaciones;
	}

}
