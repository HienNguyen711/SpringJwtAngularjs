package org.spring.jwt.angularjs.repository;

import org.spring.jwt.angularjs.model.Authority;
import org.spring.jwt.angularjs.model.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(AuthorityName name);
}
