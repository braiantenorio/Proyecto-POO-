package test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datos.CargarDatos;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import negocio.UsuarioNoValidoException;
import net.datastructures.TreeMap;

public class JunitTestAntiguedad {

	TreeMap<String, Usuario> usuarios = new TreeMap<>();
	List<Relacion> relaciones = new ArrayList<>();
	Calculo calculo;
	List<Usuario> listUsr;
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
		calculo = new Calculo(usuarios, relaciones);
		listUsr = calculo.mostrarUsuarios();
	}

	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test (expected = UsuarioNoValidoException.class)
	public void testException1() {
		calculo.antiguedad(null, null);
	}
	
	@Test (expected = UsuarioNoValidoException.class)
	public void testException2() {
		Usuario usr1 = listUsr.get(0);
		Usuario usr2 = listUsr.get(1);
		
		calculo.antiguedad(usr1, usr2);
		
	}
}
