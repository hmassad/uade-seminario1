package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import modelo.Usuario;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MenuPrincipalFrame extends JFrame implements ILoginPerformer {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		try {
			MenuPrincipalFrame mp = new MenuPrincipalFrame();
			// int i = 0;
			// while (mp.getUsuario() == null && i < 3) {
			// LoginDialog loginDialog = new LoginDialog();
			// loginDialog.setLoginPerformer(mp);
			// loginDialog.setVisible(true);
			// i++;
			// }
			// if (i >= 3)
			// return;
			mp.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Usuario usuario;

	public MenuPrincipalFrame() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
						FormFactory.UNRELATED_GAP_ROWSPEC, }));

		JButton generarOrdenTrabajoButton = new JButton(
				"Generar Orden de Trabajo");
		generarOrdenTrabajoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerarOrdenTrabajoDialog generarOrdenTrabajoDialog = new GenerarOrdenTrabajoDialog();
				generarOrdenTrabajoDialog.setVisible(true);
			}
		});
		getContentPane().add(generarOrdenTrabajoButton, "2, 2, left, top");

		JButton informesButton = new JButton(
				"Generar Informe de \u00D3rdenes de Trabajo");
		getContentPane().add(informesButton, "2, 4, left, top");
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
