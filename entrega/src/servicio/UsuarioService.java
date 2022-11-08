package servicio;

import modelo.Usuario;
import net.datastructures.Map;

public interface UsuarioService {
	
	void insertar(Usuario usuario);

	void actualizar(Usuario usuario);

	void borrar(Usuario usuario);

	Map<String, Usuario> buscarTodos();

}
