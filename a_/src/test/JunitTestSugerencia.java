package test;

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
import net.datastructures.Pair;
import net.datastructures.TreeMap;

public class JunitTestSugerencia {

	TreeMap<String, Usuario> usuarios = new TreeMap<>();
	List<Relacion> relaciones = new ArrayList<>();
	Calculo calculo;
	List<Usuario> listUsr;
	List<Pair<Usuario, Integer>> listAux;
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
		Usuario usuario = calculo.busqueda("101");

		listUsr = calculo.sugerenciaAmistad(usuario.getCodigo());
		for (Usuario usr : listUsr)
			assertTrue(!usuario.equals(usr));
	}

	@Test
	public void testSugerencia() {
		Usuario usuario = calculo.busqueda("101");

		listUsr = calculo.sugerenciaAmistad(usuario.getCodigo());//sugerencias
		listAux = calculo.mostrarAmigos(usuario.getCodigo()); // amigos

		Map<Usuario, Integer> amigos = calculo.mostrarAmigosM(usuario.getCodigo());
		
		System.out.println("sugerencias:"+listUsr.size());
		System.out.println("amigos:"+amigos.size());
		
		for (Usuario usr : listUsr){// sugerencias
			//assertNull(amigos.get(usr));
			//System.out.println(listAux.contains(usr));
			System.out.println(amigos.get(usr));
		
		}
		// for (Pair<Usuario,Integer> us :
		// calculo.mostrarAmigos(usr.getCodigo()))//amigos de sugerencia
		// ;
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
