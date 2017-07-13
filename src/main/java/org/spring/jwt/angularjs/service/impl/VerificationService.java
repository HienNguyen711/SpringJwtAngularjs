package org.spring.jwt.angularjs.service.impl;

import javax.transaction.Transactional;

import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.VerificationToken;
import org.spring.jwt.angularjs.repository.VerificationTokenRepository;
import org.spring.jwt.angularjs.service.IVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author whois
 *
 */
@Service
@Transactional
public class VerificationService extends GenericService<VerificationToken, Long> implements IVerificationTokenService {
	/**
	 * The Spring Data repository for User entities.
	 */
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Override
	public VerificationToken findByToken(String token) throws ServiceException {
		try {
			return verificationTokenRepository.findByToken(token);
		} catch (Exception e) {
			throw new ServiceException("VerificationService --> findByToken()", e);
		}
	}
}
