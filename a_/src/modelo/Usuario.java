package modelo;

import java.time.LocalDate;
import java.time.Period;

public class Usuario {

	private String codigo;
	private String nombre;
	private LocalDate fechadenac;
	private Genero genero;
	private String ciudadAct;
	private NivelAcademico nivelAcademico;

	/**
	 * constructor
	 * 
	 * @param codigo         ID del usuario.
	 * @param nombre         El nombre del usuario.
	 * @param fechadenac     La fecha de nacimiento del usuario.
	 * @param genero         El genero del usuario.
	 * @param ciudadAct      La localidad actual del usuario.
	 * @param nivelAcademico El nivel del usuario
	 */
	public Usuario(String codigo, String nombre, LocalDate fechadenac, Genero genero, String ciudadAct,
			NivelAcademico nivelAcademico) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.fechadenac = fechadenac;
		this.genero = genero;
		this.ciudadAct = ciudadAct;
		this.nivelAcademico = nivelAcademico;

	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	/**
	 * @return the fechadenac
	 */
	public LocalDate getFechadenac() {
		return fechadenac;
	}

	/**
	 * @param fechadenac the fechadenac to set
	 */
	public void setFechadenac(LocalDate fechadenac) {
		this.fechadenac = fechadenac;
	}

	/**
	 * @return the genero
	 */
	public Genero getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	/**
	 * @return the ciudadAct
	 */
	public String getCiudadAct() {
		return ciudadAct;
	}

	/**
	 * @param ciudadAct the ciudadAct to set
	 */
	public void setCiudadAct(String ciudadAct) {
		this.ciudadAct = ciudadAct;
	}

	/**
	 * @return the nivelAcademico
	 */
	public NivelAcademico getNivelAcademico() {
		return nivelAcademico;
	}

	/**
	 * @param nivelAcademico the nivelAcademico to set
	 */
	public void setNivelAcademico(NivelAcademico nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	/**
	 * Retorna la edad del Usuario con los anios meses y dias.
	 * 
	 * @return Period
	 */
	public Period getEdad() {
		return Period.between(fechadenac, LocalDate.now());
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

	/**
	 * Produce una representacion de tipo String, del contenido de la clase.
	 */
	@Override
	public String toString() {
		return "codigo=" + codigo + ", nombre=" + nombre + ", edad=" + getEdad().getYears() + ", genero=" + genero
				+ ", ciudadAct=" + ciudadAct + ", nivelAcademico=" + nivelAcademico;
	}

}
