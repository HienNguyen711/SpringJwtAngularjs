package org.spring.jwt.angularjs.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.spring.jwt.angularjs.constant.SystemConstant;
import org.spring.jwt.angularjs.filter.JwtUser;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SuppressWarnings("serial")
@Component
public class TokenUtil implements Serializable {
	
	/**
	 * get username from token
	 * @param token
	 * @return username
	 */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * get created date from token
     * @param token
     * @return created date
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(SystemConstant.CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * get expiration date from token
     * @param token
     * @return expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * get audience from token
     * @param token
     * @return audience
     */
    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(SystemConstant.CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    /**
     * get claims from token
     * @param token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SystemConstant.SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * generate expiration date
     * @return expiration date
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + SystemConstant.EXPIRATION * 1000);
    }

    /**
     * check token is expired
     * @param token
     * @return true/false
     */
    public Boolean isTokenExpired(String token) {
    	if(StringUtils.isBlank(token)) return true;
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration == null) 
        	return false;
        else 
        	return expiration.before(new Date());
    }

    /**
     * check if create date before last password reset
     * @param created
     * @param lastPasswordReset
     * @return
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * generate audience
     * @param device
     * @return audience
     */
    private String generateAudience(Device device) {
        String audience = SystemConstant.AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = SystemConstant.AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = SystemConstant.AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = SystemConstant.AUDIENCE_MOBILE;
        }
        return audience;
    }

    /**
     * check if ignore token expiration
     * @param token
     * @return true/false
     */
    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (SystemConstant.AUDIENCE_TABLET.equals(audience) || SystemConstant.AUDIENCE_MOBILE.equals(audience));
    }

    /**
     * generate token
     * @param userName
     * @param device
     * @return
     */
    public String generateToken(UserDetails userDetail, Device device) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SystemConstant.CLAIM_KEY_USERNAME, userDetail.getUsername());
        claims.put(SystemConstant.CLAIM_KEY_ROLES, createRoleMap(userDetail));
        claims.put(SystemConstant.CLAIM_KEY_AUDIENCE, generateAudience(device));
        claims.put(SystemConstant.CLAIM_KEY_CREATED, new Date());
        
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SystemConstant.SECRET)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(SystemConstant.CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset()));
    }
    
    private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
        Map<String, Boolean> roles = new HashMap<String, Boolean>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.put(authority.getAuthority(), Boolean.TRUE);
        }

        return roles;
    }
}