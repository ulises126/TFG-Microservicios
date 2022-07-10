package uhu.ulises.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "valoration-service", fallback = ValorationHystrixFallbackFactory.class)
public interface ValorationClient {

	@GetMapping(value = "valoraciones/{usuario}/media")
	public ResponseEntity<Float> getMediaUsuario(@PathVariable(value = "usuario") String usuario);
}
