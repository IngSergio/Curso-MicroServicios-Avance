package mx.spm.servicio.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SbServicioConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbServicioConfigServerApplication.class, args);
	}

}
