package com.openclassrooms.mddapi.payload;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {


	@NotBlank
	private String emailOrUsername;

	@Length(min = 8, max = 20)
	@NotBlank
	private String password;

	public String getEmailOrUsername() {
		return emailOrUsername;
	}

	public void setEmailOrUsername(String emailOrUsername) {
		this.emailOrUsername = emailOrUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
