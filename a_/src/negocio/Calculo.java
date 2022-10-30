package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import controlador.Coordinador;
import datos.RelacionRepetidaException;
import datos.UsuarioRepetidoException;
import modelo.Relacion;
import modelo.Usuario;
import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Entry;
import net.datastructures.Graph;
import net.datastructures.GraphAlgorithms;
import net.datastructures.Map;
import net.datastructures.Position;
import net.datastructures.PositionalList;
import net.datastructures.ProbeHashMap;
import net.datastructures.TreeMap;
import net.datastructures.Vertex;
import servicio.RelacionService;
import servicio.RelacionServiceImp;
import servicio.UsuarioService;
import servicio.UsuarioServiceImp;

public class Calculo {

	private static Calculo calculo = null;

	private Graph<Usuario, Relacion> redSocial;
	private Graph<Usuario, Integer> rapido = null;
	private TreeMap<String, Vertex<Usuario>> vertices;
	private Map<Usuario, Vertex<Usuario>> res;
	private Coordinador coordinador;
	private Map<String, Usuario> usuarios;
	private List<Relacion> relaciones;
	private UsuarioService usuarioService;
	private RelacionService relacionService;

	private Calculo() {
		usuarioService = new UsuarioServiceImp();
		usuarios = usuarioService.buscarTodos();

		relacionService = new RelacionServiceImp();
		relaciones = relacionService.buscarTodos();

	}

	/**
	 * @return Calculo
	 */
	public static Calculo getCalculo() {
		if (calculo == null) {
			calculo = new Calculo();
		}
		return calculo;
	}

