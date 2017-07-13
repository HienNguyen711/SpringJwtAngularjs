package org.spring.jwt.angularjs.service;

import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.Tag;
import org.spring.jwt.angularjs.request.TagRequest;

public interface ITagService extends IGenericService<Tag, Long> {
	Tag createTag(TagRequest tag) throws ServiceException;
    void updateTag(TagRequest tag) throws ServiceException;
    void deleteTag(Long id) throws ServiceException;
}
