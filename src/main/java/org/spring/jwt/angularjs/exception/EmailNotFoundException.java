package org.spring.jwt.angularjs.exception;

public class EmailNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2471475687159834840L;

	public EmailNotFoundException() {
        super();
    }

    public EmailNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmailNotFoundException(final String message) {
        super(message);
    }

    public EmailNotFoundException(final Throwable cause) {
        super(cause);
    }
}

