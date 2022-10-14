/*package test;

import datos.CargarDatos;
import datos.CargarParametros;
import logica.Calculo;
import modelo.Relacion;
import modelo.Usuario;

import net.datastructures.TreeMap;

import java.util.List;

import aplicacion.Constante;

import java.io.FileNotFoundException;
import java.io.IOException;

public class testTenorio {

    public static void main(String[] args) throws Exception {

        TreeMap<String, Usuario> usuarios = null;
        List<Relacion> relaciones = null;

        // Cargar datos
        try {
            CargarParametros.parametros();
        } catch (IOException e) {
            System.err.print(Constante.ERROR_PARAMETROS);
            System.exit(-1);
        }

        try {
            usuarios = CargarDatos.cargarUsuarios(CargarParametros.getArchivoUsuario());
            relaciones = CargarDatos.crearRelaciones(CargarParametros.getArchivoRelaciones());
        } catch (FileNotFoundException e) {
            System.err.print(Constante.ERROR_ARCHIVO);
            System.exit(-1);
        }

        Calculo<Usuario> c = new Calculo<Usuario>(usuarios, relaciones);

        String src = "101";// Pantalla.ingresarUsuario1();
        String target = "105"; // Pantalla.ingresarUsuario2();

        c.antiguedad(usuarios.get(src), usuarios.get(target));

        for (Usuario recomendacion : c.sugerenciaAmistad("105")) {
            System.out.println(recomendacion);

        }

        /*
         * for (Pair<Usuario, Integer> parActual : c.mostrarAmigos("101")) {
         * System.out.println(parActual.getFirst() + " " + parActual.getSecond());
         * }
         * 
         * for (Entry<Integer, Pair<Usuario, Usuario>> entryActual :
         * c.usuariosDensConectados().entrySet()) {
         * System.out.println(entryActual.getValue().getFirst() + " " +
         * entryActual.getValue().getSecond() + " " + entryActual.getKey());
         * }
         */

        /*
         * for (Entry<Integer,Pair<Usuario,Usuario>> actEntry :
         * c.usuariosDensConectados().entrySet()) {
         * System.out.println(actEntry.getKey()+ " "+ actEntry.getValue().getFirst() +
         * " " + actEntry.getValue().getSecond());
         * }
         

        // Pantalla.mostrarUsuarios(c.mostrarUsuarios());

        // Pantalla.gradoMedio(c.gradoMedio());

        // Pantalla.centralidad(c.centralidad());
    }
}*/
