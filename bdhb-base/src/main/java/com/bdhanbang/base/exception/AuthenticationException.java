package com.bdhanbang.base.exception;

public class AuthenticationException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new AuthenticationException.
	 */
	public AuthenticationException() {
		super();
	}

	/**
	 * Constructs a new AuthenticationException.
	 *
	 * @param message
	 *            the reason for the exception
	 */
	public AuthenticationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new AuthenticationException.
	 *
	 * @param cause
	 *            the underlying Throwable that caused this exception to be thrown.
	 */
	public AuthenticationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new AuthenticationException.
	 *
	 * @param message
	 *            the reason for the exception
	 * @param cause
	 *            the underlying Throwable that caused this exception to be thrown.
	 */
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
