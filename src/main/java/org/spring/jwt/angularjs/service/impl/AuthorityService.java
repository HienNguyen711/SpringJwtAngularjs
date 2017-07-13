package org.spring.jwt.angularjs.service.impl;

import javax.transaction.Transactional;

import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.Authority;
import org.spring.jwt.angularjs.model.AuthorityName;
import org.spring.jwt.angularjs.repository.AuthorityRepository;
import org.spring.jwt.angularjs.service.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ASUS
 *
 */
@Service
@Transactional
public class AuthorityService extends GenericService<Authority, Long> implements IAuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Authority findByName(AuthorityName name) throws ServiceException {
		try {
			return authorityRepository.findByName(name);
		} catch (Exception e) {
			throw new ServiceException("AuthorityService --> findByName()", e);
		}
	}
}
