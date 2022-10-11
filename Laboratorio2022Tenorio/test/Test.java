package test;

import datos.CargarDatos;
import datos.CargarParametros;
import logica.Calculo;
import modelo.Relacion;
import modelo.Usuario;
import java.util.List;

import net.datastructures.Entry;
import net.datastructures.TreeMap;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws Exception {

		TreeMap<String, Usuario> usuarios = null;
		List<Relacion> relaciones = null;

		// Cargar datos
		try {
			CargarParametros.parametros();
		} catch (IOException e) {
			System.exit(-1);
		}

		try {
			usuarios = CargarDatos.cargarUsuarios(CargarParametros.getArchivoUsuario());
			relaciones = CargarDatos.crearRelaciones(CargarParametros.getArchivoRelaciones());
		} catch (FileNotFoundException e) {
			System.exit(-1);
		}

		// Calculo
		Calculo<Usuario> c = new Calculo<Usuario>(usuarios, relaciones);
		

		for(Entry<Usuario,Integer> s: c.centralidad()){
		
			System.out.println(s.getKey().getNombre() + " " + s.getValue());
			
		}

	}
}
