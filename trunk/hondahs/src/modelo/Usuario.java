package modelo;

public class Usuario {
	
	private Integer numero;
	
	private String nombre;
	
	private Integer legajo;
	
	private TipoUsuario tipoUsuario;
	
	private String clave;

	public Usuario() {
	}

	public Usuario(Integer numero, String nombre, Integer legajo,
			TipoUsuario tipoUsuario, String clave) {
		super();
		this.numero = numero;
		this.nombre = nombre;
		this.legajo = legajo;
		this.tipoUsuario = tipoUsuario;
		this.clave = clave;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getLegajo() {
		return legajo;
	}

	public void setLegajo(Integer legajo) {
		this.legajo = legajo;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	

}
