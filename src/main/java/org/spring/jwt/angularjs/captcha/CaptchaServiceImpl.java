package org.spring.jwt.angularjs.captcha;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.jwt.angularjs.constant.SystemConstant;
import org.spring.jwt.angularjs.constant.URIConstant;
import org.spring.jwt.angularjs.exception.ReCaptchaInvalidException;
import org.spring.jwt.angularjs.exception.ReCaptchaUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

@Service
@Transactional
public class CaptchaServiceImpl implements CaptchaService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CaptchaSettings captchaSettings;

    @Autowired
    private RestOperations restTemplate;

    @Override
    public void processResponse(final String response) {
        LOGGER.debug("Attempting to validate response {}", response);

        boolean responseSanityCheck = StringUtils.hasLength(response) && SystemConstant.RESPONSE_PATTERN.matcher(response).matches();
        if (!responseSanityCheck) {
            throw new ReCaptchaInvalidException("Response contains invalid characters");
        }

        final URI verifyUri = URI.create(String.format(URIConstant.RECAPTCHA_SITEVERIFY, getReCaptchaSecret(), response, getClientIP()));
        try {
            final GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
            LOGGER.debug("Google's response: {} ", googleResponse.toString());

            if (!googleResponse.isSuccess()) {
                throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
            }
        } catch (RestClientException rce) {
            throw new ReCaptchaUnavailableException("Registration unavailable at this time.  Please try again later.", rce);
        }
    }

    @Override
    public String getReCaptchaSite() {
        return captchaSettings.getSite();
    }

    @Override
    public String getReCaptchaSecret() {
        return captchaSettings.getSecret();
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader(SystemConstant.X_FORWARDED_FOR);
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
