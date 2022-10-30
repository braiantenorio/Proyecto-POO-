package negocio;

import java.util.ArrayList;
import java.util.List;

import datos.UsuarioRepetidoException;
import modelo.Relacion;
import modelo.Usuario;
import net.datastructures.Map;
import servicio.RelacionService;
import servicio.UsuarioService;
import servicio.UsuarioServiceImp;

public class RedSocial {

	private String nombre;
	private Map<String, Usuario> usuarios;
	private List<Relacion> relaciones;
	private UsuarioService usuarioService;
	private RelacionService relacionService;

	/**
	 * @param nombre
	 */
	public RedSocial(String nombre) {
		this.nombre = nombre;

		usuarioService = new UsuarioServiceImp();
		//relaciones = relacionService.buscarTodos();
		usuarios = usuarioService.buscarTodos();
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void agregarUsuario(Usuario usuario) {
		if (usuarios.get(usuario.getCodigo()) != null)
			throw new UsuarioRepetidoException();
		usuarios.put(usuario.getCodigo(), usuario);
		usuarioService.insertar(usuario);
	}

	public void modificarUsuario(Usuario usuario) {
		usuarios.put(usuario.getCodigo(), usuario);
		usuarioService.actualizar(usuario);
	}

	public void borrarUsuario(Usuario usuario) {
		usuarios.remove(usuario.getCodigo());
		usuarioService.borrar(usuario);
	}

	public Map<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public Usuario getUsuario(Usuario usuario) {
		return usuarios.get(usuario.getCodigo());
	}

}
