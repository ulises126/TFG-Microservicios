package uhu.ulises.controller;

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
import org.springframework.web.bind.annotation.RestController;

import uhu.ulises.entity.Titulacion;
import uhu.ulises.service.TitulacionService;

@RestController
@RequestMapping(value = "/titulaciones")
public class TitulacionController {

	@Autowired
	private TitulacionService titulacionService;
	
	@GetMapping("")
	public ResponseEntity<List<Titulacion>> listTitulacion(){
		List<Titulacion> titulaciones = titulacionService.listAllTitulacion();
		if(titulaciones.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(titulaciones);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Titulacion> getTitulacion(@PathVariable Long id){
		Titulacion titulacion = titulacionService.getTitulacion(id);
		if(titulacion == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(titulacion);
	}
	
	@PostMapping()
	public ResponseEntity<Titulacion> createTitulacion(@RequestBody Titulacion titulacion){
		Titulacion titulacionCreate = titulacionService.createTitulacion(titulacion);
		return ResponseEntity.status(HttpStatus.CREATED).body(titulacionCreate);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Titulacion> updateTitulacion(@PathVariable Long id, @RequestBody Titulacion titulacion){
		titulacion.setId(id);
		Titulacion titulacionDB = titulacionService.updateTitulacion(titulacion);
		if(titulacionDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(titulacionDB);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Titulacion> deleteTitulacion(@PathVariable Long id){
		Titulacion titulacionDB = titulacionService.getTitulacion(id);
		if(titulacionDB == null) {
			return  ResponseEntity.notFound().build();
		}
		titulacionDB = titulacionService.deleteTitulacion(id);
		return ResponseEntity.ok(titulacionDB);
	}
	
}
