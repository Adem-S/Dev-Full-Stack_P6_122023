
package com.openclassrooms.mddapi.exceptions;

public class PostNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PostNotFoundException(String s) {
		super(s);
	}
}
