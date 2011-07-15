package modelo;

public class Informe {
	
	private Integer numero;
	
	private String tipoInforme;

	public Informe() {
	}

	public Informe(Integer numero, String tipoInforme) {
		super();
		this.numero = numero;
		this.tipoInforme = tipoInforme;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getTipoInforme() {
		return tipoInforme;
	}

	public void setTipoInforme(String tipoInforme) {
		this.tipoInforme = tipoInforme;
	}
	
	
}
