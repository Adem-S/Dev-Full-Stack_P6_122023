package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.LoginRequest;
import com.openclassrooms.mddapi.payload.RegisterRequest;
import com.openclassrooms.mddapi.payload.TokenResponse;
import com.openclassrooms.mddapi.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<TokenResponse> addNewUser(@Valid @RequestBody RegisterRequest registerRequest) {
		return new ResponseEntity<>(userService.registerUser(registerRequest), HttpStatus.OK);

	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);

	}

	@GetMapping("/me")
	public ResponseEntity<User> getMyInformation() {
		return new ResponseEntity<>(userService.getMyInformation(), HttpStatus.OK);
	}

}
