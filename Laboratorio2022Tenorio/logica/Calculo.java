package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import modelo.Usuario;
import modelo.Relacion;
import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Entry;
import net.datastructures.Graph;
import net.datastructures.PositionalList;
import net.datastructures.ProbeHashMap;
import net.datastructures.TreeMap;
import net.datastructures.Vertex;
import net.datastructures.GraphAlgorithms;
import net.datastructures.Map;
import net.datastructures.Position;

public class Calculo<V> {

    private Graph<Usuario, Relacion> redSocial;
    private Graph<Usuario, Integer> rapido = null;
    private TreeMap<String, Vertex<Usuario>> vertices;
    private Map<Usuario, Vertex<Usuario>> res;

    public Calculo(TreeMap<String, Usuario> usuarios, List<Relacion> relaciones) {
        // crea el grafo
        redSocial = new AdjacencyMapGraph<>(false);
        vertices = new TreeMap<String, Vertex<Usuario>>();
        for (Entry<String, Usuario> usuario : usuarios.entrySet())
            vertices.put(usuario.getKey(), redSocial.insertVertex(usuario.getValue()));

        for (Relacion relacion : relaciones)
            redSocial.insertEdge(vertices.get(relacion.getUsr1().getCodigo()),
                    vertices.get(relacion.getUsr2().getCodigo()), relacion);

    }

    /**
     * Devuelve los usuarios mas influyentes de la red social
     * 
     * @return answer lista de ordenada de pares de manera descendente de acuerdo a
     *         la cantidad de amigos
     */
    public List<Entry<Usuario, Integer>> centralidad() {
        Map<Usuario, Integer> outEdges = new ProbeHashMap<Usuario, Integer>();
        List<Entry<Usuario, Integer>> answer = new ArrayList<Entry<Usuario, Integer>>();
        for (Vertex<Usuario> usr : redSocial.vertices()) {
            outEdges.put(usr.getElement(), redSocial.outDegree(usr));
        }
        for (Entry<Usuario, Integer> s : outEdges.entrySet())
            answer.add(s);
        Collections.sort(answer, new Comparator<Entry<Usuario, Integer>>() {
            @Override
            public int compare(Entry<Usuario, Integer> lhs, Entry<Usuario, Integer> rhs) {
                return lhs.getValue() > rhs.getValue() ? -1 : lhs.getValue() < rhs.getValue() ? 1 : 0;
            }
        });
        return answer;
    }

    /**
     * Devuelve la cantidad de relaciones promedio por vertice.
     * 
     * @return double Promedio de relaciones por vertice
     */
    public double gradoMedio() {
        double i = 0;
        for (Vertex<Usuario> ver : redSocial.vertices())
            i += redSocial.outDegree(ver);
        i = i / redSocial.numVertices();
        return i;
    }

    /**
     * Usando el algoritmo de Dijkstra, encuentra el camino mas corto desde un
     * vertice dado a otro. Si el graph esta creado para no copiarlo 2 veces
     * 
     * @param src    Vertice origen
     * @param target Vertice objetivo
     * @return List<Relacion> Lista con el camino mas corto
     */
    public List<Relacion> antiguedad(Usuario src, Usuario target) {
        if (rapido == null) {
            rapido = new AdjacencyMapGraph<>(false);
            res = new ProbeHashMap<>();

            for (Vertex<Usuario> result : redSocial.vertices())
                res.put(result.getElement(),
                        rapido.insertVertex(result.getElement()));

            Vertex<Usuario>[] vert;

            for (Edge<Relacion> result : redSocial.edges()) {
                vert = redSocial.endVertices(result);
                rapido.insertEdge(res.get(vert[0].getElement()), res.get(vert[1]
                        .getElement()), result.getElement().gettSiendoAmigos());
            }
        }

        PositionalList<Vertex<Usuario>> lista = GraphAlgorithms
                .shortestPathList(rapido, res.get(src), res.get(target));
        List<Relacion> answer = new ArrayList<Relacion>();

        Vertex<Usuario> v1, v2;
        Position<Vertex<Usuario>> aux = lista.first();
        while (lista.after(aux) != null) {
            v1 = aux.getElement();
            aux = lista.after(aux);
            v2 = aux.getElement();
            answer.add(redSocial.getEdge(vertices.get(v1.getElement().getCodigo()),
                    vertices.get(v2.getElement().getCodigo())).getElement());
        }
        return answer;
    }

    /**
     * Muestra todos los usuarios de la red social
     * 
     * @return List<Usuario> con todos los usuarios
     */
    public List<Usuario> mostrarUsuarios() {
        List<Usuario> answer = new ArrayList<Usuario>();
        for (Vertex<Usuario> usr : redSocial.vertices())
            answer.add(usr.getElement());
        return answer;

    }

}
