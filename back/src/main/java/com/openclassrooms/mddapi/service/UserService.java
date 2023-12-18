package com.openclassrooms.mddapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exceptions.TokenException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.LoginRequest;
import com.openclassrooms.mddapi.payload.RegisterRequest;
import com.openclassrooms.mddapi.payload.TokenResponse;
import com.openclassrooms.mddapi.payload.UpdateUserRequest;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

@Service
public class UserService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	public TokenResponse registerUser(RegisterRequest registerRequest) {

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new TokenException("L'email est déjà utilisé.");
		}

		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			throw new TokenException("Le nom d’utilisateur est déjà utilisé.");
		}

		User user = new User();

		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encoder.encode(registerRequest.getPassword()));

		userRepository.save(user);

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmailOrUsername(registerRequest.getEmail());
		loginRequest.setPassword(registerRequest.getPassword());

		return loginUser(loginRequest);
	}

	public TokenResponse loginUser(LoginRequest loginRequest) {

		try {
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginRequest.getEmailOrUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(auth);

			String jwt = jwtUtils.generateJwtToken(auth);

			return new TokenResponse(jwt);

		} catch (AuthenticationException e) {
			throw new TokenException("");
		}

	}

	public User getMyInformation() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String email = userDetails.getEmail();

		Optional<User> user = userRepository.findByEmail(email);

		return user.orElseThrow(() -> new UserNotFoundException("No User Found"));

	}

	public User getUser(final Long id) {

		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new UserNotFoundException("No User Found"));

	}

	public User editUser(final Long id) {

		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new UserNotFoundException("No User Found"));

	}

	public TokenResponse updateUser(UpdateUserRequest updateUserRequest) {

		if (userRepository.existsByEmail(updateUserRequest.getEmail())) {
			throw new TokenException("L'email est déjà utilisé.");
		}

		if (userRepository.existsByUsername(updateUserRequest.getUsername())) {
			throw new TokenException("Le nom d’utilisateur est déjà utilisé.");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String email = userDetails.getEmail();

		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isPresent()) {

			User user = optionalUser.get();

			user.setUsername(updateUserRequest.getUsername());
			user.setEmail(updateUserRequest.getEmail());

			userRepository.save(user);

			String jwt = jwtUtils.generateJwtTokenAfterUpdateUser(user.getEmail());

			return new TokenResponse(jwt);

		} else

		{
			throw new TokenException("");
		}

	}

}