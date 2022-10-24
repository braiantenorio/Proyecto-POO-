package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import controlador.Coordinador;
import modelo.Relacion;
import modelo.Usuario;
import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Entry;
import net.datastructures.Graph;
import net.datastructures.GraphAlgorithms;
import net.datastructures.Map;
import net.datastructures.Pair;
import net.datastructures.Position;
import net.datastructures.PositionalList;
import net.datastructures.ProbeHashMap;
import net.datastructures.TreeMap;
import net.datastructures.Vertex;

public class Calculo {

	private static Calculo calculo;

	private Graph<Usuario, Relacion> redSocial;
	private Graph<Usuario, Integer> rapido = null;
	private TreeMap<String, Vertex<Usuario>> vertices;
	private Map<Usuario, Vertex<Usuario>> res;
	private Coordinador coordinador;

	// constructor
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

	// constructor vacio
	public Calculo() {
	}

	public static Calculo getCalculo() {
		if (calculo == null) {
			calculo = new Calculo();
		}
		return calculo;
	}

	/**
	 * Devuelve los usuarios mas influyentes de la red social
	 * 
	 * @return influyentes lista de ordenada de pares de manera descendente de
	 *         acuerdo a la cantidad de amigos
	 */
	public List<Entry<Usuario, Integer>> centralidad() {
		Map<Usuario, Integer> outEdges = new ProbeHashMap<Usuario, Integer>();
		List<Entry<Usuario, Integer>> influyentes = new ArrayList<Entry<Usuario, Integer>>();

		for (Vertex<Usuario> usr : redSocial.vertices()) {
			outEdges.put(usr.getElement(), redSocial.outDegree(usr));
		}

		for (Entry<Usuario, Integer> s : outEdges.entrySet())
			influyentes.add(s);

		Collections.sort(influyentes, new Comparator<Entry<Usuario, Integer>>() {
			@Override
			public int compare(Entry<Usuario, Integer> lhs, Entry<Usuario, Integer> rhs) {
				return lhs.getValue() > rhs.getValue() ? -1 : lhs.getValue() < rhs.getValue() ? 1 : 0;
			}
		});
		return influyentes;
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

		if (redSocial.numVertices() == 0)
			return i;
		return i / redSocial.numVertices();
	}

	/**
	 * Usando el algoritmo de Dijkstra, encuentra el camino mas corto desde un
	 * vertice dado a otro. Si el graph esta creado para no copiarlo 2 veces
	 * 
	 * @see AdjacencyMapGraph
	 * @param src    Vertice origen
	 * @param target Vertice objetivo
	 * @return List<Relacion> Lista con el camino mas corto
	 */
	public List<Relacion> antiguedad(Usuario src, Usuario target) {
		if (rapido == null) {
			rapido = new AdjacencyMapGraph<>(false);
			res = new ProbeHashMap<>();

			for (Vertex<Usuario> result : redSocial.vertices())
				res.put(result.getElement(), rapido.insertVertex(result.getElement()));

			Vertex<Usuario>[] vert;

			for (Edge<Relacion> result : redSocial.edges()) {
				vert = redSocial.endVertices(result);
				rapido.insertEdge(res.get(vert[0].getElement()), res.get(vert[1].getElement()),
						result.getElement().gettSiendoAmigos());
			}
		}

		PositionalList<Vertex<Usuario>> lista = GraphAlgorithms.shortestPathList(rapido, res.get(src), res.get(target));
		List<Relacion> answer = new ArrayList<Relacion>();

		Vertex<Usuario> v1, v2;
		Position<Vertex<Usuario>> aux = lista.first();
		while (lista.after(aux) != null) {
			v1 = aux.getElement();
			aux = lista.after(aux);
			v2 = aux.getElement();
			answer.add(redSocial
					.getEdge(vertices.get(v1.getElement().getCodigo()), vertices.get(v2.getElement().getCodigo()))
					.getElement());
		}
		return answer;
	}

