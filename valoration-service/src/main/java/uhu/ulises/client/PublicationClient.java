package uhu.ulises.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import uhu.ulises.model.PublicacionValoracionDTO;

@FeignClient(name = "publication-service", fallback = PublicationHystrixFallbackFactory.class)
public interface PublicationClient {

	@GetMapping(value = "/publicaciones/publicacionesid/{usuario}")
	public ResponseEntity<List<PublicacionValoracionDTO>> listPublicacionesId(@PathVariable("usuario") String usuario);
}
