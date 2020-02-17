package mx.spm.servicio.productos.service;

import java.util.List;

import mx.spm.servicio.productos.entity.Producto;

public interface ProductoService {
	
	public List<Producto> findAll();
	
	public Producto findById(Long id);
	
	public Producto save(Producto producto);
	
	public void deleteById(Long id);
	

}
