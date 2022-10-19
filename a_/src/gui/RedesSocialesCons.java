package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import gui.Constante;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import controlador.Aplicacion;
import controlador.Coordinador;
import datos.CargarDatos;
import datos.CargarParametros;
import modelo.Usuario;
import negocio.Calculo;
import modelo.Relacion;

import presentacion.Pantalla;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.util.List;
import java.util.TreeMap;
import java.util.logging.Handler;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class RedesSocialesCons extends JDialog {

	private Coordinador coordinador;

	private JPanel contentPane;
	private JTextField ltfInsertarNumero;

	public RedesSocialesCons() {
		setBounds(100, 100, 662, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMostrarUsuarios = new JLabel("1-Mostrar todos los usuarios");
		lblMostrarUsuarios.setBounds(10, 11, 163, 13);
		contentPane.add(lblMostrarUsuarios);

		JLabel lblGradoMedio = new JLabel("2-Grado Medio");
		lblGradoMedio.setBounds(10, 35, 106, 14);
		contentPane.add(lblGradoMedio);

		JLabel lblCentralidad = new JLabel("3-Centralidad");
		lblCentralidad.setBounds(10, 61, 106, 14);
		contentPane.add(lblCentralidad);

		JLabel lblAntiguedad = new JLabel("4-Antiguedad");
		lblAntiguedad.setBounds(10, 86, 106, 14);
		contentPane.add(lblAntiguedad);

		ltfInsertarNumero = new JTextField();
		ltfInsertarNumero.setBounds(10, 111, 86, 20);
		contentPane.add(ltfInsertarNumero);
		ltfInsertarNumero.setColumns(10);
		// Integer.parseInt(ltfInsertarNumero.getText());
		// validar

		Handler handler = new Handler();

		JButton btnBotonAceptar = new JButton("Aceptar");
		btnBotonAceptar.setBounds(111, 111, 89, 23);
		contentPane.add(btnBotonAceptar);
		btnBotonAceptar.addActionListener(handler);

	}

	/**
	 * InnerRedesSocialesCons
	 */
	private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			Calculo c = new Calculo(Aplicacion.usuarios, Aplicacion.relaciones);
			int opcion = Integer.parseInt(ltfInsertarNumero.getText());
			switch (opcion) {
				case (Constante.SALIR):

					System.out.print(c.mostrarUsuarios().toArray(new String[c.mostrarUsuarios().size()])[1]);

					Pantalla.despedida();
					System.exit(-1);
					break;
				case (Constante.MOSTRAR_USUARIOS):
					String[] nombres = new String[c.mostrarUsuarios().size()];
					for (int i = 0; i < c.mostrarUsuarios().size(); i++) {
						nombres[i] = c.mostrarUsuarios().get(i).toString();

					}
					JPanel panel = new JPanel(new BorderLayout());
					JList<String> list = new JList<String>(nombres);
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setViewportView(list);
					list.setLayoutOrientation(JList.VERTICAL_WRAP);
					panel.add(scrollPane);
					JFrame frame = new JFrame("DEMO");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.add(panel);
					frame.setSize(500, 250);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					break;
				case (Constante.GRADOMEDIO):
					Pantalla.gradoMedio(c.gradoMedio());
					break;
				case (Constante.CENTRALIDAD):
					Pantalla.centralidad(c.centralidad());
					break;
				case (Constante.ANTIGUEDAD):
					String src = Pantalla.ingresarUsuario1();
					String target = Pantalla.ingresarUsuario2();
					try {
						Pantalla.antiguedad(
								c.antiguedad(Aplicacion.usuarios.get(src), Aplicacion.usuarios.get(target)));
					} catch (NullPointerException e) {
						Pantalla.error(Constante.ERROR_CODIGO_INVALIDO);
					} catch (IllegalArgumentException e) {
						Pantalla.error(Constante.ERROR_USUARIO_SAMISTADES);
					}
					break;
				default:
					Pantalla.error(Constante.ERROR_OPCION);
			}
		}

	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
