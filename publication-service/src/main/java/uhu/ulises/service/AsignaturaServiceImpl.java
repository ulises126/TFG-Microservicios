package uhu.ulises.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uhu.ulises.entity.Asignatura;
import uhu.ulises.entity.Titulacion;
import uhu.ulises.repository.AsignaturaRepository;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {

	@Autowired
	AsignaturaRepository asignaturaRepository;
	
	@Override
	public List<Asignatura> listAllAsignatura() {
		return asignaturaRepository.findAll();
	}

	@Override
	public Asignatura getAsignatura(Long id) {
		return asignaturaRepository.findById(id).orElse(null);
	}

	@Override
	public Asignatura createAsignatura(Asignatura asignatura) {
		return asignaturaRepository.save(asignatura);
	}

	@Override
	public Asignatura updateAsignatura(Asignatura asignatura) {
		return asignaturaRepository.save(asignatura);
	}

	@Override
	public Asignatura deleteAsignatura(Long id) {
		Asignatura asignaturaBD = this.getAsignatura(id);
		if(asignaturaBD == null) {
			return null;
		}
		asignaturaRepository.delete(asignaturaBD);
		return asignaturaBD;
	}

	@Override
	public List<Asignatura> listAllAsignaturasByTitulacionAndCurso(Long titulacion, int curso) {
		return asignaturaRepository.findAllByTitulacionAndCurso(Titulacion.builder().id(titulacion).build(), curso);
	}

}
