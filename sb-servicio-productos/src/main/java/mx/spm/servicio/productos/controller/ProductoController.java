package mx.spm.servicio.productos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mx.spm.servicio.commons.entity.Producto;
import mx.spm.servicio.productos.service.ProductoService;

@RestController
public class ProductoController {

	@Autowired
	private Environment env;

	@Value("${server.port}")
	private Integer port;

	@Autowired
	private ProductoService productoService;

	@GetMapping("/listar")
	public List<Producto> listar() {
		return productoService.findAll().stream().map(producto -> {
			// Implementandoo ribbon con feign
			// producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			/// implementando ribbon con restTemplate
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}

	@GetMapping("/ver/{id}")
	public Producto listar(@PathVariable("id") Long id) {// throws Exception{
		Producto producto = productoService.findById(id);
		// producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		/// implementando ribbon con restTemplate
		producto.setPort(port);

		// Probando Hystrix
		/*
		 * boolean ok = false; if (ok == false) { throw new
		 * Exception("No se pudo cargar el producto"); }
		 */

		// TimeOut -> con Hystrix y Ribbon (interrupciones)
		// Lo comentamos para que no de problemas con el timeout de Zuul
		/*
		 * try { Thread.sleep(3000L); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */

		return producto;
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);
	}

	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		Producto productoActualizado = productoService.findById(id);

		productoActualizado.setNombre(producto.getNombre());
		productoActualizado.setPrecio(producto.getPrecio());

		return productoService.save(productoActualizado);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.deleteById(id);
	}

}
