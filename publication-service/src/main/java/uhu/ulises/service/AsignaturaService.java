package uhu.ulises.service;

import java.util.List;

import uhu.ulises.entity.Asignatura;

public interface AsignaturaService {
	public List<Asignatura> listAllAsignatura();
	public Asignatura getAsignatura(Long id);
	public Asignatura createAsignatura(Asignatura asignatura);
	public Asignatura updateAsignatura(Asignatura asignatura);
	public Asignatura deleteAsignatura(Long id);
	public List<Asignatura> listAllAsignaturasByTitulacionAndCurso(Long titulacion, int curso);
}
