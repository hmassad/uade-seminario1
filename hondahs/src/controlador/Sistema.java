package controlador;

import java.util.List;

import modelo.Cliente;
import modelo.EstadoOrdenTrabajo;
import modelo.EstadoTarea;
import modelo.OrdenTrabajo;
import modelo.Presupuesto;
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

	public Cliente crearCliente(String nombre, String email, Integer telefono,
			Integer celular, String direccion) {
		return new Cliente(nombre, email, telefono, celular, direccion);
	}

	public Usuario getUsuario(String nombre, String password, String perfil) {
		// busco al usuario con el nombre y password
		Usuario usuario = UsuarioDAO.getInstancia().select(nombre, password);
		if (usuario != null
				&& usuario.getTipoUsuario().getCode().equals(perfil))
			return usuario;
		return null;
	}

	public OrdenTrabajo grenerarOrdenTrabajo(Presupuesto presupuesto,
			String fechaInicio, String fechaFin, EstadoOrdenTrabajo estado,
			List<Tarea> listaTareas) {
		OrdenTrabajo ordenTrabajo = new OrdenTrabajo(presupuesto, fechaInicio,
				fechaFin, estado, listaTareas);

		OrdenTrabajoDAO.getInstancia().insert(ordenTrabajo);
		return ordenTrabajo;
	}

	public void agregarTarea(OrdenTrabajo ordenTrabajo, TipoTarea tipoTarea,
			Usuario operario) {
		ordenTrabajo.getListaTareas().add(
				new Tarea(tipoTarea, EstadoTarea.ASIGNADA, null, operario));
		OrdenTrabajoDAO.getInstancia().update(ordenTrabajo);
	}

}
