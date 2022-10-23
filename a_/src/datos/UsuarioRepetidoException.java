package datos;

// si dos usuarios iguales
public class UsuarioRepetidoException extends RuntimeException {

	public UsuarioRepetidoException(String message) {
		super(message);
	}

}
