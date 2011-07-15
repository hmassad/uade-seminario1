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
	 * Identificador de alineación de texto.
	 */
	private Long id;

	/**
	 * Código de alineación de texto.
	 */
	private String code;

	/**
	 * Constructor del enumerador.
	 * 
	 * @param id Identificador de alineación de texto.
	 * @param code Código de alineación de texto.
	 */
	private TipoUsuario(final Long id, final String code) {

		this.id = id;
		this.code = code;
	}

	/**
	 * Obtiene el identificador de alineación de texto.
	 * 
	 * @return Identificador de alineación de texto.
	 */
	public final Long getId() {

		return (this.id);
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
	 * Obtiene el elemento correspondiente al código indicado.
	 * 
	 * @param code Código cuyo elemento obtener.
	 * @return Elemento obtenido, o null si ninguno coincide con dicho código.
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
