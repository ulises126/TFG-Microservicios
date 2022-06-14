package uhu.ulises.service;

import java.util.List;

import uhu.ulises.entity.Usuario;

public interface UsuarioService {

	public List<Usuario> listAllUsuario();
	public Usuario getUsuario(String username);
	public Usuario createUsuario(Usuario usuario);
	public Usuario updateUsuario(Usuario usuario);
	public Usuario deleteUsuario(String username);
}
