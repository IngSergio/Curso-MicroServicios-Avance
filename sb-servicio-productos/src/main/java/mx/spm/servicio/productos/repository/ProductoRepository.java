package mx.spm.servicio.productos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.spm.servicio.commons.entity.Producto;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

}
