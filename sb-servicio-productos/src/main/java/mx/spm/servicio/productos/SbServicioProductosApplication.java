package mx.spm.servicio.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
//Escaneando el package del otro proyecto para que reconozca la clase 
//Producto. Si tenemos mas package como dependencia los separamos con comas
@EntityScan({"mx.spm.servicio.commons.entity"})
public class SbServicioProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbServicioProductosApplication.class, args);
	}

}
