package vista;

import interfaces.ITareaPerformer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.EstadoTarea;
import modelo.Tarea;
import modelo.TipoTarea;
import modelo.Usuario;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class AgregarTareaDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private ITareaPerformer tareaPerformer;

	private JPanel contentPanel;
	private JLabel tareaLabel;
	private JComboBox tiposTareaComboBox;
	private JLabel operarioLabel;
	private JComboBox operariosComboBox;
	private JPanel botonesPanel;
	private JButton aceptarButton;
	private JButton cancelarButton;

	public AgregarTareaDialog() {
		setName("agregarTareaDialog");
		setModal(true);
		setSize(new Dimension(350, 150));
		setLocation(400,300);
		setResizable(false);
		setTitle("Agregar Tarea");

		contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.UNRELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC, }));

		tareaLabel = new JLabel("Tarea");
		contentPanel.add(tareaLabel, "3, 2, right, default");
		
		tiposTareaComboBox = new JComboBox();
		contentPanel.add(tiposTareaComboBox, "5, 2, fill, default");
		Vector<TipoTarea> tiposTareas = new Vector<TipoTarea>(); 
		for (TipoTarea tipoTarea : Sistema.getInstancia().getTiposDeTareas()) {
			tiposTareas.add(tipoTarea);
		}
		tiposTareaComboBox.setModel(new DefaultComboBoxModel(tiposTareas));
		
		
		operarioLabel = new JLabel("Operario");
		contentPanel.add(operarioLabel, "3, 4, right, default");

		operariosComboBox = new JComboBox();
		contentPanel.add(operariosComboBox, "5, 4, fill, default");
		Vector<Usuario> operarios = new Vector<Usuario>(); 
		for (Usuario usuario : Sistema.getInstancia().getOperarios()) {
			operarios.add(usuario);
		}
		operariosComboBox.setModel(new DefaultComboBoxModel(operarios));

		botonesPanel = new JPanel();
		getContentPane().add(botonesPanel, BorderLayout.SOUTH);

		cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AgregarTareaDialog.this.dispose();
			}
		});
		botonesPanel.add(cancelarButton);

		aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tareaPerformer != null) {
					
					Calendar calendar = Calendar.getInstance();
					String fechaDeInicio = Integer.toString(calendar.get(Calendar.DATE))
							+ "/" + Integer.toString(calendar.get(Calendar.MONTH))
							+ "/" + Integer.toString(calendar.get(Calendar.YEAR));
					
					tareaPerformer.addTarea(new Tarea(
						(TipoTarea) AgregarTareaDialog.this.tiposTareaComboBox.getSelectedItem(),
						EstadoTarea.ASIGNADA,null,
						(Usuario) AgregarTareaDialog.this.operariosComboBox.getSelectedItem(),
						fechaDeInicio,null));
				}
				AgregarTareaDialog.this.dispose();
			}
		});
		botonesPanel.add(aceptarButton);
	}

	public ITareaPerformer getTareaPerformer() {
		return this.tareaPerformer;
	}

	public void setTareaPerformer(ITareaPerformer tareaPerformer) {
		this.tareaPerformer = tareaPerformer;
	}
}
