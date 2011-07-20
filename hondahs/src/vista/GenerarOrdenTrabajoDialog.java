package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import modelo.EstadoOrdenTrabajo;
import modelo.Presupuesto;
import modelo.Tarea;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class GenerarOrdenTrabajoDialog extends JDialog implements
		IPresupuestoPerformer, ITareaPerformer {

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

		String[] titulos = new String[] { "Número", "Tarea", "Operario" };

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
				return tarea.getUsuario().getNombre();
			}
			throw new RuntimeException("La columna no existe.");
		}

		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] { Integer.class, String.class,
				String.class };

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}

		boolean[] columnEditables = new boolean[] { false, false, false };

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

	private JPanel filtrosPanel;
	private JLabel presupuestoLabel;
	private JTextField presupuestoTextField;
	private JButton buscarButton;
	private JPanel tareasPanel;
	private JTable tareasTable;
	private TareasTableModel tareasTableModel;
	private JPanel botonesPanel;
	private JButton cancelarButton;
	private JButton aceptarButton;

	private Presupuesto presupuesto;
	private JPanel tareasButtonsPanel;
	private JButton agregarTareaButton;
	private JButton eliminarTareaButton;

	public GenerarOrdenTrabajoDialog() {
		setName("generarOrdenTrabajoDialog");
		setModal(true);
		setSize(new Dimension(400, 300));
		setPreferredSize(new Dimension(400, 300));
		setTitle("Generar Orden de Trabajo");

		filtrosPanel = new JPanel();
		getContentPane().add(filtrosPanel, BorderLayout.NORTH);
		filtrosPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(89dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.BUTTON_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC, }));

		presupuestoLabel = new JLabel("Presupuesto");
		filtrosPanel.add(presupuestoLabel, "2, 2, right, default");

		presupuestoTextField = new JTextField();
		filtrosPanel.add(presupuestoTextField, "4, 2, fill, default");
		presupuestoTextField.setColumns(10);

		buscarButton = new JButton("Buscar");
		filtrosPanel.add(buscarButton, "6, 2");
		buscarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BuscarPresupuestoDialog buscarPresupuestoDialog = new BuscarPresupuestoDialog(
						GenerarOrdenTrabajoDialog.this);
				buscarPresupuestoDialog.setVisible(true);
				cargarNumeroPresupuesto();
			}
		});

		tareasPanel = new JPanel();
		getContentPane().add(tareasPanel, BorderLayout.CENTER);

		tareasTableModel = new TareasTableModel();

		tareasTable = new JTable();
		tareasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tareasTable.setDoubleBuffered(true);
		tareasTable.setFillsViewportHeight(true);
		tareasTable.setModel(tareasTableModel);
		tareasTable.getColumnModel().getColumn(0).setResizable(false);
		tareasTable.getColumnModel().getColumn(1).setResizable(false);
		tareasTable.getColumnModel().getColumn(2).setResizable(false);
		tareasPanel.setLayout(new BorderLayout(0, 0));
		tareasPanel.add(tareasTable);

		tareasButtonsPanel = new JPanel();
		tareasPanel.add(tareasButtonsPanel, BorderLayout.NORTH);
		tareasButtonsPanel.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, }));

		agregarTareaButton = new JButton("Agregar Tarea");
		agregarTareaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AgregarTareaDialog agregarTareaDialog = new AgregarTareaDialog();
				agregarTareaDialog
						.setTareaPerformer(GenerarOrdenTrabajoDialog.this);
				agregarTareaDialog.setVisible(true);

			}
		});
		tareasButtonsPanel.add(agregarTareaButton, "2, 2, left, top");

		eliminarTareaButton = new JButton("Eliminar Tarea");
		eliminarTareaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Tarea tarea = tareasTableModel.getTareaAt(tareasTable
						.getSelectedRow());
				if (tarea != null)
					GenerarOrdenTrabajoDialog.this.removeTarea(tarea);
			}
		});
		tareasButtonsPanel.add(eliminarTareaButton, "4, 2");

		botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		cancelarButton = new JButton("Cancelar");
		botonesPanel.add(cancelarButton);
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerarOrdenTrabajoDialog.this.dispose();
			}
		});

		aceptarButton = new JButton("Aceptar");
		botonesPanel.add(aceptarButton);
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sistema.getInstancia().generarOrdenTrabajo(
						GenerarOrdenTrabajoDialog.this.getPresupuesto(),
						new java.util.Date().toString(), null,
						EstadoOrdenTrabajo.PREPARADO,
						GenerarOrdenTrabajoDialog.this.getTareas());
				GenerarOrdenTrabajoDialog.this.dispose();
			}
		});
	}

	/**
	 * Actualiza el textField con el numero de presupuesto devuelto por el
	 * buscar presupuesto
	 */
	private void cargarNumeroPresupuesto() {
		if (this.presupuesto != null) {
			this.presupuestoTextField.setText(this.presupuesto.getNumero()
					.toString());
		}
	}

	@Override
	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	@Override
	public Presupuesto getPresupuesto() {
		return this.presupuesto;
	}

	public void setTareas(List<Tarea> tareas) {
		tareasTableModel.setTareas(tareas);
	}

	public List<Tarea> getTareas() {
		return tareasTableModel.getTareas();
	}

	@Override
	public void addTarea(Tarea tarea) {
		tareasTableModel.add(tarea);
	}

	@Override
	public void removeTarea(Tarea tarea) {
		tareasTableModel.remove(tarea);
	}
}
