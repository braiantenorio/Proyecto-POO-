package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import controlador.Coordinador;
//import gui.DesktopFrame.Handler;
import modelo.Relacion;
import modelo.Usuario;
import negocio.RelacionNoValidaException;
import negocio.UsuarioNoValidoException;
import net.datastructures.Entry;
import net.datastructures.Map;

public class DesktopFrame extends JFrame {

	final static Logger logger = Logger.getLogger(DesktopFrame.class);

	private Coordinador coordinador;
	private JPanel ContentPanel;
	private ButtonGroup btnGroupRadio;
	private JTextField textFieldUsuario1;
	private JTextField textFieldUsuario2;
	private JButton btnAntiguedad;
	private JButton btnAmigoDeUnUsr;
	private JButton btnSugerenciaAmistad;
	private JLabel lblConsultas;
	private JList list;
	private JScrollPane scrollPane;
	private JTextArea textAreaConsultas;

	private JButton btnSalir;

	public DesktopFrame() {
		// Frame config
		setSize(720, 400);
		setResizable(false);

		// Frame Panel
		getContentPane().setLayout(null);

		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Opcion");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Usuarios");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador.mostrarUsuarioList();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Relaciones");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador.mostrarRelacionList();
			}
		});

		mnNewMenu.add(mntmNewMenuItem_2);

		// contentPanel

		ContentPanel = new JPanel();
		ContentPanel.setSize(504, 224);
		ContentPanel.setBackground(new Color(255, 255, 240));
		ContentPanel.setBorder(new LineBorder(null, 1, true));
		ContentPanel.setBounds(230, 11, 464, 317);
		getContentPane().add(ContentPanel);
		ContentPanel.setLayout(null);

		lblConsultas = new JLabel("");
		lblConsultas.setBounds(10, 11, 112, 24);
		ContentPanel.add(lblConsultas);

		// Text Area

		textAreaConsultas = new JTextArea();
		textAreaConsultas.setEditable(false);
		textAreaConsultas.setEnabled(false);
		textAreaConsultas.setVisible(false);
		textAreaConsultas.setBounds(10, 11, 444, 295);
		ContentPanel.add(textAreaConsultas);

		list = new JList();
		list.setEnabled(false);
		list.setVisible(false);
		list.setBounds(10, 11, 444, 295);
		ContentPanel.add(list);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setVisible(false);
		scrollPane.setBounds(10, 11, 444, 295);
		ContentPanel.add(scrollPane);

		// Radio button y MouseEvent clicked
		JRadioButton rdbtnMostrarUsuario = new JRadioButton("Mostrar Usuarios");
		rdbtnMostrarUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblConsultas.setText("");
				lblConsultas.setEnabled(false);
				textFieldUsuario1.setVisible(false);
				textFieldUsuario2.setVisible(false);
				btnAntiguedad.setVisible(false);
				textAreaConsultas.setEnabled(false);
				textAreaConsultas.setVisible(false);
				textAreaConsultas.setText("");

				mostrarUsuarioList();

			}
		});
		rdbtnMostrarUsuario.setBounds(6, 11, 150, 23);
		getContentPane().add(rdbtnMostrarUsuario);

		JRadioButton rdbtnGradoMedio = new JRadioButton("Grado Medio");
		rdbtnGradoMedio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				list.setVisible(false);
				list.setEnabled(false);
				scrollPane.setVisible(false);
				scrollPane.setEnabled(false);

				textAreaConsultas.setEnabled(false);
				textAreaConsultas.setVisible(false);
				textAreaConsultas.setEditable(false);
				textFieldUsuario1.setVisible(false);
				textFieldUsuario2.setVisible(false);
				btnAntiguedad.setVisible(false);
				String gradoMedio = String.valueOf(coordinador.gradoMedio());
				lblConsultas.setEnabled(true);
				lblConsultas.setText("Grado medio: " + gradoMedio);
			}
		});
		rdbtnGradoMedio.setBounds(6, 115, 150, 23);
		getContentPane().add(rdbtnGradoMedio);

		JRadioButton rdbtnCentralidad = new JRadioButton("Centralidad");
		rdbtnCentralidad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblConsultas.setText("");
				list.setVisible(false);
				list.setEnabled(false);
				scrollPane.setVisible(false);
				scrollPane.setEnabled(false);
				textFieldUsuario1.setVisible(false);
				textFieldUsuario2.setVisible(false);
				btnAntiguedad.setVisible(false);

				textAreaConsultas.setEnabled(true);
				textAreaConsultas.setVisible(true);

				textAreaConsultas.setText("Codigo\tNombre\tCantidad de amigos\n");
				// int i = 0;
				for (Entry<Usuario, Integer> cen : coordinador.centralidad()) {
					// if (i == Constante.REPETECIONES)
					// break;
					textAreaConsultas.append(cen.getKey().getCodigo() + "\t" + cen.getKey().getNombre()
							+ "\t               " + cen.getValue() + "\n");
					// i++;
				}

			}
		});
		rdbtnCentralidad.setBounds(6, 141, 150, 23);
		getContentPane().add(rdbtnCentralidad);

		JRadioButton rdbtnAntiguedad = new JRadioButton("Antiguedad");
		rdbtnAntiguedad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblConsultas.setText("");
				textAreaConsultas.setText("");
				textAreaConsultas.setEnabled(false);
				textAreaConsultas.setVisible(false);
				textFieldUsuario1.setVisible(true);
				textFieldUsuario2.setVisible(true);
				btnAntiguedad.setVisible(true);

				mostrarUsuarioList();

			}
		});

		rdbtnAntiguedad.setBounds(6, 167, 150, 23);
		getContentPane().add(rdbtnAntiguedad);

		JRadioButton rdbtnusuariosDensaConectados = new JRadioButton("Usuarios Densamente Conectados");
		rdbtnusuariosDensaConectados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					lblConsultas.setText("");
					list.setVisible(false);
					list.setEnabled(false);
					scrollPane.setVisible(false);
					scrollPane.setEnabled(false);
					textFieldUsuario1.setVisible(false);
					textFieldUsuario2.setVisible(false);
					btnAntiguedad.setVisible(false);

					textAreaConsultas.setEnabled(true);
					textAreaConsultas.setVisible(true);

					textAreaConsultas.setText("Codigo\tNombre\t\tInteraccion diaria\n");

					for (Entry<Usuario, Integer> cen : coordinador.usuariosDensaConectados()) {
						// if (i == Constante.REPETECIONES)
						// break;
						textAreaConsultas.append(cen.getKey().getCodigo() + "\t" + cen.getKey().getNombre() + "\t\t"
								+ cen.getValue() + "\n");
						// i++;
					}

				}
			}
		});
		rdbtnusuariosDensaConectados.setBounds(6, 37, 203, 23);
		getContentPane().add(rdbtnusuariosDensaConectados);

		JRadioButton rdbtnAmigosUsuario = new JRadioButton("Amigos de un usuario");
		rdbtnAmigosUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblConsultas.setText("");
				textAreaConsultas.setText("");
				textAreaConsultas.setEnabled(false);
				textAreaConsultas.setVisible(false);
				textFieldUsuario2.setVisible(false);

				textFieldUsuario1.setVisible(true);
				btnAmigoDeUnUsr.setVisible(true);

				mostrarUsuarioList();

			}
		});
		rdbtnAmigosUsuario.setBounds(6, 63, 150, 23);
		getContentPane().add(rdbtnAmigosUsuario);

		JRadioButton rdbtnSugerenciaAmistad = new JRadioButton("Sugerencias de amistad de un usuario");
		rdbtnSugerenciaAmistad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblConsultas.setText("");
				textAreaConsultas.setText("");
				textAreaConsultas.setEnabled(false);
				textAreaConsultas.setVisible(false);
				textFieldUsuario2.setVisible(false);

				textFieldUsuario1.setVisible(true);
				btnSugerenciaAmistad.setVisible(true);

				mostrarUsuarioList();
			}
		});
		rdbtnSugerenciaAmistad.setVerticalAlignment(SwingConstants.TOP);
		rdbtnSugerenciaAmistad.setBounds(6, 89, 214, 23);
		getContentPane().add(rdbtnSugerenciaAmistad);

		btnGroupRadio = new ButtonGroup();
		btnGroupRadio.add(rdbtnMostrarUsuario);
		btnGroupRadio.add(rdbtnGradoMedio);
		btnGroupRadio.add(rdbtnCentralidad);
		btnGroupRadio.add(rdbtnAntiguedad);
		btnGroupRadio.add(rdbtnusuariosDensaConectados);
		btnGroupRadio.add(rdbtnAmigosUsuario);
		btnGroupRadio.add(rdbtnSugerenciaAmistad);

		textFieldUsuario1 = new JTextField();
		textFieldUsuario1.setBounds(6, 239, 50, 20);
		getContentPane().add(textFieldUsuario1);
		textFieldUsuario1.setColumns(10);
		textFieldUsuario1.setVisible(false);

		textFieldUsuario2 = new JTextField();
		textFieldUsuario2.setBounds(6, 269, 50, 20);
		getContentPane().add(textFieldUsuario2);
		textFieldUsuario2.setColumns(10);
		textFieldUsuario2.setVisible(false);

		JLabel lblUsuario1 = new JLabel("");
		lblUsuario1.setBounds(70, 239, 150, 20);
		getContentPane().add(lblUsuario1);

		JLabel lblUsuario2 = new JLabel("");
		lblUsuario2.setBounds(66, 269, 154, 20);
		getContentPane().add(lblUsuario2);

		Handler handler = new Handler();

		btnAntiguedad = new JButton("Aceptar");
		btnAntiguedad.addActionListener(handler);

		btnAntiguedad.setBounds(6, 300, 95, 23);
		getContentPane().add(btnAntiguedad);
		btnAntiguedad.setVisible(false);

		btnAmigoDeUnUsr = new JButton("Aceptar");
		btnAmigoDeUnUsr.setBounds(6, 300, 95, 23);
		getContentPane().add(btnAmigoDeUnUsr);
		btnAmigoDeUnUsr.setVisible(false);
		btnAmigoDeUnUsr.addActionListener(handler);

		btnSugerenciaAmistad = new JButton("Aceptar");
		btnSugerenciaAmistad.setBounds(6, 300, 95, 23);
		getContentPane().add(btnSugerenciaAmistad);
		btnSugerenciaAmistad.setVisible(false);
		btnSugerenciaAmistad.addActionListener(handler);

		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(NORMAL);
			}
		});
		btnSalir.setBounds(6, 205, 85, 23);
		getContentPane().add(btnSalir);
		btnSalir.setVisible(true);
	}

	private void mostrarUsuarioList() {
		list.setVisible(true);
		list.setEnabled(true);
		scrollPane.setVisible(true);
		scrollPane.setEnabled(true);

		String[] nombres = new String[coordinador.listaUsuarios().size()];
		int i = 0;
		for (Entry<String, Usuario> u : coordinador.listaUsuarios().entrySet()) {
			nombres[i++] = u.getValue().toString();
		}

		list.setListData(nombres);
		scrollPane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
	}

	private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnAntiguedad) {
				String codUsuario1 = textFieldUsuario1.getText();
				String codUsuario2 = textFieldUsuario2.getText();
				list.setVisible(false);
				list.setEnabled(false);
				scrollPane.setVisible(false);
				scrollPane.setEnabled(false);

				List<Relacion> r = null;
				try {
					r = coordinador.antiguedad(
							coordinador.buscarUsuario(new Usuario(codUsuario1, null, null, null, null, null)),
							coordinador.buscarUsuario(new Usuario(codUsuario2, null, null, null, null, null)));
				} catch (IllegalArgumentException ex) {
					list.setVisible(true);
					list.setEnabled(true);
					scrollPane.setVisible(true);
					scrollPane.setEnabled(true);
					logger.error("Relaciones no existen!");
					JOptionPane.showMessageDialog(null, "no existe relacion posible!");
					return;
				} catch (UsuarioNoValidoException ex) {
					list.setVisible(true);
					list.setEnabled(true);
					scrollPane.setVisible(true);
					scrollPane.setEnabled(true);
					logger.error("Usuario no existe...!");
					JOptionPane.showMessageDialog(null, ex.getMessage());
					return;
				} catch (RelacionNoValidaException ex) {
					list.setVisible(true);
					list.setEnabled(true);
					scrollPane.setVisible(true);
					scrollPane.setEnabled(true);
					logger.error("Mismo Usuario!");
					JOptionPane.showMessageDialog(null, ex.getMessage());
					return;
				}

				textAreaConsultas.setEnabled(true);
				textAreaConsultas.setVisible(true);
				textAreaConsultas
						.setText("Codigo del primer usuario\tTiempo siendo amigos\tCodigo del segundo usuario\n");

				for (Relacion re : r) {

					textAreaConsultas.append(re.getUsr1().getCodigo() + "\t\t" + re.gettSiendoAmigos() + "\t\t"
							+ re.getUsr2().getCodigo() + "\n");

				}
				logger.info("Consulta concretada");
				return;
			}

			if (event.getSource() == btnAmigoDeUnUsr) {
				String codUsuario1 = textFieldUsuario1.getText();
				list.setVisible(false);
				list.setEnabled(false);
				scrollPane.setVisible(false);
				scrollPane.setEnabled(false);
				Map<Usuario, Integer> amigos = null;
				try {
					amigos = coordinador.mapaAmigos(codUsuario1);
				} catch (UsuarioNoValidoException ex) {
					list.setVisible(true);
					list.setEnabled(true);
					scrollPane.setVisible(true);
					scrollPane.setEnabled(true);
					logger.error("Usuario no existe...!");
					JOptionPane.showMessageDialog(null, ex.getMessage());
					return;
				}

				textAreaConsultas.setEnabled(true);
				textAreaConsultas.setVisible(true);
				textAreaConsultas.setText("Nombre del amigo\tTiempo siendo amigos\n");

				for (Entry<Usuario, Integer> amigo : amigos.entrySet()) {
					textAreaConsultas.append(amigo.getKey().getNombre() + "\t\t" + amigo.getValue() + "\n");
				}
//				for (Relacion re : r) {
//
//					textAreaConsultas.append(re.getUsr1().getCodigo() + "\t\t" + re.gettSiendoAmigos() + "\t\t"
//							+ re.getUsr2().getCodigo() + "\n");
//
//				}
				return;

			}

			if (event.getSource() == btnSugerenciaAmistad) {
				String codUsuario1 = textFieldUsuario1.getText();
				list.setVisible(false);
				list.setEnabled(false);
				scrollPane.setVisible(false);
				scrollPane.setEnabled(false);
				List<Usuario> sugerenciaAmistad = null;
				try {
					sugerenciaAmistad = coordinador.sugerenciaAmistad(codUsuario1);
				} catch (UsuarioNoValidoException ex) {
					list.setVisible(true);
					list.setEnabled(true);
					scrollPane.setVisible(true);
					scrollPane.setEnabled(true);
					logger.error("Usuario no existe...!");
					JOptionPane.showMessageDialog(null, ex.getMessage());
					return;
				}

				textAreaConsultas.setEnabled(true);
				textAreaConsultas.setVisible(true);
				textAreaConsultas.setText("Sugencia de amistad\n");

				for (Usuario usr : sugerenciaAmistad) {
					textAreaConsultas.append(usr.getNombre() + "\n");
				}

				return;
			}
		}

	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
