package com.openclassrooms.mddapi.payload;

public class MessageApiResponse {
	private String message;

	public MessageApiResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
