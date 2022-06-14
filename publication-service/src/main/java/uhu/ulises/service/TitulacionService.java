package uhu.ulises.service;

import java.util.List;

import uhu.ulises.entity.Titulacion;

public interface TitulacionService {
	public List<Titulacion> listAllTitulacion();
	public Titulacion getTitulacion(Long id);
	public Titulacion createTitulacion(Titulacion titulacion);
	public Titulacion updateTitulacion(Titulacion titulacion);
	public Titulacion deleteTitulacion(Long id);
}
