package org.spring.jwt.angularjs.event;


import org.spring.jwt.angularjs.model.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.mobile.device.Device;

import java.util.Locale;

public class OnResetPasswordCompleteEvent extends ApplicationEvent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String appUrl;
    private final Locale locale;
    private final User user;
    private final Device device;

    public OnResetPasswordCompleteEvent(User user, Locale locale, String appUrl, Device device) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.device = device;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }

	public Device getDevice() {
		return device;
	}
}
