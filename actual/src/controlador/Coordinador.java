package controlador;

import java.util.List;

import datos.RelacionRepetidaException;
import datos.UsuarioRepetidoException;
import gui.Constantes;
import gui.DesktopFrame;
import gui.RelacionesForm;
import gui.RelacionesList;
import gui.UsuariosForm;
import gui.UsuariosList;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import negocio.RedSocial;
import net.datastructures.Entry;
import net.datastructures.Map;

/***
 * 
 * */
public class Coordinador {

	private RedSocial redSocial;
	private Calculo calculo;
	private DesktopFrame desktopFrame;
	private UsuariosForm usuariosForm;
	private UsuariosList usuariosList;
	private RelacionesForm relacionesForm;
	private RelacionesList relacionesList;

	/**
	 * 
	 */
	public void setDesktopFrame(DesktopFrame desktopFrame) {
		this.desktopFrame = desktopFrame;
	}

	/**
	 * 
	 */
	public DesktopFrame getDesktopFrame() {
		return desktopFrame;
	}

	/**
	 * 
	 */
	public void setUsuariosForm(UsuariosForm usuariosForm) {
		this.usuariosForm = usuariosForm;
	}

	/**
	 * 
	 */
	public UsuariosForm getUsuariosForm() {
		return usuariosForm;
	}

	/**
	 * 
	 */
	public void setUsuariosList(UsuariosList usuariosList) {
		this.usuariosList = usuariosList;
	}

	/**
	 * 
	 */
	public UsuariosList getUsuariosList() {
		return usuariosList;
	}

	/**
	 * @param relacionesForm the relacionesForm to set
	 */
	public void setRelacionesForm(RelacionesForm relacionesForm) {
		this.relacionesForm = relacionesForm;
	}

	/**
	 * @return the relacionesForm
	 */
	public RelacionesForm getRelacionesForm() {
		return relacionesForm;
	}

	/**
	 * @param relacionesList the relacionesList to set
	 */
	public void setRelacionesList(RelacionesList relacionesList) {
		this.relacionesList = relacionesList;
	}

	/**
	 * @return the relacionesList
	 */
	public RelacionesList getRelacionesList() {
		return relacionesList;
	}

	/**
	 * 
	 * */
	public Calculo getCalculo() {
		return calculo;
	}

	/**
	 * 
	 * */
	public void setCalculo(Calculo calculo) {
		this.calculo = calculo;
	}

	/**
	 * 
	 * */
	public RedSocial getRedSocial() {
		return redSocial;
	}

	/**
	 * 
	 * */
	public void setRedSocial(RedSocial redSocial) {
		this.redSocial = redSocial;
	}

	/**
	 * 
	 * */
	public List<Entry<Usuario, Integer>> centralidad() {
		return calculo.centralidad();
	}

	/**
	 * 
	 * */
	public double gradoMedio() {
		return calculo.gradoMedio();

	}

	/**
	 * 
	 * */
	public List<Relacion> antiguedad(Usuario usu1, Usuario usu2) throws IllegalArgumentException {
		return calculo.antiguedad(usu1, usu2);
	}

	/**
	 * 
	 * */
	public List<Entry<Usuario, Integer>> usuariosDensaConectados() {
		return calculo.usuariosDensaConectados();
	}

	/**
	 * 
	 * */
	public Map<Usuario, Integer> mapaAmigos(String codUsuario1) {
		return calculo.mapaAmigos(codUsuario1);
	}

	/**
	 * 
	 * */
	public List<Usuario> sugerenciaAmistad(String usr) {
		return calculo.sugerenciaAmistad(usr);
	}

	// DAO

	/**
	 * 
	 * */
	public void mostrarUsuarioList() {
		usuariosList.loadTable();
		usuariosList.setVisible(true);
	};

	/**
	 * 
	 * */
	public void mostrarRelacionList() {
		relacionesList.loadTable();
		relacionesList.setVisible(true);
	}

	/**
	 * 
	 * */
	public void insertarUsuarioForm() {
		usuariosForm.accion(Constantes.INSERTAR, null);
		usuariosForm.setVisible(true);
	}

