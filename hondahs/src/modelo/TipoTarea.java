package modelo;

public class TipoTarea {
	
	private Integer numero;
	
	private String descripcion;

	public TipoTarea() {
	}

	public TipoTarea(Integer numero, String descripcion) {
		super();
		this.numero = numero;
		this.descripcion = descripcion;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
