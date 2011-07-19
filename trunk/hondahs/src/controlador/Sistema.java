package controlador;

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
		//busco al usuario con el nombre y password
		Usuario usr = UsuarioDAO.getInstancia().select(usuario,password);
		if(usr!=null){
			if(usr.getTipoUsuario().getCode().equals(perfil)){
				this.usuarioLogeado = usr;
				return true;
			}else{
				return false;
			}		
		}else{
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

	public Usuario[] getOperarios() {
		List<Usuario> operarios = new Vector<Usuario>();
		for (Usuario usuario : UsuarioDAO.getInstancia().selectAll()) {
			if (usuario.getTipoUsuario().getCode().equals(TipoUsuario.OPERARIO))
				operarios.add(usuario);
		}
		return (Usuario[]) operarios.toArray();
	}
	
	
	public void crearOrdenDeTrabajo(int numeroPresupuesto,String fechaInicio, String fechaFin, String estado,
			List<Tarea> listaTareas){
		
		//se obtiene el presupuesto
		Presupuesto presupuesto = PresupuestoDAO.getInstancia().select(numeroPresupuesto);
		
		//Se crea la orden de trabajo
		OrdenTrabajo ot = new OrdenTrabajo(presupuesto,fechaInicio,fechaFin,EstadoOrdenTrabajo.
				fromCode(estado),listaTareas);
		
		//se inserta en la base de datos
		OrdenTrabajoDAO.getInstancia().insert(ot);
	}
	
	public void actualizarOrdenDeTrabajo(int numeroOrdenTrabajo,String estado){
		
		//se busca la ot
		OrdenTrabajo ot = OrdenTrabajoDAO.getInstancia().select(numeroOrdenTrabajo);

		//se actualiza la ot
		ot.setEstado(EstadoOrdenTrabajo.fromCode(estado));
		
		//se guarda la actualizacion en la base de datos
		OrdenTrabajoDAO.getInstancia().update(ot);
	}
	
	public void getDatosInforme(){
		
	}
	
	public void CrearTarea(String descripcionTipoTarea, String estadoTarea, String fechaFin){
		
		//Se obtiene el tipo de tarea seleccionado.
		TipoTarea tipoTarea = TipoTareaDAO.getInstancia().select(descripcionTipoTarea);
		
		//Se obtiene el estado seleccionado.
		TipoTarea estado = TipoTareaDAO.getInstancia().select(estadoTarea);
		
		//Se crea la tarea.
		//Tarea tarea = new Tarea(tipoTarea,estado,fechaFin,this.usuarioLogeado);
		
		//Se inserta la tarea en la base.
		//TareaDAO.getInstancia().insert(tarea);
		
	}

	public OrdenTrabajo generarOrdenTrabajo(Presupuesto presupuesto,
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
		return (Tarea[]) tareas.toArray();
	}

}