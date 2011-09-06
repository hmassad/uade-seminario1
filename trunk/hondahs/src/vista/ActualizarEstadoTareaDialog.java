package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import modelo.EstadoTarea;
import modelo.Tarea;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class ActualizarEstadoTareaDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private Tarea tarea;

	private JPanel contentPanel;
	private JLabel tareaLabel;
	private JLabel tareaValueLabel;
	private JLabel descripcionLabel;
	private JLabel descripcionValueLabel;
	private JLabel estadoLabel;
	private JComboBox estadoComboBox;
	private JPanel botonesPanel;
	private JButton cancelarButton;
	private JButton aceptarButton;

	public ActualizarEstadoTareaDialog() {
		setName("actualizarEstadoTareaDialog");
		setModal(true);
		setSize(new Dimension(400, 149));
		setPreferredSize(new Dimension(400, 300));
		setTitle("Actualizar Estado de Tarea");

		contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		tareaLabel = new JLabel("Tarea");
		contentPanel.add(tareaLabel, "2, 2");

		tareaValueLabel = new JLabel("1");
		contentPanel.add(tareaValueLabel, "4, 2");

		descripcionLabel = new JLabel("Descripci\u00F3n");
		contentPanel.add(descripcionLabel, "2, 4");

		descripcionValueLabel = new JLabel("Electricidad");
		contentPanel.add(descripcionValueLabel, "4, 4");

		estadoLabel = new JLabel("Estado");
		estadoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPanel.add(estadoLabel, "2, 6, left, default");

		estadoComboBox = new JComboBox();
		contentPanel.add(estadoComboBox, "4, 6, fill, default");
		estadoComboBox.setModel(new DefaultComboBoxModel(new EstadoTarea[] {
				EstadoTarea.ASIGNADA, EstadoTarea.CANCELADA,
				EstadoTarea.TERMINADA, }));

		botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ActualizarEstadoTareaDialog.this.dispose();
			}
		});
		botonesPanel.add(cancelarButton);

		aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sistema.getInstancia().modificarEstadoTarea(tarea,
					(EstadoTarea) ActualizarEstadoTareaDialog.this.estadoComboBox.getSelectedItem());
				ActualizarEstadoTareaDialog.this.dispose();
			}
		});
		botonesPanel.add(aceptarButton);
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
		this.tareaValueLabel.setText(tarea.getNumero().toString());
		this.descripcionValueLabel.setText(tarea.getTipoTarea()
				.getDescripcion());
		this.estadoComboBox.setSelectedItem(tarea.getEstado());
	}

	public Tarea getTarea() {
		return this.tarea;
	}
}
