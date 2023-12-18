package com.openclassrooms.mddapi.exceptions;

public class SubscriptionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SubscriptionNotFoundException(String s) {
		super(s);
	}
}