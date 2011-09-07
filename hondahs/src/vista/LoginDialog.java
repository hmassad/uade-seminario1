package vista;

import interfaces.ILoginPerformer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.TipoUsuario;
import modelo.Usuario;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controlador.Sistema;

public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JLabel tituloLabel;
	private JLabel usuarioLabel;
	private JTextField usuarioTextField;
	private JLabel passwordLabel;
	private JPasswordField passwordTextField;
	private JLabel perfilLabel;
	private JComboBox perfilComboBox;
	private JButton loginButton;

	private ILoginPerformer loginPerformer;

	public LoginDialog() {
		setName("loginDialog");
		setModal(true);
		setSize(new Dimension(300, 200));
		setLocation(400,300);
		setPreferredSize(new Dimension(300, 200));
		setTitle("Sistema de Administraci\u00F3n de \u00D3rdenes de Trabajo para Honda HS");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationByPlatform(false);
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
						FormFactory.UNRELATED_GAP_COLSPEC,
						FormFactory.MIN_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(137dlu;default)"),
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC, },
						new RowSpec[] { FormFactory.UNRELATED_GAP_ROWSPEC,
								RowSpec.decode("max(20dlu;default)"),
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC, }));

		tituloLabel = new JLabel();
		tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tituloLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.getContentPane().add(tituloLabel, "2, 2, 3, 1, fill, fill");
		tituloLabel.setText("Ingreso");
		tituloLabel.setAlignmentX(CENTER_ALIGNMENT);

		usuarioLabel = new JLabel();
		this.getContentPane().add(usuarioLabel, "2, 4, fill, fill");
		usuarioLabel.setText("Usuario");

		usuarioTextField = new JTextField();
		usuarioLabel.setLabelFor(usuarioTextField);
		this.getContentPane().add(usuarioTextField, "4, 4, fill, fill");

		passwordLabel = new JLabel();
		this.getContentPane().add(passwordLabel, "2, 6, fill, fill");
		passwordLabel.setText("Contrase\u00F1a");

		passwordTextField = new JPasswordField();
		passwordLabel.setLabelFor(passwordTextField);
		this.getContentPane().add(passwordTextField, "4, 6, fill, fill");

		perfilLabel = new JLabel();
		this.getContentPane().add(perfilLabel, "2, 8, fill, fill");
		perfilLabel.setText("Perfil");

		perfilComboBox = new JComboBox();
		perfilComboBox
				.setModel(new DefaultComboBoxModel(new String[] {
						TipoUsuario.ADMINISTRATIVO.getCode(),
						TipoUsuario.JEFEDETALLER.getCode(),
						TipoUsuario.DUENIO.getCode(),
						TipoUsuario.OPERARIO.getCode(), }));
		perfilLabel.setLabelFor(perfilComboBox);
		this.getContentPane().add(perfilComboBox, "4, 8, fill, fill");

		loginButton = new JButton();
		loginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				Usuario usuario = Sistema.getInstancia().validarLogin(
						usuarioTextField.getText(),
						passwordTextField.getText(),
						perfilComboBox.getSelectedItem().toString());
				
				if (usuario == null) {
				
					JOptionPane.showMessageDialog(LoginDialog.this,
							"El usuario o la contraseña son incorrectos.",
							"Error de Usuario y/o Contraseña",
							JOptionPane.ERROR_MESSAGE);
					return;

				} else {
					
					LoginDialog.this.loginPerformer.setUsuario(usuario);
					LoginDialog.this.loginPerformer.cargarMenuAplicacion();
					LoginDialog.this.dispose();
				}
			}
		});
		this.getContentPane().add(loginButton, "4, 10, fill, fill");
		loginButton.setText("Ingresar");
	}

	public void setLoginPerformer(ILoginPerformer loginPerformer) {
		this.loginPerformer = loginPerformer;
	}

	public ILoginPerformer getLoginPerformer() {
		return this.loginPerformer;
	}
}
