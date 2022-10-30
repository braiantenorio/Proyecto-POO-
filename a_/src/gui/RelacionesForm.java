package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;
import datos.RelacionRepetidaException;
import modelo.Relacion;
import modelo.Usuario;
import negocio.UsuarioNoValidoException;
import util.Validation;

public class RelacionesForm extends JDialog {

	private Coordinador coordinador;

	private JPanel contentPane;
	private JTextField jtfUsr1;
	private JTextField jtfUsr2;
	private JTextField jtfInteraccion;
	private JTextField jtfLikes;
	private JTextField jtfFecha;

	private JLabel lblErrorUsr1;
	private JLabel lblErrorUsr2;

	private JLabel lblErrorInteraccion;
	private JLabel lblErrorLikes;
	private JLabel lblErrorFecha;

	private JButton btnInsertar;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnCancelar;

	/**
	 * Create the frame.
	 */
	public RelacionesForm() {
		setBounds(100, 100, 662, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsuario1 = new JLabel("ID:");
		lblUsuario1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsuario1.setBounds(42, 24, 107, 14);
		contentPane.add(lblUsuario1);

		jtfUsr1 = new JTextField();
		jtfUsr1.setBounds(159, 24, 86, 20);
		contentPane.add(jtfUsr1);
		jtfUsr1.setColumns(10);

		JLabel lblUsuario2 = new JLabel("ID:");
		lblUsuario2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsuario2.setBounds(42, 55, 107, 14);
		contentPane.add(lblUsuario2);

		jtfUsr2 = new JTextField();
		jtfUsr2.setBounds(159, 55, 86, 20);
		contentPane.add(jtfUsr2);
		jtfUsr2.setColumns(10);

		JLabel lblInteraccion = new JLabel("Interaccion:");
		lblInteraccion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInteraccion.setBounds(42, 89, 107, 14);
		contentPane.add(lblInteraccion);

		jtfInteraccion = new JTextField("0");
		jtfInteraccion.setBounds(159, 86, 86, 20);
		contentPane.add(jtfInteraccion);
		jtfInteraccion.setColumns(10);

		JLabel lblLikes = new JLabel("Likes:");
		lblLikes.setBounds(42, 120, 107, 14);
		contentPane.add(lblLikes);

		jtfLikes = new JTextField("0");
		jtfLikes.setColumns(10);
		jtfLikes.setBounds(159, 117, 86, 20);
		contentPane.add(jtfLikes);

		JLabel lblFecha = new JLabel("Fecha Amistad:");
		lblFecha.setBounds(42, 151, 107, 14);
		contentPane.add(lblFecha);

		jtfFecha = new JTextField();
		jtfFecha.setColumns(10);
		jtfFecha.setBounds(159, 148, 86, 20);
		contentPane.add(jtfFecha);

		lblErrorUsr1 = new JLabel("");
		lblErrorUsr1.setForeground(Color.RED);
		lblErrorUsr1.setBounds(255, 24, 300, 14);
		contentPane.add(lblErrorUsr1);

		lblErrorUsr2 = new JLabel("");
		lblErrorUsr2.setForeground(Color.RED);
		lblErrorUsr2.setBounds(255, 55, 300, 14);
		contentPane.add(lblErrorUsr2);

		lblErrorInteraccion = new JLabel("");
		lblErrorInteraccion.setForeground(Color.RED);
		lblErrorInteraccion.setBounds(255, 89, 300, 14);
		contentPane.add(lblErrorInteraccion);

		lblErrorLikes = new JLabel("");
		lblErrorLikes.setForeground(Color.RED);
		lblErrorLikes.setBounds(255, 120, 300, 14);
		contentPane.add(lblErrorLikes);

		lblErrorFecha = new JLabel("");
		lblErrorFecha.setForeground(Color.RED);
		lblErrorFecha.setBounds(255, 151, 300, 14);
		contentPane.add(lblErrorFecha);

		Handler handler = new Handler();

		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(85, 202, 114, 32);
		contentPane.add(btnInsertar);
		btnInsertar.addActionListener(handler);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(85, 202, 114, 32);
		contentPane.add(btnModificar);
		btnModificar.addActionListener(handler);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(85, 202, 114, 32);
		contentPane.add(btnBorrar);
		btnBorrar.addActionListener(handler);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(225, 202, 114, 32);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(handler);

		setModal(true);
	}

	public void accion(int accion, Relacion relacion) {
		btnInsertar.setVisible(false);
		btnModificar.setVisible(false);
		btnBorrar.setVisible(false);
		jtfUsr1.setEditable(true);
		jtfUsr2.setEditable(true);
		jtfInteraccion.setEditable(true);
		jtfLikes.setEditable(true);
		jtfFecha.setEditable(true);

		if (accion == Constantes.INSERTAR) {
			btnInsertar.setVisible(true);
			limpiar();
		}

		if (accion == Constantes.MODIFICAR) {
			btnModificar.setVisible(true);
			jtfUsr1.setEditable(false);
			jtfUsr2.setEditable(false);
			mostrar(relacion);
		}

		if (accion == Constantes.BORRAR) {
			btnBorrar.setVisible(true);
			jtfUsr1.setEditable(false);
			jtfUsr2.setEditable(false);
			jtfInteraccion.setEditable(false);
			jtfLikes.setEditable(false);
			jtfFecha.setEditable(false);
			mostrar(relacion);
		}
	}

	private void mostrar(Relacion relacion) {
		jtfUsr1.setText(relacion.getUsr1().getCodigo());
		jtfUsr2.setText(relacion.getUsr2().getCodigo());
		jtfInteraccion.setText(relacion.gettInterDiaria() + "");
		jtfLikes.setText(relacion.getLikes() + "");
		jtfFecha.setText(relacion.getFecha().toString());

	}

	private void limpiar() {
		jtfUsr1.setText("");
		jtfUsr2.setText("");
		jtfInteraccion.setText("");
		jtfLikes.setText("");
		jtfFecha.setText("");
	}

	//

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == btnCancelar) {
				coordinador.cancelarRelacion();
				return;
			}

