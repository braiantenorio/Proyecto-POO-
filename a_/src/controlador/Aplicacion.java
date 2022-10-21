package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import datos.CargarDatos;
import datos.CargarParametros;
import gui.DesktopFrame;
import gui.RedesSocialesCons;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import net.datastructures.TreeMap;
import gui.Constante;

public class Aplicacion {

	public static TreeMap<String, Usuario> usuarios;
	public static List<Relacion> relaciones;

	// Logica
	private Calculo calculo;
	// VISTA
	private DesktopFrame desktopFrame;
	private RedesSocialesCons redesSocialesform;
	// CONTROLADOR
	private Coordinador coordinador;

	public static void main(String[] args) {

		Aplicacion miAplicacion = new Aplicacion();

		miAplicacion.iniciar();

	}

	private void iniciar() {
		/* Se instancian las clases */
		calculo = Calculo.getCalculo();
		desktopFrame = new DesktopFrame();
		redesSocialesform = new RedesSocialesCons();
		coordinador = new Coordinador();

		/* Se establecen las relaciones entre clases */
		calculo.setCoordinador(coordinador);
		desktopFrame.setCoordinador(coordinador);
		redesSocialesform.setCoordinador(coordinador);
		/* Se establecen relaciones con la clase coordinador */
		coordinador.setCalculo(calculo);
		coordinador.setDesktopFrame(desktopFrame);
		coordinador.setRedesSocialesForm(redesSocialesform);
		//cargarEmpleados();
		cargarDatos();
		desktopFrame.setVisible(true);

	}
	
	private void cargarDatos() {


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
	}

}
