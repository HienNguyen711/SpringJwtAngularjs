package org.spring.jwt.angularjs.exception;

public class EmailExistsException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2918421925296437470L;

	public EmailExistsException() {
        super();
    }

    public EmailExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmailExistsException(final String message) {
        super(message);
    }

    public EmailExistsException(final Throwable cause) {
        super(cause);
    }
    
}
