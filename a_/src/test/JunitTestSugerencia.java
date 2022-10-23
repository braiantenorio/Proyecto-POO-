package test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datos.CargarDatos;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import net.datastructures.Pair;
import net.datastructures.TreeMap;

public class JunitTestSugerencia {

	TreeMap<String, Usuario> usuarios = new TreeMap<>();
	List<Relacion> relaciones = new ArrayList<>();
	Calculo calculo;
	List<Pair<Usuario,Integer>> listUsr;

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
		
	}

	@Test
	public void test() {
		calculo = new Calculo(usuarios, relaciones);
		listUsr = calculo.mostrarAmigos("101");
		
		System.out.println(calculo.mostrarAmigos("101"));
		//System.out.println(calculo.sugerenciaAmistad("101"));
		for (Pair<Usuario, Integer> usuario : listUsr)
			System.out.println("\n"+usuario.getFirst());
			
	}

}
