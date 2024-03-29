package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ordenTrabajo")
public class OrdenTrabajo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "numero")
	private Integer numero;

	@OneToOne
	@JoinColumn(name = "idPresupuesto")
	private Presupuesto presupuesto;

	@Column(name = "fechaInicio", nullable = false)
	private String fechaInicio;

	@Column(name = "fechaFin", nullable = true)
	private String fechaFin;

	@Enumerated(EnumType.STRING) 
	@Column(name="estado")
	private EstadoOrdenTrabajo estado;

	@OneToMany(mappedBy = "ordenTrabajo", fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tarea> listaTareas;

	public OrdenTrabajo() {
		this.listaTareas = new ArrayList<Tarea>();
	}

	public OrdenTrabajo(Presupuesto presupuesto, String fechaInicio,
			String fechaFin, EstadoOrdenTrabajo estado, List<Tarea> listaTareas) {

		this.presupuesto = presupuesto;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.listaTareas = listaTareas;
	}

	public Integer getNumero() {
		return numero;
	}

	// Solo lo usa hibernate
	@SuppressWarnings("unused")
	private void setNumero(Integer numero) {
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

	public EstadoOrdenTrabajo getEstado() {
		return estado;
	}

	public void setEstado(EstadoOrdenTrabajo estado) {
		this.estado = estado;
	}

	public List<Tarea> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<Tarea> listaTareas) {
		this.listaTareas = listaTareas;
	}

}
