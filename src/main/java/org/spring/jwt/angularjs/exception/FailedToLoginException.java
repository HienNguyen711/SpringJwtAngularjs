package org.spring.jwt.angularjs.exception;

import static java.lang.String.format;

public class FailedToLoginException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FailedToLoginException(String username) {
        super(format("Failed to login with username %s", username));
    }
}
