package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import controlador.Coordinador;
import datos.UsuarioRepetidoException;
import modelo.Genero;
import modelo.NivelAcademico;
import modelo.Relacion;
import modelo.Usuario;
import util.Validation;

public class UsuariosForm extends JDialog {

	final static Logger logger = Logger.getLogger(UsuariosForm.class);

	private Coordinador coordinador;

	private JPanel contentPane;
	private JTextField jtfNombre;
	private JTextField jtfGenero;
	private JTextField jtfLocalidad;
	private JTextField jtfFechaNacimiento;
	private JTextField jtfCodigo;
	private JTextField jtfNivelAcademico;

	private JLabel lblErrorNombre;
	private JLabel lblErrorGenero;
	private JLabel lblErrorCodigo;
	private JLabel lblErrorFechaNacimiento;
	private JLabel lblErrorLocalidad;
	private JLabel lblErrorNivelAcademico;

	private JButton btnInsertar;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnCancelar;

	/**
	 * Create the frame.
	 */
	public UsuariosForm() {
		setBounds(100, 100, 662, 300);

		// contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// -- id
		JLabel lblCodigo = new JLabel("ID:");
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCodigo.setBounds(42, 24, 107, 14);
		contentPane.add(lblCodigo);

		jtfCodigo = new JTextField();
		jtfCodigo.setBounds(159, 24, 86, 20);
		contentPane.add(jtfCodigo);
		jtfCodigo.setColumns(10);

		// -- nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(42, 55, 107, 14);
		contentPane.add(lblNombre);

		jtfNombre = new JTextField(15);
		jtfNombre.setBounds(159, 55, 86, 20);
		contentPane.add(jtfNombre);
		jtfNombre.setColumns(10);

		// -- genero
		JLabel lblGenero = new JLabel("Genero:");
		lblGenero.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGenero.setBounds(42, 89, 107, 14);
		contentPane.add(lblGenero);

		jtfGenero = new JTextField();
		jtfGenero.setToolTipText("m, f, x");
		jtfGenero.setBounds(159, 85, 86, 20);
		contentPane.add(jtfGenero);
		jtfGenero.setColumns(10);

		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
		lblFechaNacimiento.setBounds(42, 120, 107, 14);
		contentPane.add(lblFechaNacimiento);

		jtfFechaNacimiento = new JTextField();
		jtfFechaNacimiento.setToolTipText("formato: 2007-08-07");
		jtfFechaNacimiento.setColumns(10);
		jtfFechaNacimiento.setBounds(159, 117, 86, 20);
		contentPane.add(jtfFechaNacimiento);

		JLabel lblLocalidad = new JLabel("Localidad:");
		lblLocalidad.setBounds(42, 151, 107, 14);
		contentPane.add(lblLocalidad);

		jtfLocalidad = new JTextField();
		jtfLocalidad.setColumns(10);
		jtfLocalidad.setBounds(159, 148, 86, 20);
		contentPane.add(jtfLocalidad);

		JLabel lblNivelAcademico = new JLabel("NivelAcademico:");
		lblNivelAcademico.setBounds(42, 183, 107, 14);
		contentPane.add(lblNivelAcademico);

		jtfNivelAcademico = new JTextField();
		jtfNivelAcademico.setColumns(10);
		jtfNivelAcademico.setBounds(159, 181, 86, 20);
		contentPane.add(jtfNivelAcademico);

		lblErrorCodigo = new JLabel("");
		lblErrorCodigo.setForeground(Color.RED);
		lblErrorCodigo.setBounds(255, 24, 300, 14);
		contentPane.add(lblErrorCodigo);

		lblErrorNombre = new JLabel("");
		lblErrorNombre.setForeground(Color.RED);
		lblErrorNombre.setBounds(255, 55, 300, 14);
		contentPane.add(lblErrorNombre);

		lblErrorGenero = new JLabel("");
		lblErrorGenero.setForeground(Color.RED);
		lblErrorGenero.setBounds(255, 89, 300, 14);
		contentPane.add(lblErrorGenero);

		lblErrorFechaNacimiento = new JLabel("");
		lblErrorFechaNacimiento.setForeground(Color.RED);
		lblErrorFechaNacimiento.setBounds(255, 120, 300, 14);
		contentPane.add(lblErrorFechaNacimiento);

		lblErrorLocalidad = new JLabel("");
		lblErrorLocalidad.setForeground(Color.RED);
		lblErrorLocalidad.setBounds(255, 151, 300, 14);
		contentPane.add(lblErrorLocalidad);

		lblErrorNivelAcademico = new JLabel("");
		lblErrorNivelAcademico.setForeground(Color.RED);
		lblErrorNivelAcademico.setBounds(255, 183, 300, 14);
		contentPane.add(lblErrorNivelAcademico);

		Handler handler = new Handler();

		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(85, 212, 114, 32);
		contentPane.add(btnInsertar);
		btnInsertar.addActionListener(handler);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(85, 212, 114, 32);
		contentPane.add(btnModificar);
		btnModificar.addActionListener(handler);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(85, 212, 114, 32);
		contentPane.add(btnBorrar);
		btnBorrar.addActionListener(handler);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(225, 212, 114, 32);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(handler);

		setModal(true);

	}

