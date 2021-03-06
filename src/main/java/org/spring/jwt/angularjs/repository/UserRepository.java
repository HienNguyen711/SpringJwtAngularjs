package org.spring.jwt.angularjs.repository;

import org.spring.jwt.angularjs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserName(String userName);
	User findByEmail(String email);
}
