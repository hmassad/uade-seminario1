package controlador;

import junit.framework.TestCase;

public class ConfiguracionTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testParser() {
		System.out.printf("direccion: %s\n", Configuracion.getInstancia().getPersistencia().getDireccion());
		System.out.printf("puerto: %d\n", Configuracion.getInstancia().getPersistencia().getPuerto());
		System.out.printf("catalogo: %s\n", Configuracion.getInstancia().getPersistencia().getCatalogo());
		System.out.printf("usuario: %s\n", Configuracion.getInstancia().getPersistencia().getUsuario());
		System.out.printf("contraseña: %s\n", Configuracion.getInstancia().getPersistencia().getContrasena());
	}
}
