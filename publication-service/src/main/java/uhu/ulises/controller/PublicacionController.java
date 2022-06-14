package uhu.ulises.controller;

import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uhu.ulises.entity.Asignatura;
import uhu.ulises.entity.Modalidad;
import uhu.ulises.entity.Publicacion;
import uhu.ulises.entity.Titulacion;
import uhu.ulises.model.PublicacionValoracionDTO;
import uhu.ulises.service.PublicacionService;

@RestController
@RequestMapping(value = "/publicaciones")
public class PublicacionController {

	@Autowired
	private PublicacionService publicacionService;
	
	@GetMapping(value = "/sinparametros")
	public ResponseEntity<List<Publicacion>> listPublicaciones(){
		List<Publicacion> publicaciones = publicacionService.listAllPublicacion();
		if(publicaciones.isEmpty()) {
			ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(publicaciones);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Publicacion> getPublicacion(@PathVariable Long id){
		Publicacion publicacion = publicacionService.getPublicacion(id);
		if(publicacion == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(publicacion);
	}
	
	@PostMapping()
	public ResponseEntity<Publicacion> createPublicacion(@RequestBody Publicacion publicacion){
		publicacion.setFechaPublicacion(new Date());
		publicacion.setStatus("CREATED");
		Publicacion publicacionCreate = publicacionService.createPublicacion(publicacion);
		return ResponseEntity.status(HttpStatus.CREATED).body(publicacionCreate);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Publicacion> updatePublicacion(@PathVariable Long id, @RequestBody Publicacion publicacion){
		publicacion.setId(id);
		Publicacion publicacionDB = publicacionService.updatePublicacion(publicacion);
		if(publicacionDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(publicacionDB);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Publicacion> deletePublicacion(@PathVariable Long id){
		Publicacion publicacion = publicacionService.getPublicacion(id);
		if(publicacion == null) {
			return  ResponseEntity.notFound().build();
		}
		publicacion.setStatus("DELETED");
		publicacion = publicacionService.updatePublicacion(publicacion);
		return ResponseEntity.ok(publicacion);
	}
	
	@GetMapping(value = "/publicacionesid/{usuario}")
	public ResponseEntity<List<PublicacionValoracionDTO>> listPublicacionesId(@PathVariable String usuario) {
		List<PublicacionValoracionDTO> publicacionesId = publicacionService.findPublicacionesIdByUsuario(usuario);
		if(publicacionesId == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(publicacionesId);
	}
	
	@GetMapping(value = "/usuario/{usuario}")
	public ResponseEntity<List<Publicacion>> listPublicacionesPorUsuario(@PathVariable String usuario) {
		List<Publicacion> publicaciones = publicacionService.listByUsuario(usuario);
		if(publicaciones == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(publicaciones);
	}
	
	@GetMapping(value = "")
	public ResponseEntity<List<Publicacion>> listPublicacionesParametros(
			@RequestParam(required = false) Long titulacion,
			@RequestParam(required = false) Integer curso,
			@RequestParam(required = false) Long asignatura,
			@RequestParam(required = false) Float precioMin,
			@RequestParam(required = false) Float precioMax,
			@RequestParam(required = false) String modalidad,
			@RequestParam(required = false) String antiguedad) {
		
		Modalidad m = null;
		if(modalidad != null) {
			switch(modalidad) {
				case "MIXTA":
				case "M":
					m = Modalidad.MIXTA;
					break;
				case "PRESENCIAL":
				case "P":
					m = Modalidad.PRESENCIAL;
					break;
				case "ONLINE":
				case "O":
					m = Modalidad.ONLINE;
					break;
				default:break;
			}
		}
		
		Titulacion t = null;
		if(titulacion != null)
			t = Titulacion.builder().id(titulacion).build();
		
		Asignatura a = null;
		if(asignatura != null)
			a = Asignatura.builder().id(asignatura).build();
		
		List<Publicacion> publicaciones = publicacionService.findAllByParameters(t, curso, a, precioMin, precioMax, m, antiguedad);
		
		if(publicaciones.isEmpty()) {
			ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(publicaciones);
	}
}
