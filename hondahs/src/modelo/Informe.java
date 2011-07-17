package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="informe")
public class Informe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="numero")
	private Integer numero;
	
	@Column(name="tipoInforme",nullable=false)
	private String tipoInforme;

	public Informe() {
	}

	public Informe(String tipoInforme) {
		this.tipoInforme = tipoInforme;
	}

	public Integer getNumero() {
		return numero;
	}

	//Solo lo usa hibernate
	@SuppressWarnings("unused")
	private void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getTipoInforme() {
		return tipoInforme;
	}

	public void setTipoInforme(String tipoInforme) {
		this.tipoInforme = tipoInforme;
	}
	
	
}
