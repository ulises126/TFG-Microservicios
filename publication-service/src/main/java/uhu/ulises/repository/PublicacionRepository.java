package uhu.ulises.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uhu.ulises.entity.Asignatura;
import uhu.ulises.entity.Publicacion;
import uhu.ulises.entity.Titulacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long>{

	public List<Publicacion> findByStatus(String status);
	public List<Publicacion> findByUsuario(String usuario);
	
	@Query(value = "SELECT p.id FROM Publicacion p WHERE p.usuario like ?1 ")
	public List<Long> findIdByUsuario(String usuario);
	
	@Query(value = "SELECT p FROM Publicacion p "
			+ "INNER JOIN p.asignatura a "
			+ "INNER JOIN a.titulacion t "
			+ "WHERE (?1 IS NULL OR ?1 = t) "
			+ "AND (?2 IS NULL OR ?2 = 0 OR ?2 = a.curso) "
			+ "AND (?3 IS NULL OR ?3 = a) "
			+ "AND (?4 IS NULL OR ?4 = 0 OR ?4 <= p.precioHora) "
			+ "AND (?5 IS NULL OR ?5 = 0 OR ?5 >= p.precioHora) "
			+ "AND (?6 IS NULL OR ?6 LIKE 'CUALQUIERA' OR p.modalidad LIKE ?6) "
			+ "AND (CASE "
			+ "		WHEN ?7 IS NULL THEN 1 "
			+ "		WHEN ?7 <= p.fechaPublicacion THEN 1 "
			+ "		ELSE 0 "
			+ "END) = 1 "
			+ "AND p.status LIKE 'CREATED' ")
	List<Publicacion> findAllByParameters(
			Titulacion titulacion,
			Integer curso,
			Asignatura asignatura,
			Float precioMin,
			Float precioMax,
			String modalidad,
			Date fechaAPartir);
}
