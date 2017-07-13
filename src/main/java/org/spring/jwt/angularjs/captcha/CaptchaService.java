package org.spring.jwt.angularjs.captcha;

import org.spring.jwt.angularjs.exception.ReCaptchaInvalidException;

public interface CaptchaService {
    void processResponse(final String response) throws ReCaptchaInvalidException;
    String getReCaptchaSite();
    String getReCaptchaSecret();
}
