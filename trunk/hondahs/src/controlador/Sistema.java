package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

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

	public Usuario validarLogin(String usuario, String password, String perfil) {
		// busco al usuario con el nombre y password
		Usuario usr = UsuarioDAO.getInstancia().select(usuario, password);
		if (usr != null) {
			if (usr.getTipoUsuario().getCode().equals(perfil)) {
				this.usuarioLogeado = usr;
				return this.usuarioLogeado;
			} else {
				return null;
			}
		} else {
			return null;
		}
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
		
		Calendar calendar = Calendar.getInstance();
		String fechaDeInicio = Integer.toString(calendar.get(Calendar.DATE))
				+ "/" + Integer.toString(calendar.get(Calendar.MONTH))
				+ "/" + Integer.toString(calendar.get(Calendar.YEAR));
		
		ordenTrabajo.getListaTareas().add(
				new Tarea(tipoTarea, EstadoTarea.ASIGNADA, null, operario,
						fechaDeInicio,ordenTrabajo));
		OrdenTrabajoDAO.getInstancia().update(ordenTrabajo);
	}

	/*-------------------------------------------------------------------------*/
	/*--------------------- GENERAR INFORME DE OTs ----------------------------*/
	/*-------------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------------*/
	/*--------------------- ACTUALIZAR ESTADO DE TAREA ------------------------*/
	/*-------------------------------------------------------------------------*/
	
	public List<Tarea> getTareas(String fechaInicio,String fechaFin) {
		List<Tarea> tareas = new Vector<Tarea>();
		
		// Dependiendo el perfil del usuario se muestran las tareas
		if(usuarioLogeado.getTipoUsuario().name().equals("ADMINISTRATIVO")
		 	|| usuarioLogeado.getTipoUsuario().name().equals("JEFEDETALLER")  
		 	|| usuarioLogeado.getTipoUsuario().name().equals("DUENIO")){
			
			//Traigo todas las tareas cargadas en la base de datos
			for (Tarea tarea : TareaDAO.getInstancia().selectAll()) {
				if (perteneceAlRangoDeFechas(fechaInicio, fechaFin, tarea.getFechaInicio())){
					tareas.add(tarea);
				}
			}
		}else{
			//Traigo las tareas que solo correspondan al usurio que esta usando el sistema
			for (Tarea tarea : TareaDAO.getInstancia().selectAll()) {
				if (tarea.getUsuario().getLegajo().equals(this.usuarioLogeado.getLegajo())
						&& perteneceAlRangoDeFechas(fechaInicio, fechaFin, tarea.getFechaInicio()))
					tareas.add(tarea);
			}
		}
		return tareas;
	}
	
	public void modificarEstadoTarea(Tarea tarea, EstadoTarea estadoTarea) {
		
		// Si es terminada guardo la fecha de fin
		if(estadoTarea.name().equals("TERMINADA")) { 
			Calendar calendar = Calendar.getInstance();
			String fechaDefin = Integer.toString(calendar.get(Calendar.DATE))
					+ "/" + Integer.toString(calendar.get(Calendar.MONTH))
					+ "/" + Integer.toString(calendar.get(Calendar.YEAR));
			tarea.setFechaFin(fechaDefin);
		}

		tarea.setEstado(estadoTarea);
		TareaDAO.getInstancia().update(tarea);
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
