package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="numero")
	private Integer numero;
	
	@Column(name="nombre",nullable=false)
	private String nombre;
	
	@Column(name="legajo",nullable=false)
	private Integer legajo;
	
	@Column(name="tipoUsuario")
	private TipoUsuario tipoUsuario;
	
	@Column(name="clave",nullable=false)
	private String clave;

	public Usuario() {
	}

	public Usuario(String nombre, Integer legajo,
			TipoUsuario tipoUsuario, String clave) {
		
		this.nombre = nombre;
		this.legajo = legajo;
		this.tipoUsuario = tipoUsuario;
		this.clave = clave;
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
