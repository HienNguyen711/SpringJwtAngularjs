package org.spring.jwt.angularjs.service;

import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.Authority;
import org.spring.jwt.angularjs.model.AuthorityName;

public interface IAuthorityService extends IGenericService<Authority, Long> {
	Authority findByName(AuthorityName name) throws ServiceException;
}
