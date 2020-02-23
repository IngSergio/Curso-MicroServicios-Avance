package mx.spm.servicio.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import mx.spm.servicio.usuarios.commons.entity.Rol;
import mx.spm.servicio.usuarios.commons.entity.Usuario;


@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{

	//Configurando para que se muestren los id de las 2 tablas
	//ya que por defecto estan ocultos
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Usuario.class, Rol.class);
	}
	
	

}
