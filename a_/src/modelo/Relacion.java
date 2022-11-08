package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Relacion {
	private Usuario usr1;
	private Usuario usr2;
	private int tInterDiaria;
	private int likes;
	private LocalDate fecha;

	/**
	 * @param usr1         El usuario A.
	 * @param usr2         El usuario B.
	 * @param tInterDiaria La cantidad de interaccion diaria.
	 * @param likes        La cantidad de likes entre ambos.
	 * @param fecha        La fecha de inicio de la Relacion
	 */
	public Relacion(Usuario usr1, Usuario usr2, int tInterDiaria, int likes, LocalDate fecha) {
		super();
		this.usr1 = usr1;
		this.usr2 = usr2;
		this.tInterDiaria = tInterDiaria;
		this.likes = likes;
		this.fecha = fecha;
	}

	/**
	 * @return the usr1
	 */
	public Usuario getUsr1() {
		return usr1;
	}

	/**
	 * @param usr1 the usr1 to set
	 */
	public void setUsr1(Usuario usr1) {
		this.usr1 = usr1;
	}

	/**
	 * @return the usr2
	 */
	public Usuario getUsr2() {
		return usr2;
	}

	/**
	 * @param usr2 the usr2 to set
	 */
	public void setUsr2(Usuario usr2) {
		this.usr2 = usr2;
	}

	/**
	 * @return the tInterDiaria
	 */
	public int gettInterDiaria() {
		return tInterDiaria;
	}

	/**
	 * @param tInterDiaria the tInterDiaria to set
	 */
	public void settInterDiaria(int tInterDiaria) {
		this.tInterDiaria = tInterDiaria;
	}

	/**
	 * @return the likes
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}

	/**
	 * @return the fecha
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
	 * Calcula el tiempo de relacion en dias.
	 * 
	 * @return La cantidad de dias desde que se inicio la relacion.
	 */
	public int gettSiendoAmigos() {
		return (int) ChronoUnit.DAYS.between(fecha, LocalDate.now());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usr1 == null) ? 0 : usr1.hashCode());
		result = prime * result + ((usr2 == null) ? 0 : usr2.hashCode());
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
		Relacion other = (Relacion) obj;
		if (usr1 == null) {
			if (other.usr1 != null)
				return false;
		} else if (!usr1.equals(other.usr1) && !usr1.equals(other.usr2))
			return false;
		if (usr2 == null) {
			if (other.usr2 != null)
				return false;
		} else if (!usr2.equals(other.usr1) && !usr2.equals(other.usr2))
			return false;
		return true;
	}

	/**
	 * Produce una representacion de tipo String, del contenido de la clase.
	 */
	@Override
	public String toString() {
		return usr1.getCodigo() + "-" + usr2.getCodigo() + "Tiempo de interaccion diaria: " + tInterDiaria
				+ " Cantidad de likes:" + likes;
	}

}
