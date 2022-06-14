package uhu.ulises.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uhu.ulises.entity.Asignatura;
import uhu.ulises.entity.Titulacion;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long>{

	public List<Asignatura> findAllByTitulacionAndCurso(Titulacion titulacion, int curso);
}
