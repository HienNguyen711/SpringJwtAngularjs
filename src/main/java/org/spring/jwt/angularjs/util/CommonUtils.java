package org.spring.jwt.angularjs.util;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.spring.jwt.angularjs.constant.AuthoritiesConstants;
import org.spring.jwt.angularjs.views.Views;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * 
 * @author ASUS
 *
 */
public class CommonUtils {
	/**
	 * get base url
	 * @param httpRequest
	 * @return String
	 */
	public static String getBaseUrl(HttpServletRequest httpRequest) {
		if (httpRequest == null) return StringUtils.EMPTY;
		try {
			return httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort() + httpRequest.getContextPath();
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * calculate expire time minute
	 * @param expiryTimeInMinutes
	 * @return
	 */
	public static Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
	
	/**
	 * get object writer by view role
	 * @param mapper
	 * @return
	 */
	public static ObjectWriter getObjectWriter(ObjectMapper mapper) {
		ObjectWriter viewWriter;
	    if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
	        viewWriter = mapper.writerWithView(Views.Admin.class);
	    } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)){
	        viewWriter = mapper.writerWithView(Views.User.class);
	    } else {
	    	viewWriter = mapper.writerWithView(Views.Anonymous.class);
	    }
	    return viewWriter;
	}
	
}
