package vista;

import interfaces.IPresupuestoPerformer;

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

import modelo.Presupuesto;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class BuscarPresupuestoDialog extends JDialog {

	class PresupuestosTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private List<Presupuesto> presupuestos = new Vector<Presupuesto>();

		public void clear() {
			int rows = presupuestos.size();
			presupuestos.clear();
			TableModelEvent event = new TableModelEvent(this, 0, rows,
					TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(event);
				}
		}

		public void add(Presupuesto presupuesto) {
			presupuestos.add(presupuesto);
			TableModelEvent event = new TableModelEvent(this,
					presupuestos.size() - 1, presupuestos.size() - 1,
					TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(event);
				}
		}

		public void remove(Presupuesto presupuesto) {
			int index = presupuestos.indexOf(presupuesto);
			presupuestos.remove(presupuesto);

			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(new TableModelEvent(
							this, index, index, TableModelEvent.ALL_COLUMNS,
							TableModelEvent.DELETE));
				}
		}

		public Presupuesto getPresupuestoAt(int rowIndex) {
			return presupuestos.get(rowIndex);
		}

		String[] titulos = new String[] { "N\u00FAmero", "Cliente", "Veh\u00EDculo", "Fecha" };

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
			return presupuestos.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Presupuesto presupuesto = presupuestos.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return presupuesto.getNumero();
			case 1:
				return presupuesto.getCliente().getNombre();
			case 2:
				return presupuesto.getVehiculo().getPatente();
			case 3:
				return presupuesto.getFecha();
			}
			throw new RuntimeException("La columna no existe.");
		}

		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] { Integer.class, String.class,
				String.class, String.class };

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}

		boolean[] columnEditables = new boolean[] { false, false, false, false };

		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}

		public void setPresupuestos(List<Presupuesto> presupuestos) {
			clear();
			for (Presupuesto presupuesto : presupuestos)
				add(presupuesto);
		}

		public List<Presupuesto> getPresupuestos() {
			return presupuestos;
		}
	}

	private static final long serialVersionUID = 1L;

	private PresupuestosTableModel presupuestosTableModel;
	private IPresupuestoPerformer presupuestoPerformer;

	private JTable presupuestosTable;
	private JTextField fechaInicioTextField;
	private JTextField fechaFinTextField;
	private JPanel filtrosPanel;
	private JLabel fechaInicioLabel;
	private JButton fechaInicioHoyButton;
	private JButton fechaFinHoyButton;
	private JButton buscarButton;
	private JPanel tablaPanel;
	private JScrollPane scrollPane;

	public BuscarPresupuestoDialog() {

		setName("buscarPresupuestoDialog");
		setModal(true);
		setSize(new Dimension(400, 300));
		setLocation(400,300);
		setPreferredSize(new Dimension(400, 300));
		setTitle("Buscar Presupuesto");

		presupuestosTableModel = new PresupuestosTableModel();

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
				List<Presupuesto> presupuestos = Sistema.getInstancia()
						.getPresupuestos(fechaInicioTextField.getText(),
								fechaFinTextField.getText());
				presupuestosTableModel.setPresupuestos(presupuestos);
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

		presupuestosTableModel = new PresupuestosTableModel();
		tablaPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablaPanel.add(scrollPane);
		scrollPane.setViewportView(presupuestosTable);

		presupuestosTable = new JTable();
		presupuestosTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(presupuestosTable);
		presupuestosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		presupuestosTable.setDoubleBuffered(true);
		presupuestosTable.setModel(presupuestosTableModel);
		presupuestosTable.getColumnModel().getColumn(0).setResizable(false);
		presupuestosTable.getColumnModel().getColumn(1).setResizable(false);
		presupuestosTable.getColumnModel().getColumn(2).setResizable(false);
		presupuestosTable.getColumnModel().getColumn(3).setResizable(false);
				
		JPanel botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BuscarPresupuestoDialog.this.dispose();
			}
		});
		botonesPanel.add(cancelarButton);

		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Presupuesto presupuesto = presupuestosTableModel
						.getPresupuestoAt(presupuestosTable.getSelectedRow());
				presupuestoPerformer.setPresupuesto(presupuesto);

				BuscarPresupuestoDialog.this.dispose();
			}

		});
		botonesPanel.add(aceptarButton);

	}

	public void setPresupuestoPerformer(
			IPresupuestoPerformer presupuestoPerformer) {
		this.presupuestoPerformer = presupuestoPerformer;
	}

	public IPresupuestoPerformer getPresupuestoPerformer() {
		return this.presupuestoPerformer;
	}
}
