package org.spring.jwt.angularjs.service.impl;

import org.spring.jwt.angularjs.model.PostDetail;
import org.spring.jwt.angularjs.service.IPostDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostDetailService extends GenericService<PostDetail, Long> implements IPostDetailService {
	
}
