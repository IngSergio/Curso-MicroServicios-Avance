package mx.spm.servicio.productos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.spm.servicio.productos.entity.Producto;
import mx.spm.servicio.productos.repository.ProductoRepository;
import mx.spm.servicio.productos.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository productoRepo;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return productoRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {
		return productoRepo.save(producto);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productoRepo.deleteById(id);
	}

}
