package mx.spm.servicio.users.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mx.spm.servicio.usuarios.commons.entity.Usuario;


@RepositoryRestResource(path = "usuarios")
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	
	//Consultas perzonalizadas
	
	//Por nombre del metodo
	@RestResource(path = "buscar-user")
	public Usuario findByUser(@Param("user") String user);
	
	//Con anotacion @Query
	@Query("select u from Usuario u where u.user=?1")
	public Usuario obtenerPorUser(String user);
	
	/*
	Con 2 párametros (la clave es el numero de parametro "?'numero'") 
	@Query("select u from Usuario u where u.user=?1, u.email=?2")
	public Usuario obtenerPorUsedr(String user, String  email);
	*/
}
