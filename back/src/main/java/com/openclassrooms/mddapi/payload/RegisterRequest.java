package com.openclassrooms.mddapi.payload;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegisterRequest {

	@Length(min = 3, max = 20)
	@NotBlank
	private String username;

	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Length(min = 8, max = 20)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=\\*])(?!\\s).{8,20}$", message = "Le mot de passe doit contenir au moins 8 caractères et au moins un chiffre, une lettre majuscule, une lettre minuscule et un caractère spécial.")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}