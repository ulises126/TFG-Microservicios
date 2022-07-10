package uhu.ulises.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import uhu.ulises.dto.AuthUserDto;
import uhu.ulises.dto.TokenDto;
import uhu.ulises.entity.AuthUser;
import uhu.ulises.repository.AuthUserRepository;
import uhu.ulises.security.JwtProvider;

@Service
public class AuthUserService {

	@Autowired
	AuthUserRepository authUserRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtProvider jwtProvider;
	
	public AuthUser save(AuthUserDto dto) {
		Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());
		if(user.isPresent())
			return null;
		String password = passwordEncoder.encode(dto.getPassword());
		AuthUser authUser = AuthUser.builder()
				.username(dto.getUsername())
				.password(password)
				.build();
		return authUserRepository.save(authUser);
	}
	
	public TokenDto login(AuthUserDto dto) {
		Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());
		if(!user.isPresent())
			return null;
		if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword()))
			return new TokenDto(jwtProvider.createToken(user.get()));
		return null;
	}
	
	public TokenDto validate(String token) {
		if(!jwtProvider.validate(token))
			return null;
		String username = jwtProvider.getUsernameFromToken(token);
		if(!authUserRepository.findByUsername(username).isPresent())
			return null;
		return new TokenDto(token);
	}
}
