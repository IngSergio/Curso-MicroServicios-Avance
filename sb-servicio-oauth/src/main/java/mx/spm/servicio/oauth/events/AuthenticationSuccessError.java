package mx.spm.servicio.oauth.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import feign.FeignException;
import mx.spm.servicio.oauth.service.UsuarioServic;
import mx.spm.servicio.usuarios.commons.entity.Usuario;

@Component
public class AuthenticationSuccessError implements AuthenticationEventPublisher{

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessError.class);
	
	@Autowired
	private UsuarioServic usuarioService; 
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String msg = "Success | Login: " +  user.getUsername();
		log.info(msg);
		System.out.println(msg);
		
		Usuario usuario = usuarioService.findByUser(authentication.getName());
		if (usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			usuarioService.update(usuario, usuario.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String msg = "Error | Login: " +  exception.getMessage();
		log.error(msg);
		System.out.println(msg);
		
		try {
			Usuario usuario = usuarioService.findByUser(authentication.getName());
			if (usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			
			log.info("Intentos actuales: " + usuario.getIntentos());
			
			usuario.setIntentos(usuario.getIntentos() + 1);
			
			log.info("Intentos despues: " + usuario.getIntentos());
			
			if (usuario.getIntentos() >= 3) {
				log.error(String.format("El usuario %s se deshabilito | Intentos agotados", usuario.getUser()));
				usuario.setEnabled(false);
			}
			
			usuarioService.update(usuario, usuario.getId());
			
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema ", authentication.getName()));
		}
	}
	
}
