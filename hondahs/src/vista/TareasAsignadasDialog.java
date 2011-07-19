package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import modelo.Tarea;
import modelo.Usuario;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class TareasAsignadasDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	Usuario usuario;

	private JTable tareasTable;
	private JTextField fechaInicioTextField;
	private JTextField textField;

	public TareasAsignadasDialog() {
		setName("buscarPresupuestoDialog");
		setModal(true);
		setSize(new Dimension(400, 300));
		setPreferredSize(new Dimension(400, 300));
		setTitle("Buscar Presupuesto");

		JPanel filtrosPanel = new JPanel();
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

		JLabel fechaInicioLabel = new JLabel("Fecha Inicio");
		filtrosPanel.add(fechaInicioLabel, "2, 2, right, default");

		fechaInicioTextField = new JTextField();
		filtrosPanel.add(fechaInicioTextField, "4, 2, fill, default");
		fechaInicioTextField.setColumns(10);

		JButton fechaInicioHoyButton = new JButton("Hoy");
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

		tareasTable = new JTable();
		tareasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tareasTable.setDoubleBuffered(true);
		tareasTable.setFillsViewportHeight(true);
		tablaPanel.setLayout(new BorderLayout(0, 0));
		tareasTable.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null }, }, new String[] { "OT",
				"Tipo de Tarea", "Fecha Inicio", "Fecha Fin", "Estado" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					Object.class, Object.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tareasTable.getColumnModel().getColumn(0).setResizable(false);
		tareasTable.getColumnModel().getColumn(1).setPreferredWidth(142);
		tareasTable.getColumnModel().getColumn(2).setResizable(false);
		tareasTable.getColumnModel().getColumn(3).setResizable(false);
		tareasTable.getColumnModel().getColumn(3).setMinWidth(75);
		tareasTable.getColumnModel().getColumn(3).setMaxWidth(75);
		tablaPanel.add(tareasTable);

		JPanel botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TareasAsignadasDialog.this.dispose();
			}
		});
		botonesPanel.add(cancelarButton);

		JButton modificarButton = new JButton("Modificar");
		modificarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO: Obtener la tarea seleccionada
				Tarea tarea = null;
				if (tarea != null) {
					ActualizarEstadoTareaDialog actualizarEstadoTareaDialog = new ActualizarEstadoTareaDialog();
					actualizarEstadoTareaDialog.setTarea(tarea);
					actualizarEstadoTareaDialog.setVisible(true);
					updateTareasTable();
				}
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

	private void updateTareasTable() {
		for (Tarea tarea : Sistema.getInstancia().getTareasPorOperario(usuario)) {
			// TODO cargar tareas en la grilla
		}
	}
}
