package modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pedidoMateriales")
public class PedidoMateriales {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="numero")
	private Integer numero;
	
	@OneToOne
	@JoinColumn(name="numero")
	private Tarea tarea;
	
	@Column(name="estado")
	private EstadoPedidoMateriales estado;
	
	@OneToMany
    @JoinColumn(name="numero")
	private List<ItemPedidoMateriales> listaItemPedidosMateriales;

	public PedidoMateriales() {
	}

	public PedidoMateriales(Tarea tarea,
			EstadoPedidoMateriales estado,
			List<ItemPedidoMateriales> listaItemPedidosMateriales) {
		
		this.tarea = tarea;
		this.estado = estado;
		this.listaItemPedidosMateriales = listaItemPedidosMateriales;
	}

	public Integer getNumero() {
		return numero;
	}

	//Solo lo usa hibernate
	@SuppressWarnings("unused")
	private void setNumero(Integer numero) {
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
