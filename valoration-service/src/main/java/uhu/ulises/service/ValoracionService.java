package uhu.ulises.service;

import java.util.List;

import uhu.ulises.entity.Valoracion;
import uhu.ulises.entity.ValoracionIdentity;

public interface ValoracionService {

	public List<Valoracion> listAllValoracion();
	public Valoracion getValoracion(ValoracionIdentity valoracionIdentity);
	public Valoracion createValoracion(Valoracion valoracion);
	public Valoracion updateValoracion(Valoracion valoracion);
	public Valoracion deleteValoracion(ValoracionIdentity valoracionIdentity);
	public List<Valoracion> findByIdPublicacion(Long idPublicacion);
	//public List<Integer> getPuntuacionByValoracionIdentityIdPublicacion(List<Long> listaIdPublicaciones);
	public float getPuntuacionMediaByUsuario(String usuario);
	public List<Valoracion> listValoracionesByUsuario(String usuario);
}
