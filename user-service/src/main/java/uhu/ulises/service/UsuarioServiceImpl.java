package uhu.ulises.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uhu.ulises.entity.Usuario;
import uhu.ulises.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> listAllUsuario() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario getUsuario(String username) {
		return usuarioRepository.findById(username).orElse(null);
	}

	@Override
	public Usuario createUsuario(Usuario usuario) {
		usuario.setFechaRegistro(new Date());
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario deleteUsuario(String username) {
		Usuario usuarioDB = getUsuario(username);
		if(usuarioDB == null) {
			return null;
		}
		usuarioRepository.delete(usuarioDB);
		return usuarioDB;
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		Usuario usuarioDB = getUsuario(usuario.getUsername());
		if(usuarioDB == null) {
			return null;
		}
		usuarioDB.setNumeroTelefono(usuario.getNumeroTelefono());
		usuarioDB.setDescripcion(usuario.getDescripcion());
		usuarioDB.setNombre(usuario.getNombre());
		return usuarioRepository.save(usuarioDB);
	}

}
