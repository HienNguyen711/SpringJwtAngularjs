package org.spring.jwt.angularjs.validation;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.spring.jwt.angularjs.constant.SystemConstant;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {
    private Matcher matcher;

    @Override
    public void initialize(final EmailValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return (validateEmail(username));
    }

    private boolean validateEmail(final String email) {
        matcher = SystemConstant.EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
