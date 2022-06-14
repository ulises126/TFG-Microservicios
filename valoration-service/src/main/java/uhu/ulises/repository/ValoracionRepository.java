package uhu.ulises.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uhu.ulises.entity.Valoracion;
import uhu.ulises.entity.ValoracionIdentity;

public interface ValoracionRepository extends JpaRepository<Valoracion, ValoracionIdentity>{

	public List<Valoracion> findByValoracionIdentityIdPublicacion(Long idPublicacion);
	
	@Query(value = "SELECT v.puntuacion FROM Valoracion v "
			+ "WHERE v.valoracionIdentity.idPublicacion in ?1")
	public List<Integer> getPuntuacionByValoracionIdentityIdPublicacion(List<Long> listaIdPublicaciones);
	
	@Query(value = "SELECT v FROM Valoracion v "
			+ "WHERE v.valoracionIdentity.idPublicacion in ?1")
	public List<Valoracion> getValoracionByValoracionIdentityIdPublicacion(List<Long> listaIdPublicaciones);
}
