package controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import modelo.Cliente;
import modelo.EstadoOrdenTrabajo;
import modelo.EstadoTarea;
import modelo.OrdenTrabajo;
import modelo.Presupuesto;
import modelo.Tarea;
import modelo.TipoTarea;
import modelo.TipoUsuario;
import modelo.Usuario;
import persistencia.OrdenTrabajoDAO;
import persistencia.PresupuestoDAO;
import persistencia.TareaDAO;
import persistencia.TipoTareaDAO;
import persistencia.UsuarioDAO;

public class Sistema {

	private static Sistema instancia;

	private Usuario usuarioLogeado;

	public static Sistema getInstancia() {
		if (instancia == null) {
			instancia = new Sistema();
		}
		return instancia;
	}

	private Sistema() {
	}

	public boolean validarLogin(String usuario, String password, String perfil) {
		// busco al usuario con el nombre y password
		Usuario usr = UsuarioDAO.getInstancia().select(usuario, password);
		if (usr != null) {
			if (usr.getTipoUsuario().getCode().equals(perfil)) {
				this.usuarioLogeado = usr;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
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

	/**
	 * Obtiene los usuarios con perfil operario.
	 * 
	 * @return usuario operario.
	 */
	public List<Usuario> getOperarios() {
		List<Usuario> operarios = new ArrayList<Usuario>();
		for (Usuario usuario : UsuarioDAO.getInstancia().selectAll()) {
			if (usuario.getTipoUsuario().equals(TipoUsuario.OPERARIO))
				operarios.add(usuario);
		}
		if (operarios.size() > 0) {
			return operarios;
		} else {
			return null;
		}
	}

	public List<TipoTarea> getTiposDeTareas() {
		return TipoTareaDAO.getInstancia().selectAll();
	}
	
	/**
	 * Busca los presupuestos con fecha entre fechaInicio y fechaFin
	 * 
	 * @param fechaInicio de la busqueda.
	 * @param fechaFin de la busqueda.
	 * @return List<Presupuesto> de presupuestos encontrados.
	 */
	public List<Presupuesto> getPresupuestos(String fechaInicio, String fechaFin) {

		if (fechaInicio.equals("") && fechaFin.equals("")) {
			return PresupuestoDAO.getInstancia().selectAll();
		}
		return PresupuestoDAO.getInstancia().selectAllByDates(fechaInicio,
				fechaFin);
	}

	/*-------------------------------------------------------------------------*/
	/*---------------------  GENERAR ORDEN DE TRABAJO -------------------------*/
	/*-------------------------------------------------------------------------*/
	public OrdenTrabajo generarOrdenTrabajo(Presupuesto presupuesto,
			String fechaInicio, String fechaFin, EstadoOrdenTrabajo estado,
			List<Tarea> listaTareas) {
		
		OrdenTrabajo ordenTrabajo = new OrdenTrabajo(presupuesto, fechaInicio,
				fechaFin, estado, listaTareas);
		
		for (Tarea tarea : listaTareas) {
			tarea.setOrdenTrabajo(ordenTrabajo);
		}

		OrdenTrabajoDAO.getInstancia().insert(ordenTrabajo);
		return ordenTrabajo;
	}
	
	/*-------------------------------------------------------------------------*/
	/*--------------------- ACTUALIZAR ORDEN DE TRABAJO -----------------------*/
	/*-------------------------------------------------------------------------*/
	/**
	 * Retorna la lista de ordenes de trabajo que esten entre la fecha 
	 * de inico y fin pasadas
	 */
	@SuppressWarnings("unused")
	public List<OrdenTrabajo> getOrdenesTrabajo(String fechaInicio,
			String fechaFin) {
		
		List<OrdenTrabajo> ordenesTrabajo = OrdenTrabajoDAO.getInstancia()
		.selectAll();
		for (OrdenTrabajo ordenTrabajo : ordenesTrabajo) {
			// TODO comparar fechas o arreglar el DAO
			if (false)
				ordenesTrabajo.remove(ordenTrabajo);
		}
		return ordenesTrabajo;
	}
	
	public void actualizarOrdenDeTrabajo(OrdenTrabajo ordenTrabajo,
			List<Tarea> listaTareas){
		
		//Borro las tareas anteriores que tenia la orden de trabajo
		for (Tarea tarea : ordenTrabajo.getListaTareas()) {
			TareaDAO.getInstancia().delete(tarea);
		}
		
		//Guardo la lista actualizada de tareas
		for (Tarea tarea : listaTareas) {
			tarea.setOrdenTrabajo(ordenTrabajo);
			TareaDAO.getInstancia().insert(tarea);
		}
		
	}

	public void agregarTarea(OrdenTrabajo ordenTrabajo, TipoTarea tipoTarea,
			Usuario operario) {
		
		ordenTrabajo.getListaTareas().add(
				new Tarea(tipoTarea, EstadoTarea.ASIGNADA, null, operario,
						new java.util.Date().toString(),ordenTrabajo));
		OrdenTrabajoDAO.getInstancia().update(ordenTrabajo);
	}

	public void modificarEstadoTarea(Tarea tarea, EstadoTarea estadoTarea) {
		tarea.setEstado(estadoTarea);
		TareaDAO.getInstancia().update(tarea);
	}

	public Tarea[] getTareasPorOperario(Usuario usuario) {
		List<Tarea> tareas = new Vector<Tarea>();
		for (Tarea tarea : TareaDAO.getInstancia().selectAll()) {
			if (tarea.getUsuario().equals(usuario))
				tareas.add(tarea);
		}
		return tareas.toArray(new Tarea[0]);
	}
	
	/*-------------------------------------------------------------------------*/
	/*--------------------- GENERAR INFORME DE OTs ----------------------------*/
	/*-------------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------------*/
	/*--------------------- ACTUALIZAR ESTADO DE TAREA ------------------------*/
	/*-------------------------------------------------------------------------*/


}
