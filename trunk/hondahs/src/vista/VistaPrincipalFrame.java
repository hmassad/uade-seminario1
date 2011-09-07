package vista;

import interfaces.ILoginPerformer;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import modelo.Usuario;

public class VistaPrincipalFrame extends JFrame implements ILoginPerformer {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	public VistaPrincipalFrame() {}

	@Override
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	@Override
	public void cargarMenuAplicacion() {
		
		MenuPrincipalFrame mp = new MenuPrincipalFrame(this.usuario);
		mp.setVisible(true);
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		VistaPrincipalFrame vistaPrincipalFrame = new VistaPrincipalFrame();
		
		LoginDialog loginDialog = new LoginDialog();
		loginDialog.setLoginPerformer(vistaPrincipalFrame);
		loginDialog.setLocation(400, 300);
		loginDialog.setVisible(true);
		
	}
}
