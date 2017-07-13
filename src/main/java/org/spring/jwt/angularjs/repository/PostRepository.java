package org.spring.jwt.angularjs.repository;

import org.spring.jwt.angularjs.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
}
