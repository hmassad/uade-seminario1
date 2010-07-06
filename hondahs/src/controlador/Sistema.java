package controlador;

public class Sistema {

	private static Sistema instancia;

	public static Sistema getInstancia() {
		if (instancia == null) {
			instancia = new Sistema();
		}
		return instancia;
	}

	private Sistema() {
	}
}
