package org.spring.jwt.angularjs.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.spring.jwt.angularjs.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenValidator implements ConstraintValidator<TokenValid, String> {
    @Autowired
	private TokenUtil jwtTokenUtil;
    
    @Override
    public void initialize(final TokenValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String token, final ConstraintValidatorContext context) {
        return (validateToken(token));
    }

    private boolean validateToken(final String token) {
    	return jwtTokenUtil.isTokenExpired(token);
    }
}
