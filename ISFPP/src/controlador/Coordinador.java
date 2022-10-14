package controlador;

import gui.DesktopFrame;
import gui.RedesSocialesCons;
import logica.Calculo;

public class Coordinador {
	
	private Calculo calculo;
	
	private DesktopFrame desktopFrame;
	private RedesSocialesCons redesSocialesForm;
	
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
	
	public RedesSocialesCons getRedesSocialesForm() {
		return redesSocialesForm;
	}

	public void setRedesSocialesForm(RedesSocialesCons redesSocialesForm) {
		this.redesSocialesForm = redesSocialesForm;
	}

	public void mostrarRedesSocialesForm() {
		redesSocialesForm.setVisible(true);
	}


}
