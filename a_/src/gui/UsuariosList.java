package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controlador.Coordinador;
import modelo.Usuario;
import net.datastructures.Entry;

public class UsuariosList extends JDialog {

	private Coordinador coordinador;
	private int accion;
	private Usuario usuario;

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tableUsuario;
	private JButton btnInsertar;
	private JButton btnSalir;

	public UsuariosList() {
		setBounds(100, 100, 756, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(38, 280, 114, 32);
		contentPane.add(btnInsertar);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(178, 280, 114, 32);
		contentPane.add(btnSalir);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 25, 673, 244);
		contentPane.add(scrollPane);

		tableUsuario = new JTable();
		tableUsuario.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Codigo", "Nombre", "Fecha Nac.",
				"Sexo", "Localidad", "NivelAcademico", "Modificar", "Borrar" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		tableUsuario.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
		tableUsuario.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox()));
		tableUsuario.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
		tableUsuario.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox()));
		scrollPane.setViewportView(tableUsuario);

		Handler handler = new Handler();
		btnInsertar.addActionListener(handler);
		btnSalir.addActionListener(handler);

		setModal(true);
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			UsuariosForm usuarioForm = null;
			if (event.getSource() == btnInsertar) {
				coordinador.insertarUsuarioForm();
			}
			if (event.getSource() == btnSalir) {
				coordinador.cancelarList();
			}
		}
	}

	public void loadTable() {
		// Eliminar todas las filas
		((DefaultTableModel) tableUsuario.getModel()).setRowCount(0);
		for (Entry<String, Usuario> usr : coordinador.listaUsuarios().entrySet())
			if (usr.getValue() instanceof Usuario)
				addRow((Usuario) usr.getValue());
	}

	public void addRow(Usuario usr) {
		Object[] row = new Object[tableUsuario.getModel().getColumnCount()];

		row[0] = usr.getCodigo();
		row[1] = usr.getNombre();
		row[2] = usr.getGenero();
		row[3] = usr.getFechadenac();
		row[4] = usr.getCiudadAct();
		row[5] = usr.getNivelAcademico();
		row[6] = "edit";
		row[7] = "drop";
		((DefaultTableModel) tableUsuario.getModel()).addRow(row);
	}

	private void updateRow(int row) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		tableUsuario.setValueAt(usuario.getCodigo(), row, 0);
		tableUsuario.setValueAt(usuario.getNombre(), row, 1);
		tableUsuario.setValueAt(usuario.getGenero(), row, 2);
		tableUsuario.setValueAt(usuario.getFechadenac(), row, 3);
		tableUsuario.setValueAt(usuario.getCiudadAct(), row, 4);
		tableUsuario.setValueAt(usuario.getNivelAcademico(), row, 5);
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			// setText((value == null) ? "" : value.toString());
			Icon icon = null;
			if (value.toString().equals("edit"))
				icon = new ImageIcon(getClass().getResource("/imagen/b_edit.png"));
			if (value.toString().equals("drop"))
				icon = new ImageIcon(getClass().getResource("/imagen/b_drop.png"));
			setIcon(icon);
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;
		private JTable table;
		private boolean isDeleteRow = false;
		private boolean isUpdateRow = false;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {

			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}

			label = (value == null) ? "" : value.toString();
			// button.setText(label);
			Icon icon = null;
			if (value.toString().equals("edit"))
				icon = new ImageIcon(getClass().getResource("/imagen/b_edit.png"));
			if (value.toString().equals("drop"))
				icon = new ImageIcon(getClass().getResource("/imagen/b_drop.png"));
			button.setIcon(icon);
			isPushed = true;
			this.table = table;
			isDeleteRow = false;
			isUpdateRow = false;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isPushed) {
				String id = tableUsuario.getValueAt(tableUsuario.getSelectedRow(), 0).toString();
				Usuario usr = (Usuario) coordinador.buscarUsuario(new Usuario(id, null, null, null, null, null));
				if (label.equals("edit"))
					coordinador.modificarUsuarioForm(usr);
				else
					coordinador.borrarUsuarioForm(usr);
			}
			if (accion == Constantes.BORRAR)
				isDeleteRow = true;
			if (accion == Constantes.MODIFICAR)
				isUpdateRow = true;
			isPushed = false;
			return new String(label);
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();

			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			if (isDeleteRow)
				tableModel.removeRow(table.getSelectedRow());

			if (isUpdateRow) {
				updateRow(table.getSelectedRow());
			}

		}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
