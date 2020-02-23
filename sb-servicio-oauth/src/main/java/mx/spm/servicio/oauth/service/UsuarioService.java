package mx.spm.servicio.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import feign.FeignException;
import mx.spm.servicio.oauth.feignCliente.UsuarioFeignCliente;
import mx.spm.servicio.usuarios.commons.entity.Usuario;

@Service
public class UsuarioService implements UsuarioServic, UserDetailsService {

	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeignCliente feignClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {

			Usuario usuario = feignClient.findByUser(username);

			List<GrantedAuthority> authorities = usuario.getRoles().stream()
					.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
					.peek(authority -> log.info("Rol: " + authority.getAuthority())).collect(Collectors.toList());

			log.info("UsuarioServic autenticado: " + username);

			return new User(usuario.getUser(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
					authorities);
		} catch (FeignException e) {
			log.error("Error | Login, El usuario '" + username + "' no existe en la BD");
			throw new UsernameNotFoundException("Error | Login, El usuario '" + username + "' no existe en la BD");
		}
	}

	@Override
	public Usuario findByUser(String user) {
		return feignClient.findByUser(user);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return feignClient.update(usuario, id);
	}

}
