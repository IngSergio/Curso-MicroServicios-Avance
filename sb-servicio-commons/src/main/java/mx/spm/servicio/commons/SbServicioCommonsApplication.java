package mx.spm.servicio.commons;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//Con esto deshabilitamos la autoconfiguracion de la conexion a la base de datos
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SbServicioCommonsApplication {
	
}
