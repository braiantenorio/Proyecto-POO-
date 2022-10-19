package modelo;

import java.time.LocalDate;
import java.time.Period;

public class Usuario {

	private String codigo;
	private String nombre;
	private LocalDate fechadenac;
	private String genero;
	private String ciudadAct;

	public Usuario(String codigo, String nombre, LocalDate fechadenac, String genero, String ciudadAct) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.fechadenac = fechadenac;
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

	public Period getEdad() {
		return Period.between(fechadenac, LocalDate.now());
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + codigo + " " + nombre + " " + getEdad().getYears() + " " + genero + " " + ciudadAct + "]";
	}

}
