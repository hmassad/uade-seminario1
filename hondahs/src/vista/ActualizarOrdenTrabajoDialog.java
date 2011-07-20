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
import javax.swing.table.DefaultTableModel;

import modelo.EstadoOrdenTrabajo;
import modelo.Presupuesto;
import modelo.Tarea;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class ActualizarOrdenTrabajoDialog extends JDialog implements
		IPresupuestoPerformer, ITareaPerformer {

	private static final long serialVersionUID = 1L;

	private JPanel filtrosPanel;
	private JLabel presupuestoLabel;
	private JTextField presupuestoTextField;
	private JButton buscarButton;
	private JPanel tareasPanel;
	private JTable tareasTable;
	private JPanel botonesPanel;
	private JButton cancelarButton;
	private JButton aceptarButton;

	private Presupuesto presupuesto;
	private List<Tarea> tareas;
	private JPanel tareasButtonsPanel;
	private JButton agregarTareaButton;
	private JButton eliminarTareaButton;

	public ActualizarOrdenTrabajoDialog() {
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
				BuscarPresupuestoDialog buscarPresupuestoDialog = new BuscarPresupuestoDialog(ActualizarOrdenTrabajoDialog.this);
				buscarPresupuestoDialog.setVisible(true);
//				buscarPresupuestoDialog
//						.setPresupuestoPerformer(ActualizarOrdenTrabajoDialog.this);
			}
		});

		tareasPanel = new JPanel();
		getContentPane().add(tareasPanel, BorderLayout.CENTER);

		tareasTable = new JTable();
		tareasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tareasTable.setDoubleBuffered(true);
		tareasTable.setFillsViewportHeight(true);
		tareasTable.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, }, new String[] { "N\u00FAmero",
				"Cliente", "Veh\u00EDculo", "Fecha" }) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					String.class, Object.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { true, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tareasTable.getColumnModel().getColumn(0).setResizable(false);
		tareasTable.getColumnModel().getColumn(2).setResizable(false);
		tareasTable.getColumnModel().getColumn(3).setResizable(false);
		tareasTable.getColumnModel().getColumn(3).setMinWidth(75);
		tareasTable.getColumnModel().getColumn(3).setMaxWidth(75);
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
						.setTareaPerformer(ActualizarOrdenTrabajoDialog.this);
				agregarTareaDialog.setVisible(true);
			}
		});
		tareasButtonsPanel.add(agregarTareaButton, "2, 2, left, top");

		eliminarTareaButton = new JButton("Eliminar Tarea");
		eliminarTareaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO obtener la tarea seleccionada
				Tarea tarea = null;
				if (tarea != null)
					ActualizarOrdenTrabajoDialog.this.removeTarea(tarea);
			}
		});
		tareasButtonsPanel.add(eliminarTareaButton, "4, 2");

		botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		cancelarButton = new JButton("Cancelar");
		botonesPanel.add(cancelarButton);
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ActualizarOrdenTrabajoDialog.this.dispose();
			}
		});

		aceptarButton = new JButton("Aceptar");
		botonesPanel.add(aceptarButton);
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sistema.getInstancia().generarOrdenTrabajo(
						ActualizarOrdenTrabajoDialog.this.getPresupuesto(),
						new java.util.Date().toString(), null,
						EstadoOrdenTrabajo.PREPARADO,
						ActualizarOrdenTrabajoDialog.this.getTareas());
				ActualizarOrdenTrabajoDialog.this.dispose();
			}
		});

		tareas = new Vector<Tarea>();
	}

	protected void updateTareasTable() {
		// TODO borrar el contenido de la tabla
		for (Tarea tarea : this.tareas) {
			// TODO agregar las tareas a la tabla
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
		this.tareas = tareas;
	}

	public List<Tarea> getTareas() {
		return tareas;
	}

	@Override
	public void addTarea(Tarea tarea) {
		this.tareas.add(tarea);
		updateTareasTable();
	}

	@Override
	public void removeTarea(Tarea tarea) {
		if (this.tareas.contains(tarea)) {
			this.tareas.remove(tarea);
			updateTareasTable();
		}
	}
}
