package test;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datos.CargarDatos;
import modelo.Genero;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import negocio.UsuarioNoValidoException;
import net.datastructures.Map;
import net.datastructures.TreeMap;

public class JunitTestAntiguedad {

	TreeMap<String, Usuario> usuarios = new TreeMap<>();
	List<Relacion> relaciones = new ArrayList<>();
	Calculo calculo;
	Map<String,Usuario> mapUsr;
	List<Relacion> listRel;

	@Before
	public void setUp() throws FileNotFoundException {
		try {
			usuarios = CargarDatos.cargarUsuarios("users.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			relaciones = CargarDatos.crearRelaciones("relations.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		calculo = Calculo.getCalculo();
		calculo.calculoDatos(usuarios, relaciones);
		mapUsr = calculo.mostrarUsuarios();
	}

	@Test (expected = UsuarioNoValidoException.class)
	public void testException1() {
		calculo.antiguedad(null, null);
	}
	
	@Test (expected = UsuarioNoValidoException.class)
	public void testException2() {
		Usuario usr1 = mapUsr.get("102");
		// USUARIOS SIN RELACION ALGUNA
		Usuario usr2 = new Usuario("1", "juan", LocalDate.of(2002, 8, 23), Genero.valueOf("M"), null, null);
		calculo.antiguedad(usr1, usr2);
		
	}
}
