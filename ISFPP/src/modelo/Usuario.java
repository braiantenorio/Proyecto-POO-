package modelo;

public class Usuario {

	private String codigo;
	private String nombre;
	private int edad;
	private String genero;
	private String ciudadAct;

	public Usuario(String codigo, String nombre, int edad, String genero, String ciudadAct) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.edad = edad;
		this.genero = genero;
		this.ciudadAct = ciudadAct;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCiudadAct() {
		return ciudadAct;
	}

	public void setCiudadAct(String ciudadAct) {
		this.ciudadAct = ciudadAct;
	}

	@Override
	public String toString() {
		return "[" + codigo + " " + nombre + " " + edad + " " + genero + " " + ciudadAct + "]";
	}

}
