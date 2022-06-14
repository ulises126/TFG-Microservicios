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

import uhu.ulises.entity.Asignatura;
import uhu.ulises.service.AsignaturaService;

@RestController
@RequestMapping(value = "/asignaturas")
public class AsignaturaController {

	@Autowired
	private AsignaturaService asignaturaService;
	
	@GetMapping("")
	public ResponseEntity<List<Asignatura>> listAsignaturas(){
		List<Asignatura> asignaturas = asignaturaService.listAllAsignatura();
		if(asignaturas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(asignaturas);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Asignatura> getAsignatura(@PathVariable Long id){
		Asignatura asignatura = asignaturaService.getAsignatura(id);
		if(asignatura == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(asignatura);
	}
	
	@PostMapping()
	public ResponseEntity<Asignatura> createAsignatura(@RequestBody Asignatura asignatura){
		Asignatura asignaturaCreate = asignaturaService.createAsignatura(asignatura);
		return ResponseEntity.status(HttpStatus.CREATED).body(asignaturaCreate);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Asignatura> updateAsignatura(@PathVariable Long id, @RequestBody Asignatura asignatura){
		asignatura.setId(id);
		Asignatura asignaturaDB = asignaturaService.updateAsignatura(asignatura);
		if(asignaturaDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(asignaturaDB);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Asignatura> deleteAsignatura(@PathVariable Long id){
		Asignatura asignaturaDB = asignaturaService.getAsignatura(id);
		if(asignaturaDB == null) {
			return  ResponseEntity.notFound().build();
		}
		asignaturaDB = asignaturaService.deleteAsignatura(id);
		return ResponseEntity.ok(asignaturaDB);
	}
	
	@GetMapping(value = "/portitulacion/{titulacion}/{curso}")
	public ResponseEntity<List<Asignatura>> listAsignaturasPorTitulacion(@PathVariable Long titulacion, @PathVariable int curso){
		List<Asignatura> asignaturas = asignaturaService.listAllAsignaturasByTitulacionAndCurso(titulacion, curso);
		if(asignaturas.isEmpty()) {
			ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(asignaturas);
	}
}
