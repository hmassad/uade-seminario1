package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JComboBox;

public class InformeFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTable resultadosTable;
	private JTextField fechaInicioTextField;
	private JTextField fechaFinTextField;
	private JTextField clienteTextField;
	private JTextField vehiculoTextField;

	public InformeFrame() {
		setName("informeFrame");
		setSize(new Dimension(692, 300));
		setPreferredSize(new Dimension(400, 300));
		setTitle("Informes");

		JPanel filtrosPanel = new JPanel();
		getContentPane().add(filtrosPanel, BorderLayout.NORTH);
		filtrosPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(89dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,}));

		JLabel fechaInicioLabel = new JLabel("Fecha Inicio");
		filtrosPanel.add(fechaInicioLabel, "2, 2, right, default");

		fechaInicioTextField = new JTextField();
		filtrosPanel.add(fechaInicioTextField, "4, 2, fill, default");
		fechaInicioTextField.setColumns(10);

		JButton fechaInicioHoyButton = new JButton("Hoy");
		filtrosPanel.add(fechaInicioHoyButton, "6, 2");
		
				JLabel fechaFinalLabel = new JLabel("Fecha Final");
				filtrosPanel.add(fechaFinalLabel, "8, 2, right, default");
		
				fechaFinTextField = new JTextField();
				fechaFinTextField.setColumns(10);
				filtrosPanel.add(fechaFinTextField, "10, 2, fill, default");
						
								JButton fechaFinHoyButton = new JButton("hoy");
								filtrosPanel.add(fechaFinHoyButton, "12, 2");
						
						JLabel clienteLabel = new JLabel("Cliente");
						filtrosPanel.add(clienteLabel, "2, 4, center, default");
						
						clienteTextField = new JTextField();
						filtrosPanel.add(clienteTextField, "4, 4, 9, 1, fill, default");
						clienteTextField.setColumns(10);
								
								JLabel vehiculoLabel = new JLabel("Veh\u00EDculo");
								filtrosPanel.add(vehiculoLabel, "2, 6, right, default");
								
								vehiculoTextField = new JTextField();
								filtrosPanel.add(vehiculoTextField, "4, 6, 3, 1, fill, default");
								vehiculoTextField.setColumns(10);
								
								JLabel estadoLabel = new JLabel("Estado");
								filtrosPanel.add(estadoLabel, "8, 6, right, default");
								
								JComboBox estadoComboBox = new JComboBox();
								filtrosPanel.add(estadoComboBox, "10, 6, 3, 1, fill, default");
						
								JButton btnBuscar = new JButton("Buscar");
								filtrosPanel.add(btnBuscar, "12, 8");

		JPanel tablaPanel = new JPanel();
		getContentPane().add(tablaPanel, BorderLayout.CENTER);

		resultadosTable = new JTable();
		resultadosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultadosTable.setDoubleBuffered(true);
		resultadosTable.setFillsViewportHeight(true);
		resultadosTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"N\u00FAmero", "Cliente", "Veh\u00EDculo", "Fecha Inicio", "Fecha Fin", "Estado"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Object.class, Object.class, Object.class, Object.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		resultadosTable.getColumnModel().getColumn(0).setResizable(false);
		resultadosTable.getColumnModel().getColumn(2).setResizable(false);
		resultadosTable.getColumnModel().getColumn(3).setResizable(false);
		resultadosTable.getColumnModel().getColumn(3).setMinWidth(75);
		resultadosTable.getColumnModel().getColumn(3).setMaxWidth(75);
		tablaPanel.setLayout(new BorderLayout(0, 0));
		tablaPanel.add(resultadosTable);
	}
}
