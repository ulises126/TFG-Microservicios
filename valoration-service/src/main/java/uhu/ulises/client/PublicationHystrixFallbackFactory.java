package uhu.ulises.client;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import uhu.ulises.model.PublicacionValoracionDTO;

@Component
public class PublicationHystrixFallbackFactory implements PublicationClient{

	@Override
	public ResponseEntity<List<PublicacionValoracionDTO>> listPublicacionesId(String usuario) {
		return ResponseEntity.noContent().build();
	}

}