	/**
	 * Muestra todos los usuarios de la red social
	 * 
	 * @return List<Usuario> con todos los usuarios
	 */
	public List<Usuario> mostrarUsuarios() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		for (Vertex<Usuario> usr : redSocial.vertices())
			usuarios.add(usr.getElement());
		return usuarios;

	}

	/**
	 * Muestra todas las relaciones de la red social
	 * 
	 * @return List<Relacion> con todos las relacion
	 */
	public List<Relacion> mostrarRelaciones() {
		List<Relacion> relaciones = new ArrayList<>();
		for (Edge<Relacion> relacion : redSocial.edges()) {
			relaciones.add(relacion.getElement());
		}
		return relaciones;
	}

	/**
	 * @param usr
	 * @return List<Pair<Usuario, Integer>>
	 */
	public List<Pair<Usuario, Integer>> mostrarAmigos(String usr) {
		Vertex<Usuario> usuario = vertices.get(usr);
		List<Pair<Usuario, Integer>> amigos = new ArrayList<Pair<Usuario, Integer>>();

		for (Edge<Relacion> relacionActual : redSocial.outgoingEdges(usuario)) {
			amigos.add(new Pair<Usuario, Integer>(redSocial.opposite(usuario, relacionActual).getElement(),
					relacionActual.getElement().gettSiendoAmigos()));
		}
		return amigos;
	}

	public Map<Usuario, Integer> mostrarAmigosM(String usr) {
		// el usuario
		Vertex<Usuario> usuario = vertices.get(usr);
		Usuario amigo = null;
		// el mapas con los amigos
		Map<Usuario, Integer> amigos = new ProbeHashMap<>();
		if (usuario == null)
			throw new UsuarioNoValidoException("Codigo no valido:" + usr);

		for (Edge<Relacion> relacionActual : redSocial.outgoingEdges(usuario)) {
			amigo = redSocial.opposite(usuario, relacionActual).getElement();
			int i = relacionActual.getElement().gettSiendoAmigos();

			amigos.put(amigo, i);
		}
		return amigos;
	}

	/**
	 * 
	 * @return Map<Integer, Pair<Usuario, Usuario>>
	 */
	@Deprecated
	public Map<Integer, Pair<Usuario, Usuario>> usuariosDensConectados() {
		Map<Integer, Pair<Usuario, Usuario>> interaccion = new TreeMap<>(Collections.reverseOrder());
		for (Edge<Relacion> relacion : redSocial.edges()) {
			Integer i = relacion.getElement().gettInterDiaria();
			if (interaccion.get(i) == null)
				interaccion.put(i,
						new Pair<Usuario, Usuario>(relacion.getElement().getUsr1(), relacion.getElement().getUsr2()));

		}
		return interaccion;
	}

	/**
	 * Ver para cada vértice la suma de la interacción.
	 * 
	 * @return Lista con Usuario y Interaccion
	 */
	public List<Entry<Usuario, Integer>> usuariosDensaConectados() {
		Map<Usuario, Integer> interacciones = new ProbeHashMap<>();
		int interaccion = 0;

		for (Usuario usuario : mostrarUsuarios()) {
			for (Pair<Usuario, Integer> amigo : mostrarAmigos(usuario.getCodigo())) {
				interaccion += amigo.getSecond();
			}
			interacciones.put(usuario, interaccion);

		}
		List<Entry<Usuario, Integer>> lista = new ArrayList<Entry<Usuario, Integer>>();
		for (Entry<Usuario, Integer> e : interacciones.entrySet())
			lista.add(e);

		Collections.sort(lista, new Comparator<Entry<Usuario, Integer>>() {
			@Override
			public int compare(Entry<Usuario, Integer> arg0, Entry<Usuario, Integer> arg1) {
				return arg0.getValue() > arg1.getValue() ? -1 : arg0.getValue() < arg1.getValue() ? 1 : 0;
			}
		});

		return lista;
	}

	/**
	 * Los amigos de los amigos de un usuario.
	 * 
	 * Tener en cuenta uno o mas atributos de los arcos con un peso dado
	 * (parametrizado).
	 * 
	 * Ej.: cantidad de like * 0.4 + tiempo de interacción * 1.5
	 * 
	 * @param usr
	 * @return List<Usuario>
	 */
	public List<Usuario> sugerenciaAmistad(String usr) {
		List<Usuario> sugerencias = new ArrayList<Usuario>();
		if (usr == null)
			throw new UsuarioNoValidoException("Codigo no valido:" + usr);

		Vertex<Usuario> usuario = vertices.get(usr);
		if (usuario != null)
			for (Edge<Relacion> relacionActual : redSocial.outgoingEdges(usuario)) {
				Vertex<Usuario> vUsr = redSocial.opposite(usuario, relacionActual);
				for (Edge<Relacion> relacionActual2 : redSocial.outgoingEdges(vUsr)) {
					Usuario nuevo = redSocial.opposite(vUsr, relacionActual2).getElement();

					if (!sugerencias.contains(nuevo) && !nuevo.equals(usuario.getElement())
							&& mostrarAmigosM(usr).get(nuevo) == null)
						sugerencias.add(nuevo);
				}
			}
		else
			throw new UsuarioNoValidoException("Codigo no valido:" + usr);
		return sugerencias;
	}

	/**
	 * Busca un vertice en base a un codigo de usuario ingresado. El usuario debe
	 * estar en la redSocial.
	 * 
	 * @param str el codigo del usuario que se buscara
	 * @return el usuario, null en caso contrario
	 */
	public Usuario busqueda(String str) {
		return vertices.get(str).getElement();
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
