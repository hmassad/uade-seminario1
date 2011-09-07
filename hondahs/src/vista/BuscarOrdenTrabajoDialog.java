package vista;

import interfaces.IOrdenTrabajoPerformer;

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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import modelo.OrdenTrabajo;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class BuscarOrdenTrabajoDialog extends JDialog {

	class OrdenesTrabajoTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private List<OrdenTrabajo> ordenesTrabajo = new Vector<OrdenTrabajo>();

		public void clear() {
			int rows = ordenesTrabajo.size();
			ordenesTrabajo.clear();
			TableModelEvent event = new TableModelEvent(this, 0, rows,
					TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(event);
				}
		}

		public void add(OrdenTrabajo ordenTrabajo) {
			ordenesTrabajo.add(ordenTrabajo);
			TableModelEvent event = new TableModelEvent(this,
					ordenesTrabajo.size() - 1, ordenesTrabajo.size() - 1,
					TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(event);
				}
		}

		public void remove(OrdenTrabajo ordenTrabajo) {
			int index = ordenesTrabajo.indexOf(ordenTrabajo);
			ordenesTrabajo.remove(ordenTrabajo);

			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(new TableModelEvent(
							this, index, index, TableModelEvent.ALL_COLUMNS,
							TableModelEvent.DELETE));
				}
		}

		public OrdenTrabajo getOrdenTrabajoAt(int rowIndex) {
			return ordenesTrabajo.get(rowIndex);
		}

		String[] titulos = new String[] { "N\u00FAmero", "Cliente", "Veh\u00EDculo",
				"Fecha Inicio", "Estado" };

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
			return ordenesTrabajo.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			OrdenTrabajo ordenTrabajo = ordenesTrabajo.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return ordenTrabajo.getNumero();
			case 1:
				return ordenTrabajo.getPresupuesto().getCliente().getNombre();
			case 2:
				return ordenTrabajo.getPresupuesto().getVehiculo().getPatente();
			case 3:
				return ordenTrabajo.getFechaInicio();
			case 4:
				return ordenTrabajo.getEstado().toString();
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

		public void setOrdenesTrabajo(List<OrdenTrabajo> ordenesTrabajo) {
			clear();
			for (OrdenTrabajo ordenTrabajo : ordenesTrabajo)
				add(ordenTrabajo);
		}

		public List<OrdenTrabajo> getOrdenesTrabajo() {
			return ordenesTrabajo;
		}
	}

	private static final long serialVersionUID = 1L;

	private OrdenesTrabajoTableModel ordenesTrabajoTableModel;
	private IOrdenTrabajoPerformer ordenTrabajoPerformer;

	private JTable ordenesTrabajoTable;
	private JTextField fechaInicioTextField;
	private JTextField fechaFinTextField;
	private JPanel filtrosPanel;
	private JLabel fechaInicioLabel;
	private JButton fechaInicioHoyButton;
	private JButton fechaFinHoyButton;
	private JButton buscarButton;
	private JPanel tablaPanel;
	private JScrollPane scrollPane;

	public BuscarOrdenTrabajoDialog() {

		setName("buscarOrdenTrabajoDialog");
		setModal(true);
		setSize(new Dimension(400, 300));
		setLocation(400,300);
		setPreferredSize(new Dimension(400, 300));
		setTitle("Buscar Orden de Trabajo");

		ordenesTrabajoTableModel = new OrdenesTrabajoTableModel();

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
		filtrosPanel.add(buscarButton, "8, 2, 1, 3");
		buscarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				List<OrdenTrabajo> ordenesTrabajo = Sistema.getInstancia()
						.getOrdenesTrabajo(fechaInicioTextField.getText(),
								fechaFinTextField.getText());
				ordenesTrabajoTableModel.setOrdenesTrabajo(ordenesTrabajo);
			}
		});

		JLabel fechaFinalLabel = new JLabel("Fecha Final");
		filtrosPanel.add(fechaFinalLabel, "2, 4, right, default");

		fechaFinTextField = new JTextField();
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

		ordenesTrabajoTableModel = new OrdenesTrabajoTableModel();
		tablaPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablaPanel.add(scrollPane);
		scrollPane.setViewportView(ordenesTrabajoTable);

		ordenesTrabajoTable = new JTable();
		ordenesTrabajoTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(ordenesTrabajoTable);
		ordenesTrabajoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ordenesTrabajoTable.setDoubleBuffered(true);
		ordenesTrabajoTable.setModel(ordenesTrabajoTableModel);
		ordenesTrabajoTable.getColumnModel().getColumn(0).setResizable(false);
		ordenesTrabajoTable.getColumnModel().getColumn(1).setResizable(false);
		ordenesTrabajoTable.getColumnModel().getColumn(2).setResizable(false);
		ordenesTrabajoTable.getColumnModel().getColumn(3).setResizable(false);
		ordenesTrabajoTable.getColumnModel().getColumn(4).setResizable(false);
		
		JPanel botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BuscarOrdenTrabajoDialog.this.dispose();
			}
		});
		botonesPanel.add(cancelarButton);

		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OrdenTrabajo ordenTrabajo = ordenesTrabajoTableModel
						.getOrdenTrabajoAt(ordenesTrabajoTable.getSelectedRow());
				ordenTrabajoPerformer.setOrdenTrabajo(ordenTrabajo);
				BuscarOrdenTrabajoDialog.this.dispose();
			}

		});
		botonesPanel.add(aceptarButton);

	}

	public void setOrdenTrabajoPerformer(
			IOrdenTrabajoPerformer ordenTrabajoPerformer) {
		this.ordenTrabajoPerformer = ordenTrabajoPerformer;
	}

	public IOrdenTrabajoPerformer getOrdenTrabajoPerformer() {
		return this.ordenTrabajoPerformer;
	}
}
