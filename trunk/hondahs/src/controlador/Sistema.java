package controlador;

import modelo.Cliente;

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

	public Cliente crearCliente(Integer numero, String nombre, String email, Integer telefono, Integer celular, String direccion) {
		return new Cliente(numero, nombre, email, telefono, celular, direccion);
	}

	public boolean validarLogin(String usuario, String password, String perfil) {
		// TODO Auto-generated method stub
		return true;
	}
}
