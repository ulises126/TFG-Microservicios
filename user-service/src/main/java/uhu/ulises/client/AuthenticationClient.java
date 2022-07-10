package uhu.ulises.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import uhu.ulises.dto.AuthUser;

@FeignClient(name = "auth-service", fallback = AuthenticationHystrixFallbackFactory.class)
public interface AuthenticationClient {

	@PostMapping(value = "/auth/create")
	public ResponseEntity<AuthUser> createAuthUser(@RequestBody AuthUser dto);
}
