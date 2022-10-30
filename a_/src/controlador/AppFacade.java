package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import datos.CargarDatos;
import datos.CargarParametros;
import datos.RelacionRepetidaException;
import datos.UsuarioRepetidoException;
import gui.DesktopFrame;
import gui.RelacionesForm;
import gui.RelacionesList;
import gui.UsuariosForm;
import gui.UsuariosList;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import negocio.RedSocial;
import net.datastructures.TreeMap;

//cambiar nombre clase
public class AppFacade {
	// LOGICA
	private Calculo calculo;
	// VISTA
	private DesktopFrame desktopFrame;
	private UsuariosForm usuariosForm;
	private UsuariosList usuariosList;
	private RelacionesForm relacionesForm;
	private RelacionesList relacionesList;
	// CONTROLADOR
	private Coordinador coordinador;
	// datos
	private CargarDatos cargarDatos;
	private CargarParametros cargarParametros;

	private RedSocial redSocial;

	public static TreeMap<String, Usuario> usuarios = null;
	public static List<Relacion> relaciones = null;

	/**
	 * 
	 */
	public AppFacade() {
		calculo = Calculo.getCalculo();
		desktopFrame = new DesktopFrame();
		coordinador = new Coordinador();
		cargarDatos = new CargarDatos();
		cargarParametros = new CargarParametros();

		usuariosList = new UsuariosList();
		usuariosForm = new UsuariosForm();

		relacionesForm = new RelacionesForm();
		relacionesList = new RelacionesList();

		redSocial = new RedSocial("");

	}

	public void cargarDatos() {

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
		} catch (UsuarioRepetidoException e) {
			JOptionPane.showMessageDialog(null, e);
			System.exit(-1);
		} catch (RelacionRepetidaException e) {
			JOptionPane.showMessageDialog(null, e);
			System.exit(-1);
		}
	}

	public void cargarCalculo() {
		// calculo.calculoDatos(usuarios, relaciones);
		calculo.calculoDatos((TreeMap) calculo.mostrarUsuarios(), calculo.mostrarRelaciones());
		calculo.setCoordinador(coordinador);
	}

	public void cargarFrame() {
		desktopFrame.setCoordinador(coordinador);
		desktopFrame.setVisible(true);
		usuariosForm.setCoordinador(coordinador);
		usuariosList.setCoordinador(coordinador);
		relacionesList.setCoordinador(coordinador);
		relacionesForm.setCoordinador(coordinador);

	}

	public void cargarCordinador() {
		coordinador.setCalculo(calculo);
		coordinador.setDesktopFrame(desktopFrame);
		coordinador.setUsuariosList(usuariosList);
		coordinador.setUsuariosForm(usuariosForm);
		coordinador.setRelacionesForm(relacionesForm);
		coordinador.setRelacionesList(relacionesList);
	}

}
