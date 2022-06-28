package uhu.ulises.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uhu.ulises.client.AuthenticationClient;
import uhu.ulises.client.ValorationClient;
import uhu.ulises.dto.AuthUser;
import uhu.ulises.entity.Usuario;
import uhu.ulises.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ValorationClient valorationClient;
	
	@Autowired
	private AuthenticationClient authenticationClient;
	
	@Override
	public List<Usuario> listAllUsuario() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario getUsuario(String username) {
		Usuario usuario = usuarioRepository.findById(username).orElse(null);
		if(usuario != null) {
			float media = (float)valorationClient.getMediaUsuario(username).getBody();
			usuario.setMediaValoraciones(media);
		}
			
		return usuario;
	}

	@Override
	public Usuario createUsuario(Usuario usuario) {
		usuario.setFechaRegistro(new Date());
		Usuario userAlta = usuarioRepository.save(usuario);
		if(userAlta == null) {
			return null;
		}
		AuthUser authUser = AuthUser.builder()
				.username(usuario.getUsername())
				.password(usuario.getPassword())
				.build();
		AuthUser response = authenticationClient.createAuthUser(authUser).getBody();
		if(response == null) {
			this.usuarioRepository.delete(userAlta);
			return null;
		}
		return userAlta;
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