	/**
	 * 
	 * */
	public void modificarUsuarioForm(Usuario usr) {
		usuariosForm.accion(Constantes.MODIFICAR, usr);
		usuariosForm.setVisible(true);

	}

	/**
	 * 
	 * */
	public void borrarUsuarioForm(Usuario usuario) {
		usuariosForm.accion(Constantes.BORRAR, usuario);
		usuariosForm.setVisible(true);
	}

	/**
	 * 
	 * */
	public void insertarRelacionesForm() {
		relacionesForm.accion(Constantes.INSERTAR, null);
		relacionesForm.setVisible(true);
		usuariosForm.setVisible(false);
	}

	/**
	 * 
	 * */
	public void modificarRelacionForm(Relacion relacion) {
		relacionesForm.accion(Constantes.MODIFICAR, relacion);
		relacionesForm.setVisible(true);
	}

	/**
	 * 
	 * */
	public void borrarRelacionForm(Relacion relacion) {
		relacionesForm.accion(Constantes.BORRAR, relacion);
		relacionesForm.setVisible(true);
	}

	/**
	 * 
	 * */
	public void cancelarUsuario() {
		usuariosForm.setVisible(false);
	}

	/**
	 * 
	 * */
	public void cancelarList() {
		usuariosList.setVisible(false);
	}

	/**
	 * 
	 * */
	public void cancelarRelacion() {
		relacionesForm.setVisible(false);
	}

	/**
	 * 
	 * */
	public void cancelarListRelacion() {
		relacionesList.setVisible(false);

	}

	/**
	 * 
	 * */
	public Usuario buscarUsuario(Usuario usuario) {
		return calculo.busquedaUsuario(usuario.getCodigo()); //redSocial.buscarUsuario(usuario.getCodigo());
	};

	/**
	 * 
	 * */
	public Map<String, Usuario> listaUsuarios() {
		return redSocial.mostrarUsuarios();
	}

	/**
	 * 
	 * */
	public void insertarUsuario(Usuario usuario) throws UsuarioRepetidoException {
		redSocial.agregarUsuario(usuario);
		usuariosForm.setVisible(false);
		usuariosList.addRow(usuario);

	}

	/**
	 * 
	 * */
	public void modificarUsuario(Usuario usuario) {
		redSocial.modificarUsuario(usuario);
		usuariosList.setAccion(Constantes.MODIFICAR);
		usuariosList.setUsuario(usuario);
		usuariosForm.setVisible(false);
	}

	/**
	 * 
	 * */
	public void borrarUsuario(Usuario usuario) {
		redSocial.borrarUsuario(usuario);
		usuariosList.setAccion(Constantes.BORRAR);
		usuariosForm.setVisible(false);
	}

	/**
	 * 
	 * */
	public List<Relacion> listaRelaciones() {
		return redSocial.mostrarRelaciones();
	}

	/**
	 * 
	 * */
	public Relacion buscarRelacion(Relacion relacion) {
		return redSocial.buscarRelacion(relacion);
	}

	/**
	 * 
	 * */
	public void borrarRelacion(Relacion relacion) {
		redSocial.borrarRelacion(relacion);
		usuariosList.setAccion(Constantes.BORRAR);

		usuariosForm.setVisible(false);
	}

	/**
	 * 
	 * */
	public void insertarRelacion(Relacion relacion) throws RelacionRepetidaException {
		redSocial.agregarRelacion(relacion);
		relacionesForm.setVisible(false);
		relacionesList.addRow(relacion);

	}

	/**
	 * 
	 * */
	public void modificarRelacion(Relacion relacion) {
		redSocial.modificarRelacion(relacion);
		relacionesList.setAccion(Constantes.MODIFICAR);
		relacionesList.setRelacion(relacion);
		relacionesForm.setVisible(false);

	}

	/**
	 * 
	 * */
	public void actualizarList() {
		relacionesList.setVisible(false);
		relacionesList.loadTable();
		relacionesList.setVisible(true);

	}

}// fin clase Coordinador