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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import modelo.EstadoOrdenTrabajo;
import modelo.OrdenTrabajo;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class InformeFrame extends JFrame {

	class InformeTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private List<OrdenTrabajo> registros = new Vector<OrdenTrabajo>();

		public void clear() {
			int rows = registros.size();
			registros.clear();
			TableModelEvent event = new TableModelEvent(this, 0, rows,
					TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(event);
				}
		}

		public void add(OrdenTrabajo registroInforme) {
			registros.add(registroInforme);
			TableModelEvent event = new TableModelEvent(this,
					registros.size() - 1, registros.size() - 1,
					TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(event);
				}
		}

		public void remove(OrdenTrabajo registroInforme) {
			int index = registros.indexOf(registroInforme);
			registros.remove(registroInforme);

			for (Object l : listenerList.getListenerList())
				if (l instanceof TableModelListener) {
					((TableModelListener) l).tableChanged(new TableModelEvent(
							this, index, index, TableModelEvent.ALL_COLUMNS,
							TableModelEvent.DELETE));
				}
		}

		public OrdenTrabajo getRegistroInformeAt(int rowIndex) {
			return registros.get(rowIndex);
		}

		String[] titulos = new String[] { "N\u00FAmero", "Cliente",
				"Veh\u00EDculo", "Fecha Inicio", "Fecha Fin", "Estado" };

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
			return registros.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			OrdenTrabajo registroInforme = registros.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return registroInforme.getNumero();
			case 1:
				return registroInforme.getPresupuesto().getCliente().getNombre();
			case 2:
				return registroInforme.getPresupuesto().getVehiculo().getPatente();
			case 3:
				return registroInforme.getFechaInicio();
			case 4:
				return registroInforme.getFechaFin();
			case 5:
				return registroInforme.getEstado().getCode();
			}
			throw new RuntimeException("La columna no existe.");
		}

		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] { Integer.class, String.class,
				String.class, String.class, String.class, String.class };

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}

		boolean[] columnEditables = new boolean[] { false, false, false, false,
				false, false };

		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}

		public void setRegistros(List<OrdenTrabajo> registroInforme) {
			clear();
			for (OrdenTrabajo registro : registroInforme)
				add(registro);
		}

		public List<OrdenTrabajo> getPresupuestos() {
			return registros;
		}

	}

	private static final long serialVersionUID = 1L;
	private InformeTableModel informeTableModel;
	
	private JTable resultadosTable;
	private JTextField fechaInicioTextField;
	private JTextField fechaFinTextField;
	private JTextField clienteTextField;
	private JTextField vehiculoTextField;
	private JButton fechaInicioHoyButton;
	private JButton fechaFinHoyButton;
	private JComboBox estadoComboBox;
	private JScrollPane scrollPane;

	public InformeFrame() {
		setName("informeFrame");
		setSize(new Dimension(545, 364));
		setLocation(400, 300);
		setPreferredSize(new Dimension(400, 300));
		setTitle("Informes");
		
		informeTableModel = new InformeTableModel();
		
		JPanel filtrosPanel = new JPanel();
		getContentPane().add(filtrosPanel, BorderLayout.NORTH);
		filtrosPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("15dlu"), FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC, }));

		JLabel fechaInicioLabel = new JLabel("Fecha Inicio");
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

		JLabel fechaFinalLabel = new JLabel("Fecha Final");
		filtrosPanel.add(fechaFinalLabel, "8, 2, right, default");

		fechaFinTextField = new JTextField();
		fechaFinTextField.setColumns(10);
		filtrosPanel.add(fechaFinTextField, "10, 2, fill, default");

		fechaFinHoyButton = new JButton("Hoy");
		fechaFinHoyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fechaFinTextField.setText(new SimpleDateFormat("dd/MM/yyyy")
						.format(new Date()));
			}
		});
		filtrosPanel.add(fechaFinHoyButton, "12, 2");

		JLabel clienteLabel = new JLabel("Cliente");
		filtrosPanel.add(clienteLabel, "2, 4, right, default");

		clienteTextField = new JTextField();
		filtrosPanel.add(clienteTextField, "4, 4, 3, 1, fill, default");
		clienteTextField.setColumns(10);

		JLabel estadoLabel = new JLabel("Estado");
		filtrosPanel.add(estadoLabel, "8, 4, right, default");

		estadoComboBox = new JComboBox();
		EstadoOrdenTrabajo[] estados = EstadoOrdenTrabajo.values();
		estadoComboBox.addItem("Todos los estados");
		for (EstadoOrdenTrabajo estadoOrdenTrabajo : estados) {
			estadoComboBox.addItem(estadoOrdenTrabajo.getCode().toString());
		}

		filtrosPanel.add(estadoComboBox, "10, 4, 3, 1, fill, default");

		JLabel vehiculoLabel = new JLabel("Veh\u00EDculo");
		filtrosPanel.add(vehiculoLabel, "2, 6, right, default");

		vehiculoTextField = new JTextField();
		filtrosPanel.add(vehiculoTextField, "4, 6, 3, 1, fill, default");
		vehiculoTextField.setColumns(10);

		JButton generarInformeButton = new JButton("Generar Informe");
		generarInformeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				List<OrdenTrabajo> registros = Sistema.getInstancia()
						.getInforme(
								clienteTextField.getText(),
								vehiculoTextField.getText(),
								(String)estadoComboBox.getSelectedItem(),
								fechaInicioTextField.getText(),
								fechaFinTextField.getText());
				informeTableModel.setRegistros(registros);
			}
		});
		filtrosPanel.add(generarInformeButton, "2, 8, 11, 1, center, default");
		
		JPanel tablaPanel = new JPanel();
		getContentPane().add(tablaPanel, BorderLayout.CENTER);
		
		informeTableModel = new InformeTableModel();
		tablaPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablaPanel.add(scrollPane);
		scrollPane.setViewportView(resultadosTable);
		
		resultadosTable = new JTable();
		resultadosTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(resultadosTable);
		resultadosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultadosTable.setDoubleBuffered(true);
		resultadosTable.setModel(informeTableModel);
		resultadosTable.getColumnModel().getColumn(0).setResizable(false);
		resultadosTable.getColumnModel().getColumn(1).setResizable(false);
		resultadosTable.getColumnModel().getColumn(2).setResizable(false);
		resultadosTable.getColumnModel().getColumn(3).setResizable(false);
		resultadosTable.getColumnModel().getColumn(4).setResizable(false);
		resultadosTable.getColumnModel().getColumn(5).setResizable(false);
	}
}
