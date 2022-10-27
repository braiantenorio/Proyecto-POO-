package controlador;

import java.util.List;

import gui.DesktopFrame;
import modelo.Relacion;
import modelo.Usuario;
import negocio.Calculo;
import net.datastructures.Entry;

public class Coordinador {

	private Calculo calculo;

	private DesktopFrame desktopFrame;

	public DesktopFrame getDesktopFrame() {
		return desktopFrame;
	}

	public void setDesktopFrame(DesktopFrame desktopFrame) {
		this.desktopFrame = desktopFrame;
	}

	public Calculo getCalculo() {
		return calculo;
	}

	public void setCalculo(Calculo calculo) {
		this.calculo = calculo;
	}

	public List<Entry<Usuario, Integer>> centralidad() {
		return calculo.centralidad();
	}

	public double gradoMedio() {
		return calculo.gradoMedio();

	}

	public List<Relacion> antiguedad(Usuario usu1, Usuario usu2) {
		return calculo.antiguedad(usu1, usu2);
	}

	public List<Usuario> mostrarUsuarios() {
		return calculo.mostrarUsuarios();

	}

}