	public void accion(int accion, Usuario usuario) {
		btnInsertar.setVisible(false);
		btnModificar.setVisible(false);
		btnBorrar.setVisible(false);
		jtfNombre.setEditable(true);
		jtfGenero.setEditable(true);
		jtfCodigo.setEditable(true);
		jtfFechaNacimiento.setEditable(true);
		jtfLocalidad.setEditable(true);
		jtfNivelAcademico.setEditable(true);

		if (accion == Constantes.INSERTAR) {
			btnInsertar.setVisible(true);
			limpiar();
		}

		if (accion == Constantes.MODIFICAR) {
			btnModificar.setVisible(true);
			jtfCodigo.setEditable(false);
			mostrar(usuario);
		}

		if (accion == Constantes.BORRAR) {
			btnBorrar.setVisible(true);
			jtfNombre.setEditable(false);
			jtfGenero.setEditable(false);
			jtfCodigo.setEditable(false);
			jtfFechaNacimiento.setEditable(false);
			jtfLocalidad.setEditable(false);
			jtfNivelAcademico.setEditable(false);
			mostrar(usuario);
		}
	}

	private void mostrar(Usuario usuario) {
		jtfCodigo.setText(usuario.getCodigo());
		jtfNombre.setText(usuario.getNombre());
		jtfFechaNacimiento.setText(usuario.getFechadenac().toString());
		jtfGenero.setText(usuario.getGenero().toString());
		jtfLocalidad.setText(usuario.getCiudadAct());
		jtfNivelAcademico.setText(usuario.getNivelAcademico().toString());
	}

	private void limpiar() {
		jtfNombre.setText("");
		jtfGenero.setText("");
		jtfCodigo.setText("");
		jtfFechaNacimiento.setText("");
		jtfLocalidad.setText("");
		jtfNivelAcademico.setText("");
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == btnCancelar) {
				coordinador.cancelarUsuario();
				return;
			}
			if (event.getSource() == btnBorrar) {
				int resp = JOptionPane.showConfirmDialog(null, "Est� seguro que borra este registro?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == resp) {
					List<Relacion> relaciones = new ArrayList<Relacion>();//
					relaciones.addAll(coordinador.listaRelaciones()); // -- copia de las relaciones
					for (Relacion relacion : relaciones) {
						if (relacion.getUsr1().equals(new Usuario(jtfCodigo.getText(), null, null, null, null, null))
								|| relacion.getUsr2()
										.equals(new Usuario(jtfCodigo.getText(), null, null, null, null, null)))
							coordinador.borrarRelacion(relacion);
					}
					logger.info("Relaciones posibles borradas");
					coordinador.borrarUsuario(new Usuario(jtfCodigo.getText(), null, null, null, null, null));
					logger.info("Usuario borrado");

				}
				return;
			}

			boolean valido = true;

			lblErrorNombre.setText("");
			lblErrorGenero.setText("");
			lblErrorCodigo.setText("");
			lblErrorFechaNacimiento.setText("");
			lblErrorLocalidad.setText("");
			lblErrorNivelAcademico.setText("");

			// valisaddar nombre
			String nombre = jtfNombre.getText().trim();
			if (nombre.isEmpty()) {
				lblErrorNombre.setText("Campo obligatorio");
				valido = false;
			} else if (nombre.matches("[A-Z][a-zA-Z]*") != true) {
				lblErrorNombre.setText("Solo letras. Primera con may�scula");
				valido = false;
			}

			// validar genero
			String genero = jtfGenero.getText().trim().toUpperCase();
			if (genero.isEmpty()) {
				lblErrorGenero.setText("Campo obligatorio");
				valido = false;
			} else if (Genero.valueOf(genero) == null) {
				lblErrorGenero.setText("Una letra.");
				valido = false;
			}

			// validar id
			String codigo = jtfCodigo.getText().trim();
			if (codigo.isEmpty()) {
				lblErrorCodigo.setText("Campo obligatorio");
				valido = false;
			} else if (codigo.matches("\\d{3,5}") != true) {
				lblErrorCodigo.setText("Solo digitos");
				valido = false;
			}

			// validar fechaNacimiento
			String fecha = jtfFechaNacimiento.getText().trim();
			DateFormat dateFormat = null;
			LocalDate fechadenac = null;
			// jtfFechaNacimiento.getText(), "yyyy-MM-dd"
			if (fecha == null) {
				lblErrorFechaNacimiento.setText("Formato de fecha AAAA-MM-DD");
				valido = false;
			} else if (Validation.isDate(fecha) == null) {
				lblErrorFechaNacimiento.setText("Frmto de fecha AAAA-MM-DD");
				valido = false;
			}

			// validar localidad
			String localidad = jtfLocalidad.getText().trim();
			if (localidad.isEmpty()) {
				lblErrorLocalidad.setText("Localidad no valido");
				valido = false;
			}

			String nivelAcademico = jtfNivelAcademico.getText().trim().toUpperCase();
			if (nivelAcademico.isEmpty()) {
				lblErrorNivelAcademico.setText("Campo obligatorio");
				valido = false;
			} else if (NivelAcademico.valueOf(nivelAcademico) == null) {
				lblErrorNivelAcademico.setText("asd");
				valido = false;
			}

			if (!valido) {
				logger.error("Campos no v�lidos!");
				return;
			}

			Usuario usuario = new Usuario(codigo, nombre, LocalDate.parse(fecha), Genero.valueOf(genero), localidad,
					NivelAcademico.valueOf(nivelAcademico));

			if (event.getSource() == btnInsertar)
				try {
					coordinador.insertarUsuario(usuario);
				} catch (UsuarioRepetidoException e) {
					logger.error("Este usuario ya existe!");
					JOptionPane.showMessageDialog(null, "Este Usuario ya existe!");
					return;
				}
			if (event.getSource() == btnModificar) {
				coordinador.modificarUsuario(usuario);
				logger.info("Usuario Modificado");
				return;
			}

			logger.info("Usuario Agregado");
		}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

}
