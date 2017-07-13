package org.spring.jwt.angularjs.api;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.spring.jwt.angularjs.constant.URIConstant;
import org.spring.jwt.angularjs.dto.PostDto;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.request.PostRequest;
import org.spring.jwt.angularjs.service.IPostService;
import org.spring.jwt.angularjs.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.swagger.annotations.Api;

@Api
@RestController
public class PostController {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private IPostService postService;

    /**
     * get one post
     * @param id
     * @return post
     * @throws JsonProcessingException 
     * @throws ServiceException 
     */
    @GetMapping(value = {URIConstant.POSTS_GET_BY_ID, URIConstant.ADMIN_FIND_POST})
    public ResponseEntity<?> getPost(@PathVariable Long id) throws JsonProcessingException, ServiceException {
    	ObjectWriter viewWriter = CommonUtils.getObjectWriter(mapper);
        PostDto postDetail = postService.getPostDetail(id);
    	if (postDetail == null) return ResponseEntity.ok().body("can not find post");
    	return ResponseEntity.ok(viewWriter.writeValueAsString(postDetail));
    }
    
    /**
     * find all post
     * @param request
     * @return
     * @throws JsonProcessingException 
     * @throws ServiceException 
     */
    @GetMapping(value = {URIConstant.POSTS})
    public ResponseEntity<?> getPosts(HttpServletRequest request) throws JsonProcessingException, ServiceException {
        ObjectWriter viewWriter = CommonUtils.getObjectWriter(mapper);
        List<PostDto> listPost = postService.findAllPost(false);
        return ResponseEntity.ok(viewWriter.writeValueAsString(listPost));
    }
    
    
    /**
     * find manage posts
     * @param request
     * @return
     * @throws JsonProcessingException
     * @throws ServiceException
     */
    @GetMapping(value = {URIConstant.ADMIN_POSTS})
    public ResponseEntity<?> getManagePosts(HttpServletRequest request) throws JsonProcessingException, ServiceException {
        ObjectWriter viewWriter = CommonUtils.getObjectWriter(mapper);
        List<PostDto> listPost = postService.findAllPost(true);
        return ResponseEntity.ok(viewWriter.writeValueAsString(listPost));
    }
    
    /**
     * create post
     * @param post
     * @return
     * @throws ServiceException 
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = {URIConstant.ADMIN_POSTS})
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest post) throws ServiceException {
    	Long postId = postService.createPost(post);
    	return ResponseEntity.ok(postId);
    }
    
    /**
     * update post
     * @param id
     * @param post
     * @param request
     * @return
     * @throws ServiceException 
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = {URIConstant.ADMIN_UPDATE_POSTS})
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequest post) throws ServiceException {
    	postService.updatePost(id, post);
    	return ResponseEntity.ok(null);
    }
    
    /**
     * delete post
     * @param id
     * @return
     * @throws ServiceException 
     */
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = {URIConstant.ADMIN_DELETE_POSTS})
    public ResponseEntity<?> deletePost(@PathVariable Long id, HttpServletRequest request) throws ServiceException {
    	postService.deletePost(id);
    	return ResponseEntity.ok(null);
    }
}

 
