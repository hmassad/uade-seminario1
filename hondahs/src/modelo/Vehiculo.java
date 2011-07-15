package modelo;

public class Vehiculo {
	
	private Integer numero;
	
	private String patente;
	
	private String modelo;
	
	private String chasis;
	
	private Integer kilometraje;
	
	private String combustible;

	public Vehiculo() {
	}

	public Vehiculo(Integer numero, String patente, String modelo,
			String chasis, Integer kilometraje, String combustible) {
	
		this.numero = numero;
		this.patente = patente;
		this.modelo = modelo;
		this.chasis = chasis;
		this.kilometraje = kilometraje;
		this.combustible = combustible;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getChasis() {
		return chasis;
	}

	public void setChasis(String chasis) {
		this.chasis = chasis;
	}

	public Integer getKilometraje() {
		return kilometraje;
	}

	public void setKilometraje(Integer kilometraje) {
		this.kilometraje = kilometraje;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}
	
	

}
