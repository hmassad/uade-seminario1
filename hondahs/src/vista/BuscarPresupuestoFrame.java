package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class BuscarPresupuestoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable presupuestosTable;
	private JTextField fechaInicioTextField;
	private JTextField textField;

	public BuscarPresupuestoFrame() {

		JPanel filtrosPanel = new JPanel();
		getContentPane().add(filtrosPanel, BorderLayout.NORTH);
		filtrosPanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.UNRELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("max(89dlu;default):grow"), FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.BUTTON_COLSPEC, FormFactory.UNRELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC, }));

		JLabel fechaInicioLabel = new JLabel("Fecha Inicio");
		filtrosPanel.add(fechaInicioLabel, "2, 2, right, default");

		fechaInicioTextField = new JTextField();
		filtrosPanel.add(fechaInicioTextField, "4, 2, fill, default");
		fechaInicioTextField.setColumns(10);

		JButton fechaInicioHoyButton = new JButton("hoy");
		filtrosPanel.add(fechaInicioHoyButton, "6, 2");

		JButton btnBuscar = new JButton("Buscar");
		filtrosPanel.add(btnBuscar, "8, 2, 1, 3");

		JLabel fechaFinalLabel = new JLabel("Fecha Final");
		filtrosPanel.add(fechaFinalLabel, "2, 4, right, default");

		textField = new JTextField();
		textField.setColumns(10);
		filtrosPanel.add(textField, "4, 4, fill, default");

		JButton fechaFinHoyButton = new JButton("hoy");
		filtrosPanel.add(fechaFinHoyButton, "6, 4");

		JPanel tablaPanel = new JPanel();
		getContentPane().add(tablaPanel, BorderLayout.CENTER);

		presupuestosTable = new JTable();
		presupuestosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		presupuestosTable.setDoubleBuffered(true);
		presupuestosTable.setFillsViewportHeight(true);
		presupuestosTable.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null },
				{ null, null, null, null },
				{ null, null, null, null },
				{ null, null, null, null },
				{ null, null, null, null },
				{ null, null, null, null },
				{ null, null, null, null },
		}, new String[] {
				"N\u00FAmero", "Cliente", "Veh\u00EDculo", "Fecha" }) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, Object.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { true, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		presupuestosTable.getColumnModel().getColumn(0).setResizable(false);
		presupuestosTable.getColumnModel().getColumn(2).setResizable(false);
		presupuestosTable.getColumnModel().getColumn(3).setResizable(false);
		presupuestosTable.getColumnModel().getColumn(3).setMinWidth(75);
		presupuestosTable.getColumnModel().getColumn(3).setMaxWidth(75);
		tablaPanel.add(presupuestosTable);

		JPanel botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		JButton cancelarButton = new JButton("Cancelar");
		botonesPanel.add(cancelarButton);

		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		botonesPanel.add(aceptarButton);
	}

}