			Usuario usr1 = null;
			Usuario usr2 = null;
			if (event.getSource() == btnBorrar) {
				int resp = JOptionPane.showConfirmDialog(null, "Esta seguro que borra este registro?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == resp) {
					usr1 = coordinador.buscarUsuario(new Usuario(jtfUsr1.getText(), null, null, null, null, null));
					usr2 = coordinador.buscarUsuario(new Usuario(jtfUsr2.getText(), null, null, null, null, null));

					coordinador.borrarRelacion((new Relacion(usr1, usr2, 0, 0, null)));
				}
				return;

			}

			boolean valido = true;

			lblErrorUsr1.setText("");
			lblErrorUsr2.setText("");
			lblErrorInteraccion.setText("");
			lblErrorLikes.setText("");
			lblErrorFecha.setText("");

			// validar ID
			String id1 = jtfUsr1.getText().trim();
			if (id1.isEmpty()) {
				lblErrorUsr1.setText("Campo obligatorio");
				valido = false;
			} else if (id1.matches("\\d{1,8}") != true) {
				lblErrorUsr1.setText("Solo d�gitos");
				valido = false;
			}
			// validar ID
			String id2 = jtfUsr2.getText().trim();
			if (id2.isEmpty()) {
				lblErrorUsr2.setText("Campo obligatorio");
				valido = false;
			} else if (id2.matches("\\d{1,8}") != true) {
				lblErrorUsr2.setText("Solo d�gitos");
				valido = false;
			}

			// validar Interaccion
			Integer imteraccion = Validation.isInteger(jtfInteraccion.getText());
			if (imteraccion == null) {
				lblErrorInteraccion.setText("Campo obligatorio");
				valido = false;
			}

			// validar Likes
			Integer likes = Validation.isInteger(jtfLikes.getText());
			if (likes == null) {
				lblErrorLikes.setText("Likes no v�lido");
				valido = false;
			}
			// validar fecha
			LocalDate fecha = Validation.isDate(jtfFecha.getText());
			if (fecha == null) {
				lblErrorFecha.setText("Formato de fecha AAAA-MM-DD");
				valido = false;
			}

			if (!valido)
				return;

			try {
				usr1 = coordinador.buscarUsuario(new Usuario(id1, null, null, null, null, null));
				usr2 = coordinador.buscarUsuario(new Usuario(id2, null, null, null, null, null));
			} catch (UsuarioNoValidoException e) {
				JOptionPane.showMessageDialog(null, "ID ??!");
				return;
			}
			
			Relacion relacion = new Relacion(usr1, usr2, imteraccion, likes, fecha);
			
			if (event.getSource() == btnInsertar)
				try {
					coordinador.insertarRelacion(relacion);

				} catch (RelacionRepetidaException e) {
					
					JOptionPane.showMessageDialog(null, "Esta Relacion ya existe!");
					return;
				}
			if (event.getSource() == btnModificar)
				coordinador.modificarRelacion(relacion);
		}
	}

	//
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}