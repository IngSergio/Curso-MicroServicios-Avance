package mx.spm.servicio.items.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.spm.servicio.commons.entity.Producto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

	private Producto producto;
	private Integer cantidad;

	public Double getTotal() {
		return producto.getPrecio() * cantidad.doubleValue();
	}

}
