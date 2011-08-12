package modelo;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tarea")
public class Tarea {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="numero")
	private Integer numero;
	
	@OneToOne
	@JoinColumn(name="tipoTarea")
	private TipoTarea tipoTarea;
	
	@Enumerated(EnumType.STRING) 
	@Column(name="estado")
	private EstadoTarea estado;
	
	@Column(name="fechaFin",nullable=true)
	private String fechaFin;
	
	@Column(name="fechaInicio",nullable=false)
	private String fechaInicio;

	@OneToOne
	@JoinColumn(name="usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ordentrabajo", nullable = false)
	private OrdenTrabajo ordenTrabajo; 
	
	public Tarea() {
	}

	public Tarea(TipoTarea tipoTarea, EstadoTarea estado,
			String fechaFin, Usuario usuario, String fechaInicio,
			OrdenTrabajo ordenDeTrabajo) {
		
		this.tipoTarea = tipoTarea;
		this.estado = estado;
		this.fechaFin = fechaFin;
		this.usuario = usuario;
		this.fechaInicio = fechaInicio;
		this.ordenTrabajo = ordenDeTrabajo;
	}

	public Integer getNumero() {
		return numero;
	}

	//Solo lo usa hibernate
	@SuppressWarnings("unused")
	private void setNumero(Integer numero) {
		this.numero = numero;
	}

	public TipoTarea getTipoTarea() {
		return tipoTarea;
	}

	public void setTipoTarea(TipoTarea tipoTarea) {
		this.tipoTarea = tipoTarea;
	}

	public EstadoTarea getEstado() {
		return estado;
	}

	public void setEstado(EstadoTarea estado) {
		this.estado = estado;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public OrdenTrabajo getOrdenTrabajo() {
		return ordenTrabajo;
	}

	public void setOrdenTrabajo(OrdenTrabajo ordenDeTrabajo) {
		this.ordenTrabajo = ordenDeTrabajo;
	}
}
