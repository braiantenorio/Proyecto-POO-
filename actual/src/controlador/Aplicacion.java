package controlador;

public class Aplicacion {

	public static void main(String[] args) {

		AppFacade miAplicacion = new AppFacade();

		miAplicacion.cargarCordinador();
		miAplicacion.cargarCalculo();
		miAplicacion.cargarFrame();

	}

} // fin clase Aplicacion