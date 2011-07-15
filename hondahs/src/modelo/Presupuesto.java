package modelo;

public class Presupuesto {
	
	private Integer numero;
	
	private String fecha;
	
	private Cliente cliente;
	
	private Vehiculo vehiculo;

	public Presupuesto() {
	}

	public Presupuesto(Integer numero, String fecha, Cliente cliente,
			Vehiculo vehiculo) {
	
		this.numero = numero;
		this.fecha = fecha;
		this.cliente = cliente;
		this.vehiculo = vehiculo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

}
