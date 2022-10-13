package test;

import datos.CargarDatos;
import datos.CargarParametros;
import logica.Calculo;
import modelo.Relacion;
import modelo.Usuario;
import presentacion.Pantalla;
import net.datastructures.Pair;


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

        for (Pair<Usuario,Integer> relacion : c.mostrarAmigos( usuarios.get("104"))) {
            System.out.println(relacion.getFirst()+ " "+ relacion.getSecond() );
            
        }
        ;



       // Pantalla.mostrarUsuarios(c.mostrarUsuarios());

        //Pantalla.gradoMedio(c.gradoMedio());

        //Pantalla.centralidad(c.centralidad());


/* 
        String src = "101";//Pantalla.ingresarUsuario1();
        String target ="105"; //Pantalla.ingresarUsuario2();
        Pantalla.antiguedad(c.antiguedad(usuarios.get(src), usuarios.get(target)));
*/
    }
}
