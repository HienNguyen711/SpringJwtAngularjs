package org.spring.jwt.angularjs.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:captcha.properties")
public class CaptchaSettings {
	@Value("${google.recaptcha.site-key}")
    private String site;
	@Value("${google.recaptcha.secret-key}")
    private String secret;

    public CaptchaSettings() {}

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
