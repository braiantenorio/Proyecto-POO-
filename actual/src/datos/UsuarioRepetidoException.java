package datos;

// si un usuario se repite
public class UsuarioRepetidoException extends RuntimeException {

	public UsuarioRepetidoException() {
		super();
	}

	public UsuarioRepetidoException(String message) {
		super(message);
	}

}
