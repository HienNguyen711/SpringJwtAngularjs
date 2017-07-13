package org.spring.jwt.angularjs.advice;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.security.SignatureException;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.spring.jwt.angularjs.dto.GenericResponse;
import org.spring.jwt.angularjs.exception.DefaultExceptionAttributes;
import org.spring.jwt.angularjs.exception.EmailExistsException;
import org.spring.jwt.angularjs.exception.EmailNotFoundException;
import org.spring.jwt.angularjs.exception.ExceptionAttributes;
import org.spring.jwt.angularjs.exception.FailedToLoginException;
import org.spring.jwt.angularjs.exception.ReCaptchaInvalidException;
import org.spring.jwt.angularjs.exception.ReCaptchaUnavailableException;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.exception.UserAlreadyExistException;
import org.spring.jwt.angularjs.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messages;

    public RestExceptionHandler() {
        super();
    }

    // API

    // 400
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("RestExceptionHandler > handleBindException: ", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getFieldErrors(), result.getGlobalErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("RestExceptionHandler > handleMethodArgumentNotValid", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getFieldErrors(), result.getGlobalErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ ReCaptchaInvalidException.class })
    public ResponseEntity<Object> handleReCaptchaInvalid(final RuntimeException ex, final WebRequest request) {
    	logger.error("RestExceptionHandler > handleReCaptchaInvalid", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.invalidReCaptcha", null, request.getLocale()), "InvalidReCaptcha");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ EmailExistsException.class })
    public ResponseEntity<Object> handleEmailExistsException(final RuntimeException ex, final WebRequest request) {
    	logger.error("RestExceptionHandler > handleEmailExistsException", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.emailExists", null, request.getLocale()), "EmailExistsException");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ EmailNotFoundException.class })
    public ResponseEntity<Object> handleEmailNotFoundException(final RuntimeException ex, final WebRequest request) {
    	logger.error("RestExceptionHandler > EmailNotFoundException", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.emailNotFound", null, request.getLocale()), "EmailNotFoundException");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    // 404
    @ExceptionHandler({UserNotFoundException.class })
    public ResponseEntity<Object> handleUserNotFound(final RuntimeException ex, final WebRequest request) {
    	logger.error("UserNotFoundException");
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.userNotFound", null, request.getLocale()), "UserNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409
    @ExceptionHandler({UserAlreadyExistException.class })
    public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        logger.error("UserAlreadyExistException");
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.regError", null, request.getLocale()), "UserAlreadyExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 500
    @ExceptionHandler({ MailAuthenticationException.class })
    public ResponseEntity<Object> handleMail(final RuntimeException ex, final WebRequest request) {
    	logger.error("MailAuthenticationException");
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.email.config.error", null, request.getLocale()), "MailError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ ReCaptchaUnavailableException.class })
    public ResponseEntity<Object> handleReCaptchaUnavailable(final RuntimeException ex, final WebRequest request) {
        logger.error("ReCaptchaUnavailableException ");
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.unavailableReCaptcha", null, request.getLocale()), "InvalidReCaptcha");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.error", null, request.getLocale()), "InternalError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String, Object>> handleNoResultException(
            NoResultException noResultException, HttpServletRequest request) {

        logger.info("> handleNoResultException");

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes
                .getExceptionAttributes(noResultException, request,
                        HttpStatus.NOT_FOUND);

        logger.info("< handleNoResultException");
        return new ResponseEntity<Map<String, Object>>(responseBody,
                HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest request) {

        logger.error("> handleException");
        logger.error("- Exception: ", exception);

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes
                .getExceptionAttributes(exception, request,
                        HttpStatus.INTERNAL_SERVER_ERROR);

        logger.error("< handleException");
        return new ResponseEntity<Map<String, Object>>(responseBody,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(FailedToLoginException.class)
    public void failedToLogin() {}

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(SignatureException.class)
    public void failedToVerify() {
        System.out.println("");
    }
}
