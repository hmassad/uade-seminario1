package modelo;

public enum EstadoOrdenTrabajo {

	ACEPTADO("Aceptado"),

	PREPARADO("Preparado"),

	AENTREGAR("A entregar"),
	
	ENTREGADO("Entregado"),
	
	CANCELADO("Cancelado");

	private String code;

	/**
	 * Constructor del enumerador.
	 * 
	 * @param code Código de alineación de texto.
	 */
	private EstadoOrdenTrabajo(final String code) {

		this.code = code;
	}

	/**
	 * Obtiene el código de alineación de texto.
	 * 
	 * @return Código de alineación de texto.
	 */
	public final String getCode() {

		return (this.code);
	}


	/**
	 * Obtiene el elemento correspondiente al código indicado.
	 * 
	 * @param code Código cuyo elemento obtener.
	 * @return Elemento obtenido, o null si ninguno coincide con dicho código.
	 */
	public static EstadoOrdenTrabajo fromCode(final String code) {

		for (EstadoOrdenTrabajo tipoItem : EstadoOrdenTrabajo.values()) {
			if (tipoItem.getCode().equals(code)) {
				return (tipoItem);
			}
		}

		return (null);
	}
}
