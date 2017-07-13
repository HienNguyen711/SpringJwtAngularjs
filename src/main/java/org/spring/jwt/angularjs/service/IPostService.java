package org.spring.jwt.angularjs.service;

import java.util.List;

import org.spring.jwt.angularjs.dto.PostDto;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.Post;
import org.spring.jwt.angularjs.request.PostRequest;

public interface IPostService extends IGenericService<Post, Long> {
	PostDto getPostDetail(Long postId) throws ServiceException;
    Long createPost(PostRequest post) throws ServiceException;
    void updatePost(Long id, PostRequest post) throws ServiceException;
    void deletePost(Long id) throws ServiceException;
    List<PostDto> findAllPost(boolean isManage) throws ServiceException;
}
