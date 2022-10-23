package modelo;

import java.time.LocalDate;
import java.time.Period;

public class Relacion {
	private Usuario usr1;
	private Usuario usr2;
	private int tInterDiaria;
	private int likes;
	private LocalDate fecha;

	public Relacion(Usuario usr1, Usuario usr2, int tInterDiaria, int likes, LocalDate fecha) {
		super();
		this.usr1 = usr1;
		this.usr2 = usr2;
		this.tInterDiaria = tInterDiaria;
		this.likes = likes;
		this.fecha = fecha;
	}

	public Usuario getUsr1() {
		return usr1;
	}

	public void setUsr1(Usuario usr1) {
		this.usr1 = usr1;
	}

	public Usuario getUsr2() {
		return usr2;
	}

	public void setUsr2(Usuario usr2) {
		this.usr2 = usr2;
	}

	public int gettInterDiaria() {
		return tInterDiaria;
	}

	public void settInterDiaria(int tInterDiaria) {
		this.tInterDiaria = tInterDiaria;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int gettSiendoAmigos() {
		return Period.between(fecha, LocalDate.now()).getDays();
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

	@Override
	public String toString() {
		return "Tiempo de interaccion diaria: " + tInterDiaria + " Cantidad de likes:" + likes;
	}

}
