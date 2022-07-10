package uhu.ulises.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uhu.ulises.entity.Valoracion;
import uhu.ulises.entity.ValoracionIdentity;
import uhu.ulises.service.ValoracionService;

@RestController
@RequestMapping(value = "/valoraciones")
public class ValoracionController {

	@Autowired
	private ValoracionService valoracionService;
	
	private static final Logger logger = LoggerFactory.getLogger(ValoracionController.class);
	
	@GetMapping(value = "")
	public ResponseEntity<List<Valoracion>> listAllValoraciones() {
		List<Valoracion> listaValoraciones = valoracionService.listAllValoracion();
		if(listaValoraciones == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaValoraciones);
	}
	
	@GetMapping(value = "/{publicacion}/{usuario}")
	public ResponseEntity<Valoracion> getValoracion(@PathVariable Long publicacion, @PathVariable String usuario) {
		if(publicacion == null || usuario == null || usuario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Valoracion valoracion = valoracionService.getValoracion(new ValoracionIdentity(publicacion, usuario));
		if(valoracion == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(valoracion);
	}
	
	@PostMapping(value = "")
	public ResponseEntity<Valoracion> createValoracion(@RequestBody Valoracion valoracion) {
		Valoracion valoracionCreate = valoracionService.createValoracion(valoracion);
		return ResponseEntity.status(HttpStatus.CREATED).body(valoracionCreate);
	}
	
	@PutMapping(value = "/{publicacion}/{usuario}")
	public ResponseEntity<Valoracion> updateValoracion(@PathVariable Long publicacion, @PathVariable String usuario, @RequestBody Valoracion valoracion) {
		if(publicacion == null || usuario == null || usuario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		valoracion.setValoracionIdentity(new ValoracionIdentity(publicacion, usuario));
		Valoracion valoracionDB = valoracionService.updateValoracion(valoracion);
		if(valoracionDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(valoracionDB);
	}
	
	@DeleteMapping(value = "/{publicacion}/{usuario}")
	public ResponseEntity<Valoracion> deleteValoracion(@PathVariable Long publicacion, @PathVariable String usuario) {
		Valoracion valoracion = valoracionService.getValoracion(new ValoracionIdentity(publicacion, usuario));
		if(valoracion == null) {
			return ResponseEntity.notFound().build();
		}
		valoracionService.deleteValoracion(new ValoracionIdentity(publicacion, usuario));
		return ResponseEntity.ok(valoracion);
	}
	
	@GetMapping(value = "/{usuario}/media")
	public ResponseEntity<Float> getPuntuacionMedia(@PathVariable String usuario) {
		logger.info("/valoraciones/" + usuario + "/media");
		float media = valoracionService.getPuntuacionMediaByUsuario(usuario);
		return ResponseEntity.ok(media);
	}
	
	@GetMapping(value = "/{usuario}")
	public ResponseEntity<List<Valoracion>> listAllValoracionesByUsuario(@PathVariable String usuario) {
		logger.info("/valoraciones/" + usuario);
		List<Valoracion> listaValoraciones = valoracionService.listValoracionesByUsuario(usuario);
		if(listaValoraciones == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(listaValoraciones);
	}
}
