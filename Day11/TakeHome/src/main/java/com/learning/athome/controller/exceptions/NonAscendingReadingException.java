package com.learning.athome.controller.exceptions;

public class NonAscendingReadingException extends ReadingException {

	private static final long serialVersionUID = 3266136373310190903L;

	public NonAscendingReadingException(String message) {
		super(message);
	}

}