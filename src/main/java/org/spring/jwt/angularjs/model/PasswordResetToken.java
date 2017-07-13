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
@Table(name = "password_reset_token")
public class PasswordResetToken {
    @Id
    private Long id;

    private String token;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(final User user, final String token) {
        super();
        this.user = user;
        this.token = token;
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

    public void updateToken(final String token) {
        this.token = token;
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
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PasswordResetToken other = (PasswordResetToken) obj;
        if (token == null) {
            if (other.token != null) {
                return false;
            }
        } else if (!token.equals(other.token)) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=").append(token).append("]");
        return builder.toString();
    }
}
