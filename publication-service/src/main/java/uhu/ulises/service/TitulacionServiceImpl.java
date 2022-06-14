package uhu.ulises.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uhu.ulises.entity.Titulacion;
import uhu.ulises.repository.TitulacionRepository;

@Service
public class TitulacionServiceImpl implements TitulacionService {

	@Autowired
	private TitulacionRepository titulacionRepository;
	
	@Override
	public List<Titulacion> listAllTitulacion() {
		return titulacionRepository.findAll();
	}

	@Override
	public Titulacion getTitulacion(Long id) {
		return titulacionRepository.findById(id).orElse(null);
	}

	@Override
	public Titulacion createTitulacion(Titulacion titulacion) {
		return titulacionRepository.save(titulacion);
	}

	@Override
	public Titulacion updateTitulacion(Titulacion titulacion) {
		return titulacionRepository.save(titulacion);
	}

	@Override
	public Titulacion deleteTitulacion(Long id) {
		Titulacion titulacionBD = this.getTitulacion(id);
		if(titulacionBD == null) {
			return null;
		}
		titulacionRepository.delete(titulacionBD);
		return titulacionBD;
	}

}
