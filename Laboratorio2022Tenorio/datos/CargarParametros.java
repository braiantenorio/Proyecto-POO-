package datos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CargarParametros {

	private static String archivoUsuario;
	private static String archivoRelaciones;

	/**
	 * Carga los parametros de un archivo de configuracion
	 * 
	 * @throws IOException si no encuentra el archivo o no tiene el formato
	 *                     especificado
	 */
	public static void parametros() throws IOException {

		Properties prop = new Properties();
		InputStream input = new FileInputStream("config.properties");
		prop.load(input);
		// obtiene el valor de la propiedad
		archivoUsuario = prop.getProperty("usuario");
		archivoRelaciones = prop.getProperty("relaciones");
	}

	public static String getArchivoUsuario() {
		return archivoUsuario;
	}

	public static String getArchivoRelaciones() {
		return archivoRelaciones;
	}
}
