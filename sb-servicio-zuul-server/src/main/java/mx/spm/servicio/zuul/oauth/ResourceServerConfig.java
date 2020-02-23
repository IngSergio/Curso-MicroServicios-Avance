package mx.spm.servicio.zuul.oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Value("${config.security.oauth.jwt.key}")
	private String jwtKey;
	
	//Metodo que se encarga de proteger el token
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	//Metodo que se encarga de proteger los endpoint (cada ruta de zuul server)
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//Configurando endpoint para que cualquiera pueda loguearse
		//en primer lugar es el endpoint que mnos genera el token
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
		//Configurando para que cualquiera pueda leer las listas de todos nuestros servicios
		//Acceso publico
		.antMatchers(HttpMethod.GET, 
					 "/api/productos/listar",
					 "/api/items/listar",
					 "/api/usuarios/usuarios")
					 .permitAll()
		//Configurando para que cualquiera pueda leer por ID de todos nuestros servicios con 'ROLES'
		.antMatchers(HttpMethod.GET, 
				     "/api/productos/ver", 
				     "/api/items/ver/{id}/cantidad/{cantidad}", 
				     "/api/usuarios/usuarios/{id}")
					 .hasAnyRole("ADMIN","USER")
		
		//Configurando para que solo los 'ADMIN' puedan realizar POST, PUT Y DELETE
		//Digamos que esta es la forma larga, pero se codifica a manera de ejemplo
		//.antMatchers(HttpMethod.POST, "/api/productos/crear", "/api/items/crear", "/api/usuarios/usuarios").hasRole("ADMIN")
		//.antMatchers(HttpMethod.PUT, "/api/productos/editar/{id}", "/api/items/editar/{id}", "/api/usuarios/usuarios/{id}").hasRole("ADMIN")
		//.antMatchers(HttpMethod.DELETE, "/api/productos/eliminar/{id}", "/api/items/eliminar/{id}","/api/usuarios/usuarios/{id}").hasRole("ADMIN")
		
		//Simplificando la manera de proteger los endpoints POST, PUT Y DELETE
		 .antMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/**").hasRole("ADMIN")
		 //Para cualquier otra ruta que aun no este configurada
		 .anyRequest().authenticated()
		 .and()
		 //Configurando cors -> Intercambio de origencruzado 'cross-origin-resource-sharing'
		 //Basucamente es un mecanismo que nos permite acceder a los recursos de nuestras api-rest
		 //desde otro dominio. Usualmente una aplicacion front-end que consuma nuestras api-rest.
		 .cors().configurationSource(corsConfigurationSource());
		 
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		//agregando ubicacion fisica '*' -> cualquier origen
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		//Metodos HTTP
		corsConfig.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
		
		//Pasando corsConfig a nuestras rutas URL
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}
	
	//Filtro de cors para que no solamente este configurado en spring security
	//si no que quede configurado a nivel global
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
	

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtKey);
		return tokenConverter;
	}
	

}
