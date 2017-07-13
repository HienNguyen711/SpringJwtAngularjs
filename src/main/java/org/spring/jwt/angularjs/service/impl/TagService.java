package org.spring.jwt.angularjs.service.impl;

import org.spring.jwt.angularjs.constant.AuthoritiesConstants;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.Tag;
import org.spring.jwt.angularjs.repository.TagRepository;
import org.spring.jwt.angularjs.request.TagRequest;
import org.spring.jwt.angularjs.service.ITagService;
import org.spring.jwt.angularjs.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;

@Service
@Transactional
public class TagService extends GenericService<Tag, Long> implements ITagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	@Override
	public Tag createTag(TagRequest tagReq) throws ServiceException {
		try {
			if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
				throw new AccessDeniedException("Access deniel");
			}
	        Tag tag = new Tag();
	        tag.setName(tagReq.getName());
	        tag.setVisible(tagReq.getVisible());
	        tagRepository.save(tag);
	        
	        return tag;
		} catch (Exception e) {
			throw new ServiceException("TagService --> createTag()", e);
		}
	}

	@Override
	public void updateTag(TagRequest tag) throws ServiceException {
		try {
			if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
				throw new AccessDeniedException("Access deniel");
			}
			final Tag oldTag = tagRepository.findOne(tag.getId());
	    	if (oldTag == null) throw new NotFoundException("Tag is not found!!!");
	    	oldTag.setName(tag.getName());
	    	oldTag.setVisible(tag.getVisible());
	    	tagRepository.save(oldTag);
		} catch (Exception e) {
			throw new ServiceException("TagService --> updatePost()", e);
		}
	}

	@Override
	public void deleteTag(Long id) throws ServiceException {
		try {
			if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
				throw new AccessDeniedException("Access deniel");
			}
			
			tagRepository.delete(id);
		} catch (Exception e) {
			throw new ServiceException("TagService --> deleteTag()", e);
		}
	}
}
