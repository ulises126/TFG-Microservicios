package uhu.ulises.service;

import java.util.List;

import uhu.ulises.entity.Asignatura;
import uhu.ulises.entity.Modalidad;
import uhu.ulises.entity.Publicacion;
import uhu.ulises.entity.Titulacion;
import uhu.ulises.model.PublicacionValoracionDTO;

public interface PublicacionService {
	
	public List<Publicacion> listAllPublicacion();
	public List<Publicacion> listByUsuario(String usuario);
	public Publicacion getPublicacion(Long id);
	public Publicacion createPublicacion(Publicacion publicacion);
	public Publicacion updatePublicacion(Publicacion publicacion);
	public Publicacion deletePublicacion(Long id);
	public List<Publicacion> findByStatus(String status);
	public List<PublicacionValoracionDTO> findPublicacionesIdByUsuario(String usuario);
	public List<Publicacion> findAllByParameters(Titulacion t, Integer curso, Asignatura a, Float precioMin, Float precioMax, Modalidad modalidad, String antiguedad);
	//public List<Asignatura> findAllAsignaturasByTitulación(Titulacion titulacion);
	//public List<Titulacion> findAllTitulaciones();
}
