package mx.spm.servicio.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService usuarioService;

	// Metodo que nos ayuda a encriptar nuestras contraseñas
	// lo anotamos con @Bean para que spring lo guarde en el contenedor de beans
	// La diferencia con las clases es que este es un bean de tipo metodo y no de clase
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private AuthenticationEventPublisher eventPublisher; 

	// Registrando al usuario en el authentication manager. Se sobreescribe el
	// siguiente metodo
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Registrando con oauth
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder())
		    .and()
		    .authenticationEventPublisher(eventPublisher);
	}

	// Tambien tenemos que registrar al authenticationManager para poder utilizarlo
	// tal como BCryptPasswordEncoder (de la misma manera):
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

}
