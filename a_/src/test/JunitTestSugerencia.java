package test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import net.datastructures.Map;
import net.datastructures.TreeMap;

public class JunitTestSugerencia {

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

	}

	@Test
	public void testDistinto() {
		// que no sugiera al mismo usuario
		Usuario usuario = calculo.busquedaUsuario("101");

		listUsr = calculo.sugerenciaAmistad(usuario.getCodigo());
		for (Usuario usr : listUsr)
			assertTrue(!usuario.equals(usr));
	}

	@Test
	public void testSugerencia() {
		// no sugerir amigos compartidos
		Usuario usuario = calculo.busquedaUsuario("101");
		listUsr = calculo.sugerenciaAmistad(usuario.getCodigo());// sugerencias

		Map<Usuario, Integer> amigos = calculo.mapaAmigos(usuario.getCodigo());// amigos

		for (Usuario usr : listUsr) {// sugerencias
			assertNull(amigos.get(usr));
		}
	}
	
	

	@Test(expected = UsuarioNoValidoException.class)
	public void testExceptionNulo() {
		listUsr = calculo.sugerenciaAmistad(null);
	}

	@Test(expected = UsuarioNoValidoException.class)
	public void testExceptionNoExiste() {
		listUsr = calculo.sugerenciaAmistad("1");
	}
}
