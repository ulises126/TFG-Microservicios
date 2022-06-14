package uhu.ulises.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ListaIdPublicacionesDTO {

	private List<Long> listaIdPublicaciones;
	
	public ListaIdPublicacionesDTO() {
		listaIdPublicaciones = new ArrayList<Long>();
	}
}
