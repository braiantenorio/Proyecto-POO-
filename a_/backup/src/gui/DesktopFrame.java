package gui;

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
import javax.swing.border.LineBorder;

import controlador.AppFacade;
import controlador.Coordinador;
import modelo.Relacion;
import modelo.Usuario;
import negocio.RelacionNoValidaException;
import negocio.UsuarioNoValidoException;
import net.datastructures.Entry;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;

public class DesktopFrame extends JFrame {

	private Coordinador coordinador;
	private ButtonGroup btnGroupRadio;
	private JTextField textFieldUsuario1;
	private JTextField textFieldUsuario2;
	private JButton btnAntiguedad;
	private JButton btnSalir;
	private JLabel lblConsultas;
	private JList list;
	private JTextArea textAreaConsultas;

	public DesktopFrame() {
		setResizable(false);
		// Frame config
		setSize(720, 285);

		// Frame Panel
		getContentPane().setLayout(null);

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
		JPanel ContentPanel = new JPanel();
		ContentPanel.setSize(504, 224);
		ContentPanel.setBackground(new Color(255, 255, 240));
		ContentPanel.setBorder(new LineBorder(null, 1, true));
		ContentPanel.setBounds(190, 11, 504, 224);
		getContentPane().add(ContentPanel);
		ContentPanel.setLayout(null);

		lblConsultas = new JLabel("");
		lblConsultas.setBounds(10, 11, 112, 24);
		ContentPanel.add(lblConsultas);

		textAreaConsultas = new JTextArea();
		textAreaConsultas.setEnabled(false);
		textAreaConsultas.setVisible(false);
		textAreaConsultas.setBounds(10, 11, 484, 202);
		ContentPanel.add(textAreaConsultas);
		

		list = new JList();
		list.setEnabled(false);
		list.setVisible(false);
		list.setBounds(10, 11, 484, 202);
		ContentPanel.add(list);

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
		rdbtnMostrarUsuario.setBounds(6, 11, 109, 23);
		getContentPane().add(rdbtnMostrarUsuario);

		JRadioButton rdbtnGradoMedio = new JRadioButton("Grado Medio");
		rdbtnGradoMedio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				list.setVisible(false);
				list.setEnabled(false);

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
		rdbtnGradoMedio.setBounds(6, 47, 109, 23);
		getContentPane().add(rdbtnGradoMedio);

		JRadioButton rdbtnCentralidad = new JRadioButton("Centralidad");
		rdbtnCentralidad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblConsultas.setText("");
				list.setVisible(false);
				list.setEnabled(false);

				textAreaConsultas.setEnabled(true);
				textAreaConsultas.setVisible(true);
				textFieldUsuario1.setVisible(false);
				textFieldUsuario2.setVisible(false);
				btnAntiguedad.setVisible(false);
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
		rdbtnCentralidad.setBounds(6, 81, 109, 23);
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
		rdbtnAntiguedad.setBounds(6, 117, 109, 23);
		getContentPane().add(rdbtnAntiguedad);

		btnGroupRadio = new ButtonGroup();
		btnGroupRadio.add(rdbtnMostrarUsuario);
		btnGroupRadio.add(rdbtnGradoMedio);
		btnGroupRadio.add(rdbtnCentralidad);
		btnGroupRadio.add(rdbtnAntiguedad);

		textFieldUsuario1 = new JTextField();
		textFieldUsuario1.setBounds(6, 147, 50, 20);
		getContentPane().add(textFieldUsuario1);
		textFieldUsuario1.setColumns(10);
		textFieldUsuario1.setVisible(false);

		textFieldUsuario2 = new JTextField();
		textFieldUsuario2.setBounds(6, 178, 50, 20);
		getContentPane().add(textFieldUsuario2);
		textFieldUsuario2.setColumns(10);
		textFieldUsuario2.setVisible(false);

		JLabel lblUsuario1 = new JLabel("");
		lblUsuario1.setBounds(66, 147, 115, 20);
		getContentPane().add(lblUsuario1);

		JLabel lblUsuario2 = new JLabel("");
		lblUsuario2.setBounds(66, 178, 114, 20);
		getContentPane().add(lblUsuario2);

		btnAntiguedad = new JButton("Aceptar");
		btnAntiguedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String codUsuario1 = textFieldUsuario1.getText();
				String codUsuario2 = textFieldUsuario2.getText();
				list.setVisible(false);
				list.setEnabled(false);

				List<Relacion> r = null;
				try {
					r = coordinador.antiguedad(AppFacade.usuarios.get(codUsuario1),
							AppFacade.usuarios.get(codUsuario2));
				} catch (UsuarioNoValidoException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
					return;
				} catch (RelacionNoValidaException ex) {
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

			}
		});
		btnAntiguedad.setBounds(6, 212, 95, 23);
		getContentPane().add(btnAntiguedad);
		btnAntiguedad.setVisible(false);

		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(NORMAL);
			}
		});
		btnSalir.setBounds(103, 212, 85, 23);
		getContentPane().add(btnSalir);
		btnSalir.setVisible(true);
	}

	private void mostrarUsuarioList() {
		list.setVisible(true);
		list.setEnabled(true);

		String[] nombres = new String[coordinador.listaUsuarios().size()];
		int i = 0;
		// for (int i = 0; i < coordinador.listaUsuarios().size(); i++) {
		// nombres[i] = coordinador.listaUsuarios().get(i).toString();
		for (Entry<String, Usuario> u : coordinador.listaUsuarios().entrySet()) {
			nombres[i++] = u.getValue().toString();
		}

		list.setListData(nombres);
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}