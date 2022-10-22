package test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import datos.CargarDatos;
import modelo.Genero;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import net.datastructures.TreeMap;


// test visual para depuracion
public class testCalculo {

	public static void main(String[] args) {
		TreeMap <String, Usuario> usuarios = new TreeMap<>();
		List<Relacion> relaciones = new ArrayList<>();
		Calculo calculo = new Calculo();
		
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
		List<Usuario> listUsr = new ArrayList<>();
		
		listUsr = calculo.sugerenciaAmistad("101");
		int i = 0;
		
		// modificacion metodo sugerenciaAmistad
		System.out.println("amigos de "+calculo.busqueda("101"));
		for (Usuario usuario : listUsr) {
			System.out.println("\t:"+i+++usuario);
		}
			System.out.println(listUsr+"\n\n");	
			
		Usuario usr = calculo.busqueda("102");
		
		// Period 
		System.out.println(
				"anios:"+usr.getEdad().getYears()+
				", meses:"+usr.getEdad().getMonths()+
				", dias:"+usr.getEdad().getDays());
		
		// ver todos los valores posibles
		for (Genero g : Genero.values()) {
			System.out.println(g);
		}
		
		// enum genero
		System.out.println(usr.getGenero());
		
		
	}
}
