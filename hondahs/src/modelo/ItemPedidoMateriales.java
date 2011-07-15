package modelo;

public class ItemPedidoMateriales {
	
	private Integer numero;
	
	private Integer cantidad;
	
	private String descripcion;

	public ItemPedidoMateriales() {
	}

	public ItemPedidoMateriales(Integer numero, Integer cantidad,
			String descripcion) {
		
		this.numero = numero;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
