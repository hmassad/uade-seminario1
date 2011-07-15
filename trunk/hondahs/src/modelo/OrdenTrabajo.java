package modelo;

import java.util.ArrayList;
import java.util.List;

public class OrdenTrabajo {
	
	private Integer numero;
	
	private Presupuesto presupuesto;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private String estado;
	
	private List<Tarea> listaTareas;

	public OrdenTrabajo() {
		this.listaTareas = new ArrayList<Tarea>(); 
	}

	public OrdenTrabajo(Integer numero, Presupuesto presupuesto,
			String fechaInicio, String fechaFin, String estado,
			List<Tarea> listaTareas) {
		
		this.numero = numero;
		this.presupuesto = presupuesto;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.listaTareas = listaTareas;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Tarea> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<Tarea> listaTareas) {
		this.listaTareas = listaTareas;
	}
	
	
	
}
