package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="numero") 
	private Integer numero;
	
	@Column(name="nombre",nullable=false) 
	private String nombre;
	
	@Column(name="email",nullable=false)
	private String email;
	
	@Column(name="telefono",nullable=false)
	private Integer telefono;
	
	@Column(name="celular",nullable=false)
	private Integer celular;
	
	@Column(name="direccion",nullable=false)
	private String direccion;
		
	public Cliente() {
		super();
	}

	public Cliente(String nombre, String email,
			Integer telefono, Integer celular, String direccion) {
		
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.celular = celular;
		this.direccion = direccion;
	}

	public Integer getNumero() {
		return numero;
	}

	//Solo lo usa hibernate
	@SuppressWarnings("unused")
	private void setNumero(Integer numero) {
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
