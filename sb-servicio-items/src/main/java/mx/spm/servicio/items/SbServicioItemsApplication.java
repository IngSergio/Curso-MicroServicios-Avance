package mx.spm.servicio.items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker
@EnableEurekaClient
//@RibbonClient(name = "servicio-productos")
@EnableFeignClients
@SpringBootApplication
//Escaneando el package del otro proyecto para que reconozca la clase 
//Producto. Si tenemos mas package como dependencia los separamos con comas
@EntityScan({"mx.spm.servicio.commons.entity"})
//Con esto deshabilitamos la autoconfiguracion de la conexion a la base de datos
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SbServicioItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbServicioItemsApplication.class, args);
	}

}
