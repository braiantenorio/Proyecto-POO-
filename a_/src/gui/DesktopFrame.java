package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;

public class DesktopFrame extends JFrame {

	private Coordinador coordinador;
	private JPanel contentPane;

	public DesktopFrame() {		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 600);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("Red Social");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Salir");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 System.exit(NORMAL);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);

		JMenu mnNewMenu = new JMenu("Redes Sociales");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Consultas");
		
		mntmNewMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg) {
				coordinador.mostrarRedesSocialesForm();
			}
		});
		
		//mntmNewMenuItem.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent arg0) {
		//		coordinador.mostrarRedesSocialesForm;
		//	}
		//});
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel(new GridLayout( 2, 2, 5, 5 ));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setSize(600, 480);
		setTitle("Redes Sociales: MVC");
		setLocationRelativeTo(null);
		setResizable(false);
		//setLayout(new FlowLayout());
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	
}