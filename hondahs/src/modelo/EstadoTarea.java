package modelo;

public enum EstadoTarea {
	
	ASIGNADA("Asignada"),

	TERMINADA("Terminada"),

	CANCELADA("Cancelada");

	private String code;

	/**
	 * Constructor del enumerador.
	 * 
	 * @param code C�digo de alineaci�n de texto.
	 */
	private EstadoTarea(final String code) {

		this.code = code;
	}

	/**
	 * Obtiene el c�digo de alineaci�n de texto.
	 * 
	 * @return C�digo de alineaci�n de texto.
	 */
	public final String getCode() {

		return (this.code);
	}


	/**
	 * Obtiene el elemento correspondiente al c�digo indicado.
	 * 
	 * @param code C�digo cuyo elemento obtener.
	 * @return Elemento obtenido, o null si ninguno coincide con dicho c�digo.
	 */
	public static TipoUsuario fromCode(final String code) {

		for (TipoUsuario tipoItem : TipoUsuario.values()) {
			if (tipoItem.getCode().equals(code)) {
				return (tipoItem);
			}
		}

		return (null);
	}

}
