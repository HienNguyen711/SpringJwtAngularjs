package org.spring.jwt.angularjs.repository;

import org.spring.jwt.angularjs.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Long> {
}
