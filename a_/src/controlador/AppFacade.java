package controlador;

import gui.DesktopFrame;
import gui.RelacionesForm;
import gui.RelacionesList;
import gui.UsuariosForm;
import gui.UsuariosList;
import modelo.Usuario;
import negocio.Calculo;
import negocio.RedSocial;
import net.datastructures.TreeMap;

/**
 * Clase que provee las capas a la aplicacion final.
 * 
 * El facade es el punto de entrada a la capa de aplicación o bien, el punto de
 * contacto entre el frontend y el backend.
 */
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
	// DAO
	private RedSocial redSocial;

	/**
	 * 
	 */
	public AppFacade() {
		// capa de negocio
		calculo = Calculo.getCalculo();

		// capa de presentacion
		desktopFrame = new DesktopFrame();

		usuariosList = new UsuariosList();
		usuariosForm = new UsuariosForm();

		relacionesForm = new RelacionesForm();
		relacionesList = new RelacionesList();

		// controlador
		coordinador = new Coordinador();

		redSocial = new RedSocial();
	}

	/**
	 * 
	 * */
	public void cargarCalculo() {
		redSocial.setCoordinador(coordinador);

		calculo.calculoDatos((TreeMap<String, Usuario>) coordinador.listaUsuarios(), coordinador.listaRelaciones());
		calculo.setCoordinador(coordinador);
	}

	/**
	 * 
	 * */
	public void cargarFrame() {
		desktopFrame.setCoordinador(coordinador);
		desktopFrame.setVisible(true);
		usuariosForm.setCoordinador(coordinador);
		usuariosList.setCoordinador(coordinador);
		relacionesList.setCoordinador(coordinador);
		relacionesForm.setCoordinador(coordinador);

	}

	/**
	 * 
	 * */
	public void cargarCordinador() {
		coordinador.setCalculo(calculo);
		coordinador.setRedSocial(redSocial);
		coordinador.setDesktopFrame(desktopFrame);
		coordinador.setUsuariosList(usuariosList);
		coordinador.setUsuariosForm(usuariosForm);
		coordinador.setRelacionesForm(relacionesForm);
		coordinador.setRelacionesList(relacionesList);
	}

}// fin clase AppFacade