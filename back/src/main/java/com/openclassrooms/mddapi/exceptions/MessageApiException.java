package com.openclassrooms.mddapi.exceptions;

public class MessageApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MessageApiException(String s) {
		super(s);
	}
}
