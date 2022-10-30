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
import modelo.Relacion;
import modelo.Usuario;

public class RelacionesList extends JDialog {

	private Coordinador coordinador;
	private int accion;
	private Relacion relacion;

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tableRelacion;
	private JButton btnInsertar;
	private JButton btnSalir;

	/**
	 * Create the frame.
	 */
	public RelacionesList() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		tableRelacion = new JTable();
		tableRelacion.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "ID", "Interaccion", "likes", "Desde", "Modificar", "Borrar" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableRelacion.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
		tableRelacion.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox()));
		tableRelacion.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
		tableRelacion.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox()));
		scrollPane.setViewportView(tableRelacion);

		Handler handler = new Handler();
		btnInsertar.addActionListener(handler);
		btnSalir.addActionListener(handler);
		
		setModal(true);
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			RelacionesForm relacionForm = null;
			if (event.getSource() == btnInsertar)
				coordinador.insertarRelacionesForm();
			if (event.getSource() == btnSalir) {
				coordinador.cancelarListRelacion();
			}
			
		}
	}

	public void loadTable() {
		// Eliminar todas las filas
		((DefaultTableModel) tableRelacion.getModel()).setRowCount(0);
		for (Relacion rel : coordinador.listaRelaciones())
			if (rel instanceof Relacion)
				addRow((Relacion) rel);
	}

	public void addRow(Relacion rel) {
		Object[] row = new Object[tableRelacion.getModel().getColumnCount()];
		
		row[0] = rel.getUsr1().getCodigo();
		row[1] = rel.getUsr2().getCodigo();
		row[2] = rel.gettInterDiaria();
		row[3] = rel.getLikes();
		row[4] = rel.getFecha();
		row[5] = "edit";
		row[6] = "drop";
		((DefaultTableModel) tableRelacion.getModel()).addRow(row);
	}

	private void updateRow(int row) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		tableRelacion.setValueAt(relacion.getUsr1().getCodigo(), row, 0);
		tableRelacion.setValueAt(relacion.getUsr2().getCodigo(), row, 1);
		tableRelacion.setValueAt(relacion.gettInterDiaria(), row, 2);
		tableRelacion.setValueAt(relacion.getLikes(), row, 3);
		tableRelacion.setValueAt(relacion.getFecha(), row, 4);
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
				String id = tableRelacion.getValueAt(tableRelacion.getSelectedRow(), 0).toString();
				Usuario id1 = coordinador.buscarUsuario(new Usuario(id, null, null, null, null, null));
				id = tableRelacion.getValueAt(tableRelacion.getSelectedRow(), 1).toString();
				Usuario id2 = coordinador.buscarUsuario(new Usuario(id, null, null, null, null, null));
				Relacion rel = (Relacion) coordinador.buscarRelacion(new Relacion(id1, id2, 0, 0, null));
				if (label.equals("edit"))
					coordinador.modificarRelacionForm(rel);
				else
					coordinador.borrarRelacionForm(rel);
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

	public void setRelacion(Relacion relacion) {
		this.relacion = relacion;
	}

}
