package org.spring.jwt.angularjs.service;

import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.VerificationToken;

/**
 * 
 * @author ASUS
 *
 */
public interface IVerificationTokenService extends IGenericService<VerificationToken, Long> {
	VerificationToken findByToken(String token) throws ServiceException;
}
