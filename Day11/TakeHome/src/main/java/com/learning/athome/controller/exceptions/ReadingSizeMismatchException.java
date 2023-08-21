package com.learning.athome.controller.exceptions;

public class ReadingSizeMismatchException extends ReadingException {

	private static final long serialVersionUID = -8400298464997605706L;

	public ReadingSizeMismatchException(String message) {
		super(message);
	}

}