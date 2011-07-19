package controlador;

import persistencia.UsuarioDAO;
import modelo.Cliente;
import modelo.Usuario;

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

	public Cliente crearCliente(String nombre, String email, Integer telefono,
			Integer celular, String direccion) {
		return new Cliente(nombre, email, telefono, celular, direccion);
	}

	public boolean validarLogin(String usuario, String password, String perfil) {
		//busco al usuario con el nombre y password
		Usuario usr = UsuarioDAO.getInstancia().select(usuario,password);
		if(usr!=null){
			if(usr.getTipoUsuario().getCode().equals(perfil)){
				return true;
			}else{
				return false;
			}		
		}else{
			return false;
		}
	}
}
