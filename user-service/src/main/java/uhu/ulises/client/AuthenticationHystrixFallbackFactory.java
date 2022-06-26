package uhu.ulises.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import uhu.ulises.dto.AuthUser;

@Component
public class AuthenticationHystrixFallbackFactory implements AuthenticationClient{

	@Override
	public ResponseEntity<AuthUser> createAuthUser(@RequestBody AuthUser dto) {
		return ResponseEntity.noContent().build();
	}

}
