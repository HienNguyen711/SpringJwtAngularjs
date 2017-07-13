package org.spring.jwt.angularjs.repository;

import org.spring.jwt.angularjs.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	PasswordResetToken findByToken(String token);
}
