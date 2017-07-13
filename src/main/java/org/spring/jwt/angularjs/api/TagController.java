package org.spring.jwt.angularjs.api;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.spring.jwt.angularjs.constant.URIConstant;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.Tag;
import org.spring.jwt.angularjs.request.TagRequest;
import org.spring.jwt.angularjs.service.ITagService;
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
public class TagController {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ITagService tagService;

    /**
     * get one tag
     * @param id
     * @return tag
     * @throws JsonProcessingException 
     * @throws ServiceException 
     */
    @GetMapping(value = {URIConstant.TAGS_GET_BY_ID, URIConstant.ADMIN_FIND_TAG})
    public ResponseEntity<?> getTag(@PathVariable Long id) throws JsonProcessingException, ServiceException {
    	ObjectWriter viewWriter = CommonUtils.getObjectWriter(mapper);
        Tag tag = tagService.findOne(id);
    	if (tag == null) return ResponseEntity.ok().body("can not find tag");
    	return ResponseEntity.ok(viewWriter.writeValueAsString(tag));
    }
    
    /**
     * find all tag
     * @param request
     * @return
     * @throws JsonProcessingException 
     * @throws ServiceException 
     */
    @GetMapping(value = {URIConstant.TAGS, URIConstant.ADMIN_TAGS})
    public ResponseEntity<?> getTags(HttpServletRequest request) throws JsonProcessingException, ServiceException {
        ObjectWriter viewWriter = CommonUtils.getObjectWriter(mapper);
        List<Tag> tags = tagService.findAll();
        return ResponseEntity.ok(viewWriter.writeValueAsString(tags));
    }
    
    /**
     * create tag
     * @param tag
     * @return
     * @throws ServiceException 
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = {URIConstant.ADMIN_TAGS})
    public ResponseEntity<?> createTag(@Valid @RequestBody TagRequest tagReq) throws ServiceException {
    	Tag tag = tagService.createTag(tagReq);
    	return ResponseEntity.ok(tag);
    }
    
    /**
     * update tag
     * @param id
     * @param tag
     * @param request
     * @return
     * @throws ServiceException 
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = {URIConstant.ADMIN_TAGS})
    public ResponseEntity<?> updateTag(@RequestBody TagRequest tag) throws ServiceException {
		tagService.updateTag(tag);
    	return ResponseEntity.ok(null);
    }
    
    /**
     * delete tag
     * @param id
     * @return
     * @throws ServiceException 
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = {URIConstant.ADMIN_DELETE_TAGS})
    public ResponseEntity<?> deleteTag(@PathVariable Long id, HttpServletRequest request) throws ServiceException {
    	tagService.deleteTag(id);
    	return ResponseEntity.ok(null);
    }
}

 
