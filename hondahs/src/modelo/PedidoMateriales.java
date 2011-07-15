package modelo;

import java.util.List;

public class PedidoMateriales {
	
	private Integer numero;
	
	private Tarea tarea;
	
	private EstadoPedidoMateriales estado;
	
	private List<ItemPedidoMateriales> listaItemPedidosMateriales;

	public PedidoMateriales() {
	}

	public PedidoMateriales(Integer numero, Tarea tarea,
			EstadoPedidoMateriales estado,
			List<ItemPedidoMateriales> listaItemPedidosMateriales) {
		super();
		this.numero = numero;
		this.tarea = tarea;
		this.estado = estado;
		this.listaItemPedidosMateriales = listaItemPedidosMateriales;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public EstadoPedidoMateriales getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedidoMateriales estado) {
		this.estado = estado;
	}

	public List<ItemPedidoMateriales> getListaItemPedidosMateriales() {
		return listaItemPedidosMateriales;
	}

	public void setListaItemPedidosMateriales(
			List<ItemPedidoMateriales> listaItemPedidosMateriales) {
		this.listaItemPedidosMateriales = listaItemPedidosMateriales;
	}
	
	

}
