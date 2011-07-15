package modelo;

public class Cliente {
	
	private Integer numero;
	
	private String nombre;
	
	private String email;
	
	private Integer telefono;
	
	private Integer celular;
	
	private String direccion;
		
	public Cliente() {
		super();
	}

	public Cliente(Integer numero, String nombre, String email,
			Integer telefono, Integer celular, String direccion) {
		super();
		this.numero = numero;
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.celular = celular;
		this.direccion = direccion;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public Integer getCelular() {
		return celular;
	}

	public void setCelular(Integer celular) {
		this.celular = celular;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
