package vista;

import javax.swing.JFrame;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		MenuPrincipal mp = new MenuPrincipal();
		mp.setVisible(true);
	}

	public MenuPrincipal() {
		initGUI();
	}

	private void initGUI() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(400, 300);
		this.setLocationByPlatform(true);
	}
}
