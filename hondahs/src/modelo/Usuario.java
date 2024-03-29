package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@Column(name="usuario",nullable=false)
	private String usuario;
	
	@Column(name="legajo",nullable=false)
	private Integer legajo;
	
	@Enumerated(EnumType.STRING) 
	@Column(name="tipoUsuario", nullable = false)
	private TipoUsuario tipoUsuario;
	
	@Column(name="clave",nullable=false)
	private String clave;

	public Usuario() {
	}

	public Usuario(String nombre, Integer legajo,
			TipoUsuario tipoUsuario, String clave, 
			String usuario) {
		
		this.nombre = nombre;
		this.legajo = legajo;
		this.tipoUsuario = tipoUsuario;
		this.clave = clave;
		this.usuario = usuario;
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

	@Override
	public String toString() {
		return nombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	

}
