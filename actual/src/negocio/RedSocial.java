package negocio;

import java.util.ArrayList;
import java.util.List;

import controlador.Coordinador;
import datos.RelacionRepetidaException;
import datos.UsuarioRepetidoException;
import modelo.Relacion;
import modelo.Usuario;
import net.datastructures.Map;
import net.datastructures.TreeMap;
import servicio.RelacionService;
import servicio.RelacionServiceImp;
import servicio.UsuarioService;
import servicio.UsuarioServiceImp;

public final class RedSocial {

	private Coordinador coordinador;

	private Map<String, Usuario> usuarios;
	private List<Relacion> relaciones;
	private UsuarioService usuarioService;
	private RelacionService relacionService;

	public RedSocial() {
		usuarios = new TreeMap<String, Usuario>();
		relaciones = new ArrayList<Relacion>();

		usuarioService = new UsuarioServiceImp();
		usuarios = usuarioService.buscarTodos();
		relacionService = new RelacionServiceImp();
		relaciones.addAll(relacionService.buscarTodos());
	}

	/**
	 * Muestra todos los usuarios de la red social.
	 * 
	 * @return Mapa con todos los usuarios, clave el ID del usuario.
	 */
	public Map<String, Usuario> mostrarUsuarios() {
		return usuarios;

	}

	/**
	 * Muestra todas las relaciones de la red social.
	 * 
	 * @return Una lista con todos las relacion.
	 */
	public List<Relacion> mostrarRelaciones() {
		return relaciones;
	}

	/**
	 * @param coordinador El coordinador a establecer
	 */
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	// DAO
	/**
	 * Modifica un Usuario existente
	 * 
	 * @param usuario El usuario a modificar
	 */
	public void modificarUsuario(Usuario usuario) {
		usuarios.put(usuario.getCodigo(), usuario);
		usuarioService.actualizar(usuario);
	}

	/**
	 * Busca el mismo usuario en los distintos usuarios.
	 * 
	 * @param usuario El usuario que se busca.
	 * @return si pertenece a la lista el mismo Usuario, sino null
	 */
	public Usuario buscarUsuario(String id) {
		if (id == null || usuarios.get(id) == null)
			throw new UsuarioNoValidoException();

		return usuarios.get(id);
	}

	/**
	 * Agrega un Usuario no existente, si existe lanza excepcion
	 * 
	 * @throws RelacionNoValidaException si la relacion ya existe
	 * @param usuario El usuario a agregar
	 */
	public void agregarUsuario(Usuario usuario) {
		if (usuarios.get(usuario.getCodigo()) != null)
			throw new UsuarioRepetidoException();

		usuarios.put(usuario.getCodigo(), usuario);
		usuarioService.insertar(usuario);
	}

	/**
	 * Borra un usuario existente.
	 * 
	 * @param usuario El usuario a borrar
	 */
	public void borrarUsuario(Usuario usuario) {
		if (usuarios.get(usuario.getCodigo()) == null)
			throw new UsuarioNoValidoException();

		usuarios.remove(usuario.getCodigo());
		usuarioService.borrar(usuario);
	}

	/**
	 * Busca la misma relacion en las distintas relaciones.
	 * 
	 * @param relacion La relacion que se busca.
	 * @return si pertenece a la lista la misma Relacion, sino null
	 */
	public Relacion buscarRelacion(Relacion relacion) {
		int pos = relaciones.indexOf(relacion);
		if (pos == -1)
			throw new RelacionNoValidaException();
		return relaciones.get(pos);
	}

	/**
	 * Agrega una Relacion no existente, si existe lanza excepcion
	 * 
	 * @throws RelacionNoValidaException si la relacion ya existe
	 * @param relacion La relacion a agregar
	 */
	public void agregarRelacion(Relacion relacion) {
		if (relaciones.contains(relacion))
			throw new RelacionRepetidaException();
		relaciones.add(relacion);
		relacionService.insertar(relacion);
	}

	/**
	 * Borra una Relacion existente
	 * 
	 * @param relacion La relacion a borrar
	 */
	public void borrarRelacion(Relacion relacion) {
		Relacion rl = buscarRelacion(relacion);
		relaciones.remove(rl);
		relacionService.borrar(relacion);

	}

	/**
	 * Modifica una Relacion existente
	 * 
	 * @param relacion La relacion a modificar
	 */
	public void modificarRelacion(Relacion relacion) {
		int pos = relaciones.indexOf(relacion);
		if (pos == -1)
			throw new RelacionNoValidaException();
		relaciones.set(pos, relacion);
		relacionService.actualizar(relacion);

	}

}
