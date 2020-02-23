package mx.spm.servicio.oauth.service;

import mx.spm.servicio.usuarios.commons.entity.Usuario;

public interface UsuarioServic {
	
	public Usuario findByUser(String user);
	
	public Usuario update(Usuario usuario, Long id);


}
