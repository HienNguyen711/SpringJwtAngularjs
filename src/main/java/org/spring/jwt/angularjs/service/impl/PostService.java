package org.spring.jwt.angularjs.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.spring.jwt.angularjs.constant.AuthoritiesConstants;
import org.spring.jwt.angularjs.dto.PostDto;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.Post;
import org.spring.jwt.angularjs.model.PostDetail;
import org.spring.jwt.angularjs.model.User;
import org.spring.jwt.angularjs.repository.PostDetailRepository;
import org.spring.jwt.angularjs.repository.PostRepository;
import org.spring.jwt.angularjs.repository.UserRepository;
import org.spring.jwt.angularjs.request.PostRequest;
import org.spring.jwt.angularjs.service.IPostService;
import org.spring.jwt.angularjs.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;

@Service
@Transactional
public class PostService extends GenericService<Post, Long> implements IPostService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private PostDetailRepository postDetailRepository;
	
	@Override
	public PostDto getPostDetail(Long postId) throws ServiceException {
		try {
			PostDetail postDetail = entityManager.find(PostDetail.class, postId);
			if(postDetail == null) throw new NotFoundException("Post is not found!!!");
			
			Post post = postDetail.getPost();
			PostDto postDto = mapper.map(post, PostDto.class);
			postDto.setContent(postDetail.getContent());
			postDto.setCreateBy(post.getUser().getFullName());
			return postDto;
		} catch (Exception e) {
			throw new ServiceException("PostService --> getPostDetail()", e);
		}
	}
	
	@Override
	public Long createPost(PostRequest postReq) throws ServiceException {
		try {
			String userName = SecurityUtils.getCurrentUserLogin();
	        User user = userRepository.findByUserName(userName);
	        
	        Post post = mapper.map(postReq, Post.class);
	        post.setCreateAt(new Date());
	        post.setUser(user);
	        post.setVisible(false);
	        post.setVisited(0);
	        postRepository.save(post);
	        
	        PostDetail postDetail = new PostDetail();
	        postDetail.setContent(postReq.getContent());
	        postDetail.setPost(post);
	        
	        postDetailRepository.save(postDetail);
	        
	        return post.getId();
		} catch (Exception e) {
			throw new ServiceException("PostService --> createPost()", e);
		}
	}

	@Override
	public void updatePost(Long id, PostRequest post) throws ServiceException {
		try {
			PostDetail oldPostDetail = postDetailRepository.findOne(id);
	    	if (oldPostDetail == null) throw new NotFoundException("Post is not found!!!");
	    	
	    	oldPostDetail.setContent(post.getContent());
	    	
	    	Post oldPost = oldPostDetail.getPost();
	    	oldPost.setTitle(post.getTitle());
	    	oldPost.setDescription(post.getDescription());
	    	oldPost.setLogoUrl(post.getLogoUrl());
	    	
	    	postRepository.save(oldPost);
		} catch (Exception e) {
			throw new ServiceException("PostService --> updatePost()", e);
		}
	}

	@Override
	public void deletePost(Long id) throws ServiceException {
		try {
			if(!SecurityUtils.isAuthenticated()) {
				throw new AccessDeniedException("Access deniel");
	    	} else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
	    		postRepository.delete(id);
	    	} else {
	    		Post post = postRepository.findOne(id);
	    		if (post == null || !post.getUser().getUserName().equals(SecurityUtils.getCurrentUserLogin())) {
	    			throw new AccessDeniedException("Access deniel");
	    		}
	    		postRepository.delete(id);
	    	}
		} catch (Exception e) {
			throw new ServiceException("PostService --> deletePost()", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDto> findAllPost(boolean isManage) throws ServiceException {
		try {
			
			Session currentSession = (Session) entityManager.getDelegate();
			
			StringBuilder sb = new StringBuilder();
			sb.append("Select p.id, p.title, p.description, ");
			sb.append("p.logo_url logoUrl, p.create_at createAt, ");
			sb.append("u.username createBy, p.visible visible, p.visited ");
			sb.append("From Post p ");
			sb.append("Left Join User u ");
			sb.append("On p.create_by = u.id ");
			
			// if is user get post belong to this user
			if(isManage && !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
				sb.append("Where u.username = :username");
			}
			
			Query q = currentSession.createSQLQuery(sb.toString())
			.addScalar("id", StandardBasicTypes.LONG)
			.addScalar("title", StandardBasicTypes.STRING)
			.addScalar("description", StandardBasicTypes.STRING)
			.addScalar("logoUrl", StandardBasicTypes.STRING)
			.addScalar("createAt", StandardBasicTypes.DATE)
			.addScalar("createBy", StandardBasicTypes.STRING)
			.addScalar("visible", StandardBasicTypes.BOOLEAN)
			.addScalar("visited", StandardBasicTypes.STRING)
			.setResultTransformer(Transformers.aliasToBean(PostDto.class));
			
			if(isManage && !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
				String userName = SecurityUtils.getCurrentUserLogin();
				q.setParameter("username", userName);
			}
			
			return q.list();
		} catch (Exception e) {
			throw new ServiceException("PostService --> findAllPost()", e);
		}
	}
}
