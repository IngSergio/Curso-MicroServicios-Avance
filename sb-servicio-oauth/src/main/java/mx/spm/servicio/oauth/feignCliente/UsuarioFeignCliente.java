package mx.spm.servicio.oauth.feignCliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import mx.spm.servicio.usuarios.commons.entity.Usuario;

@FeignClient(name = "servicio-usuarios")
public interface UsuarioFeignCliente {

	@GetMapping("/usuarios/search/buscar-user")
	public Usuario findByUser(@RequestParam String user);
	
	@PutMapping("/usuarios/{id}")
	public Usuario update(@RequestBody Usuario usuario, @PathVariable Long id);
}
