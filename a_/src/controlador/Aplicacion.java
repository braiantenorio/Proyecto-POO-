package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import datos.CargarDatos;
import datos.CargarParametros;
import gui.DesktopFrame;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import net.datastructures.TreeMap;

public class Aplicacion {

	public static TreeMap<String, Usuario> usuarios = null;
	public static List<Relacion> relaciones = null;

	// LOGICA
	private Calculo calculo;
	// VISTA
	private DesktopFrame desktopFrame;
	// CONTROLADOR
	private Coordinador coordinador;

	public static void main(String[] args) {

		Aplicacion miAplicacion = new Aplicacion();
		miAplicacion.iniciar();

	}

	private void iniciar() {
		/* Se instancian las clases */
		calculo = new Calculo();
		desktopFrame = new DesktopFrame();
		coordinador = new Coordinador();

		/* Se establecen las relaciones entre clases */
		calculo.setCoordinador(coordinador);
		desktopFrame.setCoordinador(coordinador);

		/* Se establecen relaciones con la clase coordinador */
		coordinador.setCalculo(calculo);
		coordinador.setDesktopFrame(desktopFrame);

		cargarDatos();
		calculo.calculoDatos(usuarios, relaciones);
		desktopFrame.setVisible(true);

	}

	private void cargarDatos() {

		try {
			CargarParametros.parametros();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
			System.exit(-1);
		}

		try {
			usuarios = CargarDatos.cargarUsuarios(CargarParametros.getArchivoUsuario());
			relaciones = CargarDatos.crearRelaciones(CargarParametros.getArchivoRelaciones());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e);
			System.exit(-1);
		}
	}

}
