package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import modelo.Usuario;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MenuPrincipalFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MenuPrincipalFrame(final Usuario usuario) {
		
		setResizable(false);
		setTitle("Honda HS: Men\u00FA Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(305, 200);
		setLocation(400,300);
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
						FormFactory.UNRELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.UNRELATED_GAP_COLSPEC, }, new RowSpec[] {
						FormFactory.UNRELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,}));

		JButton generarOrdenTrabajoButton = new JButton(
				"Generar Orden de Trabajo");
		
		if(usuario.getTipoUsuario().name().equals("JEFEDETALLER")){
			generarOrdenTrabajoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GenerarOrdenTrabajoDialog generarOrdenTrabajoDialog = new GenerarOrdenTrabajoDialog();
					generarOrdenTrabajoDialog.setVisible(true);
				}
			});
		}else{
			generarOrdenTrabajoButton.setEnabled(false);
		}
		
		getContentPane().add(generarOrdenTrabajoButton, "2, 2, fill, fill");

		JButton informesButton = new JButton(
				"Generar Informe de \u00D3rdenes de Trabajo");
		
		if(usuario.getTipoUsuario().name().equals("ADMINISTRATIVO")  
			 	|| usuario.getTipoUsuario().name().equals("DUENIO")){
			
			informesButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					InformeFrame informeFrame = new InformeFrame();
					informeFrame.setVisible(true);
				}
			});
		}else{
			informesButton.setEnabled(false);
		}
		
		JButton actualizarOrdenTrabajoButton = new JButton(
				"Actualizar Orden de Trabajo");
		if(usuario.getTipoUsuario().name().equals("JEFEDETALLER")  
			 	|| usuario.getTipoUsuario().name().equals("DUENIO")){
			
			actualizarOrdenTrabajoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ActualizarOrdenTrabajoDialog actualizarOrdenTrabajoDialog = new ActualizarOrdenTrabajoDialog();
					actualizarOrdenTrabajoDialog.setVisible(true);
				}
			});
		}else{
			actualizarOrdenTrabajoButton.setEnabled(false);
		}
		getContentPane().add(actualizarOrdenTrabajoButton, "2, 4");
		getContentPane().add(informesButton, "2, 6, fill, fill");

		JButton actualizarTareaButton = new JButton(
				"Actualizar Estado de Tarea");
		if(!usuario.getTipoUsuario().name().equals("ADMINISTRATIVO")){
			
			actualizarTareaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					TareasAsignadasDialog tareasAsignadasDialog = new TareasAsignadasDialog();
					tareasAsignadasDialog.setUsuario(usuario);
					tareasAsignadasDialog.setVisible(true);
				}
			});
		}else{
			actualizarTareaButton.setEnabled(false);
		}	
		
		JButton salirButton = new JButton("Salir");
		salirButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MenuPrincipalFrame.this.dispose();
				}
			});
		
		getContentPane().add(actualizarTareaButton, "2, 8, fill, fill");
		getContentPane().add(salirButton, "2, 10, fill, fill");
	}

}
