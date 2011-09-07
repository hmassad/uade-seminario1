package interfaces;

import modelo.Usuario;

public interface ILoginPerformer {
	
	void setUsuario(Usuario usuario);

	Usuario getUsuario();
	
	void cargarMenuAplicacion();
}
