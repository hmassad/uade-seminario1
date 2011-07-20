package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@Column(name="fechaFin",nullable=false)
	private String fechaFin;
	
	@Column(name="fechaInicio",nullable=false)
	private String fechaInicio;

	@OneToOne
	@JoinColumn(name="usuario")
	private Usuario usuario;

	public Tarea() {
	}

	public Tarea(TipoTarea tipoTarea, EstadoTarea estado,
			String fechaFin, Usuario usuario) {
		
		this.tipoTarea = tipoTarea;
		this.estado = estado;
		this.fechaFin = fechaFin;
		this.usuario = usuario;
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
	
	
}
