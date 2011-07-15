package modelo;

public class Tarea {

	private Integer numero;
	
	private TipoTarea tipoTarea;
	
	private TipoTarea estado;
	
	private String fechaFin;
	
	private Usuario usuario;

	public Tarea() {
	}

	public Tarea(Integer numero, TipoTarea tipoTarea, TipoTarea estado,
			String fechaFin, Usuario usuario) {
		
		this.numero = numero;
		this.tipoTarea = tipoTarea;
		this.estado = estado;
		this.fechaFin = fechaFin;
		this.usuario = usuario;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public TipoTarea getTipoTarea() {
		return tipoTarea;
	}

	public void setTipoTarea(TipoTarea tipoTarea) {
		this.tipoTarea = tipoTarea;
	}

	public TipoTarea getEstado() {
		return estado;
	}

	public void setEstado(TipoTarea estado) {
		this.estado = estado;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
