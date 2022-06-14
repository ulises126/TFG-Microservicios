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
import org.springframework.web.bind.annotation.RestController;

import uhu.ulises.entity.Usuario;
import uhu.ulises.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listUsuarios() {
		List<Usuario> listaUsuarios = usuarioService.listAllUsuario();
		if(listaUsuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		else return ResponseEntity.ok(listaUsuarios);
	}
	
	@GetMapping(value = "/{username}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable String username) {
		Usuario usuarioDB = usuarioService.getUsuario(username);
		if(usuarioDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuarioDB);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
		usuario.setFechaRegistro(new Date());
		Usuario usuarioCreate = usuarioService.createUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreate);
	}
	
	@PutMapping(value = "/{username}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable String username, @RequestBody Usuario usuario) {
		usuario.setUsername(username);
		Usuario usuarioDB = usuarioService.updateUsuario(usuario);
		if(usuarioDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuarioDB);
	}
	
	@DeleteMapping(value = "/{username}")
	public ResponseEntity<Usuario> deleteUsuario(@PathVariable String username){
		Usuario usuario = usuarioService.getUsuario(username);
		if(usuario == null) {
			return  ResponseEntity.notFound().build();
		}
		usuario = usuarioService.deleteUsuario(username);
		return ResponseEntity.ok(usuario);
	}
}
