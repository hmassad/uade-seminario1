package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import persistencia.PresupuestoDAO;

import modelo.Presupuesto;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class BuscarPresupuestoDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private IPresupuestoPerformer presupuestoPerformer;

	private JTable presupuestosTable;
	private JTextField fechaInicioTextField;
	private JTextField fechaFinTextField;

	public BuscarPresupuestoDialog(IPresupuestoPerformer presupuestoPerformer) {
		
		this.presupuestoPerformer = presupuestoPerformer; 
		
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
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) 
			{
				//Se obtienen los datos para buscar el presupuesto
				String fechaInicio = fechaInicioTextField.getText();
				String fechaFin = fechaFinTextField.getText();
				
				List<Presupuesto> presupuestos = Sistema.getInstancia().
											getPresupuestos(fechaInicio,fechaFin);
				
				//incluyo los presupuestos encontrados en la tabla
				if(presupuestos.size() > 0)
					incluirPresupuestosEncontrados(presupuestos);
			}
		});

		JLabel fechaFinalLabel = new JLabel("Fecha Final");
		filtrosPanel.add(fechaFinalLabel, "2, 4, right, default");

		fechaFinTextField = new JTextField();
		fechaFinTextField.setColumns(10);
		filtrosPanel.add(fechaFinTextField, "4, 4, fill, default");

		JButton fechaFinHoyButton = new JButton("hoy");
		filtrosPanel.add(fechaFinHoyButton, "6, 4");

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
			    
				int selectedRow = presupuestosTable.getSelectedRow();
				Integer numeroPresupuesto = (Integer) presupuestosTable.getValueAt(selectedRow, 0);
			
				Presupuesto presupuesto = PresupuestoDAO.getInstancia().select(numeroPresupuesto);
				if(presupuesto!=null)
				retornarAGenerarOrdenDeTrabajo(presupuesto);
					
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
	
	private void incluirPresupuestosEncontrados(
			List<Presupuesto> presupuestos) {

		Object[][] data = new Object[presupuestos.size()][4];
        int i = 0;
        //TODO sacar el modelo de aca!!!
        for (Presupuesto presupuesto : presupuestos) {
        	data[i][0] = presupuesto.getNumero();
			data[i][1] = presupuesto.getFecha();
			data[i][2] = presupuesto.getCliente().getNombre();
			data[i][3] = presupuesto.getVehiculo().getPatente() +"-"
									+ presupuesto.getVehiculo().getModelo();
			i++;
		}
        
        String[] columnNames = {"N\u00FAmero","Cliente", "Veh\u00EDculo", "Fecha"};
        
        presupuestosTable = new JTable(data, columnNames);
        presupuestosTable.setPreferredScrollableViewportSize(new Dimension(200,200));
        presupuestosTable.setFillsViewportHeight(true);
        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(presupuestosTable);
        
        //Add the scroll pane to this panel.
        add(scrollPane);
        scrollPane.setBounds(0, 90, 370, 130);
        
        getContentPane().add(scrollPane);
        repaint();
	}
	
	private void retornarAGenerarOrdenDeTrabajo(Presupuesto presupuesto) {
		
		if(this.presupuestoPerformer!=null){
			this.presupuestoPerformer.setPresupuesto(presupuesto);	
		}
		
	}
}
