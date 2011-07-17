package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehiculo")
public class Vehiculo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="numero")
	private Integer numero;
	
	@Column(name="patente",nullable=false)
	private String patente;
	
	@Column(name="modelo",nullable=false)
	private String modelo;
	
	@Column(name="chasis",nullable=false)
	private String chasis;
	
	@Column(name="kilometraje",nullable=false)
	private Integer kilometraje;
	
	@Column(name="combustible",nullable=false)
	private String combustible;

	public Vehiculo() {
	}

	public Vehiculo(String patente, String modelo,
			String chasis, Integer kilometraje, String combustible) {
	
		this.patente = patente;
		this.modelo = modelo;
		this.chasis = chasis;
		this.kilometraje = kilometraje;
		this.combustible = combustible;
	}

	public Integer getNumero() {
		return numero;
	}

	//Solo lo usa hibernate
	@SuppressWarnings("unused")
	private void setNumero(Integer numero) {
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
