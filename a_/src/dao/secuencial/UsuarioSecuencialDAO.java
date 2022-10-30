package dao.secuencial;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

import dao.UsuarioDAO;
import datos.UsuarioRepetidoException;
import modelo.Genero;
import modelo.NivelAcademico;
import modelo.Usuario;
import net.datastructures.Entry;
import net.datastructures.Map;
import net.datastructures.TreeMap;

public class UsuarioSecuencialDAO implements UsuarioDAO {

	private Map<String, Usuario> map;
	private String name;

	public UsuarioSecuencialDAO() {
		ResourceBundle rb = ResourceBundle.getBundle("secuencial");
		name = rb.getString("usuario");
		map = readFromFile(name);
	}

	private Map<String, Usuario> readFromFile(String file) {
		Map<String, Usuario> map = new TreeMap<String, Usuario>();
		Scanner inFile = null;

		try {
			inFile = new Scanner(new File(file));
			inFile.useDelimiter("\\s*;\\s*");

			String codigo, nombre, genero, ciudadAct, nivelAcademico;
			int anio, mes, dia;
			Usuario usuario;
			while (inFile.hasNext()) {
				codigo = inFile.next();
				nombre = inFile.next();
				anio = inFile.nextInt();
				mes = inFile.nextInt();
				dia = inFile.nextInt();
				genero = inFile.next();
				ciudadAct = inFile.next();
				nivelAcademico = inFile.next();
				usuario = new Usuario(codigo, nombre, LocalDate.of(anio, mes, dia),
						Genero.valueOf(genero.toUpperCase()), ciudadAct,
						NivelAcademico.valueOf(nivelAcademico.toUpperCase()));
				if (map.get(usuario.getCodigo()) != null)
					throw new UsuarioRepetidoException();
				map.put(usuario.getCodigo(), usuario);
			}

		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Error opening file.");
			fileNotFoundException.printStackTrace();
		} catch (NoSuchElementException noSuchElementException) {
			System.err.println("Error in file record structure");
			noSuchElementException.printStackTrace();
		} catch (IllegalStateException illegalStateException) {
			System.err.println("Error reading from file.");
			illegalStateException.printStackTrace();
		} finally {
			if (inFile != null)
				inFile.close();
		}
		return map;
	}

	private void writeToFile(Map<String, Usuario> map, String file) {
		Formatter outFile = null;
		Calendar calendar = Calendar.getInstance();
		try {
			outFile = new Formatter(file);

			for (Entry<String, Usuario> usrs : map.entrySet()) {
				Usuario usuario = usrs.getValue();
				calendar.setTime(
						Date.from(usuario.getFechadenac().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
				outFile.format("%s;%s;%d;%d;%d;%s;%s;%s;\n", usuario.getCodigo(), usuario.getNombre(),
						usuario.getFechadenac().getYear(), usuario.getFechadenac().getMonthValue(),
						usuario.getFechadenac().getDayOfMonth(), usuario.getGenero(), usuario.getCiudadAct(),
						usuario.getNivelAcademico());
			}

		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Error creating file.");
		} catch (FormatterClosedException formatterClosedException) {
			System.err.println("Error writing to file.");
		} finally {
			if (outFile != null)
				outFile.close();
		}
	}

	@Override
	public void insertar(Usuario usuario) {
		map.put(usuario.getCodigo(), usuario);
		writeToFile(map, name);
	}

	@Override
	public void actualizar(Usuario usuario) {
		map.put(usuario.getCodigo(), usuario);
		writeToFile(map, name);
	}

	@Override
	public void borrar(Usuario usuario) {
		map.remove(usuario.getCodigo());
		writeToFile(map, name);
	}

	@Override
	public Map<String, Usuario> buscarTodos() {
		return map;
	}

}
