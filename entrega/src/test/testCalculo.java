package test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import datos.CargarDatos;
import datos.RelacionRepetidaException;
import datos.UsuarioRepetidoException;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import net.datastructures.Entry;
import net.datastructures.Map;
import net.datastructures.TreeMap;

// test visual para depuracion
public class testCalculo {

	public static void main(String[] args) {
		TreeMap<String, Usuario> usuarios = new TreeMap<>();
		List<Relacion> relaciones = new ArrayList<>();
		Calculo calculo = Calculo.getCalculo();

		try {
			usuarios = CargarDatos.cargarUsuarios("users.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			e.printStackTrace();
		}

		try {
			relaciones = CargarDatos.crearRelaciones("relations.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (RelacionRepetidaException e) {
			e.printStackTrace();
		}

		calculo.calculoDatos(usuarios, relaciones);
		
		
		
		List<Usuario> listUsr = new ArrayList<>();

		listUsr = calculo.sugerenciaAmistad("101");
		int i = 0;

		// modificacion metodo sugerenciaAmistad
		System.out.println("amigos de " + calculo.busquedaUsuario("101"));
		for (Usuario usuario : listUsr) {
			System.out.println("\t:" + i++ + usuario);
		}
		// System.out.println(listUsr + "\n\n");

		Usuario usr1 = calculo.busquedaUsuario("101");
		Usuario usr2 = calculo.busquedaUsuario("107");
		// Period
		// System.out.println("anios:" + usr.getEdad().getYears() + ", meses:" +
		// usr.getEdad().getMonths() + ", dias:"
		// + usr.getEdad().getDays());

		// ver todos los valores posibles
		// for (Genero g : Genero.values()) {
		// System.out.println(g);
		// }

		// enum genero
		// System.out.println(usr.getGenero());

		// System.out.println(relaciones);

		//
		System.out.println(calculo.mostrarRelaciones());
		
		System.out.println("\n"+"centralidad:");
		List<Entry<Usuario, Integer>> centralidad = calculo.centralidad();
		for(Entry<Usuario, Integer> entry:centralidad) 
			System.out.println(entry.getKey()+":"+entry.getValue());
		
		System.out.println("\n"+"usuarios conectados:");
		List<Entry<Usuario, Integer>> usrConectados = calculo.usuariosDensaConectados();
		System.out.println(usrConectados);
		for (Entry<Usuario,Integer> entry : usrConectados) {
			System.out.println(entry.getKey()+":"+entry.getValue());
			
		}
		System.out.println(usrConectados.size());
		
		System.out.println("\n"+"amigos:");
		Map<Usuario, Integer> amigos = calculo.mapaAmigos("101");
		System.out.println(amigos);
		for (Entry<Usuario, Integer> amigo : amigos.entrySet()) {
			System.out.println(amigo.getKey()+"-"+amigo.getValue());
		}
		calculo.antiguedad(usr1,usr2 );
		
		System.out.println(calculo.sugerenciaAmistad("101"));
		
		System.exit(0);
	}
}
