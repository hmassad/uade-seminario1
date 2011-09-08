package vista.tablesModel;

public class RegistroInforme {
	
	private int numeroOT;
	
	private String cliente;
	
	private String patente;

	private String fechaInicio;
	
	private String fechaFin;
	
	private String estado;
	
	public RegistroInforme(){}
	
	public RegistroInforme(int numeroOT,String cliente,String patente,
			String fechaInicio,String fechaFin,String estado){
		
		this.numeroOT = numeroOT;
		this.cliente = cliente;
		this.patente = patente;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
	}

	public int getNumeroOT() {
		return numeroOT;
	}

	public void setNumeroOT(int numeroOT) {
		this.numeroOT = numeroOT;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
