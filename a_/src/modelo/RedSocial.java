package modelo;

import java.util.ArrayList;
import java.util.List;

import datos.UsuarioRepetidoException;

public class RedSocial {

	private String nombre;
	private List<Usuario> usuarios;

	/**
	 * @param nombre
	 */
	public RedSocial(String nombre) {
		this.nombre = nombre;
		this.usuarios = new ArrayList<Usuario>();
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
		if (usuarios.contains(usuario))
			throw new UsuarioRepetidoException();
		usuarios.add(usuario);
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public Usuario getUsuario(Usuario usuario) {
		int pos = usuarios.indexOf(usuario);
		if (pos == -1)
			return null;
		return usuarios.get(pos);
	}

}
