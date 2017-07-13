package org.spring.jwt.angularjs.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.spring.jwt.angularjs.request.SignupRequest;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final SignupRequest signupForm = (SignupRequest) obj;
        return signupForm.getPassword().equals(signupForm.getMatchingpassword());
    }

}
