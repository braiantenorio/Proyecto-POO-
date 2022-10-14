package aplicacion;

import datos.CargarDatos;
import datos.CargarParametros;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import presentacion.Pantalla;

import net.datastructures.TreeMap;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Aplicacion {

	public static void main(String[] args) throws Exception {

		TreeMap<String, Usuario> usuarios = null;
		List<Relacion> relaciones = null;

		// Cargar datos
		try {
			CargarParametros.parametros();
		} catch (IOException e) {
			System.err.print(Constante.ERROR_PARAMETROS);
			System.exit(-1);
		}

		try {
			usuarios = CargarDatos.cargarUsuarios(CargarParametros.getArchivoUsuario());
			relaciones = CargarDatos.crearRelaciones(CargarParametros.getArchivoRelaciones());
		} catch (FileNotFoundException e) {
			System.err.print(Constante.ERROR_ARCHIVO);
			System.exit(-1);
		}

		Calculo c = new Calculo(usuarios, relaciones);
		boolean seguir = true;
		while (seguir) {
			int opcion = Pantalla.opcion();
			switch (opcion) {
				case (Constante.SALIR):
					seguir = false;
					Pantalla.despedida();
					System.exit(-1);
					break;
				case (Constante.MOSTRAR_USUARIOS):
					Pantalla.mostrarUsuarios(c.mostrarUsuarios());
					break;
				case (Constante.GRADOMEDIO):
					Pantalla.gradoMedio(c.gradoMedio());
					break;
				case (Constante.CENTRALIDAD):
					Pantalla.centralidad(c.centralidad());
					break;
				case (Constante.ANTIGUEDAD):
					String src = Pantalla.ingresarUsuario1();
					String target = Pantalla.ingresarUsuario2();
					try {
						Pantalla.antiguedad(c.antiguedad(usuarios.get(src), usuarios.get(target)));
					} catch (NullPointerException e) {
						Pantalla.error(Constante.ERROR_CODIGO_INVALIDO);
					} catch (IllegalArgumentException e) {
						Pantalla.error(Constante.ERROR_USUARIO_SAMISTADES);
					}
					break;
				default:
					Pantalla.error(Constante.ERROR_OPCION);
			}
		}
	}
}
