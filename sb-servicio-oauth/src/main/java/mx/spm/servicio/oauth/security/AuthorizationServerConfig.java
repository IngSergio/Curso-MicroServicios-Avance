package mx.spm.servicio.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	
	@Autowired
	private Environment env;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private InfoAdditionalToken info;

	@Autowired
	private AuthenticationManager authenticationManager;

	//Aqui configuramos los permisos que van a tener los endpoints de nuestro servidor de configs
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//Permisos para que los usuarios accedan al endpoint para registrar token
		//En el ejemplo esta permitido para todos
		security.tokenKeyAccess("permitAll()")
				//Metodo que permite validar que el cliente este autenticado
				.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			   ///Identificador del cliente registrado 
			   .withClient(env.getProperty("config.security.oauth.client.id"))
			   //El secret -> para valuadr que el token es valido y se encrypta 
			   .secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))
			   //Tipo de cosas que el usuario puede realizar en el acceso a nuestras apps
			   //en este caso puede leer y escribir
			   .scopes("read","write")
			   //password -> como va ser el token generado. Hay mas como un codigo o el implicito
			   //refresh_token -> para renovar el token antes de que caduque
			   .authorizedGrantTypes("password","refresh_token")
			   //tiempo de vida del token en segundos (1 hr en el ejemplo)
			   .accessTokenValiditySeconds(3600)
			   //Tiempo en el que se tiene que renovar el token (1 hr en el ejemplo) 
			   .refreshTokenValiditySeconds(3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(info, accessTokenConverter()));
		
		endpoints.authenticationManager(authenticationManager)
				 .tokenStore(tokenStore())
				 .accessTokenConverter(accessTokenConverter())
				 .tokenEnhancer(tokenEnhancerChain);
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));
		return tokenConverter;
	}

}
