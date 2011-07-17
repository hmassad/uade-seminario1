package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="itemPedidoMateriales")
public class ItemPedidoMateriales {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="numero")
	private Integer numero;
	
	@Column(name="cantidad",nullable=false)
	private Integer cantidad;
	
	@Column(name="descripcion",nullable=false)
	private String descripcion;
	
	@ManyToOne
    @JoinColumn(name="idPedidoMateriales")
	private PedidoMateriales pedidoMateriales;

	public ItemPedidoMateriales() {
	}

	public ItemPedidoMateriales(Integer cantidad,
			String descripcion,PedidoMateriales pedidoMateriales) {
		
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.pedidoMateriales = pedidoMateriales;
	}

	public Integer getNumero() {
		return numero;
	}

	//Solo lo usa hibernate
	@SuppressWarnings("unused")
	private void setNumero(Integer numero) {
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

	public PedidoMateriales getPedidoMateriales() {
		return pedidoMateriales;
	}

	public void setPedidoMateriales(PedidoMateriales pedidoMateriales) {
		this.pedidoMateriales = pedidoMateriales;
	}
	
}
