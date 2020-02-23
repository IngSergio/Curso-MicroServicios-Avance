package mx.spm.servicio.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"mx.spm.servicio.usuarios.commons.entity"})
public class SbServicioUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbServicioUsuariosApplication.class, args);
	}

}
