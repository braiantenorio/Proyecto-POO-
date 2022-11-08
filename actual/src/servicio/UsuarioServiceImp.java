package servicio;

import conexion.Factory;
import dao.UsuarioDAO;
import modelo.Usuario;
import net.datastructures.Map;

public class UsuarioServiceImp implements UsuarioService {

	private UsuarioDAO usuarioDAO;

	public UsuarioServiceImp() {
		usuarioDAO = (UsuarioDAO) Factory.getInstancia("USUARIO");
	}

	@Override
	public void insertar(Usuario usuario) {
		usuarioDAO.insertar(usuario);
	}

	@Override
	public void actualizar(Usuario usuario) {
		usuarioDAO.actualizar(usuario);
	}

	@Override
	public void borrar(Usuario usuario) {
		usuarioDAO.borrar(usuario);

	}

	@Override
	public Map<String,Usuario> buscarTodos() {
		return usuarioDAO.buscarTodos();
	}

	
}
