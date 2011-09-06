package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
	

	/*-------------------------------------------------------------------------*/
	/*---------------------  GENERAR ORDEN DE TRABAJO -------------------------*/
	/*-------------------------------------------------------------------------*/
	public List<Presupuesto> getPresupuestos(String fechaInicio, String fechaFin) {
		
		List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();	
		for (Presupuesto presupuesto : PresupuestoDAO.getInstancia().selectAll()) {
			if (perteneceAlRangoDeFechas(fechaInicio, fechaFin, presupuesto.getFecha())){
				presupuestos.add(presupuesto);
			}
		}
		return presupuestos;
		
	}

	public OrdenTrabajo generarOrdenTrabajo(Presupuesto presupuesto, 
			EstadoOrdenTrabajo estado, List<Tarea> listaTareas) {
		
		Calendar calendario = GregorianCalendar.getInstance();
		Date fecha = calendario.getTime();
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicio = formatoDeFecha.format(fecha);
				
		OrdenTrabajo ordenTrabajo = new OrdenTrabajo(presupuesto,fechaInicio,
				null, estado, listaTareas);
		
		for (Tarea tarea : listaTareas) {
			tarea.setOrdenTrabajo(ordenTrabajo);
		}

		OrdenTrabajoDAO.getInstancia().insert(ordenTrabajo);
		return ordenTrabajo;
	}
	
	/*-------------------------------------------------------------------------*/
	/*--------------------- ACTUALIZAR ORDEN DE TRABAJO -----------------------*/
	/*-------------------------------------------------------------------------*/

	public List<OrdenTrabajo> getOrdenesTrabajo(String fechaInicio,
			String fechaFin) {
		
		List<OrdenTrabajo> ordenesTrabajo = new ArrayList<OrdenTrabajo>();	
		for (OrdenTrabajo ordenTrabajo : OrdenTrabajoDAO.getInstancia().selectAll()) {
			if (perteneceAlRangoDeFechas(fechaInicio, fechaFin, ordenTrabajo.getFechaInicio())){
				ordenesTrabajo.add(ordenTrabajo);
			}
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
	
	public List<Tarea> getTareas(String fechaInicio,String fechaFin) {
		
		List<Tarea> tareas = new ArrayList<Tarea>();	
		for (Tarea tarea : TareaDAO.getInstancia().selectAll()) {
			if (perteneceAlRangoDeFechas(fechaInicio, fechaFin, tarea.getFechaInicio())){
				tareas.add(tarea);
			}
		}
		return tareas;
	}
	
	/*-------------------------------------------------------------------------*/
	/*----- METODOS GENERALES PARA TODAS LAS FUNCIONALIDADES ------------------*/
	/*-------------------------------------------------------------------------*/
	private boolean perteneceAlRangoDeFechas(String fechaInicio, String fechaFin,
			String fechaInicioAComparar) {
		
		if((fechaInicio==null && fechaFin==null)
				|| (fechaInicio.equals("") && fechaFin.equals(""))){
			return true;
		}
		
		//Comparo si las fechas estan dentro del rango seleccionado
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaMenor = null;
		Date fechaMayor = null;
		Date fechaInicioOT = null;
		
		try {
			fechaInicioOT = formatoDelTexto.parse(fechaInicioAComparar);
		} catch (ParseException ex) {}
		try {
			fechaMenor = formatoDelTexto.parse(fechaInicio);
		} catch (ParseException ex) {}
		try {
			fechaMayor = formatoDelTexto.parse(fechaFin);
		} catch (ParseException ex) {}
		
		if((fechaMenor!=null && fechaMayor!=null)
				&& (!fechaMenor.equals("") && !fechaMayor.equals(""))){
			if (fechaMenor.compareTo(fechaInicioOT) <= 0
					&& fechaMayor.compareTo(fechaInicioOT) >= 0){
			     return true;
			}
		}else if(fechaMenor!=null && !fechaMenor.equals("")){
			if (fechaMenor.compareTo(fechaInicioOT) <= 0){
			     return true;
			}
		} else if(fechaMayor!=null && !fechaMayor.equals("")){
			if (fechaMayor.compareTo(fechaInicioOT) >= 0){
			     return true;
			}
		}else {
			return false;
		}
		return false;
	}

}
