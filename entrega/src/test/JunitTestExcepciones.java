package test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import datos.CargarDatos;
import datos.RelacionRepetidaException;
import datos.UsuarioRepetidoException;
import modelo.Relacion;
import modelo.Usuario;
import net.datastructures.TreeMap;

public class JunitTestExcepciones {

	TreeMap<String, Usuario> usuarios = new TreeMap<>();
	List<Relacion> relaciones = new ArrayList<>();

	@Test(expected = UsuarioRepetidoException.class)
	public void testUsuarioRepetido() {
		try {
			usuarios = CargarDatos.cargarUsuarios("usuarioRepetido.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = RelacionRepetidaException.class)
	public void testRelacionRep1() {
		// relaciones (a,b) y (b,a)

		try {
			usuarios = CargarDatos.cargarUsuarios("users.txt");
			relaciones = CargarDatos.crearRelaciones("relacionRepetidos.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = RelacionRepetidaException.class)
	public void testRelacionRep2() {
		// relaciones (a,b) y (a,b)
		try {
			usuarios = CargarDatos.cargarUsuarios("users.txt");
			relaciones = CargarDatos.crearRelaciones("relacionRepetidos2.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}