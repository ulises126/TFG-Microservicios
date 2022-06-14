package uhu.ulises.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uhu.ulises.client.PublicationClient;
import uhu.ulises.entity.Valoracion;
import uhu.ulises.entity.ValoracionIdentity;
import uhu.ulises.model.PublicacionValoracionDTO;
import uhu.ulises.repository.ValoracionRepository;

@Service
public class ValoracionServiceImpl implements ValoracionService {

	@Autowired
	private ValoracionRepository valoracionRepository;
	
	@Autowired
	private PublicationClient publicationClient;
	
	@Override
	public List<Valoracion> listAllValoracion() {
		return valoracionRepository.findAll();
	}

	@Override
	public Valoracion getValoracion(ValoracionIdentity valoracionIdentity) {
		return valoracionRepository.findById(valoracionIdentity).orElse(null);
	}

	@Override
	public Valoracion createValoracion(Valoracion valoracion) {
		valoracion.setFechaValoracion(new Date());
		return valoracionRepository.save(valoracion);
	}

	@Override
	public Valoracion updateValoracion(Valoracion valoracion) {
		Valoracion valoracionDB = valoracionRepository.getById(valoracion.getValoracionIdentity());
		if(valoracionDB == null) {
			return null;
		}
		valoracionDB.setComentario(valoracion.getComentario());
		valoracionDB.setPuntuacion(valoracion.getPuntuacion());
		valoracionDB.setFechaValoracion(new Date());
		return valoracionRepository.save(valoracionDB);
	}

	@Override
	public Valoracion deleteValoracion(ValoracionIdentity valoracionIdentity) {
		Valoracion valoracionDB = valoracionRepository.getById(valoracionIdentity);
		if(valoracionDB == null) {
			return null;
		}
		valoracionRepository.delete(valoracionDB);
		return valoracionDB;
	}

	@Override
	public List<Valoracion> findByIdPublicacion(Long idPublicacion) {
		List<Valoracion> valoracionesDB = valoracionRepository.findByValoracionIdentityIdPublicacion(idPublicacion);
		if(valoracionesDB.isEmpty()) {
			return null;
		}
		return valoracionesDB;
	}

	@Override
	public float getPuntuacionMediaByUsuario(String usuario) {
		
		List<PublicacionValoracionDTO> listaPublicaciones = publicationClient.listPublicacionesId(usuario).getBody();
		
		if(listaPublicaciones == null)
			return 0.0f;
		
		List<Long> listaIdsPub = new ArrayList<Long>();
		for (int i = 0; i < listaPublicaciones.size(); i++) {
			listaIdsPub.add(listaPublicaciones.get(i).getId());
		}
		List<Integer> listaVal = valoracionRepository.getPuntuacionByValoracionIdentityIdPublicacion(listaIdsPub);
		if(listaVal.isEmpty()) {
			return 0.0f;
		}
		
		float acumulado = 0;
		for (int i = 0; i < listaVal.size(); i++) {
			acumulado += listaVal.get(i);
		}
		
		return (acumulado / listaVal.size());
	}

	@Override
	public List<Valoracion> listValoracionesByUsuario(String usuario) {
		
		List<PublicacionValoracionDTO> listaPublicaciones = publicationClient.listPublicacionesId(usuario).getBody();
		
		if(listaPublicaciones == null)
			return null;
		
		List<Long> listaIdsPub = new ArrayList<Long>();
		for (int i = 0; i < listaPublicaciones.size(); i++) {
			listaIdsPub.add(listaPublicaciones.get(i).getId());
		}
		
		List<Valoracion> listaVal = valoracionRepository.getValoracionByValoracionIdentityIdPublicacion(listaIdsPub);
		
		if(listaVal.isEmpty()) {
			return null;
		}
		return listaVal;
	}

	//@Override
	//public List<Integer> getPuntuacionByValoracionIdentityIdPublicacion(List<Long> listaIdPublicaciones) {
		//List<Integer> puntuaciones = valoracionRepository.getPuntuacionByValoracionIdentityIdPublicacion(listaIdPublicaciones);
		//if(puntuaciones.isEmpty()) {
			//return null;
		//}
		//return puntuaciones;
	//}

}