	public void calculoDatos(TreeMap<String, Usuario> usuarios, List<Relacion> relaciones) {
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
		if (src == null || target == null)
			throw new UsuarioNoValidoException("Codigo no valido");
		if (vertices.get(src.getCodigo()) == null || vertices.get(target.getCodigo()) == null)
			throw new UsuarioNoValidoException("Codigo no valido");
		if (src.equals(target))
			throw new RelacionNoValidaException("Mismo usuario");

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
	public Map<String,Usuario> mostrarUsuarios() {
		return usuarios;

	}

	/**
	 * Muestra todas las relaciones de la red social
	 * 
	 * @return List<Relacion> con todos las relacion
	 */
	public List<Relacion> mostrarRelaciones() {
		return relaciones;
	}

	/**
	 * Mostrar los amigos de un usuario determinado.
	 * 
	 * @param usr El codigo del usuario
	 * @throws UsuarioNoValidoException
	 * @return un mapa con los amigos del usuario
	 */
	public Map<Usuario, Integer> mapaAmigos(String usr) {
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
	 * Ver para cada vértice la suma de la interacción. Los usuarios que están más
	 * densamente conectados son acuerdo a la interacción diaria que tienen
	 * 
	 * @return Lista con Usuario y Interaccion
	 */
	public List<Entry<Usuario, Integer>> usuariosDensaConectados() {
		Map<Usuario, Integer> interacciones = new ProbeHashMap<>();
		int interaccion;

		// obtener los usuarios con sus interacciones
		for (Entry<String,Usuario> usu : mostrarUsuarios().entrySet()) {
			Usuario usuario = usu.getValue();
			interaccion = 0;
			for (Relacion relacion : mostrarRelaciones()) {
				if (usuario.equals(relacion.getUsr1()) || usuario.equals(relacion.getUsr2()))
					interaccion += relacion.gettInterDiaria();
			}
			interacciones.put(usuario, interaccion);

		}

		List<Entry<Usuario, Integer>> lista = new ArrayList<Entry<Usuario, Integer>>();
		for (Entry<Usuario, Integer> inter : interacciones.entrySet())
			lista.add(inter);

		// orden
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
			throw new UsuarioNoValidoException();
		Vertex<Usuario> usuario = vertices.get(usr);
		if (usuario == null)
			throw new UsuarioNoValidoException("Codigo no valido:" + usr);

		Relacion relacion;

		for (Entry<Usuario, Integer> amigo : mapaAmigos(usr).entrySet()) {
			relacion = busquedaRelacion(usuario.getElement(), amigo.getKey());
			if (buenaRelacion(relacion, 45)) // si el amigo cumple con los criterios
				for (Entry<Usuario, Integer> amiAmi : mapaAmigos(amigo.getKey().getCodigo()).entrySet()) {
					Usuario nuevo = amiAmi.getKey();
					relacion = busquedaRelacion(amiAmi.getKey(), amigo.getKey());
					// si el amigo de amigo cumple con los criterios
					if (buenaRelacion(relacion, 75) && !sugerencias.contains(nuevo)
							&& !nuevo.equals(usuario.getElement()) && mapaAmigos(usr).get(nuevo) == null)
						sugerencias.add(nuevo);
				}
		}
		return sugerencias;
	}

	/**
	 * Busca un vertice en base a un codigo de usuario ingresado. El usuario debe
	 * estar en la redSocial.
	 * 
	 * @param cod el codigo del usuario que se buscara
	 * @throws UsuarioNoValidoException si no ingresa nada
	 * @return el usuario, null en caso contrario
	 */
	public Usuario busquedaUsuario(String cod) {
		if (cod == null)
			throw new UsuarioNoValidoException();
		return usuarios.get(cod);
	}

	/**
	 * @return
	 */
	public Relacion busquedaRelacion(Usuario usr1, Usuario usr2) {
		Relacion relacion = new Relacion(usr1, usr2, 0, 0, null);
		if (usr1 == null || usr2 == null)
			throw new UsuarioNoValidoException();
		if (vertices.get(usr1.getCodigo()) == null || vertices.get(usr2.getCodigo()) == null)
			throw new UsuarioNoValidoException();

		for (Relacion relac : mostrarRelaciones()) {
			if (relac.equals(relacion))
				return relac;
		}
		throw new RelacionNoValidaException();
	}

	/**
	 * @param relacion  la relacion a considerar
	 * @param parametro el criterio minimo a cumplir en la relacion
	 */
	private boolean buenaRelacion(Relacion relacion, double parametro) {
		return parametro < (relacion.getLikes() * .4 + relacion.gettInterDiaria() * 1.5);
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	// DAO
	public void borrarUsuario(Usuario usuario) {
		if (vertices.get(usuario.getCodigo()) == null)
			throw new UsuarioNoValidoException();
		vertices.remove(usuario.getCodigo());
		
		usuarioService.borrar(usuario);
	}

	public void modificarUsuario(Usuario usuario) {
		vertices.put(usuario.getCodigo(), vertices.get(usuario.getCodigo()));
		usuarioService.actualizar(usuario);
	}

	public void agregarUsuario(Usuario usuario) {
		if (vertices.get(usuario.getCodigo()) != null)
			throw new UsuarioRepetidoException();
		usuarios.put(usuario.getCodigo(), usuario);
		usuarioService.insertar(usuario);
	}

	public void borrarRelacion(Relacion relacion) {
		Relacion rl = buscarRelacion(relacion);
		relaciones.remove(rl);
		relacionService.borrar(relacion);
		
	}

	private Relacion buscarRelacion(Relacion relacion) {
		int pos = relaciones.indexOf(relacion);
		if (pos == -1)
			return null;
		return relaciones.get(pos);
	}

	public void agregarRelacion(Relacion relacion) {
		if (relaciones.contains(relacion))
			throw new RelacionRepetidaException();
		relaciones.add(relacion);
		relacionService.insertar(relacion);
		
		redSocial.insertEdge(vertices.get(relacion.getUsr1().getCodigo()), vertices.get(relacion.getUsr1().getCodigo()), relacion);
	}

	public void modificarRelacion(Relacion relacion) {
		int pos = relaciones.indexOf(relacion);
		relaciones.set(pos, relacion);
		relacionService.actualizar(relacion);
		
		borrarRelacion(relacion);
		agregarRelacion(relacion);
	}
}
