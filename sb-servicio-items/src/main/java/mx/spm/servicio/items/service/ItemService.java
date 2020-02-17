package mx.spm.servicio.items.service;

import java.util.List;

import mx.spm.servicio.items.entity.Item;
import mx.spm.servicio.items.entity.Producto;

public interface ItemService {

	public List<Item> findAll();

	public Item findById(Long id, Integer cantidad);

	public Producto save(Producto producto);

	public Producto update(Producto producto, Long id);

	public void delete(Long id);

}
