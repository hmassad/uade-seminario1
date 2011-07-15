package modelo;


/**
 * Estae es un ejemplo de enumerados.
 * @author plamas
 *
 */
public enum TipoUsuario {
	
	/**
	 * Alinea el texto a izquierda.
	 */
	LEFT(1L, "Left"),

	/**
	 * Alinea el texto a derecha.
	 */
	RIGHT(2L, "Right");

	/**
	 * Identificador de alineaci�n de texto.
	 */
	private Long id;

	/**
	 * C�digo de alineaci�n de texto.
	 */
	private String code;

	/**
	 * Constructor del enumerador.
	 * 
	 * @param id Identificador de alineaci�n de texto.
	 * @param code C�digo de alineaci�n de texto.
	 */
	private TipoUsuario(final Long id, final String code) {

		this.id = id;
		this.code = code;
	}

	/**
	 * Obtiene el identificador de alineaci�n de texto.
	 * 
	 * @return Identificador de alineaci�n de texto.
	 */
	public final Long getId() {

		return (this.id);
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
	 * Obtiene el elemento correspondiente al identificador indicado.
	 * 
	 * @param id Identificador cuyo elemento obtener.
	 * @return Elemento obtenido, o null si ninguno coincide con dicho identificador.
	 */
	public static TipoUsuario fromId(final Long id) {

		for (TipoUsuario tipoItem : TipoUsuario.values()) {
			if (tipoItem.getId().equals(id)) {
				return (tipoItem);
			}
		}

		return (null);
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
