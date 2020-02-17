package mx.spm.servicio.items.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
	
	private Long id;
	private String nombre;
	private Double precio;
	private Date fecha;
	
	private Integer port;

}
