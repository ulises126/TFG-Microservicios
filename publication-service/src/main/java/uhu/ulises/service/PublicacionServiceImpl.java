package uhu.ulises.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uhu.ulises.entity.Asignatura;
import uhu.ulises.entity.Modalidad;
import uhu.ulises.entity.Publicacion;
import uhu.ulises.entity.Titulacion;
import uhu.ulises.model.PublicacionValoracionDTO;
import uhu.ulises.repository.AsignaturaRepository;
import uhu.ulises.repository.PublicacionRepository;
import uhu.ulises.repository.TitulacionRepository;


@Service
public class PublicacionServiceImpl implements PublicacionService{

	@Autowired
	private PublicacionRepository publicacionRepository;
	@Autowired
	private AsignaturaRepository asignaturaRepository;
	@Autowired
	private TitulacionRepository titulacionRepository;
	
	@Override
	public List<Publicacion> listAllPublicacion() {
		return publicacionRepository.findAll();
	}

	@Override
	public Publicacion getPublicacion(Long id) {
		return publicacionRepository.findById(id).orElse(null);
	}

	@Override
	public Publicacion createPublicacion(Publicacion publicacion) {
		publicacion.setFechaPublicacion(new Date());
		publicacion.setStatus("CREATED");
		return publicacionRepository.save(publicacion);
	}

	@Override
	public Publicacion updatePublicacion(Publicacion publicacion) {
		
		Publicacion publicacionDB = getPublicacion(publicacion.getId());
		if(publicacionDB == null) {
			return null;
		}
		
		publicacionDB.setTitulo(publicacion.getTitulo());
		publicacionDB.setDescripcion(publicacion.getDescripcion());
		publicacionDB.setPrecioHora(publicacion.getPrecioHora());
		publicacionDB.setModalidad(publicacion.getModalidad());
		publicacionDB.setAsignatura(publicacion.getAsignatura());
		return publicacionRepository.save(publicacionDB);
	}

	@Override
	public Publicacion deletePublicacion(Long id) {
		Publicacion publicacionDB = getPublicacion(id);
		if(publicacionDB == null) {
			return null;
		}
		publicacionDB.setStatus("DELETED");
		publicacionRepository.save(publicacionDB);
		return publicacionDB;
	}

	@Override
	public List<Publicacion> findByStatus(String status) {
		return publicacionRepository.findByStatus(status);
	}

	@Override
	public List<Publicacion> findAllByParameters(Titulacion t, Integer curso, Asignatura a, Float precioMin,
			Float precioMax, Modalidad modalidad, String antiguedad) {
		
		Date antiguedadFecha = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		if(antiguedad != null) {
			switch (antiguedad) {
				case "D":
					cal.add(Calendar.DATE, -1);
					antiguedadFecha = cal.getTime();
					break;
				case "M":
					cal.add(Calendar.MONTH, -1);
					antiguedadFecha = cal.getTime();
					break;
				case "A":
					cal.add(Calendar.YEAR, -1);
					antiguedadFecha = cal.getTime();
					break;
				default:break;
			}
		}
		
		String modalidadString = "CUALQUIERA";
		if(modalidad != null)
			modalidadString = modalidad.toString();
		
		Titulacion titulacion = null;
		if(t != null)
			titulacion = titulacionRepository.getById(t.getId());
		
		Asignatura asignatura = null;
		if(a != null)
			asignatura = asignaturaRepository.getById(a.getId());

		if(titulacion != null & asignatura != null)
			return publicacionRepository.findAllByParameters(titulacion, curso, asignatura, precioMin, precioMax, modalidadString, antiguedadFecha);

		if(titulacion != null)
			return publicacionRepository.findAllByParameters(titulacion, curso, null, precioMin, precioMax, modalidadString, antiguedadFecha);

		if(asignatura != null)
			return publicacionRepository.findAllByParameters(null, curso, asignatura, precioMin, precioMax, modalidadString, antiguedadFecha);

		return publicacionRepository.findAllByParameters(null, curso, null, precioMin, precioMax, modalidadString, antiguedadFecha);

	}

	@Override
	public List<PublicacionValoracionDTO> findPublicacionesIdByUsuario(String usuario) {
		List<Publicacion> publicacionesIdTit = publicacionRepository.findByUsuario(usuario);
		if(publicacionesIdTit.isEmpty()) {
			return null;
		}
		List<PublicacionValoracionDTO> pubValDtoList = new ArrayList<PublicacionValoracionDTO>();
		for(int i=0; i< publicacionesIdTit.size(); i++) {
			PublicacionValoracionDTO pubValDto = PublicacionValoracionDTO.builder()
					.id(publicacionesIdTit.get(i).getId())
					.titulo(publicacionesIdTit.get(i).getTitulo())
					.build();
			pubValDtoList.add(pubValDto);
		}
		return pubValDtoList;
	}

	@Override
	public List<Publicacion> listByUsuario(String usuario) {
		return publicacionRepository.findByUsuario(usuario);
	}

}
