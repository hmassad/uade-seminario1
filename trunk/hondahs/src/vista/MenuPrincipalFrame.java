package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import modelo.Usuario;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MenuPrincipalFrame extends JFrame implements ILoginPerformer {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MenuPrincipalFrame mp = new MenuPrincipalFrame();

		LoginDialog loginDialog = new LoginDialog();
		loginDialog.setLoginPerformer(mp);
		loginDialog.setLocation(200, 200);
		loginDialog.setVisible(true);

		if (mp.getUsuario() == null) {
			JOptionPane.showMessageDialog(mp,
					"El usuario o la contrase�a son incorrectos.",
					"Error de Usuario y/o Contrase�a",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		mp.setVisible(true);
	}

	private Usuario usuario;

	public MenuPrincipalFrame() {
		setResizable(false);
		setTitle("Honda HS: Men\u00FA Principal");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(605, 358);
		this.setLocationByPlatform(true);
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
						FormFactory.DEFAULT_ROWSPEC, }));

		JButton generarOrdenTrabajoButton = new JButton(
				"Generar Orden de Trabajo");
		generarOrdenTrabajoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerarOrdenTrabajoDialog generarOrdenTrabajoDialog = new GenerarOrdenTrabajoDialog();
				generarOrdenTrabajoDialog.setVisible(true);
			}
		});
		getContentPane().add(generarOrdenTrabajoButton, "2, 2, fill, fill");

		JButton informesButton = new JButton(
				"Generar Informe de \u00D3rdenes de Trabajo");
		informesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InformeFrame informeFrame = new InformeFrame();
				informeFrame.setVisible(true);
			}
		});

		JButton actualizarOrdenTrabajoButton = new JButton(
				"Actualizar Orden de Trabajo");
		actualizarOrdenTrabajoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ActualizarOrdenTrabajoDialog actualizarOrdenTrabajoDialog = new ActualizarOrdenTrabajoDialog();
				//actualizarOrdenTrabajoDialog.setUsuario(usuario);
				actualizarOrdenTrabajoDialog.setVisible(true);
			}
		});
		getContentPane().add(actualizarOrdenTrabajoButton, "2, 4");
		getContentPane().add(informesButton, "2, 6, fill, fill");

		JButton actualizarTareaButton = new JButton(
				"Actualizar Estado de Tarea");
		actualizarTareaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TareasAsignadasDialog tareasAsignadasDialog = new TareasAsignadasDialog();
				tareasAsignadasDialog.setUsuario(usuario);
				tareasAsignadasDialog.setVisible(true);
			}
		});
		getContentPane().add(actualizarTareaButton, "2, 8, fill, fill");
	}

	@Override
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Usuario getUsuario() {
		return this.usuario;
	}
}