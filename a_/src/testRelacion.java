import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import datos.CargarDatos;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import net.datastructures.TreeMap;

public class testRelacion {

	public TreeMap<String, Usuario> usuarios = new TreeMap<>();
	public List<Relacion> relaciones = new ArrayList<>();
	public Calculo calculo = new Calculo();
	private ArrayList listUsr;

	@Before
	void setUp() throws FileNotFoundException {
		try {
			usuarios = CargarDatos.cargarUsuarios("users.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			relaciones = CargarDatos.crearRelaciones("relations.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calculo = new Calculo(usuarios, relaciones);
		listUsr = new ArrayList<>();
		
	}

	@Test
	public void test() {
		List<Usuario> listUsr = new ArrayList<Usuario>();

		System.out.println(calculo.sugerenciaAmistad("101"));
		for (Usuario usuario : listUsr)
			//System.out.println(usuario);
			;
	}

}
