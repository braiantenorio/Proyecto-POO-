package dao.secuencial;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

import dao.RelacionDAO;
import datos.RelacionRepetidaException;
import modelo.Relacion;
import modelo.Usuario;
import net.datastructures.Map;
import servicio.UsuarioServiceImp;

public class RelacionSecuencialDAO implements RelacionDAO {

	private List<Relacion> list;
	private String name;

	public RelacionSecuencialDAO() {
		ResourceBundle rb = ResourceBundle.getBundle("secuencial");
		name = rb.getString("relacion");
		list = readFromFile(name);
	}

	private List<Relacion> readFromFile(String file) {
		List<Relacion> list = new ArrayList<>();
		Scanner inFile = null;
		UsuarioServiceImp usuarioServiceImp = new UsuarioServiceImp();

		Map<String, Usuario> usuarios = usuarioServiceImp.buscarTodos();

		try {
			inFile = new Scanner(new File(file));
			inFile.useDelimiter("\\s*;\\s*");

			Usuario usr1, usr2;
			int likes, tInterDiaria;
			int anio, mes, dia;
			Relacion relacion;

			while (inFile.hasNext()) {
				usr1 = usuarios.get(inFile.next());
				usr2 = usuarios.get(inFile.next());
				tInterDiaria = inFile.nextInt();
				likes = inFile.nextInt();
				anio = inFile.nextInt();
				mes = inFile.nextInt();
				dia = inFile.nextInt();
				relacion = new Relacion(usr1, usr2, tInterDiaria, likes, LocalDate.of(anio, mes, dia));
				if (list.contains(relacion))
					throw new RelacionRepetidaException();
				list.add(relacion);
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
		return list;
	}

	private void writeToFile(List<Relacion> list, String file) {

		Formatter outFile = null;
		Calendar calendar = Calendar.getInstance();
		try {
			outFile = new Formatter(file);
			for (Relacion r : list) {

				outFile.format("%s;%s;%d;%d;%d;%d;%d;\n", r.getUsr1().getCodigo(), r.getUsr2().getCodigo(),
						r.gettInterDiaria(), r.getLikes(), r.getFecha().getYear(), r.getFecha().getMonthValue(),
						r.getFecha().getDayOfMonth());
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
	public void insertar(Relacion relacion) {
		list.add(relacion);
		writeToFile(list, name);
	}

	@Override
	public void actualizar(Relacion relacion) {
		int pos = list.indexOf(relacion);
		list.set(pos, relacion);
		writeToFile(list, name);
	}

	@Override
	public void borrar(Relacion relacion) {
		list.remove(relacion);
		writeToFile(list, name);
	}

	@Override
	public List<Relacion> buscarTodos() {
		return list;
	}

}
