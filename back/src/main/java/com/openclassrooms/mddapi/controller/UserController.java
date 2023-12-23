package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.payload.TokenResponse;
import com.openclassrooms.mddapi.payload.UpdateUserRequest;
import com.openclassrooms.mddapi.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@PutMapping("")
	public ResponseEntity<TokenResponse> editUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
		return new ResponseEntity<>(userService.updateUser(updateUserRequest), HttpStatus.OK);

	}

}
