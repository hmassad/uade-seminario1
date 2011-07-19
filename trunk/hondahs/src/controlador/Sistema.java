package controlador;

import modelo.Cliente;
import modelo.EstadoTarea;
import modelo.OrdenTrabajo;
import modelo.Tarea;
import modelo.TipoTarea;
import modelo.Usuario;
import persistencia.OrdenTrabajoDAO;
import persistencia.UsuarioDAO;

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

	public Cliente crearCliente(String nombre, String email, Integer telefono, Integer celular, String direccion) {
		return new Cliente(nombre, email, telefono, celular, direccion);
	}

	public boolean validarLogin(String usuario, String password, String perfil) {
		// busco al usuario con el nombre y password
		Usuario usr = UsuarioDAO.getInstancia().select(usuario, password);
		return usr != null && usr.getTipoUsuario().getCode().equals(perfil);
	}

	public void agregarTarea(OrdenTrabajo ordenTrabajo, TipoTarea tipoTarea, Usuario operario) {
		ordenTrabajo.getListaTareas().add(new Tarea(tipoTarea, EstadoTarea.ASIGNADA, null, operario));
		OrdenTrabajoDAO.getInstancia().update(ordenTrabajo);
	}
}
