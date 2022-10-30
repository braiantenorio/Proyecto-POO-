package controlador;

public class Aplicacion {

	public static void main(String[] args) {

		AppFacade miAplicacion = new AppFacade();

		miAplicacion.cargarDatos();
		miAplicacion.cargarCalculo();
		miAplicacion.cargarCordinador();
		miAplicacion.cargarFrame();

	}

}
