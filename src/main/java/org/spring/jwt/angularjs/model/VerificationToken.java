package org.spring.jwt.angularjs.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.spring.jwt.angularjs.constant.SystemConstant;
import org.spring.jwt.angularjs.util.CommonUtils;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    private Long id;

    private String token;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public VerificationToken() {
    	super();
    }
    
    public VerificationToken(User user, String token) {
    	super();
        this.token = token;
        this.user = user;
        this.expiry = CommonUtils.calculateExpiryDate(SystemConstant.EXPIRY_DATE);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	
	public boolean isExpired() {
        if (null == this.expiry) {
            return false;
        }

        return this.expiry.getTime() < System.currentTimeMillis();
    }
}
