package uhu.ulises.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValorationHystrixFallbackFactory implements ValorationClient{

	@Override
	public ResponseEntity<Float> getMediaUsuario(String usuario) {
		return ResponseEntity.ok(-(Float)1.0f);
	}

}
