package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import modelo.Tarea;
import modelo.Usuario;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class TareasAsignadasDialog extends JDialog {

	class TareasTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		List<Tarea> tareas = new Vector<Tarea>();

		public void clear() {
			int rows = tareas.size();
			tareas.clear();
			TableModelEvent event = new TableModelEvent(this, 0, rows,
					TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(event);
				}
		}

		public void add(Tarea tarea) {
			tareas.add(tarea);
			TableModelEvent event = new TableModelEvent(this,
					tareas.size() - 1, tareas.size() - 1,
					TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(event);
				}
		}

		public void remove(Tarea tarea) {
			int index = tareas.indexOf(tarea);
			tareas.remove(tarea);

			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(new TableModelEvent(
							this, index, index, TableModelEvent.ALL_COLUMNS,
							TableModelEvent.DELETE));
				}
		}

		public Tarea getTareaAt(int rowIndex) {
			return tareas.get(rowIndex);
		}

		String[] titulos = new String[] { "Número", "Tarea", "Fecha Inicio",
				"Fecha Fin", "Estado" };

		@Override
		public String getColumnName(int column) {
			return titulos[column];
		}

		@Override
		public int getColumnCount() {
			return titulos.length;
		}

		@Override
		public int getRowCount() {
			return tareas.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Tarea tarea = tareas.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return tarea.getNumero();
			case 1:
				return tarea.getTipoTarea().getDescripcion();
			case 2:
				return tarea.getFechaInicio();
			case 3:
				return tarea.getFechaFin();
			case 4:
				return tarea.getEstado().toString();
			}
			throw new RuntimeException("La columna no existe.");
		}

		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] { Integer.class, String.class,
				String.class, String.class, String.class };

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}

		boolean[] columnEditables = new boolean[] { false, false, false, false,
				false };

		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}

		public void setTareas(List<Tarea> tareas) {
			clear();
			for (Tarea tarea : tareas)
				add(tarea);
		}

		public List<Tarea> getTareas() {
			return tareas;
		}
	}

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private TareasTableModel tareasTableModel;

	private JTable tareasTable;
	private JTextField fechaInicioTextField;
	private JTextField fechaFinTextField;
	private JPanel filtrosPanel;
	private JLabel fechaInicioLabel;
	private JButton fechaInicioHoyButton;
	private JButton buscarButton;
	private JLabel fechaFinalLabel;
	private JButton fechaFinHoyButton;
	private JButton modificarButton;
	private JButton cerrarButton;
	private JPanel botonesPanel;
	private JPanel tablaPanel;

	private JScrollPane scrollPane;

	public TareasAsignadasDialog() {
		setName("tareasAsignadasDialog");
		setModal(true);
		setSize(new Dimension(400, 300));
		setPreferredSize(new Dimension(400, 300));
		setTitle("Tareas Asignadas");

		filtrosPanel = new JPanel();
		getContentPane().add(filtrosPanel, BorderLayout.NORTH);
		filtrosPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(89dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.BUTTON_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC, }));

		fechaInicioLabel = new JLabel("Fecha Inicio");
		filtrosPanel.add(fechaInicioLabel, "2, 2, right, default");

		fechaInicioTextField = new JTextField();
		fechaInicioTextField.setHorizontalAlignment(SwingConstants.CENTER);
		filtrosPanel.add(fechaInicioTextField, "4, 2, fill, default");
		fechaInicioTextField.setColumns(10);

		fechaInicioHoyButton = new JButton("Hoy");
		fechaInicioHoyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fechaInicioTextField.setText(new SimpleDateFormat("dd/MM/yyyy")
						.format(new Date()));
			}
		});
		filtrosPanel.add(fechaInicioHoyButton, "6, 2");

		buscarButton = new JButton("Buscar");
		buscarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actualizarTareas();
			}
		});
		filtrosPanel.add(buscarButton, "8, 2, 1, 3");

		fechaFinalLabel = new JLabel("Fecha Final");
		filtrosPanel.add(fechaFinalLabel, "2, 4, right, default");

		fechaFinTextField = new JTextField();
		fechaFinTextField.setHorizontalAlignment(SwingConstants.CENTER);
		fechaFinTextField.setColumns(10);
		filtrosPanel.add(fechaFinTextField, "4, 4, fill, default");

		fechaFinHoyButton = new JButton("Hoy");
		fechaFinHoyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fechaFinTextField.setText(new SimpleDateFormat("dd/MM/yyyy")
						.format(new Date()));
			}
		});
		filtrosPanel.add(fechaFinHoyButton, "6, 4");

		tablaPanel = new JPanel();
		getContentPane().add(tablaPanel, BorderLayout.CENTER);

		tareasTableModel = new TareasTableModel();
		tablaPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablaPanel.add(scrollPane);
		scrollPane.setViewportView(tareasTable);

		tareasTable = new JTable();
		tareasTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(tareasTable);
		tareasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tareasTable.setDoubleBuffered(true);
		tareasTable.setModel(tareasTableModel);
		tareasTable.getColumnModel().getColumn(0).setResizable(false);
		tareasTable.getColumnModel().getColumn(1).setPreferredWidth(142);
		tareasTable.getColumnModel().getColumn(2).setResizable(false);
		tareasTable.getColumnModel().getColumn(3).setResizable(false);
		tareasTable.getColumnModel().getColumn(4).setResizable(false);
		ListSelectionModel rowSM = tareasTable.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				ListSelectionModel rowSM = (ListSelectionModel) e.getSource();
				int selectedIndex = rowSM.getMinSelectionIndex();
				// do something with selected index
				TareasAsignadasDialog.this.modificarButton
						.setEnabled(selectedIndex != -1);
			}
		});

		botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		cerrarButton = new JButton("Cerrar");
		cerrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TareasAsignadasDialog.this.dispose();
			}
		});
		botonesPanel.add(cerrarButton);

		modificarButton = new JButton("Modificar");
		modificarButton.setEnabled(false);
		modificarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Tarea tarea = TareasAsignadasDialog.this.tareasTableModel
						.getTareaAt(tareasTable.getSelectedRow());
				ActualizarEstadoTareaDialog actualizarEstadoTareaDialog = new ActualizarEstadoTareaDialog();
				actualizarEstadoTareaDialog.setTarea(tarea);
				actualizarEstadoTareaDialog.setVisible(true);
				actualizarTareas();
			}
		});
		botonesPanel.add(modificarButton);
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	private void actualizarTareas() {
		tareasTableModel.clear();
		Tarea[] tareas = Sistema.getInstancia().getTareasPorOperario(usuario);
		for (Tarea tarea : tareas) {
			tareasTableModel.add(tarea);
		}
	}
}
