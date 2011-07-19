package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.OrdenTrabajo;
import modelo.TipoTarea;
import modelo.Usuario;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class AgregarTareaFrame extends JDialog {

	private static final long serialVersionUID = 1L;

	private OrdenTrabajo ordenTrabajo;

	private static JComboBox tiposTareaComboBox;
	private static JComboBox operariosComboBox;

	public AgregarTareaFrame() {
		setTitle("Agregar Tarea");

		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.UNRELATED_GAP_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.UNRELATED_GAP_COLSPEC, },
				new RowSpec[] { FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.UNRELATED_GAP_ROWSPEC, }));

		JLabel tareaLabel = new JLabel("Tarea");
		contentPanel.add(tareaLabel, "3, 2, right, default");

		tiposTareaComboBox = new JComboBox();
		contentPanel.add(tiposTareaComboBox, "5, 2, fill, default");

		JLabel operarioLabel = new JLabel("Operario");
		contentPanel.add(operarioLabel, "3, 4, right, default");

		operariosComboBox = new JComboBox();
		contentPanel.add(operariosComboBox, "5, 4, fill, default");

		JPanel botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		JButton cancelarButton = new JButton("Cancelar");
		botonesPanel.add(cancelarButton);

		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sistema.getInstancia().agregarTarea(AgregarTareaFrame.this.getOrdenTrabajo(),
						(TipoTarea) AgregarTareaFrame.tiposTareaComboBox.getSelectedItem(),
						(Usuario) AgregarTareaFrame.operariosComboBox.getSelectedItem());
				AgregarTareaFrame.this.dispose();
			}
		});
		botonesPanel.add(aceptarButton);
	}

	public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}

	public OrdenTrabajo getOrdenTrabajo() {
		return this.ordenTrabajo;
	}

	public void setOperarios(Usuario[] operarios) {
		operariosComboBox.setModel(new DefaultComboBoxModel(operarios));
	}

	public void setTiposTarea(TipoTarea[] tiposTarea) {
		tiposTareaComboBox.setModel(new DefaultComboBoxModel(tiposTarea));
	}
}
