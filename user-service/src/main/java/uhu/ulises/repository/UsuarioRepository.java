package uhu.ulises.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uhu.ulises.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	
}
